package com.paymennt.solanaj.api.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.fasterxml.jackson.core.type.TypeReference;
import com.paymennt.solanaj.api.rpc.types.RpcConfig;
import com.paymennt.solanaj.api.rpc.types.RpcLogsConfig;
import com.paymennt.solanaj.api.rpc.types.RpcNotificationResult;
import com.paymennt.solanaj.api.rpc.types.RpcRequest;
import com.paymennt.solanaj.api.rpc.types.RpcResponse;
import com.paymennt.solanaj.api.rpc.types.SolanaCommitment;
import com.paymennt.solanaj.api.ws.listener.NotificationEventListener;
import com.paymennt.solanaj.api.ws.listener.TransactionEventListener;
import com.paymennt.solanaj.utils.JsonUtils;

public class SolanaWebSocketClient extends WebSocketClient {

    private static SolanaWebSocketClient instance;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private Map<String, SubscriptionParams> subscriptions = new HashMap<>();
    private Map<String, Long> subscriptionIds = new HashMap<>();
    private Map<String, String> subscriptionAddressIds = new HashMap<>();
    private Map<Long, NotificationEventListener> subscriptionLinsteners = new HashMap<>();

    public SolanaWebSocketClient(URI serverURI) {
        super(serverURI);
        scheduler.scheduleAtFixedRate(() -> {
            send("ping");
        }, 20, 20, TimeUnit.SECONDS);
    }

    public static SolanaWebSocketClient getInstance(String endpoint) {
        URI endpointURI;
        URI serverURI;

        try {
            endpointURI = new URI(endpoint);
            serverURI = new URI(endpointURI.getScheme() == "https" ? "wss" : "ws" + "://" + endpointURI.getHost());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }

        if (instance == null) {
            instance = new SolanaWebSocketClient(serverURI);
            if (!instance.isOpen()) {
                instance.connect();
            }
        }

        return instance;

    }

    public void accountSubscribe(String key, NotificationEventListener listener) {
        accountSubscribe(key, SolanaCommitment.finalized, listener);
        accountSubscribe(key, SolanaCommitment.confirmed, listener);
    }

    public void accountSubscribe(String key, SolanaCommitment commitment, NotificationEventListener listener) {
        if (subscriptionAddressIds.containsKey(key))
            return;

        List<Object> params = new ArrayList<>();
        params.add(key);
        params.add(new RpcConfig(commitment, "jsonParsed"));

        RpcRequest rpcRequest = new RpcRequest("accountSubscribe", params);

        subscriptions.put(rpcRequest.getId(), new SubscriptionParams(rpcRequest, listener));
        subscriptionIds.put(rpcRequest.getId(), null);
        subscriptionAddressIds.put(key + commitment.name(), rpcRequest.getId());

        updateSubscriptions();
    }

    public void accountUnsubscribe(String key) {
        accountUnsubscribe(key, SolanaCommitment.finalized);
        accountUnsubscribe(key, SolanaCommitment.confirmed);
    }

    public void accountUnsubscribe(String key, SolanaCommitment commitment) {

        String rpcRequestId = subscriptionAddressIds.get(key + commitment.name());
        if (rpcRequestId == null)
            return;

        Long subscriptionId = subscriptionIds.get(subscriptionAddressIds.get(key + commitment.name()));
        if (subscriptionId == null)
            return;

        List<Object> params = new ArrayList<Object>();
        params.add(subscriptionId);

        RpcRequest rpcRequest = new RpcRequest("accountUnsubscribe", params);

        subscriptions.remove(rpcRequestId);
        subscriptionIds.remove(rpcRequestId);
        subscriptionAddressIds.remove(key + commitment.name());

        send(JsonUtils.encode(rpcRequest));

        updateSubscriptions();
    }

    public void signatureSubscribe(String signature, NotificationEventListener listener) {
        List<Object> params = new ArrayList<>();
        params.add(signature);

        RpcRequest rpcRequest = new RpcRequest("signatureSubscribe", params);

        subscriptions.put(rpcRequest.getId(), new SubscriptionParams(rpcRequest, listener));
        subscriptionIds.put(rpcRequest.getId(), null);
        subscriptionAddressIds.put(signature, rpcRequest.getId());

        updateSubscriptions();
    }

    public void logsSubscribe(String key, TransactionEventListener listener) {
        logsSubscribe(key, SolanaCommitment.finalized, listener);
        logsSubscribe(key, SolanaCommitment.confirmed, listener);
    }

    public void logsSubscribe(String key, SolanaCommitment commitment, TransactionEventListener listener) {
        List<Object> params = new ArrayList<>();
        params.add(new BlockSubscribe(new String[] { key }));
        params.add(new RpcLogsConfig(commitment));

        RpcRequest rpcRequest = new RpcRequest("logsSubscribe", params);

        subscriptions.put(rpcRequest.getId(), new SubscriptionParams(rpcRequest, data -> {
            @SuppressWarnings("unchecked")
            String signature = ((Map<String, Object>) data).get("signature").toString();
            listener.onTransactiEvent(signature);
        }));
        subscriptionIds.put(rpcRequest.getId(), null);
        subscriptionAddressIds.put(key + commitment.name(), rpcRequest.getId());

        updateSubscriptions();
    }

    public void logsUnsubscribe(String key) {
        logsUnsubscribe(key, SolanaCommitment.finalized);
        logsUnsubscribe(key, SolanaCommitment.confirmed);
    }

    public void logsUnsubscribe(String key, SolanaCommitment commitment) {

        String id = key;
        if (commitment != null)
            id = key + commitment.name();

        String rpcRequestId = subscriptionAddressIds.get(id);
        if (rpcRequestId == null)
            return;

        Long subscriptionId = subscriptionIds.get(subscriptionAddressIds.get(id));
        if (subscriptionId == null)
            return;

        List<Object> params = new ArrayList<Object>();
        params.add(subscriptionId);

        RpcRequest rpcRequest = new RpcRequest("accountUnsubscribe", params);

        subscriptions.remove(rpcRequestId);
        subscriptionIds.remove(rpcRequestId);
        subscriptionAddressIds.remove(id);

        send(JsonUtils.encode(rpcRequest));

        updateSubscriptions();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        updateSubscriptions();
    }

    @SuppressWarnings({ "rawtypes" })
    @Override
    public void onMessage(String message) {

        try {
            RpcResponse<Object> rpcResult = JsonUtils.getObjectMapper()//
                    .readValue(message, new TypeReference<RpcResponse<Object>>() {});

            if (rpcResult.getResult() instanceof Boolean)
                return;

            String rpcResultId = rpcResult.getId();

            if (rpcResultId != null) {
                Long subscriptionId = Long.valueOf(rpcResult.getResult().toString());
                if (subscriptionIds.containsKey(rpcResultId) && subscriptions.containsKey(rpcResultId)) {
                    subscriptionIds.put(rpcResultId, subscriptionId);
                    subscriptionLinsteners.put(subscriptionId, subscriptions.get(rpcResultId).listener);
                    subscriptions.remove(rpcResultId);
                }
            } else {
                RpcNotificationResult result = JsonUtils.decode(message, RpcNotificationResult.class);

                if (result.getParams() == null)
                    return;

                NotificationEventListener listener = subscriptionLinsteners.get(result.getParams().getSubscription());

                Map value = (Map) result.getParams().getResult().getValue();

                listener.onNotifiacationEvent(value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);

    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    private void updateSubscriptions() {

        if (!isOpen() || subscriptions.size() <= 0)
            return;

        subscriptions.values()//
                .stream()//
                .map(SubscriptionParams::getRequest)//
                .map(JsonUtils::encode)//
                .forEach(this::send);

    }

    private class SubscriptionParams {
        RpcRequest request;
        NotificationEventListener listener;

        SubscriptionParams(RpcRequest request, NotificationEventListener listener) {
            this.request = request;
            this.listener = listener;
        }

        public RpcRequest getRequest() {
            return request;
        }

    }

    public static class BlockSubscribe {
        private String[] mentions;

        public BlockSubscribe(String[] mentions) {
            super();
            this.mentions = mentions;
        }

        public String[] getMentions() {
            return mentions;
        }

        public void setMentions(String[] mentions) {
            this.mentions = mentions;
        }

    }

}

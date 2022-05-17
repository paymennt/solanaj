package com.paymennt.solanaj.api.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.fasterxml.jackson.core.type.TypeReference;
import com.paymennt.solanaj.api.rpc.types.RpcNotificationResult;
import com.paymennt.solanaj.api.rpc.types.RpcRequest;
import com.paymennt.solanaj.api.rpc.types.RpcResponse;
import com.paymennt.solanaj.api.ws.listener.NotificationEventListener;
import com.paymennt.solanaj.utils.JsonUtils;

public class SubscriptionWebSocketClient extends WebSocketClient {

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

    private static SubscriptionWebSocketClient instance;

    private Map<String, SubscriptionParams> subscriptions = new HashMap<>();
    private Map<String, Long> subscriptionIds = new HashMap<>();
    private Map<String, String> subscriptionAddressIds = new HashMap<>();
    private Map<Long, NotificationEventListener> subscriptionLinsteners = new HashMap<>();

    public static SubscriptionWebSocketClient getInstance(String endpoint) {
        URI endpointURI;
        URI serverURI;

        try {
            endpointURI = new URI(endpoint);
            serverURI = new URI(endpointURI.getScheme() == "https" ? "wss" : "ws" + "://" + endpointURI.getHost());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }

        if (instance == null) {
            instance = new SubscriptionWebSocketClient(serverURI);
        }

        if (!instance.isOpen()) {
            instance.connect();
        }

        return instance;

    }

    public SubscriptionWebSocketClient(URI serverURI) {
        super(serverURI);

    }

    public void accountSubscribe(String key, NotificationEventListener listener) {
        List<Object> params = new ArrayList<Object>();
        params.add(key);

        RpcRequest rpcRequest = new RpcRequest("accountSubscribe", params);

        subscriptions.put(rpcRequest.getId(), new SubscriptionParams(rpcRequest, listener));
        subscriptionIds.put(rpcRequest.getId(), null);
        subscriptionAddressIds.put(key, rpcRequest.getId());

        updateSubscriptions();
    }

    public void accountUnsubscribe(String key) {

        String rpcRequestId = subscriptionAddressIds.get(key);
        if (rpcRequestId == null)
            return;

        Long subscriptionId = subscriptionIds.get(subscriptionAddressIds.get(key));
        if (subscriptionId == null)
            return;

        List<Object> params = new ArrayList<Object>();
        params.add(subscriptionId);

        RpcRequest rpcRequest = new RpcRequest("accountUnsubscribe", params);

        subscriptions.remove(rpcRequestId);
        subscriptionIds.remove(rpcRequestId);
        subscriptionAddressIds.remove(key);

        send(JsonUtils.encode(rpcRequest));

        updateSubscriptions();
    }

    public void signatureSubscribe(String signature, NotificationEventListener listener) {
        List<Object> params = new ArrayList<Object>();
        params.add(signature);

        RpcRequest rpcRequest = new RpcRequest("signatureSubscribe", params);

        subscriptions.put(rpcRequest.getId(), new SubscriptionParams(rpcRequest, listener));
        subscriptionIds.put(rpcRequest.getId(), null);
        subscriptionAddressIds.put(signature, rpcRequest.getId());

        updateSubscriptions();
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

    public void programSubscribe(String key, NotificationEventListener listener) {
        List<Object> params = new ArrayList<Object>();
        params.add(new BlockSubscribe(new String[] { "E7P8yjpbjsGMLDuoK7hm43aAumyJtQ6z1VsZj4KpZ33a" }));

        RpcRequest rpcRequest = new RpcRequest("logsSubscribe", params);

        subscriptions.put(rpcRequest.getId(), new SubscriptionParams(rpcRequest, listener));
        subscriptionIds.put(rpcRequest.getId(), null);
        subscriptionAddressIds.put(key, rpcRequest.getId());

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

            Long subscriptionId = Long.valueOf(rpcResult.getResult().toString());

            String rpcResultId = rpcResult.getId();
            if (rpcResultId != null) {
                if (subscriptionIds.containsKey(rpcResultId)) {
                    subscriptionIds.put(rpcResultId, subscriptionId);
                    subscriptionLinsteners.put(subscriptionId, subscriptions.get(rpcResultId).listener);
                    subscriptions.remove(rpcResultId);
                }
            } else {
                RpcNotificationResult result = JsonUtils.decode(message, RpcNotificationResult.class);
                NotificationEventListener listener = subscriptionLinsteners.get(result.getParams().getSubscription());

                Map value = (Map) result.getParams().getResult().getValue();

                switch (result.getMethod()) {
                case "signatureNotification":
                    listener.onNotifiacationEvent(new SignatureNotification(value.get("err")));
                    break;
                case "accountNotification":
                    listener.onNotifiacationEvent(value);
                    break;
                }
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

}

/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
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
import com.paymennt.solanaj.api.rpc.Cluster;
import com.paymennt.solanaj.api.rpc.types.RpcConfig;
import com.paymennt.solanaj.api.rpc.types.RpcLogsConfig;
import com.paymennt.solanaj.api.rpc.types.RpcNotificationResult;
import com.paymennt.solanaj.api.rpc.types.RpcRequest;
import com.paymennt.solanaj.api.rpc.types.RpcResponse;
import com.paymennt.solanaj.api.rpc.types.SolanaCommitment;
import com.paymennt.solanaj.api.ws.listener.NotificationEventListener;
import com.paymennt.solanaj.api.ws.listener.TransactionEventListener;
import com.paymennt.solanaj.utils.JsonUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SolanaWebSocketClient.
 */
public class SolanaWebSocketClient extends WebSocketClient {

    /** The instance. */
    private static SolanaWebSocketClient instance;
    
    /** The scheduler. */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /** The subscriptions. */
    private Map<String, SubscriptionParams> subscriptions = new HashMap<>();
    
    /** The subscription ids. */
    private Map<String, Long> subscriptionIds = new HashMap<>();
    
    /** The subscription address ids. */
    private Map<String, String> subscriptionAddressIds = new HashMap<>();
    
    /** The subscription linsteners. */
    private Map<Long, NotificationEventListener> subscriptionLinsteners = new HashMap<>();

    /**
     * Instantiates a new solana web socket client.
     *
     * @param serverURI the server URI
     */
    public SolanaWebSocketClient(URI serverURI) {
        super(serverURI);
        scheduler.scheduleAtFixedRate(() -> {
            send("ping");
        }, 20, 20, TimeUnit.SECONDS);
    }

    /**
     * Gets the single instance of SolanaWebSocketClient.
     *
     * @param endpoint the endpoint
     * @return single instance of SolanaWebSocketClient
     */
    public static SolanaWebSocketClient getInstance(Cluster cluster) {
        URI endpointURI;
        URI serverURI;

        try {
            endpointURI = new URI(cluster.getEndpoint());
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

    /**
     * Account subscribe.
     *
     * @param key the key
     * @param listener the listener
     */
    public void accountSubscribe(String key, NotificationEventListener listener) {
        accountSubscribe(key, SolanaCommitment.finalized, listener);
        accountSubscribe(key, SolanaCommitment.confirmed, listener);
        accountSubscribe(key, SolanaCommitment.processed, listener);
    }

    /**
     * Account subscribe.
     *
     * @param key the key
     * @param commitment the commitment
     * @param listener the listener
     */
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

    /**
     * Account unsubscribe.
     *
     * @param key the key
     */
    public void accountUnsubscribe(String key) {
        accountUnsubscribe(key, SolanaCommitment.finalized);
        accountUnsubscribe(key, SolanaCommitment.confirmed);
        accountUnsubscribe(key, SolanaCommitment.processed);
    }

    /**
     * Account unsubscribe.
     *
     * @param key the key
     * @param commitment the commitment
     */
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

    /**
     * Signature subscribe.
     *
     * @param signature the signature
     * @param listener the listener
     */
    public void signatureSubscribe(String signature, NotificationEventListener listener) {
        List<Object> params = new ArrayList<>();
        params.add(signature);

        RpcRequest rpcRequest = new RpcRequest("signatureSubscribe", params);

        subscriptions.put(rpcRequest.getId(), new SubscriptionParams(rpcRequest, listener));
        subscriptionIds.put(rpcRequest.getId(), null);
        subscriptionAddressIds.put(signature, rpcRequest.getId());

        updateSubscriptions();
    }

    /**
     * Logs subscribe.
     *
     * @param key the key
     * @param listener the listener
     */
    public void logsSubscribe(String key, TransactionEventListener listener) {
        logsSubscribe(key, SolanaCommitment.finalized, listener);
        logsSubscribe(key, SolanaCommitment.confirmed, listener);
    }

    /**
     * Logs subscribe.
     *
     * @param key the key
     * @param commitment the commitment
     * @param listener the listener
     */
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

    /**
     * Logs unsubscribe.
     *
     * @param key the key
     */
    public void logsUnsubscribe(String key) {
        logsUnsubscribe(key, SolanaCommitment.finalized);
        logsUnsubscribe(key, SolanaCommitment.confirmed);
    }

    /**
     * Logs unsubscribe.
     *
     * @param key the key
     * @param commitment the commitment
     */
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

    /**
     * On open.
     *
     * @param handshakedata the handshakedata
     */
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        updateSubscriptions();
    }

    /**
     * On message.
     *
     * @param message the message
     */
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

    /**
     * On close.
     *
     * @param code the code
     * @param reason the reason
     * @param remote the remote
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);

    }

    /**
     * On error.
     *
     * @param ex the ex
     */
    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    /**
     * Update subscriptions.
     */
    private void updateSubscriptions() {

        if (!isOpen() || subscriptions.size() <= 0)
            return;

        subscriptions.values()//
                .stream()//
                .map(SubscriptionParams::getRequest)//
                .map(JsonUtils::encode)//
                .forEach(this::send);

    }

    /**
     * The Class SubscriptionParams.
     */
    private class SubscriptionParams {
        
        /** The request. */
        RpcRequest request;
        
        /** The listener. */
        NotificationEventListener listener;

        /**
         * Instantiates a new subscription params.
         *
         * @param request the request
         * @param listener the listener
         */
        SubscriptionParams(RpcRequest request, NotificationEventListener listener) {
            this.request = request;
            this.listener = listener;
        }

        /**
         * Gets the request.
         *
         * @return the request
         */
        public RpcRequest getRequest() {
            return request;
        }

    }

    /**
     * The Class BlockSubscribe.
     */
    public static class BlockSubscribe {
        
        /** The mentions. */
        private String[] mentions;

        /**
         * Instantiates a new block subscribe.
         *
         * @param mentions the mentions
         */
        public BlockSubscribe(String[] mentions) {
            super();
            this.mentions = mentions;
        }

        /**
         * Gets the mentions.
         *
         * @return the mentions
         */
        public String[] getMentions() {
            return mentions;
        }

        /**
         * Sets the mentions.
         *
         * @param mentions the new mentions
         */
        public void setMentions(String[] mentions) {
            this.mentions = mentions;
        }

    }

}

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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import com.paymennt.solanaj.utils.WebsocketClient;
import com.paymennt.solanaj.utils.WebsocketClient.WebSocketHandler;

/**
 * 
 */
public class SolanaWebSocketClient implements WebSocketHandler {

    /**  */
    private final WebsocketClient client;

    /**  */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**  */
    private Map<String, SubscriptionParams> subscriptions = new ConcurrentHashMap<>();

    /**  */
    private Map<String, Long> subscriptionIds = new HashMap<>();

    /**  */
    private Map<String, String> subscriptionAddressIds = new HashMap<>();

    /**  */
    private Map<Long, NotificationEventListener> subscriptionLinsteners = new HashMap<>();

    /**
     * 
     *
     * @param cluster 
     */
    public SolanaWebSocketClient(Cluster cluster) {
        this.client = new WebsocketClient(getServerUrl(cluster), this);
        this.client.start();

        scheduler.scheduleAtFixedRate(() -> {
            client.sendMessage("ping");
        }, 20, 20, TimeUnit.SECONDS);
    }

    /**
     * 
     *
     * @param key 
     * @param listener 
     */
    public void accountSubscribe(String key, NotificationEventListener listener) {
        accountSubscribe(key, SolanaCommitment.finalized, listener);
        accountSubscribe(key, SolanaCommitment.confirmed, listener);
        accountSubscribe(key, SolanaCommitment.processed, listener);
    }

    /**
     * 
     *
     * @param key 
     * @param commitment 
     * @param listener 
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
     * 
     *
     * @param key 
     */
    public void accountUnsubscribe(String key) {
        accountUnsubscribe(key, SolanaCommitment.finalized);
        accountUnsubscribe(key, SolanaCommitment.confirmed);
        accountUnsubscribe(key, SolanaCommitment.processed);
    }

    /**
     * 
     *
     * @param key 
     * @param commitment 
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

        client.sendMessage(JsonUtils.encode(rpcRequest));

        updateSubscriptions();
    }

    /**
     * 
     *
     * @param signature 
     * @param listener 
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
     * 
     *
     * @param key 
     * @param listener 
     */
    public void logsSubscribe(String key, TransactionEventListener listener) {
        logsSubscribe(key, SolanaCommitment.finalized, listener);
        logsSubscribe(key, SolanaCommitment.confirmed, listener);
    }

    /**
     * 
     *
     * @param key 
     * @param commitment 
     * @param listener 
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
     * 
     *
     * @param key 
     */
    public void logsUnsubscribe(String key) {
        logsUnsubscribe(key, SolanaCommitment.finalized);
        logsUnsubscribe(key, SolanaCommitment.confirmed);
    }

    /**
     * 
     *
     * @param key 
     * @param commitment 
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

        client.sendMessage(JsonUtils.encode(rpcRequest));

        updateSubscriptions();
    }

    /**
     * 
     *
     * @param handshakedata 
     */
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        updateSubscriptions();
    }

    /**
     * 
     *
     * @param message 
     */
    @SuppressWarnings({ "rawtypes" })
    @Override
    public void handleMessage(String message) {

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
     * 
     *
     * @param e 
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    /**
     * 
     *
     * @param cluster 
     * @return 
     */

    private URI getServerUrl(Cluster cluster) {

        try {
            URI endpointURI = new URI(cluster.getEndpoint());
            return new URI((endpointURI.getScheme().equals("https") ? "wss" : "ws") + "://" + endpointURI.getHost());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }

    }

    /**
     * 
     */
    private synchronized void updateSubscriptions() {

        if (subscriptions.size() <= 0)
            return;

        subscriptions.values()//
                .stream()//
                .map(SubscriptionParams::getRequest)//
                .map(JsonUtils::encode)//
                .forEach(client::sendMessage);

    }

    /**
     * 
     */
    private class SubscriptionParams {

        /**  */
        RpcRequest request;

        /**  */
        NotificationEventListener listener;

        /**
         * 
         *
         * @param request 
         * @param listener 
         */
        SubscriptionParams(RpcRequest request, NotificationEventListener listener) {
            this.request = request;
            this.listener = listener;
        }

        /**
         * 
         *
         * @return 
         */
        public RpcRequest getRequest() {
            return request;
        }

    }

    /**
     * 
     */
    public static class BlockSubscribe {

        /**  */
        private String[] mentions;

        /**
         * 
         *
         * @param mentions 
         */
        public BlockSubscribe(String[] mentions) {
            super();
            this.mentions = mentions;
        }

        /**
         * 
         *
         * @return 
         */
        public String[] getMentions() {
            return mentions;
        }

        /**
         * 
         *
         * @param mentions 
         */
        public void setMentions(String[] mentions) {
            this.mentions = mentions;
        }

    }

}

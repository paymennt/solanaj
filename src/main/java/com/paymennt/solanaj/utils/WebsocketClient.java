/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.utils;

import java.net.URI;
import java.util.LinkedList;
import java.util.Queue;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 * a websocket client wrapper that reconnects on failure.
 *
 * @author paymennt
 */

public class WebsocketClient {

    /**  */
    private URI websocketUrl;

    /**  */
    private WebSocketHandler handler;

    /**  */
    private WebSocketClient webSocketClient;

    /**  */
    private Queue<String> messageQueue = new LinkedList<>();

    /**
     * 
     *
     * @param websocketUrl 
     * @param handler 
     */
    public WebsocketClient(URI websocketUrl, WebSocketHandler handler) {
        this.websocketUrl = websocketUrl;
        this.handler = handler;
    }

    /**
     * 
     */
    public void start() {
        webSocketClient = new WebSocketClient(websocketUrl) {

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                handler.onOpen(handshakedata);
                while (!messageQueue.isEmpty())
                    sendMessage(messageQueue.poll());
            }

            @Override
            public void onMessage(String message) {
                handler.handleMessage(message);
            }

            @Override
            public void onError(Exception ex) {
                handler.onError(ex);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                start();
            }

        };

        webSocketClient.connect();
    }

    /**
     * 
     *
     * @param message 
     */
    public void sendMessage(Object message) {
        sendMessage(JsonUtils.encode(message));
    }

    /**
     * 
     *
     * @param message 
     */
    public void sendMessage(String message) {
        if (webSocketClient == null || !webSocketClient.isOpen()) {
            messageQueue.add(message);
            return;
        }

        try {
            webSocketClient.send(message);
        } catch (Exception e) {
            handler.onError(e);
        }
    }

    /**
     * 
     */
    public static interface WebSocketHandler {
        
        /**
         * 
         *
         * @param payload 
         */
        void handleMessage(String payload);

        /**
         * 
         *
         * @param e 
         */
        void onError(Throwable e);

        /**
         * 
         *
         * @param handshakedata 
         */
        default void onOpen(ServerHandshake handshakedata) {
        }
    }

}

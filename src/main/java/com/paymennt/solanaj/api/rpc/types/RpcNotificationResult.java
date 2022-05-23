/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

// TODO: Auto-generated Javadoc
/**
 * The Class RpcNotificationResult.
 */
public class RpcNotificationResult {

    /** The jsonrpc. */
    private String jsonrpc;
    
    /** The method. */
    private String method;
    
    /** The params. */
    private Params params;

    /**
     * Gets the jsonrpc.
     *
     * @return the jsonrpc
     */
    public String getJsonrpc() {
        return jsonrpc;
    }

    /**
     * Sets the jsonrpc.
     *
     * @param jsonrpc the new jsonrpc
     */
    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    /**
     * Gets the method.
     *
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the method.
     *
     * @param method the new method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets the params.
     *
     * @return the params
     */
    public Params getParams() {
        return params;
    }

    /**
     * Sets the params.
     *
     * @param params the new params
     */
    public void setParams(Params params) {
        this.params = params;
    }

    /**
     * The Class Result.
     */
    public static class Result extends RpcResultObject {
        
        /** The value. */
        private Object value;

        /**
         * Gets the value.
         *
         * @return the value
         */
        public Object getValue() {
            return value;
        }

        /**
         * Sets the value.
         *
         * @param value the new value
         */
        public void setValue(Object value) {
            this.value = value;
        }

    }

    /**
     * The Class Params.
     */
    public static class Params {

        /** The result. */
        private Result result;
        
        /** The subscription. */
        private long subscription;

        /**
         * Gets the result.
         *
         * @return the result
         */
        public Result getResult() {
            return result;
        }

        /**
         * Sets the result.
         *
         * @param result the new result
         */
        public void setResult(Result result) {
            this.result = result;
        }

        /**
         * Gets the subscription.
         *
         * @return the subscription
         */
        public long getSubscription() {
            return subscription;
        }

        /**
         * Sets the subscription.
         *
         * @param subscription the new subscription
         */
        public void setSubscription(long subscription) {
            this.subscription = subscription;
        }

    }

}

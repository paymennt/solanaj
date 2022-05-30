/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;


/**
 * 
 */
public class RpcNotificationResult {

    /**  */
    private String jsonrpc;
    
    /**  */
    private String method;
    
    /**  */
    private Params params;

    /**
     * 
     *
     * @return 
     */
    public String getJsonrpc() {
        return jsonrpc;
    }

    /**
     * 
     *
     * @param jsonrpc 
     */
    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    /**
     * 
     *
     * @return 
     */
    public String getMethod() {
        return method;
    }

    /**
     * 
     *
     * @param method 
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 
     *
     * @return 
     */
    public Params getParams() {
        return params;
    }

    /**
     * 
     *
     * @param params 
     */
    public void setParams(Params params) {
        this.params = params;
    }

    /**
     * 
     */
    public static class Result extends RpcResultObject {
        
        /**  */
        private Object value;

        /**
         * 
         *
         * @return 
         */
        public Object getValue() {
            return value;
        }

        /**
         * 
         *
         * @param value 
         */
        public void setValue(Object value) {
            this.value = value;
        }

    }

    /**
     * 
     */
    public static class Params {

        /**  */
        private Result result;
        
        /**  */
        private long subscription;

        /**
         * 
         *
         * @return 
         */
        public Result getResult() {
            return result;
        }

        /**
         * 
         *
         * @param result 
         */
        public void setResult(Result result) {
            this.result = result;
        }

        /**
         * 
         *
         * @return 
         */
        public long getSubscription() {
            return subscription;
        }

        /**
         * 
         *
         * @param subscription 
         */
        public void setSubscription(long subscription) {
            this.subscription = subscription;
        }

    }

}

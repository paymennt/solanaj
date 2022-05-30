/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;


/**
 * 
 *
 * @param <T> 
 */
public class RpcResponse<T> {

    /**  */
    private String jsonrpc;
    
    /**  */
    private T result;
    
    /**  */
    private Error error;
    
    /**  */
    private String id;

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
    public T getResult() {
        return result;
    }

    /**
     * 
     *
     * @param result 
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * 
     *
     * @return 
     */
    public Error getError() {
        return error;
    }

    /**
     * 
     *
     * @param error 
     */
    public void setError(Error error) {
        this.error = error;
    }

    /**
     * 
     *
     * @return 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     *
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     */
    public static class Error {

        /**  */
        private long code;
        
        /**  */
        private String message;

        /**
         * 
         *
         * @return 
         */
        public long getCode() {
            return code;
        }

        /**
         * 
         *
         * @param code 
         */
        public void setCode(long code) {
            this.code = code;
        }

        /**
         * 
         *
         * @return 
         */
        public String getMessage() {
            return message;
        }

        /**
         * 
         *
         * @param message 
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }

}

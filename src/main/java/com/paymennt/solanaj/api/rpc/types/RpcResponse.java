/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

// TODO: Auto-generated Javadoc
/**
 * The Class RpcResponse.
 *
 * @param <T> the generic type
 */
public class RpcResponse<T> {

    /** The jsonrpc. */
    private String jsonrpc;
    
    /** The result. */
    private T result;
    
    /** The error. */
    private Error error;
    
    /** The id. */
    private String id;

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
     * Gets the result.
     *
     * @return the result
     */
    public T getResult() {
        return result;
    }

    /**
     * Sets the result.
     *
     * @param result the new result
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * Gets the error.
     *
     * @return the error
     */
    public Error getError() {
        return error;
    }

    /**
     * Sets the error.
     *
     * @param error the new error
     */
    public void setError(Error error) {
        this.error = error;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * The Class Error.
     */
    public static class Error {

        /** The code. */
        private long code;
        
        /** The message. */
        private String message;

        /**
         * Gets the code.
         *
         * @return the code
         */
        public long getCode() {
            return code;
        }

        /**
         * Sets the code.
         *
         * @param code the new code
         */
        public void setCode(long code) {
            this.code = code;
        }

        /**
         * Gets the message.
         *
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Sets the message.
         *
         * @param message the new message
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }

}

/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.List;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * The Class RpcRequest.
 */
public class RpcRequest {
    
    /** The jsonrpc. */
    private String jsonrpc = "2.0";
    
    /** The method. */
    private String method;
    
    /** The params. */
    private List<Object> params = null;
    
    /** The id. */
    private String id = UUID.randomUUID().toString();

    /**
     * Instantiates a new rpc request.
     *
     * @param method the method
     */
    public RpcRequest(String method) {
        this(method, null);
    }

    /**
     * Instantiates a new rpc request.
     *
     * @param method the method
     * @param params the params
     */
    public RpcRequest(String method, List<Object> params) {
        this.method = method;
        this.params = params;
    }

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
    public List<Object> getParams() {
        return params;
    }

    /**
     * Sets the params.
     *
     * @param params the new params
     */
    public void setParams(List<Object> params) {
        this.params = params;
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

}

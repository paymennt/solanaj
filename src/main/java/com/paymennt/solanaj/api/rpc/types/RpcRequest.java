/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.List;
import java.util.UUID;


/**
 * 
 */
public class RpcRequest {
    
    /**  */
    private String jsonrpc = "2.0";
    
    /**  */
    private String method;
    
    /**  */
    private List<Object> params = null;
    
    /**  */
    private String id = UUID.randomUUID().toString();

    /**
     * 
     *
     * @param method 
     */
    public RpcRequest(String method) {
        this(method, null);
    }

    /**
     * 
     *
     * @param method 
     * @param params 
     */
    public RpcRequest(String method, List<Object> params) {
        this.method = method;
        this.params = params;
    }

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
    public List<Object> getParams() {
        return params;
    }

    /**
     * 
     *
     * @param params 
     */
    public void setParams(List<Object> params) {
        this.params = params;
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

}

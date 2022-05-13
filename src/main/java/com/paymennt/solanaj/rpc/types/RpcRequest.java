package com.paymennt.solanaj.rpc.types;

import java.util.List;
import java.util.UUID;

public class RpcRequest {
    private String jsonrpc = "2.0";
    private String method;
    private List<Object> params = null;
    private String id = UUID.randomUUID().toString();

    public RpcRequest(String method) {
        this(method, null);
    }

    public RpcRequest(String method, List<Object> params) {
        this.method = method;
        this.params = params;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

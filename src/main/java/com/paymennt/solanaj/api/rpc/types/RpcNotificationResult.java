package com.paymennt.solanaj.api.rpc.types;

public class RpcNotificationResult {

    private String jsonrpc;
    private String method;
    private Params params;

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

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public static class Result extends RpcResultObject {
        private Object value;

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

    }

    public static class Params {

        private Result result;
        private long subscription;

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public long getSubscription() {
            return subscription;
        }

        public void setSubscription(long subscription) {
            this.subscription = subscription;
        }

    }

}

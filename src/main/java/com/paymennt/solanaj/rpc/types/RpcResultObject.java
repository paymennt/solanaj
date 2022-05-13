package com.paymennt.solanaj.rpc.types;

public class RpcResultObject {

    protected Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static class Context {
        private long slot;

        public long getSlot() {
            return slot;
        }

        public void setSlot(long slot) {
            this.slot = slot;
        }

    }
}

package com.paymennt.solanaj.api.rpc.types;

public class RpcResultTypes {

    public static class ValueLong extends RpcResultObject {
        private long value;

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

    }

}

package com.paymennt.solanaj.api.rpc.types;

import java.util.List;

public class AccountInfo extends RpcResultObject {

    private Value value;

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public static class Value {
        private List<String> data = null;
        private boolean executable;
        private long lamports;
        private String owner;
        private long rentEpoch;

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public boolean isExecutable() {
            return executable;
        }

        public void setExecutable(boolean executable) {
            this.executable = executable;
        }

        public long getLamports() {
            return lamports;
        }

        public void setLamports(long lamports) {
            this.lamports = lamports;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public long getRentEpoch() {
            return rentEpoch;
        }

        public void setRentEpoch(long rentEpoch) {
            this.rentEpoch = rentEpoch;
        }

    }
}

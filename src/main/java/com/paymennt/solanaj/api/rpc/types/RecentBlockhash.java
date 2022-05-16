package com.paymennt.solanaj.api.rpc.types;

public class RecentBlockhash extends RpcResultObject {

    private Value value;

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getRecentBlockhash() {
        return getValue().getBlockhash();
    }

    public static class FeeCalculator {

        private long lamportsPerSignature;

        public long getLamportsPerSignature() {
            return lamportsPerSignature;
        }

    }

    public static class Value {
        private String blockhash;
        private FeeCalculator feeCalculator;

        public String getBlockhash() {
            return blockhash;
        }

        public FeeCalculator getFeeCalculator() {
            return feeCalculator;
        }

    }

}

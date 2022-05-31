/**
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

/**
 * @author paymennt
 *
 */
public class RpcTokenBalance extends RpcResultObject {

    private Value value;

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public static class Value {

        private long amount;
        private long decimals;

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public long getDecimals() {
            return decimals;
        }

        public void setDecimals(long decimals) {
            this.decimals = decimals;
        }

    }

}

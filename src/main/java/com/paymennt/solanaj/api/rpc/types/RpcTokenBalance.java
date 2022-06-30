/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

/**
 * @author paymennt
 *
 */
public class RpcTokenBalance extends RpcResultObject {

    /**  */
    private Value value;

    /**
     * 
     *
     * @return 
     */
    public Value getValue() {
        return value;
    }

    /**
     * 
     *
     * @param value 
     */
    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * 
     */
    public static class Value {

        /**  */
        private long amount;
        
        /**  */
        private long decimals;

        /**
         * 
         *
         * @return 
         */
        public long getAmount() {
            return amount;
        }

        /**
         * 
         *
         * @param amount 
         */
        public void setAmount(long amount) {
            this.amount = amount;
        }

        /**
         * 
         *
         * @return 
         */
        public long getDecimals() {
            return decimals;
        }

        /**
         * 
         *
         * @param decimals 
         */
        public void setDecimals(long decimals) {
            this.decimals = decimals;
        }

    }

}

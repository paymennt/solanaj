/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentBlockhash.
 */
public class RecentBlockhash extends RpcResultObject {

    /** The value. */
    private Value value;

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Value getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * Gets the recent blockhash.
     *
     * @return the recent blockhash
     */
    public String getRecentBlockhash() {
        return getValue().getBlockhash();
    }

    /**
     * The Class FeeCalculator.
     */
    public static class FeeCalculator {

        /** The lamports per signature. */
        private long lamportsPerSignature;

        /**
         * Gets the lamports per signature.
         *
         * @return the lamports per signature
         */
        public long getLamportsPerSignature() {
            return lamportsPerSignature;
        }

    }

    /**
     * The Class Value.
     */
    public static class Value {
        
        /** The blockhash. */
        private String blockhash;
        
        /** The fee calculator. */
        private FeeCalculator feeCalculator;

        /**
         * Gets the blockhash.
         *
         * @return the blockhash
         */
        public String getBlockhash() {
            return blockhash;
        }

        /**
         * Gets the fee calculator.
         *
         * @return the fee calculator
         */
        public FeeCalculator getFeeCalculator() {
            return feeCalculator;
        }

    }

}

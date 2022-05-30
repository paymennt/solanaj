/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;


/**
 * 
 */
public class RecentBlockhash extends RpcResultObject {

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
     *
     * @return 
     */
    public String getRecentBlockhash() {
        return getValue().getBlockhash();
    }

    /**
     * 
     */
    public static class FeeCalculator {

        /**  */
        private long lamportsPerSignature;

        /**
         * 
         *
         * @return 
         */
        public long getLamportsPerSignature() {
            return lamportsPerSignature;
        }

    }

    /**
     * 
     */
    public static class Value {
        
        /**  */
        private String blockhash;
        
        /**  */
        private FeeCalculator feeCalculator;

        /**
         * 
         *
         * @return 
         */
        public String getBlockhash() {
            return blockhash;
        }

        /**
         * 
         *
         * @return 
         */
        public FeeCalculator getFeeCalculator() {
            return feeCalculator;
        }

    }

}

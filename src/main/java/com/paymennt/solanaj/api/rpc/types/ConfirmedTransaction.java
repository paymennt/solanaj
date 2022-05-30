/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.List;

import com.paymennt.solanaj.data.SolanaTransaction;


/**
 * 
 */
public class ConfirmedTransaction {

    /**  */
    private Meta meta;
    
    /**  */
    private long slot;
    
    /**  */
    private SolanaTransaction transaction;

    /**
     * 
     *
     * @return 
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * 
     *
     * @param meta 
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     * 
     *
     * @return 
     */
    public long getSlot() {
        return slot;
    }

    /**
     * 
     *
     * @param slot 
     */
    public void setSlot(long slot) {
        this.slot = slot;
    }

    /**
     * 
     *
     * @return 
     */
    public SolanaTransaction getTransaction() {
        return transaction;
    }

    /**
     * 
     *
     * @param transaction 
     */
    public void setTransaction(SolanaTransaction transaction) {
        this.transaction = transaction;
    }

    /**
     * 
     */
    public static class Status {

        /**  */
        private Object ok;

        /**
         * 
         *
         * @return 
         */
        public Object getOk() {
            return ok;
        }

        /**
         * 
         *
         * @param ok 
         */
        public void setOk(Object ok) {
            this.ok = ok;
        }

    }

    /**
     * 
     */
    public static class Meta {

        /**  */
        private Object err;
        
        /**  */
        private long fee;
        
        /**  */
        private List<Object> innerInstructions = null;
        
        /**  */
        private List<Long> postBalances = null;
        
        /**  */
        private List<Long> preBalances = null;
        
        /**  */
        private Status status;

        /**
         * 
         *
         * @return 
         */
        public Object getErr() {
            return err;
        }

        /**
         * 
         *
         * @param err 
         */
        public void setErr(Object err) {
            this.err = err;
        }

        /**
         * 
         *
         * @return 
         */
        public long getFee() {
            return fee;
        }

        /**
         * 
         *
         * @param fee 
         */
        public void setFee(long fee) {
            this.fee = fee;
        }

        /**
         * 
         *
         * @return 
         */
        public List<Object> getInnerInstructions() {
            return innerInstructions;
        }

        /**
         * 
         *
         * @param innerInstructions 
         */
        public void setInnerInstructions(List<Object> innerInstructions) {
            this.innerInstructions = innerInstructions;
        }

        /**
         * 
         *
         * @return 
         */
        public List<Long> getPostBalances() {
            return postBalances;
        }

        /**
         * 
         *
         * @param postBalances 
         */
        public void setPostBalances(List<Long> postBalances) {
            this.postBalances = postBalances;
        }

        /**
         * 
         *
         * @return 
         */
        public List<Long> getPreBalances() {
            return preBalances;
        }

        /**
         * 
         *
         * @param preBalances 
         */
        public void setPreBalances(List<Long> preBalances) {
            this.preBalances = preBalances;
        }

        /**
         * 
         *
         * @return 
         */
        public Status getStatus() {
            return status;
        }

        /**
         * 
         *
         * @param status 
         */
        public void setStatus(Status status) {
            this.status = status;
        }

    }

}

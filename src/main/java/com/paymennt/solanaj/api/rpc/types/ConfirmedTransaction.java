/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.List;

import com.paymennt.solanaj.data.SolanaTransaction;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmedTransaction.
 */
public class ConfirmedTransaction {

    /** The meta. */
    private Meta meta;
    
    /** The slot. */
    private long slot;
    
    /** The transaction. */
    private SolanaTransaction transaction;

    /**
     * Gets the meta.
     *
     * @return the meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * Sets the meta.
     *
     * @param meta the new meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     * Gets the slot.
     *
     * @return the slot
     */
    public long getSlot() {
        return slot;
    }

    /**
     * Sets the slot.
     *
     * @param slot the new slot
     */
    public void setSlot(long slot) {
        this.slot = slot;
    }

    /**
     * Gets the transaction.
     *
     * @return the transaction
     */
    public SolanaTransaction getTransaction() {
        return transaction;
    }

    /**
     * Sets the transaction.
     *
     * @param transaction the new transaction
     */
    public void setTransaction(SolanaTransaction transaction) {
        this.transaction = transaction;
    }

    /**
     * The Class Status.
     */
    public static class Status {

        /** The ok. */
        private Object ok;

        /**
         * Gets the ok.
         *
         * @return the ok
         */
        public Object getOk() {
            return ok;
        }

        /**
         * Sets the ok.
         *
         * @param ok the new ok
         */
        public void setOk(Object ok) {
            this.ok = ok;
        }

    }

    /**
     * The Class Meta.
     */
    public static class Meta {

        /** The err. */
        private Object err;
        
        /** The fee. */
        private long fee;
        
        /** The inner instructions. */
        private List<Object> innerInstructions = null;
        
        /** The post balances. */
        private List<Long> postBalances = null;
        
        /** The pre balances. */
        private List<Long> preBalances = null;
        
        /** The status. */
        private Status status;

        /**
         * Gets the err.
         *
         * @return the err
         */
        public Object getErr() {
            return err;
        }

        /**
         * Sets the err.
         *
         * @param err the new err
         */
        public void setErr(Object err) {
            this.err = err;
        }

        /**
         * Gets the fee.
         *
         * @return the fee
         */
        public long getFee() {
            return fee;
        }

        /**
         * Sets the fee.
         *
         * @param fee the new fee
         */
        public void setFee(long fee) {
            this.fee = fee;
        }

        /**
         * Gets the inner instructions.
         *
         * @return the inner instructions
         */
        public List<Object> getInnerInstructions() {
            return innerInstructions;
        }

        /**
         * Sets the inner instructions.
         *
         * @param innerInstructions the new inner instructions
         */
        public void setInnerInstructions(List<Object> innerInstructions) {
            this.innerInstructions = innerInstructions;
        }

        /**
         * Gets the post balances.
         *
         * @return the post balances
         */
        public List<Long> getPostBalances() {
            return postBalances;
        }

        /**
         * Sets the post balances.
         *
         * @param postBalances the new post balances
         */
        public void setPostBalances(List<Long> postBalances) {
            this.postBalances = postBalances;
        }

        /**
         * Gets the pre balances.
         *
         * @return the pre balances
         */
        public List<Long> getPreBalances() {
            return preBalances;
        }

        /**
         * Sets the pre balances.
         *
         * @param preBalances the new pre balances
         */
        public void setPreBalances(List<Long> preBalances) {
            this.preBalances = preBalances;
        }

        /**
         * Gets the status.
         *
         * @return the status
         */
        public Status getStatus() {
            return status;
        }

        /**
         * Sets the status.
         *
         * @param status the new status
         */
        public void setStatus(Status status) {
            this.status = status;
        }

    }

}

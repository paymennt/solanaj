package com.paymennt.solanaj.api.rpc.types;

import java.util.List;

import com.paymennt.solanaj.data.SolanaTransaction;

public class ConfirmedTransaction {

    private Meta meta;
    private long slot;
    private SolanaTransaction transaction;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public long getSlot() {
        return slot;
    }

    public void setSlot(long slot) {
        this.slot = slot;
    }

    public SolanaTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(SolanaTransaction transaction) {
        this.transaction = transaction;
    }

    public static class Status {

        private Object ok;

        public Object getOk() {
            return ok;
        }

        public void setOk(Object ok) {
            this.ok = ok;
        }

    }

    public static class Meta {

        private Object err;
        private long fee;
        private List<Object> innerInstructions = null;
        private List<Long> postBalances = null;
        private List<Long> preBalances = null;
        private Status status;

        public Object getErr() {
            return err;
        }

        public void setErr(Object err) {
            this.err = err;
        }

        public long getFee() {
            return fee;
        }

        public void setFee(long fee) {
            this.fee = fee;
        }

        public List<Object> getInnerInstructions() {
            return innerInstructions;
        }

        public void setInnerInstructions(List<Object> innerInstructions) {
            this.innerInstructions = innerInstructions;
        }

        public List<Long> getPostBalances() {
            return postBalances;
        }

        public void setPostBalances(List<Long> postBalances) {
            this.postBalances = postBalances;
        }

        public List<Long> getPreBalances() {
            return preBalances;
        }

        public void setPreBalances(List<Long> preBalances) {
            this.preBalances = preBalances;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

    }

}

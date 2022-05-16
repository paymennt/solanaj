package com.paymennt.solanaj.api.rpc.types;

import java.util.List;

public class ConfirmedTransaction {

    private Meta meta;
    private long slot;
    private Transaction transaction;

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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public static class Header {

        private long numReadonlySignedAccounts;
        private long numReadonlyUnsignedAccounts;
        private long numRequiredSignatures;

        public long getNumReadonlySignedAccounts() {
            return numReadonlySignedAccounts;
        }

        public void setNumReadonlySignedAccounts(long numReadonlySignedAccounts) {
            this.numReadonlySignedAccounts = numReadonlySignedAccounts;
        }

        public long getNumReadonlyUnsignedAccounts() {
            return numReadonlyUnsignedAccounts;
        }

        public void setNumReadonlyUnsignedAccounts(long numReadonlyUnsignedAccounts) {
            this.numReadonlyUnsignedAccounts = numReadonlyUnsignedAccounts;
        }

        public long getNumRequiredSignatures() {
            return numRequiredSignatures;
        }

        public void setNumRequiredSignatures(long numRequiredSignatures) {
            this.numRequiredSignatures = numRequiredSignatures;
        }

    }

    public static class Instruction {

        private List<Long> accounts = null;
        private String data;
        private long programIdIndex;

        public List<Long> getAccounts() {
            return accounts;
        }

        public void setAccounts(List<Long> accounts) {
            this.accounts = accounts;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public long getProgramIdIndex() {
            return programIdIndex;
        }

        public void setProgramIdIndex(long programIdIndex) {
            this.programIdIndex = programIdIndex;
        }

    }

    public static class Message {

        private List<String> accountKeys = null;
        private Header header;
        private List<Instruction> instructions = null;
        private String recentBlockhash;

        public List<String> getAccountKeys() {
            return accountKeys;
        }

        public void setAccountKeys(List<String> accountKeys) {
            this.accountKeys = accountKeys;
        }

        public Header getHeader() {
            return header;
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public List<Instruction> getInstructions() {
            return instructions;
        }

        public void setInstructions(List<Instruction> instructions) {
            this.instructions = instructions;
        }

        public String getRecentBlockhash() {
            return recentBlockhash;
        }

        public void setRecentBlockhash(String recentBlockhash) {
            this.recentBlockhash = recentBlockhash;
        }

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

    public static class Transaction {

        private Message message;
        private List<String> signatures = null;

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public List<String> getSignatures() {
            return signatures;
        }

        public void setSignatures(List<String> signatures) {
            this.signatures = signatures;
        }

    }

}

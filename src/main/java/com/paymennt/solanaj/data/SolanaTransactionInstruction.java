package com.paymennt.solanaj.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

public class SolanaTransactionInstruction {

    private List<AccountMeta> keys;
    private AccountPublicKey programId;
    private byte[] data;
    private SolanaTransactionParsedInstruction parsed;

    public SolanaTransactionInstruction() {

    }

    public SolanaTransactionInstruction(AccountPublicKey programId, List<AccountMeta> keys, byte[] data) {
        this.programId = programId;
        this.keys = keys;
        this.data = data;
    }

    /*******************************************************************************************************************
     * setters and getters
     */

    public List<AccountMeta> getKeys() {
        return keys;
    }

    public void setKeys(List<AccountMeta> keys) {
        this.keys = keys;
    }

    public AccountPublicKey getProgramId() {
        return programId;
    }

    public void setProgramId(AccountPublicKey programId) {
        this.programId = programId;
    }

    @JsonSetter
    public void setProgramId(String programId) {
        this.programId = new AccountPublicKey(programId);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public SolanaTransactionParsedInstruction getParsed() {
        return parsed;
    }

    public void setParsed(SolanaTransactionParsedInstruction parsed) {
        this.parsed = parsed;
    }

    /*******************************************************************************************************************
     * classes
     */

    public static class SolanaTransactionParsedInstruction {

        private TransactionInfo info;
        private TransactionType type;
        

        public TransactionInfo getInfo() {
            return info;
        }

        public void setInfo(TransactionInfo info) {
            this.info = info;
        }

        public TransactionType getType() {
            return type;
        }

        public void setType(TransactionType type) {
            this.type = type;
        }

        public static class TransactionInfo {
            private String destination;
            private String source;
            private long lamports;

            public String getDestination() {
                return destination;
            }

            public void setDestination(String destination) {
                this.destination = destination;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public long getLamports() {
                return lamports;
            }

            public void setLamports(long lamports) {
                this.lamports = lamports;
            }

        }

        public enum TransactionType {
            transfer
        }
    }
}

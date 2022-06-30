/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * 
 */
public class SolanaTransactionInstruction {

    /**  */
    private List<AccountMeta> keys;

    /**  */
    private SolanaPublicKey programId;

    /**  */
    private byte[] data;

    /**  */
    private SolanaTransactionParsedInstruction parsed;

    /**
     * 
     */
    public SolanaTransactionInstruction() {

    }

    /**
     * 
     *
     * @param programId 
     * @param keys 
     * @param data 
     */
    public SolanaTransactionInstruction(SolanaPublicKey programId, List<AccountMeta> keys, byte[] data) {
        this.programId = programId;
        this.keys = keys;
        this.data = data;
    }

    /**
     * 
     *
     * @return 
     */

    public List<AccountMeta> getKeys() {
        return keys;
    }

    /**
     * 
     *
     * @param keys 
     */
    public void setKeys(List<AccountMeta> keys) {
        this.keys = keys;
    }

    /**
     * 
     *
     * @return 
     */
    public SolanaPublicKey getProgramId() {
        return programId;
    }

    /**
     * 
     *
     * @param programId 
     */
    public void setProgramId(SolanaPublicKey programId) {
        this.programId = programId;
    }

    /**
     * 
     *
     * @param programId 
     */
    @JsonSetter
    public void setProgramId(String programId) {
        this.programId = new SolanaPublicKey(programId);
    }

    /**
     * 
     *
     * @return 
     */
    public byte[] getData() {
        return data;
    }

    /**
     * 
     *
     * @param data 
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * 
     *
     * @return 
     */
    public SolanaTransactionParsedInstruction getParsed() {
        return parsed;
    }

    /**
     * 
     *
     * @param parsed 
     */
    public void setParsed(SolanaTransactionParsedInstruction parsed) {
        this.parsed = parsed;
    }

    /**
     * 
     */

    public static class SolanaTransactionParsedInstruction {

        /**  */
        private TransactionInfo info;

        /**  */
        private TransactionType type;

        /**
         * 
         *
         * @return 
         */
        public TransactionInfo getInfo() {
            return info;
        }

        /**
         * 
         *
         * @param info 
         */
        public void setInfo(TransactionInfo info) {
            this.info = info;
        }

        /**
         * 
         *
         * @return 
         */
        public TransactionType getType() {
            return type;
        }

        /**
         * 
         *
         * @param type 
         */
        public void setType(TransactionType type) {
            this.type = type;
        }

        /**
         * 
         */
        public static class TransactionInfo {

            /**  */
            private String destination;

            /**  */
            private String mint;

            /**  */
            private String source;

            /**  */
            private Long lamports;

            /**  */
            private TransactionTokenAmount tokenAmount;

            /**
             * 
             *
             * @return 
             */
            public String getDestination() {
                return destination;
            }

            /**
             * 
             *
             * @param destination 
             */
            public void setDestination(String destination) {
                this.destination = destination;
            }

            /**
             * 
             *
             * @return 
             */
            public String getMint() {
                return mint;
            }

            /**
             * 
             *
             * @param mint 
             */
            public void setMint(String mint) {
                this.mint = mint;
            }

            /**
             * 
             *
             * @return 
             */
            public String getSource() {
                return source;
            }

            /**
             * 
             *
             * @param source 
             */
            public void setSource(String source) {
                this.source = source;
            }

            /**
             * 
             *
             * @return 
             */
            public Long getLamports() {
                return lamports;
            }

            /**
             * 
             *
             * @param lamports 
             */
            public void setLamports(Long lamports) {
                this.lamports = lamports;
            }

            /**
             * 
             *
             * @return 
             */
            public TransactionTokenAmount getTokenAmount() {
                return tokenAmount;
            }

            /**
             * 
             *
             * @param tokenAmount 
             */
            public void setTokenAmount(TransactionTokenAmount tokenAmount) {
                this.tokenAmount = tokenAmount;
            }

        }

        /**
         * 
         */
        public static class TransactionTokenAmount {

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

        /**
         * 
         */
        public enum TransactionType {

            /**  */
            transfer,
            /**  */
            transferChecked
        }
    }
}

/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

// TODO: Auto-generated Javadoc
/**
 * The Class SolanaTransactionInstruction.
 */
public class SolanaTransactionInstruction {

    /** The keys. */
    private List<AccountMeta> keys;
    
    /** The program id. */
    private AccountPublicKey programId;
    
    /** The data. */
    private byte[] data;
    
    /** The parsed. */
    private SolanaTransactionParsedInstruction parsed;

    /**
     * Instantiates a new solana transaction instruction.
     */
    public SolanaTransactionInstruction() {

    }

    /**
     * Instantiates a new solana transaction instruction.
     *
     * @param programId the program id
     * @param keys the keys
     * @param data the data
     */
    public SolanaTransactionInstruction(AccountPublicKey programId, List<AccountMeta> keys, byte[] data) {
        this.programId = programId;
        this.keys = keys;
        this.data = data;
    }

    /**
     * *****************************************************************************************************************
     * setters and getters.
     *
     * @return the keys
     */

    public List<AccountMeta> getKeys() {
        return keys;
    }

    /**
     * Sets the keys.
     *
     * @param keys the new keys
     */
    public void setKeys(List<AccountMeta> keys) {
        this.keys = keys;
    }

    /**
     * Gets the program id.
     *
     * @return the program id
     */
    public AccountPublicKey getProgramId() {
        return programId;
    }

    /**
     * Sets the program id.
     *
     * @param programId the new program id
     */
    public void setProgramId(AccountPublicKey programId) {
        this.programId = programId;
    }

    /**
     * Sets the program id.
     *
     * @param programId the new program id
     */
    @JsonSetter
    public void setProgramId(String programId) {
        this.programId = new AccountPublicKey(programId);
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the data.
     *
     * @param data the new data
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Gets the parsed.
     *
     * @return the parsed
     */
    public SolanaTransactionParsedInstruction getParsed() {
        return parsed;
    }

    /**
     * Sets the parsed.
     *
     * @param parsed the new parsed
     */
    public void setParsed(SolanaTransactionParsedInstruction parsed) {
        this.parsed = parsed;
    }

    /**
     * *****************************************************************************************************************
     * classes.
     */

    public static class SolanaTransactionParsedInstruction {

        /** The info. */
        private TransactionInfo info;
        
        /** The type. */
        private TransactionType type;
        

        /**
         * Gets the info.
         *
         * @return the info
         */
        public TransactionInfo getInfo() {
            return info;
        }

        /**
         * Sets the info.
         *
         * @param info the new info
         */
        public void setInfo(TransactionInfo info) {
            this.info = info;
        }

        /**
         * Gets the type.
         *
         * @return the type
         */
        public TransactionType getType() {
            return type;
        }

        /**
         * Sets the type.
         *
         * @param type the new type
         */
        public void setType(TransactionType type) {
            this.type = type;
        }

        /**
         * The Class TransactionInfo.
         */
        public static class TransactionInfo {
            
            /** The destination. */
            private String destination;
            
            /** The source. */
            private String source;
            
            /** The lamports. */
            private long lamports;

            /**
             * Gets the destination.
             *
             * @return the destination
             */
            public String getDestination() {
                return destination;
            }

            /**
             * Sets the destination.
             *
             * @param destination the new destination
             */
            public void setDestination(String destination) {
                this.destination = destination;
            }

            /**
             * Gets the source.
             *
             * @return the source
             */
            public String getSource() {
                return source;
            }

            /**
             * Sets the source.
             *
             * @param source the new source
             */
            public void setSource(String source) {
                this.source = source;
            }

            /**
             * Gets the lamports.
             *
             * @return the lamports
             */
            public long getLamports() {
                return lamports;
            }

            /**
             * Sets the lamports.
             *
             * @param lamports the new lamports
             */
            public void setLamports(long lamports) {
                this.lamports = lamports;
            }

        }

        /**
         * The Enum TransactionType.
         */
        public enum TransactionType {
            
            /** The transfer. */
            transfer
        }
    }
}

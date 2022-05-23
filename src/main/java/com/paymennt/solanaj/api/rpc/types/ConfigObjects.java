/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.List;

import com.paymennt.solanaj.api.rpc.types.RpcSendTransactionConfig.Encoding;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigObjects.
 */
public class ConfigObjects {

    /**
     * The Class ConfirmedSignFAddr2.
     */
    public static class SignaturesForAddress {

        /** The limit. */
        private long limit;
        
        /** The before. */
        private String before;
        
        /** The until. */
        private String until;
        
        private SolanaCommitment commitment;

        /**
         * Instantiates a new confirmed sign F addr 2.
         */
        public SignaturesForAddress() {

        }

        /**
         * Instantiates a new confirmed sign F addr 2.
         *
         * @param limit the limit
         */
        public SignaturesForAddress(long limit, SolanaCommitment commitment) {
            this.limit = limit;
            this.commitment = commitment;
        }

        /**
         * Gets the limit.
         *
         * @return the limit
         */
        public long getLimit() {
            return limit;
        }

        public SolanaCommitment getCommitment() {
            return commitment;
        }

        /**
         * Sets the limit.
         *
         * @param limit the new limit
         */
        public void setLimit(long limit) {
            this.limit = limit;
        }

        /**
         * Gets the before.
         *
         * @return the before
         */
        public String getBefore() {
            return before;
        }

        /**
         * Sets the before.
         *
         * @param before the new before
         */
        public void setBefore(String before) {
            this.before = before;
        }

        /**
         * Gets the until.
         *
         * @return the until
         */
        public String getUntil() {
            return until;
        }

        /**
         * Sets the until.
         *
         * @param until the new until
         */
        public void setUntil(String until) {
            this.until = until;
        }

    }

    /**
     * The Class Memcmp.
     */
    public static class Memcmp {
        
        /** The offset. */
        private long offset;
        
        /** The bytes. */
        private String bytes;

        /**
         * Instantiates a new memcmp.
         */
        public Memcmp() {
        }

        /**
         * Instantiates a new memcmp.
         *
         * @param offset the offset
         * @param bytes the bytes
         */
        public Memcmp(long offset, String bytes) {
            this.offset = offset;
            this.bytes = bytes;
        }

        /**
         * Gets the offset.
         *
         * @return the offset
         */
        public long getOffset() {
            return offset;
        }

        /**
         * Sets the offset.
         *
         * @param offset the new offset
         */
        public void setOffset(long offset) {
            this.offset = offset;
        }

        /**
         * Gets the bytes.
         *
         * @return the bytes
         */
        public String getBytes() {
            return bytes;
        }

        /**
         * Sets the bytes.
         *
         * @param bytes the new bytes
         */
        public void setBytes(String bytes) {
            this.bytes = bytes;
        }

    }

    /**
     * The Class Filter.
     */
    public static class Filter {
        
        /** The memcmp. */
        private Memcmp memcmp;

        /**
         * Gets the memcmp.
         *
         * @return the memcmp
         */
        public Memcmp getMemcmp() {
            return memcmp;
        }

        /**
         * Sets the memcmp.
         *
         * @param memcmp the new memcmp
         */
        public void setMemcmp(Memcmp memcmp) {
            this.memcmp = memcmp;
        }

        /**
         * Instantiates a new filter.
         */
        public Filter() {
        }

        /**
         * Instantiates a new filter.
         *
         * @param memcmp the memcmp
         */
        public Filter(Memcmp memcmp) {
            this.memcmp = memcmp;
        }

    }

    /**
     * The Class ProgramAccountConfig.
     */
    public static class ProgramAccountConfig {
        
        /** The encoding. */
        private Encoding encoding = null;
        
        /** The filters. */
        private List<Object> filters = null;

        /**
         * Instantiates a new program account config.
         */
        public ProgramAccountConfig() {
        }

        /**
         * Instantiates a new program account config.
         *
         * @param filters the filters
         */
        public ProgramAccountConfig(List<Object> filters) {
            this.filters = filters;
        }

        /**
         * Instantiates a new program account config.
         *
         * @param encoding the encoding
         */
        public ProgramAccountConfig(Encoding encoding) {
            this.encoding = encoding;
        }

        /**
         * Gets the encoding.
         *
         * @return the encoding
         */
        public Encoding getEncoding() {
            return encoding;
        }

        /**
         * Sets the encoding.
         *
         * @param encoding the new encoding
         */
        public void setEncoding(Encoding encoding) {
            this.encoding = encoding;
        }

        /**
         * Gets the filters.
         *
         * @return the filters
         */
        public List<Object> getFilters() {
            return filters;
        }

        /**
         * Sets the filters.
         *
         * @param filters the new filters
         */
        public void setFilters(List<Object> filters) {
            this.filters = filters;
        }

    }
}

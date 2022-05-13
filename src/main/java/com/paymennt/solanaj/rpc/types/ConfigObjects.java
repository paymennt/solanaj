package com.paymennt.solanaj.rpc.types;

import java.util.List;

import com.paymennt.solanaj.rpc.types.RpcSendTransactionConfig.Encoding;

public class ConfigObjects {

    public static class ConfirmedSignFAddr2 {

        private long limit;
        private String before;
        private String until;

        public ConfirmedSignFAddr2() {

        }

        public ConfirmedSignFAddr2(long limit) {
            this.limit = limit;
        }

        public long getLimit() {
            return limit;
        }

        public void setLimit(long limit) {
            this.limit = limit;
        }

        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }

        public String getUntil() {
            return until;
        }

        public void setUntil(String until) {
            this.until = until;
        }

    }

    public static class Memcmp {
        private long offset;
        private String bytes;

        public Memcmp() {
        }

        public Memcmp(long offset, String bytes) {
            this.offset = offset;
            this.bytes = bytes;
        }

        public long getOffset() {
            return offset;
        }

        public void setOffset(long offset) {
            this.offset = offset;
        }

        public String getBytes() {
            return bytes;
        }

        public void setBytes(String bytes) {
            this.bytes = bytes;
        }

    }

    public static class Filter {
        private Memcmp memcmp;

        public Memcmp getMemcmp() {
            return memcmp;
        }

        public void setMemcmp(Memcmp memcmp) {
            this.memcmp = memcmp;
        }

        public Filter() {
        }

        public Filter(Memcmp memcmp) {
            this.memcmp = memcmp;
        }

    }

    public static class ProgramAccountConfig {
        private Encoding encoding = null;
        private List<Object> filters = null;

        public ProgramAccountConfig() {
        }

        public ProgramAccountConfig(List<Object> filters) {
            this.filters = filters;
        }

        public ProgramAccountConfig(Encoding encoding) {
            this.encoding = encoding;
        }

        public Encoding getEncoding() {
            return encoding;
        }

        public void setEncoding(Encoding encoding) {
            this.encoding = encoding;
        }

        public List<Object> getFilters() {
            return filters;
        }

        public void setFilters(List<Object> filters) {
            this.filters = filters;
        }

    }
}

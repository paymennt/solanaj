/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.List;


/**
 * 
 */
public class AccountInfo extends RpcResultObject {

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
     */
    public static class Value {
        
        /**  */
        private List<String> data = null;
        
        /**  */
        private boolean executable;
        
        /**  */
        private long lamports;
        
        /**  */
        private String owner;
        
        /**  */
        private long rentEpoch;

        /**
         * 
         *
         * @return 
         */
        public List<String> getData() {
            return data;
        }

        /**
         * 
         *
         * @param data 
         */
        public void setData(List<String> data) {
            this.data = data;
        }

        /**
         * 
         *
         * @return 
         */
        public boolean isExecutable() {
            return executable;
        }

        /**
         * 
         *
         * @param executable 
         */
        public void setExecutable(boolean executable) {
            this.executable = executable;
        }

        /**
         * 
         *
         * @return 
         */
        public long getLamports() {
            return lamports;
        }

        /**
         * 
         *
         * @param lamports 
         */
        public void setLamports(long lamports) {
            this.lamports = lamports;
        }

        /**
         * 
         *
         * @return 
         */
        public String getOwner() {
            return owner;
        }

        /**
         * 
         *
         * @param owner 
         */
        public void setOwner(String owner) {
            this.owner = owner;
        }

        /**
         * 
         *
         * @return 
         */
        public long getRentEpoch() {
            return rentEpoch;
        }

        /**
         * 
         *
         * @param rentEpoch 
         */
        public void setRentEpoch(long rentEpoch) {
            this.rentEpoch = rentEpoch;
        }

    }
}

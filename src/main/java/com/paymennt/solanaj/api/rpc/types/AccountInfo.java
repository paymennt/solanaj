/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountInfo.
 */
public class AccountInfo extends RpcResultObject {

    /** The value. */
    private Value value;

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Value getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * The Class Value.
     */
    public static class Value {
        
        /** The data. */
        private List<String> data = null;
        
        /** The executable. */
        private boolean executable;
        
        /** The lamports. */
        private long lamports;
        
        /** The owner. */
        private String owner;
        
        /** The rent epoch. */
        private long rentEpoch;

        /**
         * Gets the data.
         *
         * @return the data
         */
        public List<String> getData() {
            return data;
        }

        /**
         * Sets the data.
         *
         * @param data the new data
         */
        public void setData(List<String> data) {
            this.data = data;
        }

        /**
         * Checks if is executable.
         *
         * @return true, if is executable
         */
        public boolean isExecutable() {
            return executable;
        }

        /**
         * Sets the executable.
         *
         * @param executable the new executable
         */
        public void setExecutable(boolean executable) {
            this.executable = executable;
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

        /**
         * Gets the owner.
         *
         * @return the owner
         */
        public String getOwner() {
            return owner;
        }

        /**
         * Sets the owner.
         *
         * @param owner the new owner
         */
        public void setOwner(String owner) {
            this.owner = owner;
        }

        /**
         * Gets the rent epoch.
         *
         * @return the rent epoch
         */
        public long getRentEpoch() {
            return rentEpoch;
        }

        /**
         * Sets the rent epoch.
         *
         * @param rentEpoch the new rent epoch
         */
        public void setRentEpoch(long rentEpoch) {
            this.rentEpoch = rentEpoch;
        }

    }
}

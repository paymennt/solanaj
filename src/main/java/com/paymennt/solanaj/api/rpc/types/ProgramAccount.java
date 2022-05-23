/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.AbstractMap;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ProgramAccount.
 */
public class ProgramAccount {

    /** The account. */
    private Account account;
    
    /** The pubkey. */
    private String pubkey;

    /**
     * Instantiates a new program account.
     */
    public ProgramAccount() {
    }

    /**
     * Instantiates a new program account.
     *
     * @param pa the pa
     */
    @SuppressWarnings({ "rawtypes" })
    public ProgramAccount(AbstractMap pa) {
        this.account = (Account) new Account(pa.get("account"));
        this.pubkey = (String) pa.get("pubkey");
    }

    /**
     * Gets the account.
     *
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the account.
     *
     * @param account the new account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Gets the pubkey.
     *
     * @return the pubkey
     */
    public String getPubkey() {
        return pubkey;
    }

    /**
     * Sets the pubkey.
     *
     * @param pubkey the new pubkey
     */
    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    /**
     * The Class Account.
     */
    public final class Account {
        
        /** The data. */
        private String data;
        
        /** The executable. */
        private boolean executable;
        
        /** The lamports. */
        private double lamports;
        
        /** The owner. */
        private String owner;
        
        /** The rent epoch. */
        private double rentEpoch;
        
        /** The encoding. */
        private String encoding;

        /**
         * Instantiates a new account.
         */
        public Account() {

        }

        /**
         * Instantiates a new account.
         *
         * @param acc the acc
         */
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public Account(Object acc) {
            AbstractMap account = (AbstractMap) acc;

            Object rawData = account.get("data");
            if (rawData instanceof List) {
                List<String> dataList = ((List<String>) rawData);

                this.data = dataList.get(0);
                this.encoding = (String) dataList.get(1);
            } else if (rawData instanceof String) {
                this.data = (String) rawData;
            }

            this.executable = (boolean) account.get("executable");
            this.lamports = (double) account.get("lamports");
            this.owner = (String) account.get("owner");
            this.rentEpoch = (double) account.get("rentEpoch");
        }

        /**
         * Gets the data.
         *
         * @return the data
         */
        public String getData() {
            return data;
        }

        /**
         * Sets the data.
         *
         * @param data the new data
         */
        public void setData(String data) {
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
        public double getLamports() {
            return lamports;
        }

        /**
         * Sets the lamports.
         *
         * @param lamports the new lamports
         */
        public void setLamports(double lamports) {
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
        public double getRentEpoch() {
            return rentEpoch;
        }

        /**
         * Sets the rent epoch.
         *
         * @param rentEpoch the new rent epoch
         */
        public void setRentEpoch(double rentEpoch) {
            this.rentEpoch = rentEpoch;
        }

        /**
         * Gets the encoding.
         *
         * @return the encoding
         */
        public String getEncoding() {
            return encoding;
        }

        /**
         * Sets the encoding.
         *
         * @param encoding the new encoding
         */
        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

    }
}

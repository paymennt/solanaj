package com.paymennt.solanaj.api.rpc.types;

import java.util.AbstractMap;
import java.util.List;

public class ProgramAccount {

    private Account account;
    private String pubkey;

    public ProgramAccount() {
    }

    @SuppressWarnings({ "rawtypes" })
    public ProgramAccount(AbstractMap pa) {
        this.account = (Account) new Account(pa.get("account"));
        this.pubkey = (String) pa.get("pubkey");
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public final class Account {
        private String data;
        private boolean executable;
        private double lamports;
        private String owner;
        private double rentEpoch;
        private String encoding;

        public Account() {

        }

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

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public boolean isExecutable() {
            return executable;
        }

        public void setExecutable(boolean executable) {
            this.executable = executable;
        }

        public double getLamports() {
            return lamports;
        }

        public void setLamports(double lamports) {
            this.lamports = lamports;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public double getRentEpoch() {
            return rentEpoch;
        }

        public void setRentEpoch(double rentEpoch) {
            this.rentEpoch = rentEpoch;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

    }
}

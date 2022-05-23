package com.paymennt.solanaj.data;

import java.util.List;

public class AccountMeta {
    private AccountPublicKey publicKey;
    private boolean isSigner;
    private boolean isWritable;

    public AccountMeta() {

    }

    public AccountMeta(AccountPublicKey publicKey, boolean isSigner, boolean isWritable) {
        this.publicKey = publicKey;
        this.isSigner = isSigner;
        this.isWritable = isWritable;
    }

    public static int findAccountIndex(List<AccountMeta> accountMetaList, AccountPublicKey key) {
        for (int i = 0; i < accountMetaList.size(); i++) {
            if (accountMetaList.get(i).getPublicKey().equals(key)) {
                return i;
            }
        }

        return -1;
    }

    public AccountPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(AccountPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPubkey(String key) {
        this.publicKey = new AccountPublicKey(key);
    }

    public boolean isSigner() {
        return isSigner;
    }

    public void setSigner(boolean isSigner) {
        this.isSigner = isSigner;
    }

    public boolean isWritable() {
        return isWritable;
    }

    public void setWritable(boolean isWritable) {
        this.isWritable = isWritable;
    }

}
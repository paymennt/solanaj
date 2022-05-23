/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.data;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountMeta.
 */
public class AccountMeta {
    
    /** The public key. */
    private AccountPublicKey publicKey;
    
    /** The is signer. */
    private boolean isSigner;
    
    /** The is writable. */
    private boolean isWritable;

    /**
     * Instantiates a new account meta.
     */
    public AccountMeta() {

    }

    /**
     * Instantiates a new account meta.
     *
     * @param publicKey the public key
     * @param isSigner the is signer
     * @param isWritable the is writable
     */
    public AccountMeta(AccountPublicKey publicKey, boolean isSigner, boolean isWritable) {
        this.publicKey = publicKey;
        this.isSigner = isSigner;
        this.isWritable = isWritable;
    }

    /**
     * Find account index.
     *
     * @param accountMetaList the account meta list
     * @param key the key
     * @return the int
     */
    public static int findAccountIndex(List<AccountMeta> accountMetaList, AccountPublicKey key) {
        for (int i = 0; i < accountMetaList.size(); i++) {
            if (accountMetaList.get(i).getPublicKey().equals(key)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Gets the public key.
     *
     * @return the public key
     */
    public AccountPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Sets the public key.
     *
     * @param publicKey the new public key
     */
    public void setPublicKey(AccountPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Sets the pubkey.
     *
     * @param key the new pubkey
     */
    public void setPubkey(String key) {
        this.publicKey = new AccountPublicKey(key);
    }

    /**
     * Checks if is signer.
     *
     * @return true, if is signer
     */
    public boolean isSigner() {
        return isSigner;
    }

    /**
     * Sets the signer.
     *
     * @param isSigner the new signer
     */
    public void setSigner(boolean isSigner) {
        this.isSigner = isSigner;
    }

    /**
     * Checks if is writable.
     *
     * @return true, if is writable
     */
    public boolean isWritable() {
        return isWritable;
    }

    /**
     * Sets the writable.
     *
     * @param isWritable the new writable
     */
    public void setWritable(boolean isWritable) {
        this.isWritable = isWritable;
    }

}
/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.data;

import java.util.List;

/**
 * 
 */
public class AccountMeta {
    
    /**  */
    private SolanaPublicKey publicKey;
    
    /**  */
    private boolean isSigner;
    
    /**  */
    private boolean isWritable;

    /**
     * 
     */
    public AccountMeta() {

    }

    /**
     * 
     *
     * @param publicKey 
     * @param isSigner 
     * @param isWritable 
     */
    public AccountMeta(SolanaPublicKey publicKey, boolean isSigner, boolean isWritable) {
        this.publicKey = publicKey;
        this.isSigner = isSigner;
        this.isWritable = isWritable;
    }

    /**
     * 
     *
     * @param accountMetaList 
     * @param key 
     * @return 
     */
    public static int findAccountIndex(List<AccountMeta> accountMetaList, SolanaPublicKey key) {
        for (int i = 0; i < accountMetaList.size(); i++) {
            if (accountMetaList.get(i).getPublicKey().equals(key)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 
     *
     * @return 
     */
    public SolanaPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * 
     *
     * @param publicKey 
     */
    public void setPublicKey(SolanaPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * 
     *
     * @param key 
     */
    public void setPubkey(String key) {
        this.publicKey = new SolanaPublicKey(key);
    }

    /**
     * 
     *
     * @return 
     */
    public boolean isSigner() {
        return isSigner;
    }

    /**
     * 
     *
     * @param isSigner 
     */
    public void setSigner(boolean isSigner) {
        this.isSigner = isSigner;
    }

    /**
     * 
     *
     * @return 
     */
    public boolean isWritable() {
        return isWritable;
    }

    /**
     * 
     *
     * @param isWritable 
     */
    public void setWritable(boolean isWritable) {
        this.isWritable = isWritable;
    }

}
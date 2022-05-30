/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.data;

import com.paymennt.crypto.bip32.wallet.key.HdPrivateKey;
import com.paymennt.solanaj.utils.TweetNaclFast;

/**
 * 
 */
public class SolanaAccount {
    
    /**  */
    private TweetNaclFast.Signature.KeyPair keyPair;

    /**
     * 
     */
    public SolanaAccount() {
        this.keyPair = TweetNaclFast.Signature.keyPair();
    }

    /**
     * 
     *
     * @param keyPair 
     */
    public SolanaAccount(TweetNaclFast.Signature.KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    /**
     * 
     *
     * @param privateKey 
     */
    public SolanaAccount(HdPrivateKey privateKey) {
        this.keyPair = TweetNaclFast.Signature.keyPair_fromSeed(privateKey.getPrivateKey());
    }

    /**
     * 
     *
     * @param secretKey 
     * @return 
     */
    public static SolanaAccount fromSecret(byte[] secretKey) {
        TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSecretKey(secretKey);
        return new SolanaAccount(keyPair);
    }

    /**
     * 
     *
     * @param seed 
     * @return 
     */
    public static SolanaAccount fromSeed(byte[] seed) {
        TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSeed(seed);
        return new SolanaAccount(keyPair);
    }

    /**
     * 
     *
     * @return 
     */
    public SolanaPublicKey getPublicKey() {
        return new SolanaPublicKey(keyPair.getPublicKey());
    }

    /**
     * 
     *
     * @return 
     */
    public byte[] getSecretKey() {
        return keyPair.getSecretKey();
    }

}

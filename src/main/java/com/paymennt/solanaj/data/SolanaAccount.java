/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.data;

import com.paymennt.crypto.bip32.wallet.key.HdPrivateKey;
import com.paymennt.solanaj.utils.TweetNaclFast;

// TODO: Auto-generated Javadoc
/**
 * The Class SolanaAccount.
 */
public class SolanaAccount {
    
    /** The key pair. */
    private TweetNaclFast.Signature.KeyPair keyPair;

    /**
     * Instantiates a new solana account.
     */
    public SolanaAccount() {
        this.keyPair = TweetNaclFast.Signature.keyPair();
    }

    /**
     * Instantiates a new solana account.
     *
     * @param keyPair the key pair
     */
    public SolanaAccount(TweetNaclFast.Signature.KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    /**
     * Instantiates a new solana account.
     *
     * @param privateKey the private key
     */
    public SolanaAccount(HdPrivateKey privateKey) {
        this.keyPair = TweetNaclFast.Signature.keyPair_fromSeed(privateKey.getPrivateKey());
    }

    /**
     * From secret.
     *
     * @param secretKey the secret key
     * @return the solana account
     */
    public static SolanaAccount fromSecret(byte[] secretKey) {
        TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSecretKey(secretKey);
        return new SolanaAccount(keyPair);
    }

    /**
     * From seed.
     *
     * @param seed the seed
     * @return the solana account
     */
    public static SolanaAccount fromSeed(byte[] seed) {
        TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSeed(seed);
        return new SolanaAccount(keyPair);
    }

    /**
     * Gets the public key.
     *
     * @return the public key
     */
    public AccountPublicKey getPublicKey() {
        return new AccountPublicKey(keyPair.getPublicKey());
    }

    /**
     * Gets the secret key.
     *
     * @return the secret key
     */
    public byte[] getSecretKey() {
        return keyPair.getSecretKey();
    }

}

package com.paymennt.solanaj.data;

import com.paymennt.crypto.bip32.wallet.key.HdPrivateKey;
import com.paymennt.solanaj.utils.TweetNaclFast;

public class SolanaAccount {
    private TweetNaclFast.Signature.KeyPair keyPair;

    public SolanaAccount() {
        this.keyPair = TweetNaclFast.Signature.keyPair();
    }

    public SolanaAccount(TweetNaclFast.Signature.KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public SolanaAccount(HdPrivateKey privateKey) {
        this.keyPair = TweetNaclFast.Signature.keyPair_fromSeed(privateKey.getPrivateKey());
    }

    public static SolanaAccount fromSecret(byte[] secretKey) {
        TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSecretKey(secretKey);
        return new SolanaAccount(keyPair);
    }

    public static SolanaAccount fromSeed(byte[] seed) {
        TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSeed(seed);
        return new SolanaAccount(keyPair);
    }

    public AccountPublicKey getPublicKey() {
        return new AccountPublicKey(keyPair.getPublicKey());
    }

    public byte[] getSecretKey() {
        return keyPair.getSecretKey();
    }

}

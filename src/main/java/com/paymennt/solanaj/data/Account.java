package com.paymennt.solanaj.data;

import com.paymennt.crypto.bip32.wallet.key.HdPrivateKey;
import com.paymennt.solanaj.utils.TweetNaclFast;

public class Account {
    private TweetNaclFast.Signature.KeyPair keyPair;

    public Account() {
        this.keyPair = TweetNaclFast.Signature.keyPair();
    }

    public Account(TweetNaclFast.Signature.KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public Account(HdPrivateKey privateKey) {
        this.keyPair = TweetNaclFast.Signature.keyPair_fromSecretKey(privateKey.getPrivateKey());
    }

    public static Account fromSecret(byte[] secretKey) {
        TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSecretKey(secretKey);
        return new Account(keyPair);
    }

    public static Account fromSeed(byte[] seed) {
        TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSeed(seed);
        return new Account(keyPair);
    }

    public AccountPublicKey getPublicKey() {
        return new AccountPublicKey(keyPair.getPublicKey());
    }

    public byte[] getSecretKey() {
        return keyPair.getSecretKey();
    }

}

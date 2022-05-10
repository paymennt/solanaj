package com.paymennt.solanaj.core;

import com.paymennt.crypto.core.key.ExtendedPrivateKey;
import com.paymennt.crypto.core.mnemonic.MnemonicSeed;
import com.paymennt.solanaj.utils.TweetNaclFast;

public class Account {
    private TweetNaclFast.Signature.KeyPair keyPair;

    public Account() {
        this.keyPair = TweetNaclFast.Signature.keyPair();
    }

    public Account(byte[] secretKey) {
        this.keyPair = TweetNaclFast.Signature.keyPair_fromSecretKey(secretKey);
    }

    private Account(TweetNaclFast.Signature.KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public static Account fromMnemonic(String words, String passphrase) {
        char[] mnemonicPhrase = words.toCharArray();
        MnemonicSeed mnemonicSeed = new MnemonicSeed(mnemonicPhrase, passphrase.toCharArray());
        ExtendedPrivateKey xPrv = mnemonicSeed.getMasterPrivateKey("");
        TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSeed(xPrv.getKey());
        return new Account(keyPair);
    }

    public PublicKey getPublicKey() {
        return new PublicKey(keyPair.getPublicKey());
    }

    public byte[] getSecretKey() {
        return keyPair.getSecretKey();
    }

}

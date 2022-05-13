package com.paymennt.solanaj.core;

import org.apache.commons.lang3.StringUtils;

import com.paymennt.crypto.CoinType;
import com.paymennt.crypto.bip32.Network;
import com.paymennt.crypto.bip32.wallet.Bip39;
import com.paymennt.crypto.bip32.wallet.Bip39.Chain;
import com.paymennt.crypto.bip32.wallet.Bip39.Purpose;
import com.paymennt.crypto.bip32.wallet.HdAddress;
import com.paymennt.crypto.bip39.Language;
import com.paymennt.crypto.bip39.MnemonicGenerator;
import com.paymennt.solanaj.utils.TweetNaclFast;

public class SolanaAccountBuilder {

    private String mnemonic;

    private String passphrase;

    private Integer accountIndex;

    private Integer addressIndex;

    private Chain chain;

    public SolanaAccountBuilder withMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
        return this;
    }

    public SolanaAccountBuilder withPassphrase(String passphrase) {
        this.passphrase = passphrase;
        return this;
    }

    public SolanaAccountBuilder withAccountIndex(int accountIndex) {
        this.accountIndex = accountIndex;
        return this;
    }

    public SolanaAccountBuilder withAddressIndex(int addressIndex) {
        this.addressIndex = addressIndex;
        return this;
    }

    public SolanaAccountBuilder withChain(Chain chain) {
        this.chain = chain;
        return this;
    }

    public SolanaAccount build() {

        assert StringUtils.isNotBlank(mnemonic) : "mnemonic should not be blank";
        assert accountIndex != null : "account index should not be blank";
        assert chain != null : "chain should not be blank";

        Bip39 addressGenerator = new Bip39();
        MnemonicGenerator mnemonicGen = new MnemonicGenerator();

        // get seed from the word
        byte[] seed = mnemonicGen.getSeedFromWordlist(mnemonic, passphrase, Language.ENGLISH);

        HdAddress hdAddress = addressGenerator.getRootAddressFromSeed(//
                seed, //
                Network.MAINNET, //
                CoinType.SOLANA, //
                Purpose.BIP44, //
                accountIndex, //
                chain//
        );

        if (addressIndex != null)
            hdAddress = addressGenerator.getAddress(hdAddress, addressIndex);

        byte[] privateKey = hdAddress.getPrivateKey().getPrivateKey();

        TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSeed(privateKey);
        return new SolanaAccount(keyPair);

    }

}

package com.paymennt.solanaj.core;

import java.math.BigInteger;

import com.paymennt.crypto.core.derivation.DerivationPath;
import com.paymennt.crypto.core.key.ExtendedPrivateKey;
import com.paymennt.crypto.core.mnemonic.MnemonicSeed;
import com.paymennt.solanaj.utils.TweetNaclFast;

public class Account {
	private TweetNaclFast.Signature.KeyPair keyPair;

	public Account() {
	}

	private Account(TweetNaclFast.Signature.KeyPair keyPair, ExtendedPrivateKey xPrv) {
		this.keyPair = keyPair;
	}

	public static Account fromMnemonic(String words, String passphrase, DerivationPath derivationPath,
			int addressIndex) {
		char[] mnemonicPhrase = words.toCharArray();
		MnemonicSeed mnemonicSeed = new MnemonicSeed(mnemonicPhrase, passphrase.toCharArray());
		ExtendedPrivateKey xPrv = mnemonicSeed.getMasterPrivateKey("");
		ExtendedPrivateKey addressEPK = xPrv.getExtendedPrivateKey(derivationPath)
				.childKeyDerivation(new BigInteger(Integer.toString(addressIndex)), false);
		TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSeed(addressEPK.getKey());
		return new Account(keyPair, xPrv);
	}

	public PublicKey getPublicKey() {
		return new PublicKey(keyPair.getPublicKey());
	}

	public byte[] getSecretKey() {
		return keyPair.getSecretKey();
	}

	public String getAddress() {
		return getPublicKey().toString();
	}

}

package com.paymennt.solanaj.core;

import java.math.BigInteger;

import com.paymennt.crypto.core.Coin;
import com.paymennt.crypto.core.derivation.DerivationPath;
import com.paymennt.crypto.core.derivation.DerivationPath.Chain;
import com.paymennt.crypto.core.derivation.DerivationPath.Purpose;
import com.paymennt.crypto.core.derivation.DerivationPathBuilder;
import com.paymennt.crypto.core.key.ExtendedPrivateKey;
import com.paymennt.crypto.core.mnemonic.MnemonicSeed;
import com.paymennt.crypto.solana.SolanaMnemonicSeed;
import com.paymennt.solanaj.utils.TweetNaclFast;

public class SolanaAccountBuilder {

	private String mnemonic;

	private String passphrase;

	private int accountIndex;

	private int addressIndex;

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

		DerivationPath derivationPath = new DerivationPathBuilder() //
				.withPurpose(Purpose.BIP44)//
				.withCoin(Coin.SOL)//
				.withAccount(accountIndex)//
				.withChain(chain)//
				.build();

		char[] mnemonicPhrase = mnemonic.toCharArray();
		MnemonicSeed mnemonicSeed = new SolanaMnemonicSeed(mnemonicPhrase, passphrase.toCharArray());
		ExtendedPrivateKey xPrv = mnemonicSeed.getMasterPrivateKey("")//
				.getExtendedPrivateKey(derivationPath)//
				.childKeyDerivation(new BigInteger(Integer.toString(addressIndex)), false);

		TweetNaclFast.Signature.KeyPair keyPair = TweetNaclFast.Signature.keyPair_fromSeed(xPrv.getKey());
		return new SolanaAccount(keyPair);

	}

}

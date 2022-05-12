package com.paymennt.solanaj.core;

import com.paymennt.solanaj.utils.TweetNaclFast;

public class SolanaAccount {
	private TweetNaclFast.Signature.KeyPair keyPair;

	public SolanaAccount() {
		this.keyPair = TweetNaclFast.Signature.keyPair();
	}

	public SolanaAccount(byte[] secretKey) {
		this.keyPair = TweetNaclFast.Signature.keyPair_fromSecretKey(secretKey);
	}

	public SolanaAccount(TweetNaclFast.Signature.KeyPair keyPair) {
		this.keyPair = keyPair;
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

package com.paymennt.solanaj.data;

import java.util.List;

public class SolanaTransactionInstruction {

	private List<AccountMeta> keys;
	private AccountPublicKey programId;
	private byte[] data;

	public SolanaTransactionInstruction(AccountPublicKey programId, List<AccountMeta> keys, byte[] data) {
		this.programId = programId;
		this.keys = keys;
		this.data = data;
	}

	public List<AccountMeta> getKeys() {
		return keys;
	}

	public AccountPublicKey getProgramId() {
		return programId;
	}

	public byte[] getData() {
		return data;
	}

}

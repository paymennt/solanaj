/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.program;

import java.util.ArrayList;

import com.paymennt.crypto.lib.ByteUtils;
import com.paymennt.solanaj.data.AccountMeta;
import com.paymennt.solanaj.data.AccountPublicKey;
import com.paymennt.solanaj.data.SolanaTransactionInstruction;

// TODO: Auto-generated Javadoc
/**
 * The Class SystemProgram.
 */
public class SystemProgram {
    
    /** The Constant PROGRAM_ID. */
    public static final AccountPublicKey PROGRAM_ID = new AccountPublicKey("11111111111111111111111111111111");

    /** The Constant PROGRAM_INDEX_CREATE_ACCOUNT. */
    public static final int PROGRAM_INDEX_CREATE_ACCOUNT = 0;
    
    /** The Constant PROGRAM_INDEX_TRANSFER. */
    public static final int PROGRAM_INDEX_TRANSFER = 2;

    /**
     * Transfer.
     *
     * @param fromPublicKey the from public key
     * @param toPublickKey the to publick key
     * @param lamports the lamports
     * @return the solana transaction instruction
     */
    public static SolanaTransactionInstruction transfer(
            AccountPublicKey fromPublicKey,
            AccountPublicKey toPublickKey,
            long lamports) {

        return transfer(fromPublicKey, toPublickKey, null, lamports);
    }

    /**
     * Transfer.
     *
     * @param fromPublicKey the from public key
     * @param toPublickKey the to publick key
     * @param reference the reference
     * @param lamports the lamports
     * @return the solana transaction instruction
     */
    public static SolanaTransactionInstruction transfer(
            AccountPublicKey fromPublicKey,
            AccountPublicKey toPublickKey,
            AccountPublicKey reference,
            long lamports) {
        ArrayList<AccountMeta> keys = new ArrayList<>();
        keys.add(new AccountMeta(fromPublicKey, true, true));
        keys.add(new AccountMeta(toPublickKey, false, true));
        if (reference != null)
            keys.add(new AccountMeta(reference, false, false));

        // 4 byte instruction index + 8 bytes lamports
        byte[] data = new byte[4 + 8];
        ByteUtils.uint32ToByteArrayLE((long) PROGRAM_INDEX_TRANSFER, data, 0);
        ByteUtils.int64ToByteArrayLE(lamports, data, 4);

        return new SolanaTransactionInstruction(PROGRAM_ID, keys, data);
    }

    /**
     * Creates the account.
     *
     * @param fromPublicKey the from public key
     * @param newAccountPublikkey the new account publikkey
     * @param lamports the lamports
     * @param space the space
     * @param programId the program id
     * @return the solana transaction instruction
     */
    public static SolanaTransactionInstruction createAccount(
            AccountPublicKey fromPublicKey,
            AccountPublicKey newAccountPublikkey,
            long lamports,
            long space,
            AccountPublicKey programId) {
        ArrayList<AccountMeta> keys = new ArrayList<>();
        keys.add(new AccountMeta(fromPublicKey, true, true));
        keys.add(new AccountMeta(newAccountPublikkey, true, true));

        byte[] data = new byte[4 + 8 + 8 + 32];
        ByteUtils.uint32ToByteArrayLE(PROGRAM_INDEX_CREATE_ACCOUNT, data, 0);
        ByteUtils.int64ToByteArrayLE(lamports, data, 4);
        ByteUtils.int64ToByteArrayLE(space, data, 12);
        System.arraycopy(programId.toByteArray(), 0, data, 20, 32);

        return new SolanaTransactionInstruction(PROGRAM_ID, keys, data);
    }
}

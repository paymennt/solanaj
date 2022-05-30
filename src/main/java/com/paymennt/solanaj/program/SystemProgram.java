/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.program;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import com.paymennt.crypto.lib.ByteUtils;
import com.paymennt.solanaj.data.AccountMeta;
import com.paymennt.solanaj.data.SolanaPublicKey;
import com.paymennt.solanaj.data.SolanaTransactionInstruction;


/**
 * 
 */
public class SystemProgram {

    /**  */
    public static final SolanaPublicKey PROGRAM_ID = new SolanaPublicKey("11111111111111111111111111111111");

    /**  */
    public static final int PROGRAM_INDEX_CREATE_ACCOUNT = 0;
    
    /**  */
    public static final int PROGRAM_INDEX_CLOSE_ACCOUNT = 9;

    /**  */
    public static final int PROGRAM_INDEX_TRANSFER = 2;

    /**
     * 
     *
     * @param fromPublicKey 
     * @param toPublickKey 
     * @param lamports 
     * @return 
     */
    public static SolanaTransactionInstruction transfer(
            SolanaPublicKey fromPublicKey,
            SolanaPublicKey toPublickKey,
            long lamports) {

        return transfer(fromPublicKey, toPublickKey, null, lamports);
    }

    /**
     * 
     *
     * @param fromPublicKey 
     * @param toPublickKey 
     * @param reference 
     * @param lamports 
     * @return 
     */
    public static SolanaTransactionInstruction transfer(
            SolanaPublicKey fromPublicKey,
            SolanaPublicKey toPublickKey,
            SolanaPublicKey reference,
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
     * 
     *
     * @param fromPublicKey 
     * @param newAccountPublikkey 
     * @param lamports 
     * @param space 
     * @param programId 
     * @return 
     */
    public static SolanaTransactionInstruction createAccount(
            SolanaPublicKey fromPublicKey,
            SolanaPublicKey newAccountPublikkey,
            long lamports,
            long space,
            SolanaPublicKey programId) {
        List<AccountMeta> keys = new ArrayList<>();
        keys.add(new AccountMeta(fromPublicKey, true, true));
        keys.add(new AccountMeta(newAccountPublikkey, true, true));

        byte[] data = new byte[4 + 8 + 8 + 32];
        ByteUtils.uint32ToByteArrayLE(PROGRAM_INDEX_CREATE_ACCOUNT, data, 0);
        ByteUtils.int64ToByteArrayLE(lamports, data, 4);
        ByteUtils.int64ToByteArrayLE(space, data, 12);
        System.arraycopy(programId.toByteArray(), 0, data, 20, 32);

        return new SolanaTransactionInstruction(PROGRAM_ID, keys, data);
    }

    /**
     * 
     *
     * @param account 
     * @param destination 
     * @return 
     */
    public static SolanaTransactionInstruction closeAccount(SolanaPublicKey account, SolanaPublicKey destination) {

        List<AccountMeta> keys = new ArrayList<>();

        keys.add(new AccountMeta(account, true, true));
        keys.add(new AccountMeta(destination, false, true));
        keys.add(new AccountMeta(PROGRAM_ID, false, false));

        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte) PROGRAM_INDEX_CLOSE_ACCOUNT);

        return new SolanaTransactionInstruction(PROGRAM_ID, keys, buffer.array());
    }
}

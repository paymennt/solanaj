/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.data;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.paymennt.crypto.bip32.wallet.key.HdPublicKey;
import com.paymennt.crypto.lib.Base58;
import com.paymennt.crypto.lib.ByteUtils;
import com.paymennt.crypto.lib.Sha256;
import com.paymennt.solanaj.exception.SolanajException;
import com.paymennt.solanaj.utils.TweetNaclFast;

/**
 * 
 */
public class SolanaPublicKey extends HdPublicKey {

    /**  */
    public static final int PUBLIC_KEY_LENGTH = 32;

    /**  */
    private byte[] pubkey;

    /**
     * 
     *
     * @param pubkey 
     */
    public SolanaPublicKey(String pubkey) {
        if (pubkey.length() < PUBLIC_KEY_LENGTH) {
            throw new IllegalArgumentException("Invalid public key input");
        }

        this.pubkey = Base58.decode(pubkey);
    }

    /**
     * 
     *
     * @param pubkey 
     */
    public SolanaPublicKey(byte[] pubkey) {

        if (pubkey.length > PUBLIC_KEY_LENGTH) {
            throw new IllegalArgumentException("Invalid public key input");
        }

        this.pubkey = pubkey;
    }

    /**
     * 
     *
     * @param bytes 
     * @param offset 
     * @return 
     */
    public static SolanaPublicKey readPubkey(byte[] bytes, int offset) {
        byte[] buf = ByteUtils.readBytes(bytes, offset, PUBLIC_KEY_LENGTH);
        return new SolanaPublicKey(buf);
    }

    /**
     * 
     *
     * @return 
     */
    public byte[] toByteArray() {
        return pubkey;
    }

    /**
     * 
     *
     * @return 
     */
    public String toBase58() {
        return Base58.encode(pubkey);
    }

    /**
     * 
     *
     * @param pubkey 
     * @return 
     */
    public boolean equals(SolanaPublicKey pubkey) {
        return Arrays.equals(this.pubkey, pubkey.toByteArray());
    }

    /**
     * 
     *
     * @return 
     */
    public String toString() {
        return toBase58();
    }

    /**
     * 
     *
     * @param seeds 
     * @param programId 
     * @return 
     * @throws SolanajException 
     */
    public static SolanaPublicKey createProgramAddress(List<byte[]> seeds, SolanaPublicKey programId)
            throws SolanajException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        for (byte[] seed : seeds) {
            if (seed.length > 32) {
                throw new IllegalArgumentException("Max seed length exceeded");
            }

            buffer.writeBytes(seed);
        }

        buffer.writeBytes(programId.toByteArray());
        buffer.writeBytes("ProgramDerivedAddress".getBytes());

        byte[] hash = Sha256.hash(buffer.toByteArray());

        if (TweetNaclFast.is_on_curve(hash) != 0) {
            throw new SolanajException("Invalid seeds, address must fall off the curve");
        }

        return new SolanaPublicKey(hash);
    }

    /**
     * 
     */
    public static class ProgramDerivedAddress {

        /**  */
        private SolanaPublicKey address;

        /**  */
        private int nonce;

        /**
         * 
         *
         * @param address 
         * @param nonce 
         */
        public ProgramDerivedAddress(SolanaPublicKey address, int nonce) {
            this.address = address;
            this.nonce = nonce;
        }

        /**
         * 
         *
         * @return 
         */
        public SolanaPublicKey getAddress() {
            return address;
        }

        /**
         * 
         *
         * @return 
         */
        public int getNonce() {
            return nonce;
        }

    }

    /**
     * 
     *
     * @param seeds 
     * @param programId 
     * @return 
     * @throws SolanajException 
     */
    public static ProgramDerivedAddress findProgramAddress(List<byte[]> seeds, SolanaPublicKey programId)
            throws SolanajException {
        int nonce = 255;
        SolanaPublicKey address;

        List<byte[]> seedsWithNonce = new ArrayList<>();
        seedsWithNonce.addAll(seeds);

        while (nonce != 0) {
            try {
                seedsWithNonce.add(new byte[] { (byte) nonce });
                address = createProgramAddress(seedsWithNonce, programId);
            } catch (Exception e) {
                seedsWithNonce.remove(seedsWithNonce.size() - 1);
                nonce--;
                continue;
            }

            return new ProgramDerivedAddress(address, nonce);
        }

        throw new SolanajException("Unable to find a viable program address nonce");
    }

}

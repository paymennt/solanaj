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
import com.paymennt.solanaj.utils.TweetNaclFast;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountPublicKey.
 */
public class AccountPublicKey extends HdPublicKey {

    /** The Constant PUBLIC_KEY_LENGTH. */
    public static final int PUBLIC_KEY_LENGTH = 32;

    /** The pubkey. */
    private byte[] pubkey;

    /**
     * Instantiates a new account public key.
     *
     * @param pubkey the pubkey
     */
    public AccountPublicKey(String pubkey) {
        if (pubkey.length() < PUBLIC_KEY_LENGTH) {
            throw new IllegalArgumentException("Invalid public key input");
        }

        this.pubkey = Base58.decode(pubkey);
    }

    /**
     * Instantiates a new account public key.
     *
     * @param pubkey the pubkey
     */
    public AccountPublicKey(byte[] pubkey) {

        if (pubkey.length > PUBLIC_KEY_LENGTH) {
            throw new IllegalArgumentException("Invalid public key input");
        }

        this.pubkey = pubkey;
    }

    /**
     * Read pubkey.
     *
     * @param bytes the bytes
     * @param offset the offset
     * @return the account public key
     */
    public static AccountPublicKey readPubkey(byte[] bytes, int offset) {
        byte[] buf = ByteUtils.readBytes(bytes, offset, PUBLIC_KEY_LENGTH);
        return new AccountPublicKey(buf);
    }

    /**
     * To byte array.
     *
     * @return the byte[]
     */
    public byte[] toByteArray() {
        return pubkey;
    }

    /**
     * To base 58.
     *
     * @return the string
     */
    public String toBase58() {
        return Base58.encode(pubkey);
    }

    /**
     * Equals.
     *
     * @param pubkey the pubkey
     * @return true, if successful
     */
    public boolean equals(AccountPublicKey pubkey) {
        return Arrays.equals(this.pubkey, pubkey.toByteArray());
    }

    /**
     * To string.
     *
     * @return the string
     */
    public String toString() {
        return toBase58();
    }

    /**
     * Creates the program address.
     *
     * @param seeds the seeds
     * @param programId the program id
     * @return the account public key
     * @throws Exception the exception
     */
    public static AccountPublicKey createProgramAddress(List<byte[]> seeds, AccountPublicKey programId) throws Exception {
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
            throw new Exception("Invalid seeds, address must fall off the curve");
        }

        return new AccountPublicKey(hash);
    }

    /**
     * The Class ProgramDerivedAddress.
     */
    public static class ProgramDerivedAddress {
        
        /** The address. */
        private AccountPublicKey address;
        
        /** The nonce. */
        private int nonce;

        /**
         * Instantiates a new program derived address.
         *
         * @param address the address
         * @param nonce the nonce
         */
        public ProgramDerivedAddress(AccountPublicKey address, int nonce) {
            this.address = address;
            this.nonce = nonce;
        }

        /**
         * Gets the address.
         *
         * @return the address
         */
        public AccountPublicKey getAddress() {
            return address;
        }

        /**
         * Gets the nonce.
         *
         * @return the nonce
         */
        public int getNonce() {
            return nonce;
        }

    }

    /**
     * Find program address.
     *
     * @param seeds the seeds
     * @param programId the program id
     * @return the program derived address
     * @throws Exception the exception
     */
    public static ProgramDerivedAddress findProgramAddress(List<byte[]> seeds, AccountPublicKey programId) throws Exception {
        int nonce = 255;
        AccountPublicKey address;

        List<byte[]> seedsWithNonce = new ArrayList<byte[]>();
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

        throw new Exception("Unable to find a viable program address nonce");
    }

}

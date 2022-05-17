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

public class AccountPublicKey extends HdPublicKey {

    public static final int PUBLIC_KEY_LENGTH = 32;

    private byte[] pubkey;

    public AccountPublicKey(String pubkey) {
        if (pubkey.length() < PUBLIC_KEY_LENGTH) {
            throw new IllegalArgumentException("Invalid public key input");
        }

        this.pubkey = Base58.decode(pubkey);
    }

    public AccountPublicKey(byte[] pubkey) {

        if (pubkey.length > PUBLIC_KEY_LENGTH) {
            throw new IllegalArgumentException("Invalid public key input");
        }

        this.pubkey = pubkey;
    }

    public static AccountPublicKey readPubkey(byte[] bytes, int offset) {
        byte[] buf = ByteUtils.readBytes(bytes, offset, PUBLIC_KEY_LENGTH);
        return new AccountPublicKey(buf);
    }

    public byte[] toByteArray() {
        return pubkey;
    }

    public String toBase58() {
        return Base58.encode(pubkey);
    }

    public boolean equals(AccountPublicKey pubkey) {
        return Arrays.equals(this.pubkey, pubkey.toByteArray());
    }

    public String toString() {
        return toBase58();
    }

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

    public static class ProgramDerivedAddress {
        private AccountPublicKey address;
        private int nonce;

        public ProgramDerivedAddress(AccountPublicKey address, int nonce) {
            this.address = address;
            this.nonce = nonce;
        }

        public AccountPublicKey getAddress() {
            return address;
        }

        public int getNonce() {
            return nonce;
        }

    }

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

package com.paymennt.solanaj.data;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.paymennt.crypto.lib.Base58;
import com.paymennt.crypto.lib.ShortvecEncoding;
import com.paymennt.solanaj.utils.TweetNaclFast;

public class SolanaTransaction {

    public static final int SIGNATURE_LENGTH = 64;

    private SolanaMessage message;
    private List<String> signatures;
    private byte[] serializedMessage;
    private AccountPublicKey feePayer;

    public SolanaTransaction() {
        this.message = new SolanaMessage();
        this.signatures = new ArrayList<>();
    }

    public SolanaTransaction addInstruction(SolanaTransactionInstruction instruction) {
        message.addInstruction(instruction);

        return this;
    }

    public void sign(SolanaAccount signer) {
        sign(Arrays.asList(signer));
    }

    public void sign(List<SolanaAccount> signers) {

        if (signers.isEmpty()) {
            throw new IllegalArgumentException("No signers");
        }

        if (feePayer == null) {
            feePayer = signers.get(0).getPublicKey();
        }
        message.setFeePayer(feePayer);

        serializedMessage = message.serialize();

        for (SolanaAccount signer : signers) {
            TweetNaclFast.Signature signatureProvider = new TweetNaclFast.Signature(new byte[0], signer.getSecretKey());
            byte[] signature = signatureProvider.detached(serializedMessage);

            signatures.add(Base58.encode(signature));
        }
    }

    public byte[] serialize() {
        int signaturesSize = signatures.size();
        byte[] signaturesLength = ShortvecEncoding.encodeLength(signaturesSize);

        ByteBuffer out = ByteBuffer
                .allocate(signaturesLength.length + signaturesSize * SIGNATURE_LENGTH + serializedMessage.length);

        out.put(signaturesLength);

        for (String signature : signatures) {
            byte[] rawSignature = Base58.decode(signature);
            out.put(rawSignature);
        }

        out.put(serializedMessage);

        return out.array();
    }

    /*******************************************************************************************************************
     * setters and getters
     */

    public String getSignature() {
        if (signatures.size() > 0) {
            return signatures.get(0);
        }

        return null;
    }

    public SolanaMessage getMessage() {
        return message;
    }

    public void setMessage(SolanaMessage message) {
        this.message = message;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

    public byte[] getSerializedMessage() {
        return serializedMessage;
    }

    public void setSerializedMessage(byte[] serializedMessage) {
        this.serializedMessage = serializedMessage;
    }

    public AccountPublicKey getFeePayer() {
        return feePayer;
    }

    public void setFeePayer(AccountPublicKey feePayer) {
        this.feePayer = feePayer;
    }

    public void setRecentBlockHash(String recentBlockhash) {
        message.setRecentBlockhash(recentBlockhash);
    }

}

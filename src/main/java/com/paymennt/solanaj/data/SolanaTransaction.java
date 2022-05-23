/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.data;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.paymennt.crypto.lib.Base58;
import com.paymennt.crypto.lib.ShortvecEncoding;
import com.paymennt.solanaj.utils.TweetNaclFast;

// TODO: Auto-generated Javadoc
/**
 * The Class SolanaTransaction.
 */
public class SolanaTransaction {

    /** The Constant SIGNATURE_LENGTH. */
    public static final int SIGNATURE_LENGTH = 64;

    /** The message. */
    private SolanaMessage message;
    
    /** The signatures. */
    private List<String> signatures;
    
    /** The serialized message. */
    private byte[] serializedMessage;
    
    /** The fee payer. */
    private AccountPublicKey feePayer;

    /**
     * Instantiates a new solana transaction.
     */
    public SolanaTransaction() {
        this.message = new SolanaMessage();
        this.signatures = new ArrayList<>();
    }

    /**
     * Adds the instruction.
     *
     * @param instruction the instruction
     * @return the solana transaction
     */
    public SolanaTransaction addInstruction(SolanaTransactionInstruction instruction) {
        message.addInstruction(instruction);

        return this;
    }

    /**
     * Sign.
     *
     * @param signer the signer
     */
    public void sign(SolanaAccount signer) {
        sign(Arrays.asList(signer));
    }

    /**
     * Sign.
     *
     * @param signers the signers
     */
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

    /**
     * Serialize.
     *
     * @return the byte[]
     */
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

    /**
     * *****************************************************************************************************************
     * setters and getters.
     *
     * @return the signature
     */

    public String getSignature() {
        if (signatures.size() > 0) {
            return signatures.get(0);
        }

        return null;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public SolanaMessage getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(SolanaMessage message) {
        this.message = message;
    }

    /**
     * Gets the signatures.
     *
     * @return the signatures
     */
    public List<String> getSignatures() {
        return signatures;
    }

    /**
     * Sets the signatures.
     *
     * @param signatures the new signatures
     */
    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

    /**
     * Gets the serialized message.
     *
     * @return the serialized message
     */
    public byte[] getSerializedMessage() {
        return serializedMessage;
    }

    /**
     * Sets the serialized message.
     *
     * @param serializedMessage the new serialized message
     */
    public void setSerializedMessage(byte[] serializedMessage) {
        this.serializedMessage = serializedMessage;
    }

    /**
     * Gets the fee payer.
     *
     * @return the fee payer
     */
    public AccountPublicKey getFeePayer() {
        return feePayer;
    }

    /**
     * Sets the fee payer.
     *
     * @param feePayer the new fee payer
     */
    public void setFeePayer(AccountPublicKey feePayer) {
        this.feePayer = feePayer;
    }

    /**
     * Sets the recent block hash.
     *
     * @param recentBlockhash the new recent block hash
     */
    public void setRecentBlockHash(String recentBlockhash) {
        message.setRecentBlockhash(recentBlockhash);
    }

}

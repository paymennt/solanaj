/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.data;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.paymennt.crypto.lib.Base58;
import com.paymennt.crypto.lib.ShortvecEncoding;

// TODO: Auto-generated Javadoc
/**
 * The Class SolanaMessage.
 */
public class SolanaMessage {

    /** The Constant RECENT_BLOCK_HASH_LENGTH. */
    private static final int RECENT_BLOCK_HASH_LENGTH = 32;

    /** The message header. */
    private MessageHeader messageHeader;
    
    /** The recent blockhash. */
    private String recentBlockhash;
    
    /** The account keys. */
    private AccountKeysList accountKeys;
    
    /** The instructions. */
    private List<SolanaTransactionInstruction> instructions;
    
    /** The fee payer. */
    private AccountPublicKey feePayer;
    
    /** The program ids. */
    private List<String> programIds;

    /**
     * Instantiates a new solana message.
     */
    public SolanaMessage() {
        this.programIds = new ArrayList<>();
        this.accountKeys = new AccountKeysList();
        this.instructions = new ArrayList<>();
    }

    /**
     * Adds the instruction.
     *
     * @param instruction the instruction
     * @return the solana message
     */
    public SolanaMessage addInstruction(SolanaTransactionInstruction instruction) {
        accountKeys.addAll(instruction.getKeys());
        instructions.add(instruction);

        if (!programIds.contains(instruction.getProgramId().toBase58())) {
            programIds.add(instruction.getProgramId().toBase58());
        }

        return this;
    }

    /**
     * Serialize.
     *
     * @return the byte[]
     */
    public byte[] serialize() {

        if (recentBlockhash == null) {
            throw new IllegalArgumentException("recentBlockhash required");
        }

        if (instructions.size() == 0) {
            throw new IllegalArgumentException("No instructions provided");
        }

        messageHeader = new MessageHeader();

        for (String programId : programIds) {
            accountKeys.add(new AccountMeta(new AccountPublicKey(programId), false, false));
        }
        List<AccountMeta> keysList = getAccountKeysWithFeePayer();
        int accountKeysSize = keysList.size();

        byte[] accountAddressesLength = ShortvecEncoding.encodeLength(accountKeysSize);

        int compiledInstructionsLength = 0;
        List<CompiledInstruction> compiledInstructions = new ArrayList<>();

        for (SolanaTransactionInstruction instruction : instructions) {
            int keysSize = instruction.getKeys().size();

            byte[] keyIndices = new byte[keysSize];
            for (int i = 0; i < keysSize; i++) {
                keyIndices[i] =
                        (byte) AccountMeta.findAccountIndex(keysList, instruction.getKeys().get(i).getPublicKey());
            }

            CompiledInstruction compiledInstruction = new CompiledInstruction();
            compiledInstruction.programIdIndex =
                    (byte) AccountMeta.findAccountIndex(keysList, instruction.getProgramId());
            compiledInstruction.keyIndicesCount = ShortvecEncoding.encodeLength(keysSize);
            compiledInstruction.keyIndices = keyIndices;
            compiledInstruction.dataLength = ShortvecEncoding.encodeLength(instruction.getData().length);
            compiledInstruction.data = instruction.getData();

            compiledInstructions.add(compiledInstruction);

            compiledInstructionsLength += compiledInstruction.getLength();
        }

        byte[] instructionsLength = ShortvecEncoding.encodeLength(compiledInstructions.size());

        int bufferSize = MessageHeader.HEADER_LENGTH + RECENT_BLOCK_HASH_LENGTH + accountAddressesLength.length
                + (accountKeysSize * AccountPublicKey.PUBLIC_KEY_LENGTH) + instructionsLength.length
                + compiledInstructionsLength;

        ByteBuffer out = ByteBuffer.allocate(bufferSize);

        ByteBuffer accountKeysBuff = ByteBuffer.allocate(accountKeysSize * AccountPublicKey.PUBLIC_KEY_LENGTH);
        for (AccountMeta accountMeta : keysList) {
            accountKeysBuff.put(accountMeta.getPublicKey().toByteArray());

            if (accountMeta.isSigner()) {
                messageHeader.numRequiredSignatures += 1;
                if (!accountMeta.isWritable()) {
                    messageHeader.numReadonlySignedAccounts += 1;
                }
            } else {
                if (!accountMeta.isWritable()) {
                    messageHeader.numReadonlyUnsignedAccounts += 1;
                }
            }
        }

        out.put(messageHeader.toByteArray());

        out.put(accountAddressesLength);
        out.put(accountKeysBuff.array());

        out.put(Base58.decode(recentBlockhash));

        out.put(instructionsLength);
        for (CompiledInstruction compiledInstruction : compiledInstructions) {
            out.put(compiledInstruction.programIdIndex);
            out.put(compiledInstruction.keyIndicesCount);
            out.put(compiledInstruction.keyIndices);
            out.put(compiledInstruction.dataLength);
            out.put(compiledInstruction.data);
        }

        return out.array();
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
     * Gets the account keys with fee payer.
     *
     * @return the account keys with fee payer
     */
    private List<AccountMeta> getAccountKeysWithFeePayer() {
        List<AccountMeta> keysList = accountKeys.getList();

        int feePayerIndex = AccountMeta.findAccountIndex(keysList, feePayer);

        List<AccountMeta> newList = new ArrayList<>();

        if (feePayerIndex != -1) {
            AccountMeta feePayerMeta = keysList.get(feePayerIndex);
            newList.add(new AccountMeta(feePayerMeta.getPublicKey(), true, true));
            keysList.remove(feePayerIndex);
        } else {
            newList.add(new AccountMeta(feePayer, true, true));
        }
        newList.addAll(keysList);

        return newList;
    }

    /**
     * *****************************************************************************************************************
     * setters and getters.
     *
     * @return the message header
     */

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    /**
     * Sets the message header.
     *
     * @param messageHeader the new message header
     */
    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    /**
     * Gets the recent blockhash.
     *
     * @return the recent blockhash
     */
    public String getRecentBlockhash() {
        return recentBlockhash;
    }

    /**
     * Sets the recent blockhash.
     *
     * @param recentBlockhash the new recent blockhash
     */
    public void setRecentBlockhash(String recentBlockhash) {
        this.recentBlockhash = recentBlockhash;
    }

    /**
     * Gets the instructions.
     *
     * @return the instructions
     */
    public List<SolanaTransactionInstruction> getInstructions() {
        return instructions;
    }

    /**
     * Sets the instructions.
     *
     * @param instructions the new instructions
     */
    public void setInstructions(List<SolanaTransactionInstruction> instructions) {
        this.instructions = instructions;
    }

    /**
     * Gets the program ids.
     *
     * @return the program ids
     */
    public List<String> getProgramIds() {
        return programIds;
    }

    /**
     * Sets the program ids.
     *
     * @param programIds the new program ids
     */
    public void setProgramIds(List<String> programIds) {
        this.programIds = programIds;
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
     * Sets the account keys.
     *
     * @param accountKeys the new account keys
     */
    public void setAccountKeys(AccountKeysList accountKeys) {
        this.accountKeys = accountKeys;
    }

    /**
     * Sets the account keys.
     *
     * @param accountMetas the new account keys
     */
    @JsonSetter
    public void setAccountKeys(List<AccountMeta> accountMetas) {
        this.accountKeys = new AccountKeysList();
        this.accountKeys.addAll(accountMetas);
    }

    /**
     * Gets the account keys.
     *
     * @return the account keys
     */
    public List<AccountMeta> getAccountKeys() {
        return this.accountKeys.getList();
    }

    /**
     * *****************************************************************************************************************
     * classes.
     */

    public static class MessageHeader {
        
        /** The Constant HEADER_LENGTH. */
        static final int HEADER_LENGTH = 3;

        /** The num required signatures. */
        byte numRequiredSignatures = 0;
        
        /** The num readonly signed accounts. */
        byte numReadonlySignedAccounts = 0;
        
        /** The num readonly unsigned accounts. */
        byte numReadonlyUnsignedAccounts = 0;

        /**
         * To byte array.
         *
         * @return the byte[]
         */
        byte[] toByteArray() {
            return new byte[] { numRequiredSignatures, numReadonlySignedAccounts, numReadonlyUnsignedAccounts };
        }

        /**
         * *****************************************************************************************************************
         * setters and getters.
         *
         * @return the num required signatures
         */

        public byte getNumRequiredSignatures() {
            return numRequiredSignatures;
        }

        /**
         * Sets the num required signatures.
         *
         * @param numRequiredSignatures the new num required signatures
         */
        public void setNumRequiredSignatures(byte numRequiredSignatures) {
            this.numRequiredSignatures = numRequiredSignatures;
        }

        /**
         * Gets the num readonly signed accounts.
         *
         * @return the num readonly signed accounts
         */
        public byte getNumReadonlySignedAccounts() {
            return numReadonlySignedAccounts;
        }

        /**
         * Sets the num readonly signed accounts.
         *
         * @param numReadonlySignedAccounts the new num readonly signed accounts
         */
        public void setNumReadonlySignedAccounts(byte numReadonlySignedAccounts) {
            this.numReadonlySignedAccounts = numReadonlySignedAccounts;
        }

        /**
         * Gets the num readonly unsigned accounts.
         *
         * @return the num readonly unsigned accounts
         */
        public byte getNumReadonlyUnsignedAccounts() {
            return numReadonlyUnsignedAccounts;
        }

        /**
         * Sets the num readonly unsigned accounts.
         *
         * @param numReadonlyUnsignedAccounts the new num readonly unsigned accounts
         */
        public void setNumReadonlyUnsignedAccounts(byte numReadonlyUnsignedAccounts) {
            this.numReadonlyUnsignedAccounts = numReadonlyUnsignedAccounts;
        }

    }

    /**
     * The Class CompiledInstruction.
     */
    private class CompiledInstruction {
        
        /** The program id index. */
        byte programIdIndex;
        
        /** The key indices count. */
        byte[] keyIndicesCount;
        
        /** The key indices. */
        byte[] keyIndices;
        
        /** The data length. */
        byte[] dataLength;
        
        /** The data. */
        byte[] data;

        /**
         * Gets the length.
         *
         * @return the length
         */
        int getLength() {
            // 1 = programIdIndex length
            return 1 + keyIndicesCount.length + keyIndices.length + dataLength.length + data.length;
        }
    }

}

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


/**
 * 
 */
public class SolanaMessage {

    /**  */
    private static final int RECENT_BLOCK_HASH_LENGTH = 32;

    /**  */
    private MessageHeader messageHeader;
    
    /**  */
    private String recentBlockhash;
    
    /**  */
    private AccountKeysList accountKeys;
    
    /**  */
    private List<SolanaTransactionInstruction> instructions;
    
    /**  */
    private SolanaPublicKey feePayer;
    
    /**  */
    private List<String> programIds;

    /**
     * 
     */
    public SolanaMessage() {
        this.programIds = new ArrayList<>();
        this.accountKeys = new AccountKeysList();
        this.instructions = new ArrayList<>();
    }

    /**
     * 
     *
     * @param instruction 
     * @return 
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
     * 
     *
     * @return 
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
            accountKeys.add(new AccountMeta(new SolanaPublicKey(programId), false, false));
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
                + (accountKeysSize * SolanaPublicKey.PUBLIC_KEY_LENGTH) + instructionsLength.length
                + compiledInstructionsLength;

        ByteBuffer out = ByteBuffer.allocate(bufferSize);

        ByteBuffer accountKeysBuff = ByteBuffer.allocate(accountKeysSize * SolanaPublicKey.PUBLIC_KEY_LENGTH);
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
     * 
     *
     * @param feePayer 
     */
    public void setFeePayer(SolanaPublicKey feePayer) {
        this.feePayer = feePayer;
    }

    /**
     * 
     *
     * @return 
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
     * 
     *
     * @return 
     */

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    /**
     * 
     *
     * @param messageHeader 
     */
    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    /**
     * 
     *
     * @return 
     */
    public String getRecentBlockhash() {
        return recentBlockhash;
    }

    /**
     * 
     *
     * @param recentBlockhash 
     */
    public void setRecentBlockhash(String recentBlockhash) {
        this.recentBlockhash = recentBlockhash;
    }

    /**
     * 
     *
     * @return 
     */
    public List<SolanaTransactionInstruction> getInstructions() {
        return instructions;
    }

    /**
     * 
     *
     * @param instructions 
     */
    public void setInstructions(List<SolanaTransactionInstruction> instructions) {
        this.instructions = instructions;
    }

    /**
     * 
     *
     * @return 
     */
    public List<String> getProgramIds() {
        return programIds;
    }

    /**
     * 
     *
     * @param programIds 
     */
    public void setProgramIds(List<String> programIds) {
        this.programIds = programIds;
    }

    /**
     * 
     *
     * @return 
     */
    public SolanaPublicKey getFeePayer() {
        return feePayer;
    }

    /**
     * 
     *
     * @param accountKeys 
     */
    public void setAccountKeys(AccountKeysList accountKeys) {
        this.accountKeys = accountKeys;
    }

    /**
     * 
     *
     * @param accountMetas 
     */
    @JsonSetter
    public void setAccountKeys(List<AccountMeta> accountMetas) {
        this.accountKeys = new AccountKeysList();
        this.accountKeys.addAll(accountMetas);
    }

    /**
     * 
     *
     * @return 
     */
    public List<AccountMeta> getAccountKeys() {
        return this.accountKeys.getList();
    }

    /**
     * 
     */

    public static class MessageHeader {
        
        /**  */
        static final int HEADER_LENGTH = 3;

        /**  */
        byte numRequiredSignatures = 0;
        
        /**  */
        byte numReadonlySignedAccounts = 0;
        
        /**  */
        byte numReadonlyUnsignedAccounts = 0;

        /**
         * 
         *
         * @return 
         */
        byte[] toByteArray() {
            return new byte[] { numRequiredSignatures, numReadonlySignedAccounts, numReadonlyUnsignedAccounts };
        }

        /**
         * 
         *
         * @return 
         */

        public byte getNumRequiredSignatures() {
            return numRequiredSignatures;
        }

        /**
         * 
         *
         * @param numRequiredSignatures 
         */
        public void setNumRequiredSignatures(byte numRequiredSignatures) {
            this.numRequiredSignatures = numRequiredSignatures;
        }

        /**
         * 
         *
         * @return 
         */
        public byte getNumReadonlySignedAccounts() {
            return numReadonlySignedAccounts;
        }

        /**
         * 
         *
         * @param numReadonlySignedAccounts 
         */
        public void setNumReadonlySignedAccounts(byte numReadonlySignedAccounts) {
            this.numReadonlySignedAccounts = numReadonlySignedAccounts;
        }

        /**
         * 
         *
         * @return 
         */
        public byte getNumReadonlyUnsignedAccounts() {
            return numReadonlyUnsignedAccounts;
        }

        /**
         * 
         *
         * @param numReadonlyUnsignedAccounts 
         */
        public void setNumReadonlyUnsignedAccounts(byte numReadonlyUnsignedAccounts) {
            this.numReadonlyUnsignedAccounts = numReadonlyUnsignedAccounts;
        }

    }

    /**
     * 
     */
    private class CompiledInstruction {
        
        /**  */
        byte programIdIndex;
        
        /**  */
        byte[] keyIndicesCount;
        
        /**  */
        byte[] keyIndices;
        
        /**  */
        byte[] dataLength;
        
        /**  */
        byte[] data;

        /**
         * 
         *
         * @return 
         */
        int getLength() {
            // 1 = programIdIndex length
            return 1 + keyIndicesCount.length + keyIndices.length + dataLength.length + data.length;
        }
    }

}

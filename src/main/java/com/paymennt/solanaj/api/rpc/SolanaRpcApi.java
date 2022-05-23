/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.paymennt.solanaj.api.rpc.types.AccountInfo;
import com.paymennt.solanaj.api.rpc.types.ConfigObjects.ConfirmedSignFAddr2;
import com.paymennt.solanaj.api.rpc.types.ConfigObjects.Filter;
import com.paymennt.solanaj.api.rpc.types.ConfigObjects.Memcmp;
import com.paymennt.solanaj.api.rpc.types.ConfigObjects.ProgramAccountConfig;
import com.paymennt.solanaj.api.rpc.types.ConfirmedTransaction;
import com.paymennt.solanaj.api.rpc.types.ProgramAccount;
import com.paymennt.solanaj.api.rpc.types.RecentBlockhash;
import com.paymennt.solanaj.api.rpc.types.RpcConfig;
import com.paymennt.solanaj.api.rpc.types.RpcFeesResult;
import com.paymennt.solanaj.api.rpc.types.RpcResultTypes.ValueLong;
import com.paymennt.solanaj.api.rpc.types.RpcSendTransactionConfig;
import com.paymennt.solanaj.api.rpc.types.RpcSendTransactionConfig.Encoding;
import com.paymennt.solanaj.api.rpc.types.RpcSignitureStatusResult;
import com.paymennt.solanaj.api.rpc.types.RpcStatusConfig;
import com.paymennt.solanaj.api.rpc.types.SignatureInformation;
import com.paymennt.solanaj.api.rpc.types.SolanaCommitment;
import com.paymennt.solanaj.data.AccountPublicKey;
import com.paymennt.solanaj.data.SolanaMessage;
import com.paymennt.solanaj.data.SolanaTransaction;

// TODO: Auto-generated Javadoc
/**
 * The Class SolanaRpcApi.
 */
public class SolanaRpcApi {
    
    /** The client. */
    private SolanaRpcClient client;

    /**
     * Instantiates a new solana rpc api.
     *
     * @param client the client
     */
    public SolanaRpcApi(SolanaRpcClient client) {
        this.client = client;
    }

    /**
     * Gets the recent blockhash.
     *
     * @return the recent blockhash
     */
    public String getRecentBlockhash() {
        return client.call("getRecentBlockhash", null, RecentBlockhash.class).getRecentBlockhash();
    }

    /**
     * Send transaction.
     *
     * @param transaction the transaction
     * @return the string
     */
    public String sendTransaction(SolanaTransaction transaction) {
        byte[] serializedTransaction = transaction.serialize();

        String base64Trx = Base64.getEncoder().encodeToString(serializedTransaction);

        List<Object> params = new ArrayList<>();

        params.add(base64Trx);
        params.add(new RpcSendTransactionConfig());

        return client.call("sendTransaction", params, String.class);
    }

    /**
     * Gets the balance.
     *
     * @param address the address
     * @return the balance
     */
    public long getBalance(String address) {
        List<Object> params = new ArrayList<>();
        params.add(address);
        return client.call("getBalance", params, ValueLong.class).getValue();
    }

    /**
     * Gets the transaction.
     *
     * @param signature the signature
     * @return the transaction
     */
    public ConfirmedTransaction getTransaction(String signature) {
        List<Object> params = new ArrayList<>();

        params.add(signature);
        params.add(new RpcConfig(SolanaCommitment.confirmed, "jsonParsed"));

        return client.call("getTransaction", params, ConfirmedTransaction.class);
    }

    /**
     * Gets the transactions.
     *
     * @param signatures the signatures
     * @return the transactions
     */
    public List<ConfirmedTransaction> getTransactions(List<String> signatures) {

        List<List<Object>> paramsBatch = new ArrayList<>();

        for (String signature : signatures) {
            List<Object> params = new ArrayList<>();
            params.add(signature);
            params.add(new RpcConfig(SolanaCommitment.confirmed, "jsonParsed"));
            paramsBatch.add(params);
        }

        return client.callBatch("getTransaction", paramsBatch, ConfirmedTransaction.class);
    }

    /**
     * Gets the signatures for address.
     *
     * @param key the key
     * @param limit the limit
     * @return the signatures for address
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<SignatureInformation> getSignaturesForAddress(String key, int limit) {
        List<Object> params = new ArrayList<>();

        params.add(key);
        params.add(new ConfirmedSignFAddr2(limit));

        List<AbstractMap> rawResult = client.call("getSignaturesForAddress", params, List.class);

        List<SignatureInformation> result = new ArrayList<>();
        for (AbstractMap item : rawResult) {
            result.add(new SignatureInformation(item));
        }

        return result;
    }

    /**
     * Gets the program accounts.
     *
     * @param account the account
     * @param offset the offset
     * @param bytes the bytes
     * @return the program accounts
     */
    public List<ProgramAccount> getProgramAccounts(AccountPublicKey account, long offset, String bytes) {
        List<Object> filters = new ArrayList<>();
        filters.add(new Filter(new Memcmp(offset, bytes)));

        ProgramAccountConfig programAccountConfig = new ProgramAccountConfig(filters);
        return getProgramAccounts(account, programAccountConfig);
    }

    /**
     * Gets the program accounts.
     *
     * @param account the account
     * @return the program accounts
     */
    public List<ProgramAccount> getProgramAccounts(AccountPublicKey account) {
        return getProgramAccounts(account, new ProgramAccountConfig(Encoding.base64));
    }

    /**
     * Gets the program accounts.
     *
     * @param account the account
     * @param programAccountConfig the program account config
     * @return the program accounts
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<ProgramAccount> getProgramAccounts(
            AccountPublicKey account,
            ProgramAccountConfig programAccountConfig) {
        List<Object> params = new ArrayList<>();

        params.add(account.toString());

        if (programAccountConfig != null) {
            params.add(programAccountConfig);
        }

        List<AbstractMap> rawResult = client.call("getProgramAccounts", params, List.class);

        List<ProgramAccount> result = new ArrayList<>();
        for (AbstractMap item : rawResult) {
            result.add(new ProgramAccount(item));
        }

        return result;
    }

    /**
     * Gets the account info.
     *
     * @param account the account
     * @return the account info
     */
    public AccountInfo getAccountInfo(AccountPublicKey account) {
        List<Object> params = new ArrayList<>();

        params.add(account.toString());
        params.add(new RpcSendTransactionConfig());

        return client.call("getAccountInfo", params, AccountInfo.class);
    }

    /**
     * Gets the minimum balance for rent exemption.
     *
     * @param dataLength the data length
     * @return the minimum balance for rent exemption
     */
    public long getMinimumBalanceForRentExemption(long dataLength) {
        List<Object> params = new ArrayList<>();

        params.add(dataLength);

        return client.call("getMinimumBalanceForRentExemption", params, Long.class);
    }

    /**
     * Gets the block time.
     *
     * @param block the block
     * @return the block time
     */
    public long getBlockTime(long block) {
        List<Object> params = new ArrayList<>();

        params.add(block);

        return client.call("getBlockTime", params, Long.class);
    }

    /**
     * Request airdrop.
     *
     * @param address the address
     * @param lamports the lamports
     * @return the string
     */
    public String requestAirdrop(AccountPublicKey address, long lamports) {
        List<Object> params = new ArrayList<>();

        params.add(address.toString());
        params.add(lamports);

        return client.call("requestAirdrop", params, String.class);
    }

    /**
     * Gets the fees.
     *
     * @param message the message
     * @return the fees
     */
    public RpcFeesResult getFees(SolanaMessage message) {
        message.setRecentBlockhash(getRecentBlockhash());
        byte[] serializedTransaction = message.serialize();

        String base64Message = Base64.getEncoder().encodeToString(serializedTransaction);

        List<Object> params = new ArrayList<>();

        params.add(base64Message);
        params.add(new RpcConfig(SolanaCommitment.confirmed, "jsonParsed"));

        return client.call("getFeeForMessage", params, RpcFeesResult.class);
    }

    /**
     * Gets the signature statuses.
     *
     * @param signatures the signatures
     * @return the signature statuses
     */
    public RpcSignitureStatusResult getSignatureStatuses(List<String> signatures) {
        List<Object> params = new ArrayList<>();
        params.add(signatures.toArray());
        params.add(new RpcStatusConfig());

        return client.call("getSignatureStatuses", params, RpcSignitureStatusResult.class);
    }

}

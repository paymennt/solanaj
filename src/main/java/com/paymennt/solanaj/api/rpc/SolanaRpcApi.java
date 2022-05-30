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
import com.paymennt.solanaj.api.rpc.types.ConfigObjects.SignaturesForAddress;
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
import com.paymennt.solanaj.data.SolanaPublicKey;
import com.paymennt.solanaj.data.SolanaMessage;
import com.paymennt.solanaj.data.SolanaTransaction;


/**
 * 
 */
public class SolanaRpcApi {
    
    /**  */
    private SolanaRpcClient client;

    /**
     * 
     *
     * @param client 
     */
    public SolanaRpcApi(SolanaRpcClient client) {
        this.client = client;
    }

    /**
     * 
     *
     * @return 
     */
    public String getRecentBlockhash() {
        return client.call("getRecentBlockhash", null, RecentBlockhash.class).getRecentBlockhash();
    }

    /**
     * 
     *
     * @param transaction 
     * @return 
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
     * 
     *
     * @param address 
     * @return 
     */
    public long getBalance(String address) {
        List<Object> params = new ArrayList<>();
        params.add(address);
        return client.call("getBalance", params, ValueLong.class).getValue();
    }

    /**
     * 
     *
     * @param signature 
     * @return 
     */
    public ConfirmedTransaction getTransaction(String signature) {
        List<Object> params = new ArrayList<>();

        params.add(signature);
        params.add(new RpcConfig(SolanaCommitment.confirmed, "jsonParsed"));

        return client.call("getTransaction", params, ConfirmedTransaction.class);
    }

    /**
     * 
     *
     * @param signatures 
     * @return 
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
     * 
     *
     * @param key 
     * @param limit 
     * @return 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<SignatureInformation> getSignaturesForAddress(String key, int limit) {
        List<Object> params = new ArrayList<>();

        params.add(key);
        params.add(new SignaturesForAddress(limit, SolanaCommitment.confirmed));

        List<AbstractMap> rawResult = client.call("getSignaturesForAddress", params, List.class);

        List<SignatureInformation> result = new ArrayList<>();
        for (AbstractMap item : rawResult) {
            result.add(new SignatureInformation(item));
        }

        return result;
    }

    /**
     * 
     *
     * @param account 
     * @param offset 
     * @param bytes 
     * @return 
     */
    public List<ProgramAccount> getProgramAccounts(SolanaPublicKey account, long offset, String bytes) {
        List<Object> filters = new ArrayList<>();
        filters.add(new Filter(new Memcmp(offset, bytes)));

        ProgramAccountConfig programAccountConfig = new ProgramAccountConfig(filters);
        return getProgramAccounts(account, programAccountConfig);
    }

    /**
     * 
     *
     * @param account 
     * @return 
     */
    public List<ProgramAccount> getProgramAccounts(SolanaPublicKey account) {
        return getProgramAccounts(account, new ProgramAccountConfig(Encoding.base64));
    }

    /**
     * 
     *
     * @param account 
     * @param programAccountConfig 
     * @return 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<ProgramAccount> getProgramAccounts(
            SolanaPublicKey account,
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
     * 
     *
     * @param account 
     * @return 
     */
    public AccountInfo getAccountInfo(SolanaPublicKey account) {
        List<Object> params = new ArrayList<>();

        params.add(account.toString());
        params.add(new RpcSendTransactionConfig());

        return client.call("getAccountInfo", params, AccountInfo.class);
    }

    /**
     * 
     *
     * @param dataLength 
     * @return 
     */
    public long getMinimumBalanceForRentExemption(long dataLength) {
        List<Object> params = new ArrayList<>();

        params.add(dataLength);

        return client.call("getMinimumBalanceForRentExemption", params, Long.class);
    }

    /**
     * 
     *
     * @param block 
     * @return 
     */
    public long getBlockTime(long block) {
        List<Object> params = new ArrayList<>();

        params.add(block);

        return client.call("getBlockTime", params, Long.class);
    }

    /**
     * 
     *
     * @param address 
     * @param lamports 
     * @return 
     */
    public String requestAirdrop(SolanaPublicKey address, long lamports) {
        List<Object> params = new ArrayList<>();

        params.add(address.toString());
        params.add(lamports);

        return client.call("requestAirdrop", params, String.class);
    }

    /**
     * 
     *
     * @param message 
     * @return 
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
     * 
     *
     * @param signatures 
     * @return 
     */
    public RpcSignitureStatusResult getSignatureStatuses(List<String> signatures) {
        List<Object> params = new ArrayList<>();
        params.add(signatures.toArray());
        params.add(new RpcStatusConfig());

        return client.call("getSignatureStatuses", params, RpcSignitureStatusResult.class);
    }

}

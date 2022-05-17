package com.paymennt.solanaj.api.rpc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.paymennt.solanaj.api.rpc.types.RpcFeesConfig;
import com.paymennt.solanaj.api.rpc.types.RpcFeesResult;
import com.paymennt.solanaj.api.rpc.types.RpcResultTypes.ValueLong;
import com.paymennt.solanaj.api.rpc.types.RpcSendTransactionConfig;
import com.paymennt.solanaj.api.rpc.types.RpcSendTransactionConfig.Encoding;
import com.paymennt.solanaj.api.rpc.types.SignatureInformation;
import com.paymennt.solanaj.api.ws.SubscriptionWebSocketClient;
import com.paymennt.solanaj.api.ws.listener.NotificationEventListener;
import com.paymennt.solanaj.data.Account;
import com.paymennt.solanaj.data.AccountPublicKey;
import com.paymennt.solanaj.data.SolanaTransaction;

public class RpcApi {
    private RpcClient client;

    public RpcApi(RpcClient client) {
        this.client = client;
    }

    public String getRecentBlockhash() throws RpcException {
        return client.call("getRecentBlockhash", null, RecentBlockhash.class).getRecentBlockhash();
    }

    public String sendTransaction(SolanaTransaction transaction, Account signer) throws RpcException {
        return sendTransaction(transaction, Arrays.asList(signer));
    }

    public String sendTransaction(SolanaTransaction transaction, List<Account> signers) throws RpcException {
        String recentBlockhash = getRecentBlockhash();
        transaction.setRecentBlockHash(recentBlockhash);
        transaction.sign(signers);
        byte[] serializedTransaction = transaction.serialize();

        String base64Trx = Base64.getEncoder().encodeToString(serializedTransaction);

        List<Object> params = new ArrayList<>();

        params.add(base64Trx);
        params.add(new RpcSendTransactionConfig());

        return client.call("sendTransaction", params, String.class);
    }

    public void sendAndConfirmTransaction(
            SolanaTransaction transaction,
            List<Account> signers,
            NotificationEventListener listener)
            throws RpcException {
        String signature = sendTransaction(transaction, signers);

        SubscriptionWebSocketClient subClient = SubscriptionWebSocketClient.getInstance(client.getEndpoint());
        subClient.signatureSubscribe(signature, listener);
    }

    public long getBalance(String address) throws RpcException {
        List<Object> params = new ArrayList<>();
        params.add(address);
        return client.call("getBalance", params, ValueLong.class).getValue();
    }

    public ConfirmedTransaction getTransaction(String signature) throws RpcException {
        List<Object> params = new ArrayList<>();

        params.add(signature);
        // TODO jsonParsed, base58, base64
        // the default encoding is JSON
        // params.add("json");

        return client.call("getTransaction", params, ConfirmedTransaction.class);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<SignatureInformation> getSignaturesForAddress(String key, int limit) throws RpcException {
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

    public List<ProgramAccount> getProgramAccounts(AccountPublicKey account, long offset, String bytes) throws RpcException {
        List<Object> filters = new ArrayList<>();
        filters.add(new Filter(new Memcmp(offset, bytes)));

        ProgramAccountConfig programAccountConfig = new ProgramAccountConfig(filters);
        return getProgramAccounts(account, programAccountConfig);
    }

    public List<ProgramAccount> getProgramAccounts(AccountPublicKey account) throws RpcException {
        return getProgramAccounts(account, new ProgramAccountConfig(Encoding.base64));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<ProgramAccount> getProgramAccounts(AccountPublicKey account, ProgramAccountConfig programAccountConfig)
            throws RpcException {
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

    public AccountInfo getAccountInfo(AccountPublicKey account) throws RpcException {
        List<Object> params = new ArrayList<>();

        params.add(account.toString());
        params.add(new RpcSendTransactionConfig());

        return client.call("getAccountInfo", params, AccountInfo.class);
    }

    public long getMinimumBalanceForRentExemption(long dataLength) throws RpcException {
        List<Object> params = new ArrayList<>();

        params.add(dataLength);

        return client.call("getMinimumBalanceForRentExemption", params, Long.class);
    }

    public long getBlockTime(long block) throws RpcException {
        List<Object> params = new ArrayList<>();

        params.add(block);

        return client.call("getBlockTime", params, Long.class);
    }

    public String requestAirdrop(AccountPublicKey address, long lamports) throws RpcException {
        List<Object> params = new ArrayList<>();

        params.add(address.toString());
        params.add(lamports);

        return client.call("requestAirdrop", params, String.class);
    }

    public RpcFeesResult getFees(SolanaTransaction transaction, Account signer) throws RpcException {
        return getFees(transaction, Arrays.asList(signer));
    }

    public RpcFeesResult getFees(SolanaTransaction transaction, List<Account> signers) throws RpcException {
        String recentBlockhash = getRecentBlockhash();
        transaction.setRecentBlockHash(recentBlockhash);
        transaction.sign(signers);
        byte[] serializedTransaction = transaction.getMessage().serialize();

        String base64Message = Base64.getEncoder().encodeToString(serializedTransaction);

        List<Object> params = new ArrayList<>();

        params.add(base64Message);
        params.add(new RpcFeesConfig());

        return client.call("getFeeForMessage", params, RpcFeesResult.class);
    }

}

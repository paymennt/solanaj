/**
 * 
 */
package com.paymennt.solanaj;

import java.util.List;

import com.paymennt.crypto.bip32.wallet.key.HdPrivateKey;
import com.paymennt.solanaj.api.data.Account;
import com.paymennt.solanaj.api.data.PublicKey;
import com.paymennt.solanaj.api.data.Transaction;
import com.paymennt.solanaj.api.program.SystemProgram;
import com.paymennt.solanaj.api.rpc.Cluster;
import com.paymennt.solanaj.api.rpc.RpcClient;
import com.paymennt.solanaj.api.rpc.RpcException;
import com.paymennt.solanaj.api.rpc.types.SignatureInformation;
import com.paymennt.solanaj.api.ws.SubscriptionWebSocketClient;

/**
 * @author asendar
 *
 */
public class SolanaClient {

    private Cluster cluster;
    private RpcClient client;

    public SolanaClient(Cluster cluster) {
        this.cluster = cluster;
        this.client = new RpcClient(cluster);
    }

    public SubscriptionWebSocketClient getWebsocket() {
        return SubscriptionWebSocketClient.getInstance(cluster.getEndpoint());
    }

    public void subscribeUpdates(String walletAddress, SolanaEventListener listener) {
        getWebsocket().accountSubscribe(walletAddress, data -> listener.onEvent());
    }

    public void subscribeAccountUpdates(String address, SolanaEventListener listener) {
        getWebsocket().accountSubscribe(address, data -> listener.onEvent());
    }

    public void unsubscribeAccountUpdates(String walletAddress) {
        getWebsocket().accountUnsubscribe(walletAddress);
    }

    public void programSubscribe(String walletAddress, SolanaEventListener listener) {
        getWebsocket().programSubscribe(walletAddress, data -> listener.onEvent());
    }

    public String transfer(HdPrivateKey privateKey, String recipient, long amount) throws RpcException {

        Account account = new Account(privateKey);

        PublicKey fromPublicKey = account.getPublicKey();
        PublicKey toPublickKey = new PublicKey(recipient);

        long fees = getTransferFees(privateKey, recipient, amount);

        Transaction transaction = new Transaction();
        transaction.addInstruction(SystemProgram.transfer(fromPublicKey, toPublickKey, amount - fees));

        return client.getApi().sendTransaction(transaction, account);
    }

    public String transferAllFunds(HdPrivateKey privateKey, String recipient) throws RpcException {
        Account account = new Account(privateKey);
        long amount = getBalance(account.getPublicKey().toBase58());
        return this.transfer(privateKey, recipient, amount);
    }

    public long getTransferFees(HdPrivateKey privateKey, String recipient, long amount) throws RpcException {
        Account account = new Account(privateKey);
        PublicKey fromPublicKey = account.getPublicKey();
        PublicKey toPublickKey = new PublicKey(recipient);

        Transaction transaction = new Transaction();
        transaction.addInstruction(SystemProgram.transfer(fromPublicKey, toPublickKey, amount));

        return client.getApi().getFees(transaction, account).getValue();
    }

    public long getBalance(String walletAddress) throws RpcException {
        return client.getApi().getBalance(walletAddress);
    }

    public List<SignatureInformation> getTransactions(String walletAddress) throws RpcException {
        return client.getApi().getSignaturesForAddress(walletAddress, 10);
    }

    public static interface SolanaEventListener {
        public void onEvent();
    }

}

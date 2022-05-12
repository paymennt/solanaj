/**
 * 
 */
package com.paymennt.solanaj;

import com.paymennt.solanaj.core.PublicKey;
import com.paymennt.solanaj.core.SolanaAccount;
import com.paymennt.solanaj.core.Transaction;
import com.paymennt.solanaj.programs.SystemProgram;
import com.paymennt.solanaj.rpc.Cluster;
import com.paymennt.solanaj.rpc.RpcClient;
import com.paymennt.solanaj.rpc.RpcException;
import com.paymennt.solanaj.ws.SubscriptionWebSocketClient;

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

	public void subscribeAccountUpdates(SolanaAccount account, SolanaEventListener listener) {
		getWebsocket().accountSubscribe(account.getAddress(), (data) -> listener.onEvent());
	}

	public void unsubscribeAccountUpdates(SolanaAccount account) {
		getWebsocket().accountUnsubscribe(account.getAddress());
	}

	public String doTransfer(SolanaAccount account, String recipient, long amount) throws RpcException {

		PublicKey fromPublicKey = new PublicKey(account.getAddress());
		PublicKey toPublickKey = new PublicKey(recipient);

		long fees = getTransferFees(account, recipient, amount);

		Transaction transaction = new Transaction();
		transaction.addInstruction(SystemProgram.transfer(fromPublicKey, toPublickKey, amount - fees));

		return client.getApi().sendTransaction(transaction, account);
	}

	public long getTransferFees(SolanaAccount account, String recipient, long amount) throws RpcException {
		PublicKey fromPublicKey = new PublicKey(account.getAddress());
		PublicKey toPublickKey = new PublicKey(recipient);

		Transaction transaction = new Transaction();
		transaction.addInstruction(SystemProgram.transfer(fromPublicKey, toPublickKey, amount));

		return client.getApi().getFees(transaction, account).getValue();
	}

	public long getBalance(SolanaAccount account) throws RpcException {
		return client.getApi().getBalance(account.getPublicKey());
	}

	public static interface SolanaEventListener {
		public void onEvent();
	}

}

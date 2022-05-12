/**
 * 
 */
package com.paymennt.examples;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.paymennt.crypto.core.derivation.DerivationPath.Chain;
import com.paymennt.solanaj.SolanaClient;
import com.paymennt.solanaj.core.SolanaAccount;
import com.paymennt.solanaj.core.SolanaAccountBuilder;
import com.paymennt.solanaj.rpc.Cluster;

/**
 * @author asendar
 *
 */
public class FullExample {

//	public static void main(String[] args) throws RpcException {
//
//		Security.addProvider(new BouncyCastleProvider());
//
//		String mnemonic = "swing brown camera enter burden awful rent shock mobile wisdom increase scissors";
//		String passphrase = "carry";
//
//		// create an account
//		SolanaAccount account = new SolanaAccountBuilder()//
//				.withMnemonic(mnemonic)//
//				.withPassphrase(passphrase)//
//				.withAccountIndex(0)//
//				.withAddressIndex(0)//
//				.withChain(Chain.EXTERNAL)//
//				.build();
//
//		String recipient = "2Ym21uN3GqvFwkrvoWKwcTwqRjk1pFVSS6RFguNgmYdV";
//
//		SolanaClient client = new SolanaClient(Cluster.TESTNET);
//		long balance = client.getBalance(account);
//
//		String hash = client.doTransfer(account, recipient, balance);
//
//		System.out.println("transaction hash: " + hash);
//		System.out.println(
//				"view on browser: " + String.format("https://solanabeach.io/transaction/%s?cluster=testnet", hash));
//		System.out.println("---------------------------------------------------------------");
//
//	}

	public static void main(String[] args) throws InterruptedException {
		Security.addProvider(new BouncyCastleProvider());

		String mnemonic = "chase forward bone horn faith kitten steel bind mutual tide wreck novel priority card saddle";
		String passphrase = "kitten";

		// E7P8yjpbjsGMLDuoK7hm43aAumyJtQ6z1VsZj4KpZ33a
		SolanaAccount account = new SolanaAccountBuilder()//
				.withMnemonic(mnemonic)//
				.withPassphrase(passphrase)//
				.withAccountIndex(0)//
				.withAddressIndex(0)//
				.withChain(Chain.EXTERNAL)//
				.build();

		SolanaClient client = new SolanaClient(Cluster.TESTNET);
		client.subscribeAccountUpdates(account, () -> {
		});

		Thread.sleep(3000);

		client.unsubscribeAccountUpdates(account);

	}

}

/**
 * 
 */
package com.paymennt.examples;

import java.math.BigDecimal;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.paymennt.crypto.core.Coin;
import com.paymennt.crypto.core.derivation.DerivationPath;
import com.paymennt.crypto.core.derivation.DerivationPathBuilder;
import com.paymennt.crypto.core.derivation.DerivationPath.Chain;
import com.paymennt.crypto.core.derivation.DerivationPath.Purpose;
import com.paymennt.solanaj.core.Account;
import com.paymennt.solanaj.core.PublicKey;
import com.paymennt.solanaj.core.Transaction;
import com.paymennt.solanaj.programs.SystemProgram;
import com.paymennt.solanaj.rpc.Cluster;
import com.paymennt.solanaj.rpc.RpcClient;
import com.paymennt.solanaj.rpc.RpcException;

/**
 * @author asendar
 *
 */
public class FullExample {

	public static void main(String[] args) throws RpcException {

		Security.addProvider(new BouncyCastleProvider());

		String words = "swing brown camera enter burden awful rent shock mobile wisdom increase scissors";
		String passphrase = "carry";

		/**
		 * create account using mnemonic phrase
		 */
		Account account = Account.fromMnemonic(words, passphrase);
		System.out.println("address 1: " + account.getAddress());

		/**
		 * create account using mnemonic phrase, derivation path and address index
		 */
		DerivationPath derivationPath = new DerivationPathBuilder() //
				.withPurpose(Purpose.BIP84)//
				.withCoin(Coin.TSOL)//
				.withAccount(Long.valueOf(10L).hashCode())//
				.withChain(Chain.EXTERNAL)//
				.build();

		account = Account.fromMnemonic(words, passphrase, derivationPath, 1);
		System.out.println("address 2: " + account.getAddress());

		/**
		 * send a transaction
		 */
		RpcClient client = new RpcClient(Cluster.TESTNET);

		PublicKey fromPublicKey = new PublicKey("79M3HrgELA5Dnibjgir8UTkk8u9RkV3WhbkaqSjzVY3");
		PublicKey toPublickKey = new PublicKey("2Ym21uN3GqvFwkrvoWKwcTwqRjk1pFVSS6RFguNgmYdV");
		int lamports = Coin.TSOL.coinToFractions(BigDecimal.valueOf(1)).intValue();

		Transaction transaction = new Transaction();
		transaction.addInstruction(SystemProgram.transfer(fromPublicKey, toPublickKey, lamports));

		String hash = client.getApi().sendTransaction(transaction, account);
		System.out.println("transaction hash: " + hash);
		System.out.println(
				"view on browser: " + String.format("https://solanabeach.io/transaction/%s?cluster=testnet", hash));

	}

}

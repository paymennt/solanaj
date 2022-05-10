# solanaj

Updated version of [p2p-org/solanaj](https://github.com/p2p-org/solanaj)
Solana blockchain client, written in pure Java.
Solanaj is an API for integrating with Solana blockchain using the [Solana RPC API](https://docs.solana.com/apps/jsonrpc-api)

### Full example

```java
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
	System.out.println("view on browser: " + String.format("https://solanabeach.io/transaction/%s?cluster=testnet", hash));

}
```

## License

Solanaj is available under the MIT license. See the LICENSE file for more info.

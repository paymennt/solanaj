# solanaj

Updated version of [p2p-org/solanaj](https://github.com/p2p-org/solanaj)
Solana lightweight blockchain client, written in pure Java.
Solanaj is an API for integrating with Solana blockchain using the [Solana RPC API](https://docs.solana.com/apps/jsonrpc-api)

### Requirements
- Java 11+

### Dependencies
- [cryptoj](https://mvnrepository.com/artifact/com.paymennt/cryptoj)


### Usage
#### create wallet

```java
        // the netowrk, MAINNET or TESTNET
        final Network network = Network.TESTNET;

        // your mnemonic phrase
        final String mnemonic = "swing brown giraffe enter common awful rent shock mobile wisdom increase banana";

        // optional passphrase
        final String passphrase = null;

        // create wallet
        SolanaWallet solanaWallet = new SolanaWallet(mnemonic, passphrase, network);
        
        // get address (account, chain, index), used to receive
        solanaWallet.getAddress(0, Chain.EXTERNAL, null);
        
        // get private key (account, chain, index), used to sign transactions 
        solanaWallet.getPrivateKey(0, Chain.EXTERNAL, null);
```
#### Sign and send a transaction
```java
        // create new SolanaRpcClient, (DEVNET, TESTNET, MAINNET)
        SolanaRpcClient client = new SolanaRpcClient(Cluster.DEVNET);
        
        // wallet address of the receiver
        String receiverAddress = "EPBkAFmzU6CajVCjz2Jd3H5MZ7CuZz74UjuWK1MUtFtV";
        
        // amount to transfer in lamports, 1 SOL = 1000000000 lamports
        long amount = 1000000000;
        
        // create new transaction
        SolanaTransaction transaction = new SolanaTransaction();

        // create solana account, this account holds the funds that we want to transfer
        SolanaAccount account = new SolanaAccount(solanaWallet.getPrivateKey(0, Chain.EXTERNAL, null));
         
        // define the sender and receiver public keys
        SolanaPublicKey fromPublicKey = account.getPublicKey();
        SolanaPublicKey toPublickKey = new SolanaPublicKey(receiverAddress);

        // add instructions to the transaction (from, to, lamports)
        transaction.addInstruction(SystemProgram.transfer(fromPublicKey, toPublickKey, amount));
        
        // set the recent blockhash
        transaction.setRecentBlockHash(client.getApi().getRecentBlockhash());
        
        // set the fee payer
        transaction.setFeePayer(account.getPublicKey());
        
        // sign the transaction
        transaction.sign(account);
        
        // publish the transaction
        String signature = client.getApi().sendTransaction(transaction);
```
#### Listen for account updates

```java
        String walletAddress = "EPBkAFmzU6CajVCjz2Jd3H5MZ7CuZz74UjuWK1MUtFtV";
        websocket.accountSubscribe(walletAddress, data -> {
            System.out.println("update received");
        });
```

## License

Solanaj is available under the MIT license. See the LICENSE file for more info.

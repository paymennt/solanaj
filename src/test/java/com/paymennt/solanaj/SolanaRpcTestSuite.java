package com.paymennt.solanaj;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.paymennt.crypto.bip32.Network;
import com.paymennt.crypto.bip32.wallet.AbstractWallet.Chain;
import com.paymennt.crypto.bip32.wallet.key.HdPrivateKey;
import com.paymennt.solanaj.api.rpc.Cluster;
import com.paymennt.solanaj.api.rpc.SolanaRpcClient;
import com.paymennt.solanaj.api.ws.SolanaWebSocketClient;
import com.paymennt.solanaj.data.SolanaAccount;
import com.paymennt.solanaj.data.SolanaPublicKey;
import com.paymennt.solanaj.data.SolanaTransaction;
import com.paymennt.solanaj.program.TokenProgram;
import com.paymennt.solanaj.wallet.SolanaWallet;

/**
 * @author asendar
 *
 */
public class SolanaRpcTestSuite {

    private static Cluster cluster = Cluster.DEVNET;
    private static Network network = Network.TESTNET;

    private static final String mnemonic =
            "swing brown giraffe enter common awful rent shock mobile wisdom increase banana";
    private static SolanaRpcClient client;
    private static SolanaWebSocketClient websocket;
    private static SolanaWallet randomWallet;

    @BeforeClass
    public static void init() {
        Security.addProvider(new BouncyCastleProvider());
        client = new SolanaRpcClient(cluster);
        randomWallet = new SolanaWallet(mnemonic, null, network);
        websocket = new SolanaWebSocketClient(cluster);
    }

    @Test
    @Ignore
    public void testWebsocket() throws InterruptedException {

        // ACCOUNT
        websocket.accountSubscribe("E6R9rpMLi87bk7obULukemPhoCjVDG5hebWyJZj6DUhF", data -> {
            System.out.println("UPDATE: ACCOUNT");
        });

        // REFERENCE
        websocket.accountSubscribe("2Uvt5gFJXoQDP97LNeeJono4Tapc56KPe5b4rvHPpCTW", data -> {
            System.out.println("UPDATE: REFERENCE");
        });

        // TOKEN
        websocket.accountSubscribe("7v5bP63pRShDAJEF5VqVDMMePVkZ1JpANubiQ6jFmZgY", data -> {
            System.out.println("UPDATE: TOKEN");
        });

        Thread.sleep(1000000);

    }

    @Test
    @Ignore
    public void testCreateTokenProgram() {

        HdPrivateKey privateKey = randomWallet.getPrivateKey(0, Chain.EXTERNAL, null);
        SolanaAccount account = new SolanaAccount(privateKey);

        SolanaTransaction transaction = new SolanaTransaction();

        transaction.addInstruction(//
                TokenProgram.createAccount(//
                        account.getPublicKey(), //
                        new SolanaPublicKey("4zMMC9srt5Ri5X14GAgXhaHii3GnPAEERYPJgZJDncDU"), //
                        new SolanaPublicKey(randomWallet.getAddress(101, Chain.EXTERNAL, null)) //

                )//
        );

        transaction.setRecentBlockHash(client.getApi().getRecentBlockhash());
        transaction.setFeePayer(account.getPublicKey());
        transaction.sign(account);

        System.out.println(client.getApi().sendTransaction(transaction));
    }

    @Test
    @Ignore
    public void testTransferTokenProgram() {

        //        randomWallet.getAddress(1000, Chain.EXTERNAL, null);

        // fee payer, will be used to sign the transaction as well
        HdPrivateKey feepayerPrivateKey = randomWallet.getPrivateKey(100, Chain.EXTERNAL, null);
        SolanaAccount feepayerAccount = new SolanaAccount(feepayerPrivateKey);

        // source of USDC to transfer from
        HdPrivateKey sourcePrivateKey = randomWallet.getPrivateKey(0, Chain.EXTERNAL, null);
        SolanaAccount sourceAccount = new SolanaAccount(sourcePrivateKey);

        //3xETGDP48i2Ev6ka8vz8PSaKCJz9yURvcb3ooyVa7jHh
        String recipient = randomWallet.getAddress(0, Chain.EXTERNAL, 89);

        System.out.println("tx: " + client.getApi().signAndSendTokenTransaction(
                "4zMMC9srt5Ri5X14GAgXhaHii3GnPAEERYPJgZJDncDU", feepayerAccount, sourceAccount, recipient, 10000000));
    }
    
    @Test
//    @Ignore
    public void testGetTransaction() {

        client.getApi().getTransaction("4huSdrw9NwKyvKxWk4VS39ujerVWhRGj8FufQyxEGjXW3bJXwv3qeiK1tWtnEwoA6T4Nh2xcMZALbC3vAu39Jez2");
    }

}

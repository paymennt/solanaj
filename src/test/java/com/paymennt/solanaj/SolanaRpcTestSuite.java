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
import com.paymennt.solanaj.data.SolanaToken;
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
            "swing brown giraffe enter common awful rent shock mobile wisdom increase scissors";
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
        int accountIndex = Long.valueOf(0).hashCode();
        //        int index = Long.valueOf(1733351411786454800L).hashCode();
        int index = Long.valueOf(0).hashCode();

        System.out.println(randomWallet.getAddress(accountIndex, Chain.EXTERNAL, index));

        websocket.accountSubscribe("HPLmxR17p9UhFYPPJjWVPmEgWQTssU3s27uKEU6c6BkB", data -> {
            System.out.println("UPDATE");
        });

        Thread.sleep(1000000);

    }

    @Test
    //    @Ignore
    public void testTokenProgram() {

        HdPrivateKey privateKey = randomWallet.getPrivateKey(0, Chain.EXTERNAL, null);
        SolanaAccount account = new SolanaAccount(privateKey);

        SolanaTransaction transaction = new SolanaTransaction();

        transaction.addInstruction(//
                TokenProgram.createAccount(//
                        account.getPublicKey(), //
                        new SolanaPublicKey(SolanaToken.USDC.getMint(cluster)), //
                        new SolanaPublicKey(randomWallet.getAddress(101, Chain.EXTERNAL, null)) //

                )//
        );

        transaction.setRecentBlockHash(client.getApi().getRecentBlockhash());
        transaction.setFeePayer(account.getPublicKey());
        transaction.sign(account);

        System.out.println(client.getApi().sendTransaction(transaction));
    }

}

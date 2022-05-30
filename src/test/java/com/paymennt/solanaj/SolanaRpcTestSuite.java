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
import com.paymennt.solanaj.data.SolanaPublicKey;
import com.paymennt.solanaj.data.SolanaAccount;
import com.paymennt.solanaj.data.SolanaMessage;
import com.paymennt.solanaj.data.SolanaTransaction;
import com.paymennt.solanaj.program.SystemProgram;
import com.paymennt.solanaj.wallet.SolanaWallet;

/**
 * @author asendar
 *
 */
public class SolanaRpcTestSuite {

    private static final String mnemonic = "fruit wave dwarf banana earth journey tattoo true farm silk olive fence";
    private static SolanaRpcClient client;
    private static SolanaWebSocketClient websocket;
    private static SolanaWallet randomWallet;

    @BeforeClass
    public static void init() {
        Security.addProvider(new BouncyCastleProvider());
        client = new SolanaRpcClient(Cluster.TESTNET);
        randomWallet = new SolanaWallet(mnemonic, "banana", Network.TESTNET);
        websocket = new SolanaWebSocketClient(Cluster.TESTNET);
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
    public void testCloseAccount() {

        String destination = "2Ym21uN3GqvFwkrvoWKwcTwqRjk1pFVSS6RFguNgmYdV";
        int accountIndex = Long.valueOf(1599726290708002602L).hashCode();
        //        int index = Long.valueOf(1733351411786454800L).hashCode();
        int index = Long.valueOf(1733621725016923814L).hashCode();

        long amount = 253832900L;

        System.out.println(randomWallet.getAddress(accountIndex, Chain.EXTERNAL, index));

        HdPrivateKey privateKey = randomWallet.getPrivateKey(accountIndex, Chain.EXTERNAL, index);

        SolanaTransaction transaction = new SolanaTransaction();

        SolanaAccount account = new SolanaAccount(privateKey);
        SolanaPublicKey fromPublicKey = account.getPublicKey();
        SolanaPublicKey toPublickKey = new SolanaPublicKey(destination);
        
        
//        client.getApi().getBalance(randomWallet.getAddress(accountIndex, Chain.EXTERNAL, index));

        SolanaMessage message = new SolanaMessage();
        message.addInstruction(SystemProgram.transfer(fromPublicKey, toPublickKey, amount));
        message.setFeePayer(fromPublicKey);
        long fees = client.getApi().getFees(message).getValue();

        transaction.addInstruction(SystemProgram.transfer(fromPublicKey, toPublickKey, amount - fees));
        transaction.setRecentBlockHash(client.getApi().getRecentBlockhash());
        transaction.sign(account);

        System.out.println(client.getApi().sendTransaction(transaction));
    }

}

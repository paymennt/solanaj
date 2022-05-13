package com.paymennt.solanaj;

import static org.junit.Assert.assertEquals;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.BeforeClass;
import org.junit.Test;

import com.paymennt.crypto.bip32.wallet.Bip39.Chain;
import com.paymennt.solanaj.core.SolanaAccount;
import com.paymennt.solanaj.core.SolanaAccountBuilder;
import com.paymennt.solanaj.rpc.Cluster;
import com.paymennt.solanaj.rpc.RpcException;

/**
 * @author asendar
 *
 */
public class SolanaRpcTestSuite {

    private static final String mnemonic =
            "swing brown giraffe enter common awful rent shock mobile wisdom increase scissors";
    private static SolanaClient client;
    private static SolanaAccount randomAccount;

    @BeforeClass
    public static void init() {
        Security.addProvider(new BouncyCastleProvider());
        client = new SolanaClient(Cluster.TESTNET);
        randomAccount = new SolanaAccountBuilder()//
                .withMnemonic(mnemonic)//
                .withAccountIndex(0)//
                .withChain(Chain.EXTERNAL)//
                .build();
    }

    @Test
    public void testWalletAddress1() {

        SolanaAccount account = new SolanaAccountBuilder()//
                .withMnemonic(mnemonic)//
                .withAccountIndex(0)//
                .withChain(Chain.EXTERNAL)//
                .build();

        assertEquals("2Ym21uN3GqvFwkrvoWKwcTwqRjk1pFVSS6RFguNgmYdV", account.getAddress());
    }

    @Test
    public void testWalletAddress2() {

        SolanaAccount account = new SolanaAccountBuilder()//
                .withMnemonic(mnemonic)//
                .withAccountIndex(1)//
                .withChain(Chain.EXTERNAL)//
                .build();

        assertEquals("A2K2xaXbEUuCCiBU2s6zev8ougGtk3S7t1fCPG3Rgad7", account.getAddress());

    }

    @Test(expected = Test.None.class)
    public void testWebsocket() throws InterruptedException {

        client.subscribeAccountUpdates(randomAccount, () -> {
        });

        Thread.sleep(3000);

        client.unsubscribeAccountUpdates(randomAccount);
        
        Thread.sleep(30000);
    }

    @Test(expected = Test.None.class)
    public void testAccountTransactions() throws RpcException {

        client.getAccountTransactions(randomAccount);
    }

    @Test(expected = Test.None.class)
    public void testAccountBalance() throws RpcException {

        client.getBalance(randomAccount);
    }

    @Test(expected = Test.None.class)
    public void testTramsferFees() throws RpcException {
        client.getTransferFees(randomAccount, "A2K2xaXbEUuCCiBU2s6zev8ougGtk3S7t1fCPG3Rgad7", 0);
    }

}

package com.paymennt.solanaj;

import static org.junit.Assert.assertEquals;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.BeforeClass;
import org.junit.Test;

import com.paymennt.crypto.bip32.Network;
import com.paymennt.crypto.bip32.wallet.AbstractWallet.Chain;
import com.paymennt.crypto.bip32.wallet.key.HdPrivateKey;
import com.paymennt.solanaj.api.rpc.Cluster;
import com.paymennt.solanaj.api.rpc.RpcException;
import com.paymennt.solanaj.wallet.SolanaWallet;

/**
 * @author asendar
 *
 */
public class SolanaRpcTestSuite {

    private static final String mnemonic =
            "swing brown giraffe enter common awful rent shock mobile wisdom increase scissors";
    private static SolanaClient client;
    private static SolanaWallet randomWallet;

    @BeforeClass
    public static void init() {
        Security.addProvider(new BouncyCastleProvider());
        client = new SolanaClient(Cluster.TESTNET);
        randomWallet = new SolanaWallet(mnemonic, "", Network.TESTNET);
    }

    @Test
    public void testWalletAddress1() {
        assertEquals("2Ym21uN3GqvFwkrvoWKwcTwqRjk1pFVSS6RFguNgmYdV", randomWallet.getAddress(0, Chain.EXTERNAL, null));
    }

    @Test
    public void testWalletAddress2() {
        assertEquals("A2K2xaXbEUuCCiBU2s6zev8ougGtk3S7t1fCPG3Rgad7", randomWallet.getAddress(1, Chain.EXTERNAL, null));

    }

    @Test(expected = Test.None.class)
    public void testWebsocket() throws InterruptedException {
        String address = randomWallet.getAddress(0, Chain.EXTERNAL, null);
        client.subscribeAccountUpdates(address, () -> {
        });

        Thread.sleep(3000);

        client.unsubscribeAccountUpdates(address);
    }

    @Test(expected = Test.None.class)
    public void testAccountTransactions() throws RpcException {
        String address = randomWallet.getAddress(0, Chain.EXTERNAL, null);
        client.getTransactions(address);
    }

    @Test(expected = Test.None.class)
    public void testAccountBalance() throws RpcException {
        String address = randomWallet.getAddress(0, Chain.EXTERNAL, null);
        client.getBalance(address);
    }

    @Test(expected = Test.None.class)
    public void testTramsferFees() throws RpcException {
        HdPrivateKey key = randomWallet.getPrivateKey(0, Chain.EXTERNAL, null);
        assertEquals(5000, client.getTransferFees(key, "A2K2xaXbEUuCCiBU2s6zev8ougGtk3S7t1fCPG3Rgad7", 0));
    }

}

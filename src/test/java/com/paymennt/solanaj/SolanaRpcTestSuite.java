package com.paymennt.solanaj;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.paymennt.solanaj.api.rpc.Cluster;
import com.paymennt.solanaj.api.rpc.SolanaRpcClient;
import com.paymennt.solanaj.api.ws.SolanaWebSocketClient;

/**
 * @author asendar
 *
 */
public class SolanaRpcTestSuite {

    //    private static final String mnemonic =
    //            "swing brown giraffe enter common awful rent shock mobile wisdom increase scissors";
    private static SolanaRpcClient client;
    private static SolanaWebSocketClient websocket;
    //    private static SolanaWallet randomWallet;

    @BeforeClass
    public static void init() {
        Security.addProvider(new BouncyCastleProvider());
        websocket = SolanaWebSocketClient.getInstance(Cluster.TESTNET.getEndpoint());
        client = new SolanaRpcClient(Cluster.TESTNET);
        //        randomWallet = new SolanaWallet(mnemonic, "", Network.TESTNET);
    }
    
    @Test
    public void testAccountTransactions() {
        long rent = client.getApi().getMinimumBalanceForRentExemption(0);
        System.out.println(rent);
    }
    
    @Test
    @Ignore
    public void testStatus() {
        List<String> sigs = new ArrayList<>();
        sigs.add("xi6aCymB8jbafMpnAmZBeVgRYQn5tH7nZzqmCfNYbJf3UhS1D2W2i6xs5bdm81YgqLRHaBXzq2Npo1o9VczbjFt");
        sigs.add("2cjqyTySWkfDffPXy9Aid3qtibspbArMbMDweATSUAiaJ8RjPqTrttkjcwugF8XBksFux7q7TS8nNTbRzft7n7z6");
        client.getApi().getTransactions(sigs);
    }

    @Test
    public void testWebsocket() throws InterruptedException {
        websocket.accountSubscribe("2Ym21uN3GqvFwkrvoWKwcTwqRjk1pFVSS6RFguNgmYdV", data -> {
//            client.getApi().getSignaturesForAddress("2Ym21uN3GqvFwkrvoWKwcTwqRjk1pFVSS6RFguNgmYdV", 100)
//                    .forEach(info -> {
//                        System.out.println(client.getApi().getTransaction(info.getSignature()).getTransaction());
//                    });

        });

//        websocket.logsSubscribe("H35HxumQvRb1ood2J7irgiovnLTseH2jjWH6TuVKCvf2", signature -> {
//            System.out.println(signature);
//            System.out.println(JsonUtils.encode(client.getApi().getTransaction(signature)));
//
//        });

        websocket.logsSubscribe("2Ym21uN3GqvFwkrvoWKwcTwqRjk1pFVSS6RFguNgmYdV", signature -> {
            System.out.println(signature);
//            System.out.println(JsonUtils.encode(client.getApi().getTransaction(signature)));

        });

//        Thread.sleep(300000);
    }

}

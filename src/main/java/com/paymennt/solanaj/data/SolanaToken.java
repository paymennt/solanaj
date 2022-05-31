/**
 * 
 */
package com.paymennt.solanaj.data;

import com.paymennt.solanaj.api.rpc.Cluster;

/**
 * @author asendar
 *
 */
public enum SolanaToken {
    USDC(//
            "EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v", //
            "", // USDC is not setup on testnet, use devnet for testing
            "4zMMC9srt5Ri5X14GAgXhaHii3GnPAEERYPJgZJDncDU"//
    );

    private String mainnetMint;
    private String testnetMint;
    private String devnetMint;

    private SolanaToken(String mainnetMint, String testnetMint, String devnetMint) {
        this.mainnetMint = mainnetMint;
        this.testnetMint = testnetMint;
        this.devnetMint = devnetMint;
    }

    public String getMint(Cluster cluster) {
        if (Cluster.DEVNET.equals(cluster))
            return this.devnetMint;
        if (Cluster.TESTNET.equals(cluster))
            return this.testnetMint;
        return this.mainnetMint;
    }

}

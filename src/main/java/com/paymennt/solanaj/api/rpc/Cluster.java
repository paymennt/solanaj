/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc;


/**
 * 
 */
public enum Cluster {
    
    /**  */
    DEVNET("https://api.devnet.solana.com"),
    
    /**  */
    TESTNET("https://api.testnet.solana.com"),
    
    /**  */
    MAINNET("https://api.mainnet-beta.solana.com");

    /**  */
    private String endpoint;

    /**
     * 
     *
     * @param endpoint 
     */
    Cluster(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * 
     *
     * @return 
     */
    public String getEndpoint() {
        return endpoint;
    }
}

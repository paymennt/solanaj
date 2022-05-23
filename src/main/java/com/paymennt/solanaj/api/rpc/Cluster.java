/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc;

// TODO: Auto-generated Javadoc
/**
 * The Enum Cluster.
 */
public enum Cluster {
    
    /** The devnet. */
    DEVNET("https://api.devnet.solana.com"),
    
    /** The testnet. */
    TESTNET("https://api.testnet.solana.com"),
    
    /** The mainnet. */
    MAINNET("https://api.mainnet-beta.solana.com");

    /** The endpoint. */
    private String endpoint;

    /**
     * Instantiates a new cluster.
     *
     * @param endpoint the endpoint
     */
    Cluster(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Gets the endpoint.
     *
     * @return the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }
}

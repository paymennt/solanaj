/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

// TODO: Auto-generated Javadoc
/**
 * 
 */
public class RpcLogsConfig {

    /**  */
    private SolanaCommitment commitment;

    /**
     * 
     *
     * @param commitment 
     */
    public RpcLogsConfig(SolanaCommitment commitment) {
        this.commitment = commitment;
    }

    /**
     * 
     *
     * @return 
     */
    public SolanaCommitment getCommitment() {
        return commitment;
    }

    /**
     * 
     *
     * @param commitment 
     */
    public void setCommitment(SolanaCommitment commitment) {
        this.commitment = commitment;
    }

}

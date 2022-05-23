/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

// TODO: Auto-generated Javadoc
/**
 * The Class RpcLogsConfig.
 */
public class RpcLogsConfig {

    /** The commitment. */
    private SolanaCommitment commitment;

    /**
     * Instantiates a new rpc logs config.
     *
     * @param commitment the commitment
     */
    public RpcLogsConfig(SolanaCommitment commitment) {
        this.commitment = commitment;
    }

    /**
     * Gets the commitment.
     *
     * @return the commitment
     */
    public SolanaCommitment getCommitment() {
        return commitment;
    }

    /**
     * Sets the commitment.
     *
     * @param commitment the new commitment
     */
    public void setCommitment(SolanaCommitment commitment) {
        this.commitment = commitment;
    }

}

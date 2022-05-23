/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

// TODO: Auto-generated Javadoc
/**
 * The Class RpcConfig.
 */
public class RpcConfig {

    /** The commitment. */
    private SolanaCommitment commitment;
    
    /** The encoding. */
    private String encoding;

    /**
     * Instantiates a new rpc config.
     *
     * @param commitment the commitment
     * @param encoding the encoding
     */
    public RpcConfig(SolanaCommitment commitment, String encoding) {
        this.commitment = commitment;
        this.encoding = encoding;
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

    /**
     * Gets the encoding.
     *
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the encoding.
     *
     * @param encoding the new encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}

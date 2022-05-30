/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;


/**
 * 
 */
public class RpcConfig {

    /**  */
    private SolanaCommitment commitment;
    
    /**  */
    private String encoding;

    /**
     * 
     *
     * @param commitment 
     * @param encoding 
     */
    public RpcConfig(SolanaCommitment commitment, String encoding) {
        this.commitment = commitment;
        this.encoding = encoding;
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

    /**
     * 
     *
     * @return 
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * 
     *
     * @param encoding 
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}

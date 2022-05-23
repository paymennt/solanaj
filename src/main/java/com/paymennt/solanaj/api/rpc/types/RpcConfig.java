package com.paymennt.solanaj.api.rpc.types;

public class RpcConfig {

    private SolanaCommitment commitment;
    private String encoding;

    public RpcConfig(SolanaCommitment commitment, String encoding) {
        this.commitment = commitment;
        this.encoding = encoding;
    }

    public SolanaCommitment getCommitment() {
        return commitment;
    }

    public void setCommitment(SolanaCommitment commitment) {
        this.commitment = commitment;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}

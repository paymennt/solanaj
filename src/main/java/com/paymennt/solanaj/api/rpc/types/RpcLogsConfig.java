package com.paymennt.solanaj.api.rpc.types;

public class RpcLogsConfig {

    private SolanaCommitment commitment;

    public RpcLogsConfig(SolanaCommitment commitment) {
        this.commitment = commitment;
    }

    public SolanaCommitment getCommitment() {
        return commitment;
    }

    public void setCommitment(SolanaCommitment commitment) {
        this.commitment = commitment;
    }

}

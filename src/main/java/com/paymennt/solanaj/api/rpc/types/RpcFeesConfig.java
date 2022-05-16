package com.paymennt.solanaj.api.rpc.types;

public class RpcFeesConfig {

    private String commitment = "finalized";

    public String getCommitment() {
        return commitment;
    }

    public void setCommitment(String commitment) {
        this.commitment = commitment;
    }

}

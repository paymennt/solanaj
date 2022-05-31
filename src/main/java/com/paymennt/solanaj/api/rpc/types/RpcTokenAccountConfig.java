/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

/**
 * 
 */
public class RpcTokenAccountConfig {

    private String mint;

    public RpcTokenAccountConfig(String mint) {
        this.mint = mint;
    }

    public String getMint() {
        return mint;
    }

    public void setMint(String mint) {
        this.mint = mint;
    }

}

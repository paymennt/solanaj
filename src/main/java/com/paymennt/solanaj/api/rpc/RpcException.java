/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc;


/**
 * 
 */
public class RpcException extends Exception {
    
    /**  */
    private final static long serialVersionUID = 8315999767009642193L;

    /**
     * 
     *
     * @param message 
     */
    public RpcException(String message) {
        super(message);
    }
}

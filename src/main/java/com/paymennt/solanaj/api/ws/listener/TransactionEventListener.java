/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.ws.listener;


/**
 * 
 */
public interface TransactionEventListener {
    
    /**
     * 
     *
     * @param signature 
     */
    public void onTransactiEvent(String signature);
}

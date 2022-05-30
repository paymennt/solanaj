/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.ws;


/**
 * 
 */
public class SignatureNotification {
    
    /**  */
    private Object error;

    /**
     * 
     *
     * @param error 
     */
    public SignatureNotification(Object error) {
        this.error = error;
    }

    /**
     * 
     *
     * @return 
     */
    public Object getError() {
        return error;
    }

    /**
     * 
     *
     * @return 
     */
    public boolean hasError() {
        return error != null;
    }
}

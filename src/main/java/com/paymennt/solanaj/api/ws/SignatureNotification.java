/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.ws;

// TODO: Auto-generated Javadoc
/**
 * The Class SignatureNotification.
 */
public class SignatureNotification {
    
    /** The error. */
    private Object error;

    /**
     * Instantiates a new signature notification.
     *
     * @param error the error
     */
    public SignatureNotification(Object error) {
        this.error = error;
    }

    /**
     * Gets the error.
     *
     * @return the error
     */
    public Object getError() {
        return error;
    }

    /**
     * Checks for error.
     *
     * @return true, if successful
     */
    public boolean hasError() {
        return error != null;
    }
}

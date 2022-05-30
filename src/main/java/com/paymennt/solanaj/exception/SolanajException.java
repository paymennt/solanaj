/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.exception;

/**
 * @author paymennt
 * 
 */
public class SolanajException extends RuntimeException {
    
    /**  */
    private static final long serialVersionUID = 326864452189929895L;

    /**
     * 
     *
     * @param message 
     */
    public SolanajException(String message) {
        super(message);
    }

    /**
     * 
     *
     * @param cause 
     */
    public SolanajException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * 
     *
     * @param message 
     * @param args 
     */
    public SolanajException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * 
     *
     * @param message 
     * @param cause 
     * @param args 
     */
    public SolanajException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}
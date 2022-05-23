/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class SolanajException.
 *
 * @author asendar
 */
public class SolanajException extends RuntimeException {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 326864452189929895L;

    /**
     * Instantiates a new solanaj exception.
     *
     * @param message the message
     */
    public SolanajException(String message) {
        super(message);
    }

    /**
     * Instantiates a new solanaj exception.
     *
     * @param cause the cause
     */
    public SolanajException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * Instantiates a new solanaj exception.
     *
     * @param message the message
     * @param args the args
     */
    public SolanajException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * Instantiates a new solanaj exception.
     *
     * @param message the message
     * @param cause the cause
     * @param args the args
     */
    public SolanajException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}
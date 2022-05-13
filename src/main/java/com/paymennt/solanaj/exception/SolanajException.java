/**
 * 
 */
package com.paymennt.solanaj.exception;

/**
 * @author asendar
 *
 */
public class SolanajException extends RuntimeException {
    private static final long serialVersionUID = 326864452189929895L;

    public SolanajException(String message) {
        super(message);
    }

    public SolanajException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public SolanajException(String message, Object... args) {
        super(String.format(message, args));
    }

    public SolanajException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}
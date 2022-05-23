/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

// TODO: Auto-generated Javadoc
/**
 * The Class RpcSendTransactionConfig.
 */
public class RpcSendTransactionConfig {

    /** The encoding. */
    private Encoding encoding = Encoding.base64;

    /**
     * Gets the encoding.
     *
     * @return the encoding
     */
    public Encoding getEncoding() {
        return encoding;
    }

    /**
     * Sets the encoding.
     *
     * @param encoding the new encoding
     */
    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    /**
     * The Enum Encoding.
     */
    public static enum Encoding {
        
        /** The base 64. */
        base64("base64");

        /** The enc. */
        private String enc;

        /**
         * Instantiates a new encoding.
         *
         * @param enc the enc
         */
        Encoding(String enc) {
            this.enc = enc;
        }

        /**
         * Gets the encoding.
         *
         * @return the encoding
         */
        public String getEncoding() {
            return enc;
        }

    }

}

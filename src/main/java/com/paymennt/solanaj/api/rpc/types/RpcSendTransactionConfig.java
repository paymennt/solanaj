/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;


/**
 * 
 */
public class RpcSendTransactionConfig {

    /**  */
    private Encoding encoding = Encoding.base64;

    /**
     * 
     *
     * @return 
     */
    public Encoding getEncoding() {
        return encoding;
    }

    /**
     * 
     *
     * @param encoding 
     */
    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    /**
     * 
     */
    public static enum Encoding {
        
        /**  */
        base64("base64");

        /**  */
        private String enc;

        /**
         * 
         *
         * @param enc 
         */
        Encoding(String enc) {
            this.enc = enc;
        }

        /**
         * 
         *
         * @return 
         */
        public String getEncoding() {
            return enc;
        }

    }

}

package com.paymennt.solanaj.rpc.types;

public class RpcSendTransactionConfig {

    private Encoding encoding = Encoding.base64;

    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    public static enum Encoding {
        base64("base64");

        private String enc;

        Encoding(String enc) {
            this.enc = enc;
        }

        public String getEncoding() {
            return enc;
        }

    }

}

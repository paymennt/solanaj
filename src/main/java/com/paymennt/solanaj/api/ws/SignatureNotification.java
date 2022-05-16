package com.paymennt.solanaj.api.ws;

public class SignatureNotification {
    private Object error;

    public SignatureNotification(Object error) {
        this.error = error;
    }

    public Object getError() {
        return error;
    }

    public boolean hasError() {
        return error != null;
    }
}

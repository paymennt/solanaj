package com.paymennt.solanaj.api.rpc.types;

import java.util.AbstractMap;

public class SignatureInformation {
    private Object err;
    private Object memo;
    private String signature;
    private long slot;

    public SignatureInformation() {
    }

    @SuppressWarnings({ "rawtypes" })
    public SignatureInformation(AbstractMap info) {
        this.err = info.get("err");
        this.memo = info.get("memo");
        this.signature = (String) info.get("signature");
        this.err = info.get("slot");
    }

    public Object getErr() {
        return err;
    }

    public void setErr(Object err) {
        this.err = err;
    }

    public Object getMemo() {
        return memo;
    }

    public void setMemo(Object memo) {
        this.memo = memo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getSlot() {
        return slot;
    }

    public void setSlot(long slot) {
        this.slot = slot;
    }

}

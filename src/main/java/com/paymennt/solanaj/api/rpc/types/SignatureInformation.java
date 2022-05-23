/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.AbstractMap;

// TODO: Auto-generated Javadoc
/**
 * The Class SignatureInformation.
 */
public class SignatureInformation {
    
    /** The err. */
    private Object err;
    
    /** The memo. */
    private Object memo;
    
    /** The signature. */
    private String signature;
    
    /** The slot. */
    private long slot;

    /**
     * Instantiates a new signature information.
     */
    public SignatureInformation() {
    }

    /**
     * Instantiates a new signature information.
     *
     * @param info the info
     */
    @SuppressWarnings({ "rawtypes" })
    public SignatureInformation(AbstractMap info) {
        this.err = info.get("err");
        this.memo = info.get("memo");
        this.signature = (String) info.get("signature");
        this.err = info.get("slot");
    }

    /**
     * Gets the err.
     *
     * @return the err
     */
    public Object getErr() {
        return err;
    }

    /**
     * Sets the err.
     *
     * @param err the new err
     */
    public void setErr(Object err) {
        this.err = err;
    }

    /**
     * Gets the memo.
     *
     * @return the memo
     */
    public Object getMemo() {
        return memo;
    }

    /**
     * Sets the memo.
     *
     * @param memo the new memo
     */
    public void setMemo(Object memo) {
        this.memo = memo;
    }

    /**
     * Gets the signature.
     *
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the signature.
     *
     * @param signature the new signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * Gets the slot.
     *
     * @return the slot
     */
    public long getSlot() {
        return slot;
    }

    /**
     * Sets the slot.
     *
     * @param slot the new slot
     */
    public void setSlot(long slot) {
        this.slot = slot;
    }

}

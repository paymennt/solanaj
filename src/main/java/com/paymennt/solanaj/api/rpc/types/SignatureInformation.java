/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.AbstractMap;


/**
 * 
 */
public class SignatureInformation {
    
    /**  */
    private Object err;
    
    /**  */
    private Object memo;
    
    /**  */
    private String signature;
    
    /**  */
    private long slot;

    /**
     * 
     */
    public SignatureInformation() {
    }

    /**
     * 
     *
     * @param info 
     */
    @SuppressWarnings({ "rawtypes" })
    public SignatureInformation(AbstractMap info) {
        this.err = info.get("err");
        this.memo = info.get("memo");
        this.signature = (String) info.get("signature");
        this.err = info.get("slot");
    }

    /**
     * 
     *
     * @return 
     */
    public Object getErr() {
        return err;
    }

    /**
     * 
     *
     * @param err 
     */
    public void setErr(Object err) {
        this.err = err;
    }

    /**
     * 
     *
     * @return 
     */
    public Object getMemo() {
        return memo;
    }

    /**
     * 
     *
     * @param memo 
     */
    public void setMemo(Object memo) {
        this.memo = memo;
    }

    /**
     * 
     *
     * @return 
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 
     *
     * @param signature 
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * 
     *
     * @return 
     */
    public long getSlot() {
        return slot;
    }

    /**
     * 
     *
     * @param slot 
     */
    public void setSlot(long slot) {
        this.slot = slot;
    }

}

/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

// TODO: Auto-generated Javadoc
/**
 * The Class RpcResultObject.
 */
public class RpcResultObject {

    /** The context. */
    protected Context context;

    /**
     * Gets the context.
     *
     * @return the context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Sets the context.
     *
     * @param context the new context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * The Class Context.
     */
    public static class Context {
        
        /** The slot. */
        private long slot;

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
}

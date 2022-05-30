/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;


/**
 * 
 */
public class RpcResultObject {

    /**  */
    protected Context context;

    /**
     * 
     *
     * @return 
     */
    public Context getContext() {
        return context;
    }

    /**
     * 
     *
     * @param context 
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 
     */
    public static class Context {
        
        /**  */
        private long slot;

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
}

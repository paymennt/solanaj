/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

// TODO: Auto-generated Javadoc
/**
 * The Class RpcResultTypes.
 */
public class RpcResultTypes {

    /**
     * The Class ValueLong.
     */
    public static class ValueLong extends RpcResultObject {
        
        /** The value. */
        private long value;

        /**
         * Gets the value.
         *
         * @return the value
         */
        public long getValue() {
            return value;
        }

        /**
         * Sets the value.
         *
         * @param value the new value
         */
        public void setValue(long value) {
            this.value = value;
        }

    }

}

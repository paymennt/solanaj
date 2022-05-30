/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.List;

import com.paymennt.solanaj.data.ConfirmationStatus;


/**
 * 
 */
public class RpcSignitureStatusResult extends RpcResultObject {

    /**  */
    private List<SignitureStatusValue> value;

    /**
     * 
     *
     * @return 
     */
    public List<SignitureStatusValue> getValue() {
        return value;
    }

    /**
     * 
     *
     * @param value 
     */
    public void setValue(List<SignitureStatusValue> value) {
        this.value = value;
    }

    /**
     * 
     */
    public static class SignitureStatusValue {

        /**  */
        private Integer confirmations;
        
        /**  */
        private ConfirmationStatus confirmationStatus;

        /**
         * 
         *
         * @return 
         */
        public int getConfirmations() {
            return confirmations;
        }

        /**
         * 
         *
         * @param confirmations 
         */
        public void setConfirmations(int confirmations) {
            this.confirmations = confirmations;
        }

        /**
         * 
         *
         * @return 
         */
        public ConfirmationStatus getConfirmationStatus() {
            return confirmationStatus;
        }

        /**
         * 
         *
         * @param confirmationStatus 
         */
        public void setConfirmationStatus(ConfirmationStatus confirmationStatus) {
            this.confirmationStatus = confirmationStatus;
        }
    }

}

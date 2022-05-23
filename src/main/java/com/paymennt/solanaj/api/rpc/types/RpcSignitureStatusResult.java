/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.List;

import com.paymennt.solanaj.data.ConfirmationStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class RpcSignitureStatusResult.
 *
 * @author asendar
 */
public class RpcSignitureStatusResult extends RpcResultObject {

    /** The value. */
    private List<SignitureStatusValue> value;

    /**
     * Gets the value.
     *
     * @return the value
     */
    public List<SignitureStatusValue> getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(List<SignitureStatusValue> value) {
        this.value = value;
    }

    /**
     * The Class SignitureStatusValue.
     */
    public static class SignitureStatusValue {

        /** The confirmations. */
        private Integer confirmations;
        
        /** The confirmation status. */
        private ConfirmationStatus confirmationStatus;

        /**
         * Gets the confirmations.
         *
         * @return the confirmations
         */
        public int getConfirmations() {
            return confirmations;
        }

        /**
         * Sets the confirmations.
         *
         * @param confirmations the new confirmations
         */
        public void setConfirmations(int confirmations) {
            this.confirmations = confirmations;
        }

        /**
         * Gets the confirmation status.
         *
         * @return the confirmation status
         */
        public ConfirmationStatus getConfirmationStatus() {
            return confirmationStatus;
        }

        /**
         * Sets the confirmation status.
         *
         * @param confirmationStatus the new confirmation status
         */
        public void setConfirmationStatus(ConfirmationStatus confirmationStatus) {
            this.confirmationStatus = confirmationStatus;
        }
    }

}

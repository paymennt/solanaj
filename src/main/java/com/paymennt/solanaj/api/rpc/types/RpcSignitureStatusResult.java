package com.paymennt.solanaj.api.rpc.types;

import java.util.List;

import com.paymennt.solanaj.data.ConfirmationStatus;

/**
 * 
 * @author asendar
 *
 */
public class RpcSignitureStatusResult extends RpcResultObject {

    private List<SignitureStatusValue> value;

    public List<SignitureStatusValue> getValue() {
        return value;
    }

    public void setValue(List<SignitureStatusValue> value) {
        this.value = value;
    }

    public static class SignitureStatusValue {

        private Integer confirmations;
        private ConfirmationStatus confirmationStatus;

        public int getConfirmations() {
            return confirmations;
        }

        public void setConfirmations(int confirmations) {
            this.confirmations = confirmations;
        }

        public ConfirmationStatus getConfirmationStatus() {
            return confirmationStatus;
        }

        public void setConfirmationStatus(ConfirmationStatus confirmationStatus) {
            this.confirmationStatus = confirmationStatus;
        }
    }

}

/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

// TODO: Auto-generated Javadoc
/**
 * The Class RpcStatusConfig.
 */
public class RpcStatusConfig {

    /** The search transaction history. */
    private boolean searchTransactionHistory = true;

    /**
     * Checks if is search transaction history.
     *
     * @return true, if is search transaction history
     */
    public boolean isSearchTransactionHistory() {
        return searchTransactionHistory;
    }

    /**
     * Sets the search transaction history.
     *
     * @param searchTransactionHistory the new search transaction history
     */
    public void setSearchTransactionHistory(boolean searchTransactionHistory) {
        this.searchTransactionHistory = searchTransactionHistory;
    }

}

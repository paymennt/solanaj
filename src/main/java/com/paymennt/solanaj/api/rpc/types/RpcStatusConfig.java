package com.paymennt.solanaj.api.rpc.types;

public class RpcStatusConfig {

    private boolean searchTransactionHistory = true;

    public boolean isSearchTransactionHistory() {
        return searchTransactionHistory;
    }

    public void setSearchTransactionHistory(boolean searchTransactionHistory) {
        this.searchTransactionHistory = searchTransactionHistory;
    }

}

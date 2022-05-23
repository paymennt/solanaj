/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountKeysList.
 */
public class AccountKeysList {
    
    /** The accounts list. */
    private List<AccountMeta> accountsList;

    /**
     * Instantiates a new account keys list.
     */
    public AccountKeysList() {
        accountsList = new ArrayList<AccountMeta>();
    }

    /**
     * Adds the.
     *
     * @param accountMeta the account meta
     */
    public void add(AccountMeta accountMeta) {
        accountsList.add(accountMeta);
    }

    /**
     * Adds the all.
     *
     * @param metas the metas
     */
    public void addAll(Collection<AccountMeta> metas) {
        accountsList.addAll(metas);
    }

    /**
     * Gets the list.
     *
     * @return the list
     */
    public List<AccountMeta> getList() {
        ArrayList<AccountMeta> uniqueMetas = new ArrayList<AccountMeta>();

        for (AccountMeta accountMeta : accountsList) {
            AccountPublicKey pubKey = accountMeta.getPublicKey();

            int index = AccountMeta.findAccountIndex(uniqueMetas, pubKey);
            if (index > -1) {
                uniqueMetas.set(index,
                        new AccountMeta(pubKey, accountsList.get(index).isSigner() || accountMeta.isSigner(),
                                accountsList.get(index).isWritable() || accountMeta.isWritable()));
            } else {
                uniqueMetas.add(accountMeta);
            }
        }

        uniqueMetas.sort(metaComparator);

        return uniqueMetas;
    }

    /** The Constant metaComparator. */
    private static final Comparator<AccountMeta> metaComparator = new Comparator<AccountMeta>() {

        @Override
        public int compare(AccountMeta am1, AccountMeta am2) {

            int cmpSigner = am1.isSigner() == am2.isSigner() ? 0 : am1.isSigner() ? -1 : 1;
            if (cmpSigner != 0) {
                return cmpSigner;
            }

            int cmpkWritable = am1.isWritable() == am2.isWritable() ? 0 : am1.isWritable() ? -1 : 1;
            if (cmpkWritable != 0) {
                return cmpkWritable;
            }

            return 0;
        }
    };

}

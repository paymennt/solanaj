/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.wallet;

import com.paymennt.crypto.CoinType;
import com.paymennt.crypto.bip32.Network;
import com.paymennt.crypto.bip32.wallet.AbstractWallet;
import com.paymennt.crypto.lib.Base58;

// TODO: Auto-generated Javadoc
/**
 * The Class SolanaWallet.
 *
 * @author asendar
 */
public class SolanaWallet extends AbstractWallet {

    /**
     * Instantiates a new solana wallet.
     *
     * @param words the words
     * @param passphrase the passphrase
     * @param network the network
     */
    public SolanaWallet(String words, String passphrase, Network network) {
        super(words, passphrase, Purpose.BIP44, network, CoinType.SOLANA);
    }

    /**
     * Gets the address.
     *
     * @param account the account
     * @param chain the chain
     * @param index the index
     * @return the address
     */
    @Override
    public String getAddress(int account, Chain chain, Integer index) {
        return Base58.encode(getPublicKey(account, chain, index).getPublicKey());
    }

}

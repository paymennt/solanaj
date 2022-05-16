/**
 * 
 */
package com.paymennt.solanaj.wallet;

import com.paymennt.crypto.CoinType;
import com.paymennt.crypto.bip32.Network;
import com.paymennt.crypto.bip32.wallet.AbstractWallet;
import com.paymennt.crypto.lib.Base58;

/**
 * @author asendar
 *
 */
public class SolanaWallet extends AbstractWallet {

    public SolanaWallet(String words, String passphrase, Network network) {
        super(words, passphrase, Purpose.BIP44, network, CoinType.SOLANA);
    }

    @Override
    public String getAddress(int account, Chain chain, Integer index) {
        return Base58.encode(getPublicKey(account, chain, index).getPublicKey());
    }

}

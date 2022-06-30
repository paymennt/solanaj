/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.solanaj.api.rpc.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 
 */
public class RpcTokenAccount extends RpcResultObject {

    /**  */
    private List<Value> value;

    /**
     * 
     *
     * @return 
     */
    public List<Value> getValue() {
        return value;
    }

    /**
     * 
     *
     * @param value 
     */
    public void setValue(List<Value> value) {
        this.value = value;
    }
    
    /**
     * 
     *
     * @return 
     */
    public String getAddress() {
        return Optional.ofNullable(getValue())//
                .orElse(new ArrayList<>())//
                .stream()//
                .findFirst()//
                .map(Value::getPubkey)//
                .orElse(null);
    }

    /**
     * 
     */
    public static class Value {
        
        /**  */
        private String pubkey;

        /**
         * 
         *
         * @return 
         */
        public String getPubkey() {
            return pubkey;
        }

        /**
         * 
         *
         * @param pubkey 
         */
        public void setPubkey(String pubkey) {
            this.pubkey = pubkey;
        }

    }

}

//[
// {
//    "account":{
//       "data":{
//          "parsed":{
//             "info":{
//                "isNative":false,
//                "mint":"4zMMC9srt5Ri5X14GAgXhaHii3GnPAEERYPJgZJDncDU",
//                "owner":"7zVnNnnzWuCGMpULLZYq4nG7zzNcarLcfJJxdeDAcNPM",
//                "state":"initialized",
//                "tokenAmount":{
//                   "amount":"0",
//                   "decimals":6,
//                   "uiAmount":0.0,
//                   "uiAmountString":"0"
//                }
//             },
//             "type":"account"
//          },
//          "program":"spl-token",
//          "space":165
//       },
//       "executable":false,
//       "lamports":2039280,
//       "owner":"TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA",
//       "rentEpoch":319
//    },
//    "pubkey":"22b2P8j2WBmcEbAHXZP2S7cxMKpfBQyh1m2gB1wNPZ8e"
// }
//]

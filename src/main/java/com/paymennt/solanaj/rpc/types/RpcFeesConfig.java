package com.paymennt.solanaj.rpc.types;

import com.squareup.moshi.Json;

public class RpcFeesConfig {

	@Json(name = "commitment")
	private String commitment = "finalized";

}

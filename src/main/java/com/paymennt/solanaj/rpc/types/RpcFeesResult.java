package com.paymennt.solanaj.rpc.types;

import com.squareup.moshi.Json;

public class RpcFeesResult extends RpcResultObject {

	@Json(name = "value")
	private long value;

	public long getValue() {
		return this.value;
	}

}

package com.paymennt.solanaj.api.rpc;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.paymennt.solanaj.api.rpc.types.RpcRequest;
import com.paymennt.solanaj.api.rpc.types.RpcResponse;
import com.paymennt.solanaj.utils.JsonUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RpcClient {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String endpoint;
    private OkHttpClient httpClient = new OkHttpClient.Builder()//
            .connectTimeout(60, TimeUnit.SECONDS)//
            .writeTimeout(60, TimeUnit.SECONDS)//
            .readTimeout(60, TimeUnit.SECONDS)//
            .build();

    private RpcApi rpcApi;

    public RpcClient(Cluster endpoint) {
        this(endpoint.getEndpoint());
    }

    public RpcClient(String endpoint) {
        this.endpoint = endpoint;
        rpcApi = new RpcApi(this);
    }

    public <T> T call(String method, List<Object> params, Class<T> clazz) throws RpcException {
        RpcRequest rpcRequest = new RpcRequest(method, params);
        Request request = new Request.Builder()//
                .url(endpoint)//
                .post(RequestBody.create(JsonUtils.encode(rpcRequest), JSON))//
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            String responseJson = response.body().string();

            RpcResponse<T> rpcResult = JsonUtils.getObjectMapper()//
                    .readValue(responseJson, new TypeReference<RpcResponse<T>>() {});

            if (rpcResult.getError() != null) {
                throw new RpcException(rpcResult.getError().getMessage());
            }

            String resultJson = JsonUtils.getObjectMapper().writeValueAsString(rpcResult.getResult());

            return JsonUtils.decode(resultJson, clazz);
        } catch (IOException e) {
            throw new RpcException(e.getMessage());
        }
    }

    public RpcApi getApi() {
        return rpcApi;
    }

    public String getEndpoint() {
        return endpoint;
    }

}

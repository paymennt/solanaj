/************************************************************************
 * Copyright PointCheckout, Ltd.
 */
package com.paymennt.solanaj.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.paymennt.solanaj.exception.SolanajException;


/**
 * @author paymennt
 * 
 */
public class JsonUtils {

    /**  */
    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder() //
            .addModule(new ParameterNamesModule())//
            .addModule(new Jdk8Module())//
            .addModule(new JavaTimeModule())//
            // other configuration modules
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();

    /**  */
    private static final Map<Class<?>, Boolean> ENCODE_MAP = new ConcurrentHashMap<>();
    
    /**  */
    private static final Map<Class<?>, Boolean> DECODE_MAP = new ConcurrentHashMap<>();

    /**
     * 
     */
    private JsonUtils() {

    }

    /**
     * 
     *
     * @return 
     */
    public static ObjectMapper getObjectMapper() {
        return JsonUtils.OBJECT_MAPPER;
    }

    /**
     * 
     *
     * @param object 
     * @return 
     */
    public static boolean canEncode(Object object) {
        return canEncodeType(object.getClass());
    }

    /**
     * 
     *
     * @param objectClass 
     * @return 
     */
    public static boolean canEncodeType(Class<?> objectClass) {
        if (JsonUtils.ENCODE_MAP.containsKey(objectClass))
            return JsonUtils.ENCODE_MAP.get(objectClass);
        boolean canEncode = JsonUtils.OBJECT_MAPPER.canSerialize(objectClass);
        JsonUtils.ENCODE_MAP.put(objectClass, canEncode);
        return canEncode;
    }

    /**
     * 
     *
     * @param object 
     * @return 
     */
    public static boolean canDecode(Object object) {
        return canDecodeType(object.getClass());
    }

    /**
     * 
     *
     * @param objectClass 
     * @return 
     */
    public static boolean canDecodeType(Class<?> objectClass) {
        if (JsonUtils.DECODE_MAP.containsKey(objectClass))
            return JsonUtils.DECODE_MAP.get(objectClass);
        JavaType objectType = TypeFactory.defaultInstance().constructType(objectClass);
        boolean canDecode = JsonUtils.OBJECT_MAPPER.canDeserialize(objectType);
        JsonUtils.DECODE_MAP.put(objectClass, canDecode);
        return canDecode;
    }

    /**
     * 
     *
     * @param object 
     * @return 
     */
    public static boolean canEncodeDecode(Object object) {
        return canEncode(object) && canDecode(object);
    }

    /**
     * 
     *
     * @param object 
     * @return 
     */
    public static String encode(Object object) {
        try {
            return JsonUtils.OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new SolanajException("Failed to encode object", e);
        }
    }

    /**
     * 
     *
     * @param object 
     * @return 
     */
    public static String encodePretty(Object object) {
        try {
            JsonUtils.OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
            return JsonUtils.OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new SolanajException("Failed to encode object", e);
        } finally {
            JsonUtils.OBJECT_MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
        }
    }

    /**
     * 
     *
     * @param inputStream 
     * @return 
     */
    public static Map<String, Object> decode(InputStream inputStream) {
        try {
            TypeReference<HashMap<String, Object>> typeReference = new TypeReference<HashMap<String, Object>>() {};
            return JsonUtils.OBJECT_MAPPER.readValue(inputStream, typeReference);
        } catch (Exception e) {
            throw new SolanajException("Failed to decode map", e);
        }
    }

    /**
     * 
     *
     * @param <T> 
     * @param inputStream 
     * @param castAs 
     * @return 
     */
    public static <T> T decode(InputStream inputStream, Class<T> castAs) {
        try {
            return JsonUtils.OBJECT_MAPPER.readValue(inputStream, castAs);
        } catch (Exception e) {
            throw new SolanajException("Failed to decode object of type %s", e, castAs.getName());
        }
    }

    /**
     * 
     *
     * @param jsonString 
     * @return 
     */
    public static Map<String, Object> decode(String jsonString) {
        try {
            TypeReference<HashMap<String, Object>> typeReference = new TypeReference<HashMap<String, Object>>() {};
            return JsonUtils.OBJECT_MAPPER.readValue(jsonString, typeReference);
        } catch (Exception e) {
            throw new SolanajException("Failed to decode map", e);
        }
    }

    /**
     * 
     *
     * @param <T> 
     * @param jsonString 
     * @param castAs 
     * @return 
     */
    public static <T> T decode(String jsonString, Class<T> castAs) {
        try {
            return JsonUtils.OBJECT_MAPPER.readValue(jsonString, castAs);
        } catch (Exception e) {
            throw new SolanajException("Failed to decode object of type %s", e, castAs.getName());
        }
    }

}

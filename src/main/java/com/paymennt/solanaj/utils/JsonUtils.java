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

// TODO: Auto-generated Javadoc
/**
 * The Class JsonUtils.
 *
 * @author bashar
 */
public class JsonUtils {

    /** The Constant OBJECT_MAPPER. */
    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder() //
            .addModule(new ParameterNamesModule())//
            .addModule(new Jdk8Module())//
            .addModule(new JavaTimeModule())//
            // other configuration modules
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();

    /** The Constant ENCODE_MAP. */
    private static final Map<Class<?>, Boolean> ENCODE_MAP = new ConcurrentHashMap<>();
    
    /** The Constant DECODE_MAP. */
    private static final Map<Class<?>, Boolean> DECODE_MAP = new ConcurrentHashMap<>();

    /**
     * Instantiates a new json utils.
     */
    private JsonUtils() {

    }

    /**
     * Gets the object mapper.
     *
     * @return the mapper
     */
    public static ObjectMapper getObjectMapper() {
        return JsonUtils.OBJECT_MAPPER;
    }

    /**
     * Can encode.
     *
     * @param object the object
     * @return true, if successful
     */
    public static boolean canEncode(Object object) {
        return canEncodeType(object.getClass());
    }

    /**
     * Can encode type.
     *
     * @param objectClass the object class
     * @return true, if successful
     */
    public static boolean canEncodeType(Class<?> objectClass) {
        if (JsonUtils.ENCODE_MAP.containsKey(objectClass))
            return JsonUtils.ENCODE_MAP.get(objectClass);
        boolean canEncode = JsonUtils.OBJECT_MAPPER.canSerialize(objectClass);
        JsonUtils.ENCODE_MAP.put(objectClass, canEncode);
        return canEncode;
    }

    /**
     * Can decode.
     *
     * @param object the object
     * @return true, if successful
     */
    public static boolean canDecode(Object object) {
        return canDecodeType(object.getClass());
    }

    /**
     * Can decode type.
     *
     * @param objectClass the object class
     * @return true, if successful
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
     * Can encode decode.
     *
     * @param object the object
     * @return true, if successful
     */
    public static boolean canEncodeDecode(Object object) {
        return canEncode(object) && canDecode(object);
    }

    /**
     * Encode.
     *
     * @param object the object
     * @return the string
     */
    public static String encode(Object object) {
        try {
            return JsonUtils.OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new SolanajException("Failed to encode object", e);
        }
    }

    /**
     * Encode pretty.
     *
     * @param object the object
     * @return the string
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
     * Decode.
     *
     * @param inputStream the input stream
     * @return the map
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
     * Decode.
     *
     * @param <T> the generic type
     * @param inputStream the input stream
     * @param castAs the cast as
     * @return the t
     */
    public static <T> T decode(InputStream inputStream, Class<T> castAs) {
        try {
            return JsonUtils.OBJECT_MAPPER.readValue(inputStream, castAs);
        } catch (Exception e) {
            throw new SolanajException("Failed to decode object of type %s", e, castAs.getName());
        }
    }

    /**
     * Decode.
     *
     * @param jsonString the json string
     * @return the map
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
     * Decode.
     *
     * @param <T> the generic type
     * @param jsonString the json string
     * @param castAs the cast as
     * @return the t
     */
    public static <T> T decode(String jsonString, Class<T> castAs) {
        try {
            return JsonUtils.OBJECT_MAPPER.readValue(jsonString, castAs);
        } catch (Exception e) {
            throw new SolanajException("Failed to decode object of type %s", e, castAs.getName());
        }
    }

}

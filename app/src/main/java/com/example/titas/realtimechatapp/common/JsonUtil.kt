package com.example.titas.realtimechatapp.common

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * Created by Titas on 8/5/2018.
 */
/**
 * Utility class for converting JSON objects/strings/etc.
 */
object JsonUtil {
    private val mapper = ObjectMapper()

    @Throws(Exception::class)
    fun <T> fromJson(value: ByteArray, clazz: Class<T>): T {
        return mapper.readValue(value, clazz)
    }

    @Throws(Exception::class)
    fun <T> fromJson(value: String, clazz: Class<T>): T {
        return mapper.readValue(value, clazz)
    }

    @Throws(Exception::class)
    fun asJson(value: Any): String {
        return mapper.writeValueAsString(value)
    }

    @Throws(Exception::class)
    fun <T> convert(value: JsonNode, clazz: Class<T>): T {
        return mapper.treeToValue(value, clazz)
    }

    @Throws(Exception::class)
    fun <T> convert(value: Map<String, Any>, clazz: Class<T>): T {
        return mapper.convertValue(value, clazz)
    }
}

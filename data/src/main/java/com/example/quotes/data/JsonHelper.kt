package com.example.quotes.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import javax.inject.Inject

interface JsonHelper {
    fun <T> toJson(obj: T): String
    fun <T> fromJson(json: String?, objClass: Class<T>): T?
}

class JsonHelperImpl @Inject constructor() : JsonHelper {

    private val gson = GsonBuilder().create()

    override fun <T> toJson(obj: T): String = gson.toJson(obj)

    override fun <T> fromJson(json: String?, objClass: Class<T>): T? {
        return json?.let {
            try {
                gson.fromJson(it, objClass)
            } catch (e: JsonSyntaxException) {
                null
            }
        }
    }
}
package com.example.easyin

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class IdentityDeserializer(
        val keyId: String,
        val keyMessage: String
    ){
        class Deserializer: ResponseDeserializable<Array<IdentityDeserializer>> {
            override fun deserialize(content: String): Array<IdentityDeserializer>? = Gson().fromJson(content, Array<IdentityDeserializer>::class.java)
    }
}
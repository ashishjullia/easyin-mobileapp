//package com.example.easyin
//
//import android.content.Context
//import android.provider.Contacts
//import androidx.appcompat.app.AppCompatActivity
//import com.github.kittinunf.fuel.httpGet
//import com.github.kittinunf.fuel.json.responseJson
//import com.github.kittinunf.result.Result
//import kotlinx.coroutines.async
//import kotlinx.coroutines.runBlocking
//import org.json.JSONArray
//
//class Identity{
//    private var dataGetArrayFun = JSONArray()
//
//    val list = ArrayList<IdentityItem>()
//
//    fun getIdentities(){
//        "http://oneeasyin.com:8080/identity/".httpGet()
//            .responseJson { request, response, result ->
//                when (result) {
//                    is Result.Failure -> {
//                        val ex = result.getException()
//                        print(ex)
//                    }
//                    is Result.Success -> {
//                        dataGetArrayFun = result.get().array()
//                        for (i in 0 until dataGetArrayFun.length()) {
//                            val item = IdentityItem(dataGetArrayFun.getJSONObject(i).optString("_id"), dataGetArrayFun.getJSONObject(i).optString("message"))
//                            list += item
//                        }
//                        ViewIdentity().populate(list)
//                    }
//                }
//            }
//    }
//}
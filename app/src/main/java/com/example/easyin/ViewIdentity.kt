package com.example.easyin

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main_view_identities.*
import org.json.JSONArray
import java.lang.Exception

class ViewIdentity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view_identities)


        var dataGetArrayFun = JSONArray()

        val list = ArrayList<IdentityItem>()

//        fun getIdentities(){
            "http://oneeasyin.com:8080/identity/".httpGet()
                .responseJson { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            print(ex)
                        }
                        is Result.Success -> {
                            dataGetArrayFun = result.get().array()
                            for (i in 0 until dataGetArrayFun.length()) {
                                val item = IdentityItem(
                                    dataGetArrayFun.getJSONObject(i).optString("_id"),
                                    dataGetArrayFun.getJSONObject(i).optString("message")
                                )
                                list += item
                            }
                            recycler_view.adapter = IdentityAdaptor(list)
                            recycler_view.layoutManager = LinearLayoutManager(this)
                            recycler_view.setHasFixedSize(true)
                        }
                    }
                }}}

//        Identity().getIdentities()
//    }

//    fun populate(list: ArrayList<IdentityItem>) {
//        var llist : ArrayList<IdentityItem> = list
//
//        try {
//            println("LIST is ->"+ " "+list[0].textKey + " "+ list[0].textKeyName )
//
//            recycler_view.adapter = IdentityAdaptor(llist)
//            println("ADAPTOR "+ " "+recycler_view.adapter)
//            recycler_view.layoutManager = LinearLayoutManager(this)
//            recycler_view.setHasFixedSize(true)
//        } catch (e: Exception) {
//            println("ERR"+ " "+e.message)
//        }
//
//    }
//}
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

class ViewIdentity : AppCompatActivity(), IdentityAdaptor.OnItemClickListener {
    var dataGetArrayFun = JSONArray()
    val list = ArrayList<IdentityItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view_identities)

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
                        recycler_view.adapter = IdentityAdaptor(list, this)
                        recycler_view.layoutManager = LinearLayoutManager(this)
                        recycler_view.setHasFixedSize(true)
                    }
                }
            }
         }

    override fun onItemClick(position: Int) {
        val clickedItem: IdentityItem = list[position]
        println(clickedItem)
        prompt(clickedItem)
    }

    private fun prompt(item: IdentityItem) {
        Bio(context = this@ViewIdentity).buildPrompt()
    }
}



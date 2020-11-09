package com.example.easyin

import android.app.Dialog
import android.content.Context
import kotlinx.android.synthetic.main.qr_result_dialog.*

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.awaitResult
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.jsonDeserializer
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result;
import org.json.JSONObject
import kotlin.reflect.typeOf

class QrScanResultDialog(var context : Context) {
    private  lateinit var dialog: Dialog
    private  var qrResult : AddIdentity? = null

    var qrResultUrl = ""

    init {
        initDialog()
    }

    private fun initDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.qr_result_dialog)
        dialog.setCancelable(false)
        Onclicks()
    }

    fun show(qrResult: String) {
        qrResultUrl = qrResult
       // dialog.scannedText.text = qrResult
        dialog.show()
    }
    private fun Onclicks() {
        dialog.saveResult.setOnClickListener {
                saveResult(qrResultUrl)
        }
        dialog.cancelDialog.setOnClickListener {
            dialog.dismiss()

        }
    }

    private fun saveResult(Url: String) {
        var message = ""
        var file = ""
        val httpGETAsync = Url.httpGet().responseJson { request, response, result ->
                            when (result) {
                                is Result.Failure -> {
                                    val ex = result.getException()
                                    println(ex)
                                }
                                is Result.Success -> {
                                    val data = result.get().obj()
                                    //textId.text = data["file"].toString()
                                    message = data["message"].toString()
                                    file = data["file"].toString()
                                }
                            }
                        }
                    httpGETAsync.join()
                    println(httpGETAsync)

                    println("1" + message)
                    println("2" + file)

                    val data = JSONObject()
                    data.put("message", message)
                    data.put("data", file)

                    val httpPOSTAsync = "http://oneeasyin.com:8080/dashboard/postidentity".httpPost().header("Content-Type" to "application/json").body(data.toString()).responseJson {
                            request, response, result ->
                        when (result) {
                            is Result.Failure -> {
                                val ex = result.getException()
                                println(ex)
                            }
                            is Result.Success -> {
                                val data = result.get().obj()
                                println(data)
                            }
                        }
                    }
    }
}
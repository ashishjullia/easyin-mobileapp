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
   // private  var qrResult : AddIdentity? = null

    private var qrResultUrl : String = ""
    var message : String = ""
    var file : String = ""

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
        dialog.getResult.setOnClickListener {
                getResult(qrResultUrl)
        }
        dialog.postResult.setOnClickListener {
            postResult(qrResultUrl)
        }
        dialog.cancelDialog.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun getResult(Url: String) {
        dialog.scannedText.text = Url

        Url.httpGet().responseJson { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    print(ex)
                }
                is Result.Success -> {
                    val dataGET = result.get().obj()
                    println("ONE" + " " + result.get().obj());
                    println("TWO" + " " + dataGET["message"].toString())
                    println("THREE" + " " + dataGET["file"].toString())

                    message = dataGET["message"].toString()
                    file = dataGET["file"].toString()
                    dialog.scannedText.text = "Data Captured"
                }
            }
        }
    }

private fun postResult(Url: String) {


        val dataPOST = JSONObject()
        dataPOST.put("message", message)
        dataPOST.put("data", file)

        println(dataPOST)
            "http://oneeasyin.com:8080/dashboard/postidentity"
                .httpPost()
                .header("Content-Type" to "application/json")
                .body(dataPOST.toString()).responseJson {
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

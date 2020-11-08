package com.example.easyin


import android.app.DownloadManager
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.awaitResult
import com.github.kittinunf.fuel.core.extensions.jsonBody
import kotlinx.android.synthetic.main.activity_add_identity.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import java.util.jar.Manifest

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.jsonDeserializer
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result;
import org.json.JSONObject
import kotlin.reflect.typeOf


private const val CAMERA_REQUEST_CODE = 101

class AddIdentity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_identity)
        setupPermissions()
        codeScanner()
    }


    private fun codeScanner() {
        codeScanner = CodeScanner(this, addId)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            var message = ""
            var file = ""

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    val httpGETAsync = it.text.toString()
                        .httpGet().responseJson { request, response, result ->
                            when (result) {
                                is Result.Failure -> {
                                    val ex = result.getException()
                                    println(ex)
                                }
                                is Result.Success -> {
                                    val data = result.get().obj()
                                    textId.text = data["file"].toString()
                                    message = data["message"].toString()
                                    file = data["file"].toString()
//                                    println(message)
//                                    println(file)
                                }
                            }
                        }
                    httpGETAsync.join()
                    println(httpGETAsync)

//                        .responseString { request, response, result ->
//                            when (result) {
//                                is Result.Failure -> {
//                                    val ex = result.getException()
//                                    println(ex)
//                                }
//                                is Result.Success -> {
//                                    val data = result.get()
//                                    println(data)
//                                    textId.text = data.
//                                }
//                            }
//                        }


                    println("1" + message)
                    println("2" + file)

                    val data = JSONObject()
                    data.put("message", message)
                    data.put("data", file)
//                    "http://oneeasyin.com:8080/dashboard/postidentity".httpPost().header("Content-Type" to "application/json").body(data.toString()).response { req, res, result ->
//                        //Ought to be a Success!
//                        println(res)
//                    }
//
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
                    //println(httpPOSTAsync)
                }
            }
            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }

        addId.setOnClickListener {
            codeScanner.startPreview()
        }
    }



    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(
                        this,
                        "You need the camera permission to use this app!",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }
}

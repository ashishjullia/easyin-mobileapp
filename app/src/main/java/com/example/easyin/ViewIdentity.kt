package com.example.easyin

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main_view_identities.*
import org.json.JSONArray
import org.json.JSONObject

class ViewIdentity : AppCompatActivity(), IdentityAdaptor.OnItemClickListener {
    var dataGetArrayFun = JSONArray()
    val list = ArrayList<IdentityItem>()
    var emailIDSelected: String = ""

    // Prepare BIOMETRIC
    private var cancellationSignal : CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback

        get() =
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Authentication success!")
                    login()
                }
            }

    // GET the Identities
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
                                dataGetArrayFun.getJSONObject(i).optString("email")
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
         emailIDSelected = clickedItem.textEmail
        println(clickedItem)
        prompt(clickedItem)
    }

    private fun prompt(item: IdentityItem) {
        buildPrompt()

    }

    fun notifyUser(message : String) {
        Toast.makeText(this ,message, Toast.LENGTH_SHORT).show()
    }

    fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }

    fun buildPrompt() {
        checkBiometricSupport()
        val biometricPrompt = BiometricPrompt.Builder(this)
            .setTitle("Authentication is required")
            .setNegativeButton("Cancel", this.mainExecutor, DialogInterface.OnClickListener { dialog, which ->
                notifyUser("Authentication cancelled")
            }).build()
        biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
    }

    fun checkBiometricSupport() : Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if(!keyguardManager.isKeyguardSecure) {
            notifyUser("Fingerprint authentication not enabled in settings")
            return false
        }
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint authentication permission is not enabled in settings")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    // Setting a fingerprint input
    fun login() {
        val dataPOST = JSONObject()
        dataPOST.put("email", emailIDSelected)
        dataPOST.put("status", true)

        println(dataPOST)
        "http://oneeasyin.com:8080/users/setfingerprintinput"
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





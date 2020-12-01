package com.example.easyin

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
                addId.setOnClickListener {
                    val intent = Intent(this, AddIdentity::class.java)
                    startActivity(intent)
                }

                viewId.setOnClickListener {
                    val intent = Intent(this, ViewIdentity::class.java)
                    startActivity(intent)
                }
            }

    }



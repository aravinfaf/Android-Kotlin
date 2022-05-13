package com.aravind.learnandroid.auotfill

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aravind.learnandroid.OtpTextView
import com.aravind.learnandroid.R
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern


class PermissionsActivityDemo : AppCompatActivity() {

    private val READ_SMS_CODE = 123
    private val REQ_USER_CONSENT = 111
    private lateinit var smsEditText: OtpTextView
    private lateinit var etOTP : TextInputEditText
    lateinit var smsBroadcastReceiver: SmsReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_textview)

        smsEditText = findViewById(R.id.otp_view)
        etOTP = findViewById(R.id.etOTP)

        checkPermission(Manifest.permission.READ_SMS, READ_SMS_CODE)
        startSmartUserConsent()
    }

    private fun startSmartUserConsent() {
        val client = SmsRetriever.getClient(this)
        client.startSmsUserConsent(null)
    }

    private fun registerBroadcastReceiver(){
        smsBroadcastReceiver = SmsReceiver()
        smsBroadcastReceiver?.smsBroadcastReceiverListener = object : SmsReceiver.broadcastReceiverListener{
            override fun onSuccess(intent: Intent?) {
                startActivityForResult(intent,REQ_USER_CONSENT)
            }
            override fun onFailure() {
            }
        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReceiver,intentFilter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_USER_CONSENT){
            if (resultCode == RESULT_OK && data!= null){
                    val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                val otpPattern = Pattern.compile("(|^)\\d{6}")
                val matcher = otpPattern.matcher(message)
                if (matcher.find()){
                    val otp : String = matcher.group(0)
                    Log.d("OOO",otp)
                    Toast.makeText(this@PermissionsActivityDemo, otp, Toast.LENGTH_SHORT).show()
                    smsEditText.setOTP(otp)
                    etOTP.setText(otp)
                }
            }
        }
    }

    private fun getMessageOtp(message: String?) {

        val otpPattern = Pattern.compile("(|^)\\d{6}")
        val matcher = otpPattern.matcher(message)
        if (matcher.find()){
            val otp : String = matcher.group(0)
            Log.d("OOO",otp)
            Toast.makeText(this@PermissionsActivityDemo, otp, Toast.LENGTH_SHORT).show()
            smsEditText.setOTP(otp)
            etOTP.setText(otp)
        }
    }

    override fun onStart() {
        super.onStart()
        registerBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsBroadcastReceiver)
    }
    private fun checkPermission(permission: String, code: Int) {
    if (ContextCompat.checkSelfPermission(this@PermissionsActivityDemo,
            permission) == PackageManager.PERMISSION_DENIED
    ) {
        ActivityCompat.requestPermissions(this@PermissionsActivityDemo, arrayOf(permission), code)
    } else {
        Toast.makeText(this@PermissionsActivityDemo, "Permission Already Granted", Toast.LENGTH_SHORT)
            .show()
    }
}

}

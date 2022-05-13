package com.aravind.learnandroid

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OTPActivity : AppCompatActivity() {

    private var otpTextView: OtpTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_textview)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
         otpTextView = findViewById(R.id.otp_view)
        otpTextView?.requestFocusOTP()

        val data = "1467"
        otpTextView?.setOTP(data)

        otpTextView?.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                Toast.makeText(applicationContext, "The OTP is $otp", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
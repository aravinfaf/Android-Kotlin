package com.aravind.learnandroid

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class PhoneNumberPatter : AppCompatActivity() {

    private var mPhoneNumber: EditText? = null
    private var mOutputNumber: TextView? = null
    private var mOutputNumber_refined: TextView? = null
    private var mBtn: Button? = null
    val mPattern ="###.###.####"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number_pattern)

        mPhoneNumber = findViewById(R.id.phone_number)
        mBtn = findViewById<View>(R.id.getNumber) as Button
        mOutputNumber = findViewById<View>(R.id.output_number) as TextView
        mOutputNumber_refined = findViewById<View>(R.id.output_number_refinded) as TextView


        mPhoneNumber!!.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, count: Int) {
                val phone: StringBuilder = StringBuilder(s)

                if (count > 0 && !isValid(phone.toString())) {
                    for (i in  phone.indices) {
                        Log.d("TAG", String.format("%s", phone))
                        val c: Char = mPattern.get(i)
                        if (c != '#' && c != phone.get(i)) {
                            phone.insert(i, c)
                        }
                    }
                    mPhoneNumber!!.setText(phone)
                    mPhoneNumber!!.setSelection(mPhoneNumber!!.getText().length)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        //set max length of string
        val maxLength: Int = mPattern.length
        mPhoneNumber!!.filters=(arrayOf<InputFilter>(LengthFilter(maxLength)))

    }
    private fun isValid(phone: String): Boolean {
        for (i in 0 until phone.length) {
            val c: Char = mPattern.get(i)
            if (c == '#') continue
            if (c != phone[i]) {
                return false
            }
        }
        return true
    }
}
package com.aravind.learnandroid.auotfill

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsReceiver : BroadcastReceiver() {

    var smsBroadcastReceiverListener : broadcastReceiverListener? = null

    override fun onReceive(context : Context?, intent : Intent?) {

        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action){
            val extras = intent.extras
            var smsRetrieveStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as? Status
            when(smsRetrieveStatus?.statusCode){
                CommonStatusCodes.SUCCESS -> {
                    val messageIntent = extras?.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                    smsBroadcastReceiverListener?.onSuccess(messageIntent)
                }
                CommonStatusCodes.TIMEOUT -> {

                }
            }

        }
    }

    public interface broadcastReceiverListener{
        fun onSuccess(intent: Intent?)
        fun onFailure()
    }
}
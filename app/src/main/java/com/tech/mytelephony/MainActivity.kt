package com.tech.mytelephony

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //step 1: to send the sms we need to add permission  SEND_SMS
        //CALL_PHONE


        btn_call.setOnClickListener {
            var status = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            if (status == PackageManager.PERMISSION_GRANTED) {
                call()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    111
                )
            }
        }


        btn_sms.setOnClickListener {

            var status = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            if (status == PackageManager.PERMISSION_GRANTED) {
                sendSms()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 222)
            }

        }
        btn_call_me.setOnClickListener {

            Log.d("CLICK","clicked")
            var status = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            if (status == PackageManager.PERMISSION_GRANTED) {
                var intent = Intent()
                intent.action = Intent.ACTION_CALL
                intent.data = Uri.parse("tel:" + "9999999999")
                startActivity(intent)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    333
                )
            }
        }

    }

    private fun sendSms() {
        var msg = et_message.text.toString()

        var mobile_number = et_mobile_number.text.toString()


        var list = mobile_number.split('@')


        for (number in list) {
            var smsManager = SmsManager.getDefault()

            var si = Intent(this, SendActivity::class.java)
            var send_intent = PendingIntent.getActivity(this, 0, si, 0)


            var di = Intent(this, DeliveredActivity::class.java)
            var del_intent = PendingIntent.getActivity(this, 0, di, 0)


            smsManager.sendTextMessage(number, null, msg, send_intent, del_intent)

        }


    }


    private fun call() {


        var intent = Intent()
        intent.action = Intent.ACTION_CALL
        intent.data = Uri.parse("tel:" + et_mobile_number.text.toString())
        startActivity(intent)



    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            call()
        } else if (requestCode == 222 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendSms()
        } else if (requestCode == 333 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            var intent = Intent()
            intent.action = Intent.ACTION_CALL
            intent.data = Uri.parse("tel:" + "9999999999")
            startActivity(intent)
        }else
        {
            Toast.makeText(this, "user is not allowed here", Toast.LENGTH_SHORT).show()
        }

    }
}
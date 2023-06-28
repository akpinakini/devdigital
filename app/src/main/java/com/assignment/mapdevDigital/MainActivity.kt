package com.example.send_message_to_number

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.send_message_to_number.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private val SENT: String = "1"
    private val DELIVERED: String? = "1"
    val permissionRequest = 101
    lateinit var binding: ActivityMainBinding
    var sim = 0

    lateinit var smsManager: SmsManager
//    var permissionGranted = ContextCompat.checkSelfPermission(this,
//        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED


    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sim1.setOnClickListener {
            sim=0
        }
        binding.sim2.setOnClickListener {
            sim=1
        }


        binding.send.setOnClickListener {

            val number = binding.editText.text.toString().trim()
            val msg = binding.editText2.text.toString().trim()

            try {
                val permissionCheck =
                    ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

                    if (number != "" && msg != "") {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

                            val method = Class.forName("android.telephony.SubscriptionManager")
                                .getDeclaredMethod(
                                    "getSubId",
                                    Int::class.javaPrimitiveType
                                )
                            method.isAccessible = true
//                            val simID =sim //while simID is the slot number of your second simCard

                            val param = method.invoke(null, sim) as IntArray
                            val inst: Int = param[0]
                            smsManager = SmsManager.getSmsManagerForSubscriptionId(inst)
                            smsManager.sendTextMessage(number, null, msg, null, null)
//                            smsManager = getSystemService(SmsManager::class.java)

//                            smsManager.createForSubscriptionId(0)
//                                .sendTextMessage(
//                                    number,
//                                    null,
//                                    msg,
//                                    null,
//                                    null
//                                )
//                                Toast.makeText(applicationContext,
//                                    "found 1 sim $sim in the phone",
//                                    Toast.LENGTH_LONG).show()
                        }

//                        val s:SmsManager=SmsManager().createForSubscriptionId(sim)


                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Some gfhsfdh is Empty",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.SEND_SMS),
                        permissionRequest
                    )
                }
            } catch (e: Exception) {

                try {
                    smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(
                        number,
                        null,
                        msg,
                        null,
                        null
                    )

                    Toast.makeText(
                        applicationContext,
                        "Sending Sms from default sim",
                        Toast.LENGTH_LONG
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(
                        applicationContext,
                        "Please set your bank link sim as defuilt",
                        Toast.LENGTH_LONG
                    ).show()
                }


            }
        }
    }


//
//    private fun sendSMS(phoneNumber: String, message: String) {
//        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
//        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, sentPI, null)


}
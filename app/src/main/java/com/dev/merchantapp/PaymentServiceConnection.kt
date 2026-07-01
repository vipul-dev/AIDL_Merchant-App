package com.dev.merchantapp

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.dev.paymentapp.IPaymentService

class PaymentServiceConnection(
    private val onConnected: (IPaymentService) -> Unit,
) : ServiceConnection{
    override fun onServiceConnected(
        name: ComponentName?,
        service: IBinder?
    ) {

        Log.e("AIDL_TEST", "Service Connected")

        val payment = IPaymentService.Stub.asInterface(service)

        onConnected(payment)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.e("AIDL_TEST", "Service Disconnected")
    }


}

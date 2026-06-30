package com.dev.merchantapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dev.merchantapp.ui.theme.MerchantAppTheme
import com.dev.paymentapp.IPaymentCallback
import com.dev.paymentapp.IPaymentService
import com.dev.paymentapp.TransactionResult

class MainActivity : ComponentActivity() {

    private var paymentService: IPaymentService? = null
    private lateinit var connection: PaymentServiceConnection
    private var isBound = false

    private val paymentCallback =
        object : IPaymentCallback.Stub() {

            override fun onSuccess(
                result: TransactionResult?
            ) {

                Log.d(
                    "Merchant",
                    "Success: $result"
                )
            }

            override fun onFailure(
                error: String?
            ) {

                Log.d(
                    "Merchant",
                    "Failed: $error"
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MerchantAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            if (isBound) {
                                paymentService?.startPayment(100.0, paymentCallback)
                            }else{
                                Log.e("AIDL_TEST", "Payment app not installed")
                            }
                        }) {
                            Text("Pay")
                        }
                    }
                }
            }
        }

        connection = PaymentServiceConnection {
            paymentService = it
        }

        val intent = Intent("com.dev.paymentapp.PAYMENT_SERVICE").apply {
            setPackage("com.dev.paymentapp")
        }


        isBound = bindService(
            intent,
            connection,
            BIND_AUTO_CREATE
        )

        Log.e("AIDL_TEST", "Bound = $isBound")


    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    fun canBindToPaymentService(): Boolean{
        val intent = Intent("com.dev.paymentapp.PAYMENT_SERVICE").apply {
            setPackage("com.dev.paymentapp")
        }

        val service = packageManager.queryIntentServices(intent,0)

        return service.isNotEmpty()
    }
}

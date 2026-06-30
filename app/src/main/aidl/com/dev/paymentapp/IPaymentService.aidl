package com.dev.paymentapp;

import com.dev.paymentapp.TransactionResult;
import com.dev.paymentapp.IPaymentCallback;

interface IPaymentService {

   void startPayment(double amount,IPaymentCallback callback);

   boolean cancelPayment();
}
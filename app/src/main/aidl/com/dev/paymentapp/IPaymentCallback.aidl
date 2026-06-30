package com.dev.paymentapp;

import com.dev.paymentapp.TransactionResult;

interface IPaymentCallback {

void onSuccess(in TransactionResult result);

void onFailure(String error);

}
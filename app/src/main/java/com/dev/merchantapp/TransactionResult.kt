package com.dev.paymentapp

import android.os.Parcel
import android.os.Parcelable


data class TransactionResult(
    val transactionId: String,
    val status: String,
    val amount: Double
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(transactionId)
        parcel.writeString(status)
        parcel.writeDouble(amount)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<TransactionResult> {
        override fun createFromParcel(parcel: Parcel): TransactionResult {
            return TransactionResult(parcel)
        }

        override fun newArray(size: Int): Array<TransactionResult?> {
            return arrayOfNulls(size)
        }
    }
}
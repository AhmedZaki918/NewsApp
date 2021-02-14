package com.example.newsapp.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

data class MainResponse(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Article),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(articles)
        parcel.writeString(status)
        parcel.writeInt(totalResults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainResponse> {
        override fun createFromParcel(parcel: Parcel): MainResponse {
            return MainResponse(parcel)
        }

        override fun newArray(size: Int): Array<MainResponse?> {
            return arrayOfNulls(size)
        }
    }

}
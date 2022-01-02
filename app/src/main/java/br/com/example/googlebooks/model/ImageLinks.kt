package br.com.example.googlebooks.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
):Parcelable
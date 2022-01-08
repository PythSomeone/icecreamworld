package com.example.icecreamworld.data.handler

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.example.icecreamworld.data.Folder
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

const val defaultImageUrl =
    "https://firebasestorage.googleapis.com/v0/b/icecreamworld-22d70.appspot.com/o/No-Image-Placeholder.png?alt=media&token=3c7dc124-027e-4f09-9d2f-468c0ed976ce"

object StorageHandler {

    private val storage = Firebase.storage
    private val ref = storage.reference

    // It will return download url for picture
    suspend fun uploadPicture(uri: Uri, folder: Folder, dataId: String): String {
        var urlToFile = ""
        val streamRef =
            ref.child("${folder.name}/${dataId}")

        streamRef.putFile(uri)
            .addOnSuccessListener {
                Log.d(TAG, "$uri was added")
            }.await()
        streamRef.downloadUrl.addOnCompleteListener {
            urlToFile = it.result.toString()
        }.await()
        return urlToFile
    }

    fun removePicture(downloadUrl: String) {
        if (downloadUrl != defaultImageUrl) {
            val photoRef = Firebase.storage.getReferenceFromUrl(downloadUrl)
            photoRef.delete()
        }
    }

}
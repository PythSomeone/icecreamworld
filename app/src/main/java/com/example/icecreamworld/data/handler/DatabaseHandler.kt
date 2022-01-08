package com.example.icecreamworld.data

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.example.icecreamworld.data.handler.StorageHandler
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

enum class Folder {
    Shops,
    ShopForms,
    Tags
}

class Handler(private val folder: Folder) {

    private val database = Firebase.database
    private val ref = database.getReference(folder.name)

    fun addValue(obj: Any): String {
        val refKey = ref.push().key
        if (refKey != null)
            setValue(refKey, obj)
        return refKey!!
    }

    fun setValue(id: String, obj: Any) {
        ref.child(id)
            .setValue(obj)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Successfully changed $obj in database")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "Something went wrong during setting $obj in database", it)
            }
    }

    fun changeValue(id: String, obj: Any) {
        ref.child(id).get()
            .addOnSuccessListener {
                if (it.exists())
                    setValue(id, obj)
                else
                    Log.d(ContentValues.TAG, "There is no $id in database!")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "Something went wrong during changing $obj in database", it)
            }
    }

    fun deleteValue(id: String) {
        ref.child(id).removeValue()
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Successfully deleted $id in database")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "Something went wrong during deleting $id in database", it)
            }
    }

    fun initializeListener(listener: ChildEventListener) {
        ref.addChildEventListener(listener)
    }

}
package com.example.icecreamworld.data

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

enum class RefName {
    Shops,
    Tags
}

class Handler(refName: RefName) {

    private val database = Firebase.database
    private val ref = database.getReference(refName.name)

    fun addValue(obj: Any) {
        ref.push()
            .setValue(obj)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Successfully added $obj to database")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "Something went wrong setting $obj to database", it)
            }
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
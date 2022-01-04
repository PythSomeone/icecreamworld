package com.example.icecreamworld.data.repository

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

object TagRepository {

    private val _tags = mutableStateOf<MutableList<DataSnapshot>>(mutableListOf())
    val tags: State<MutableList<DataSnapshot>> = _tags

    internal fun addTag(snapshot: DataSnapshot) {
        if (snapshot.key != null) {
            _tags.component1().add(snapshot)
        }
    }
    internal fun changeTag(snapshot: DataSnapshot) {
        snapshot.key?.let { tagIndex(it) }
            .run {
                if (this != null)
                    _tags.component1().set(this, snapshot)
                else throw error("There is no tag with this key, ${snapshot.key}")
            }
    }
    internal fun removeTag(snapshot: DataSnapshot) {
        snapshot.key?.let { tagIndex(it) }
            .run {
                if (this != null)
                    _tags.component1().removeAt(this)
                else throw error("There is no tag with this key, ${snapshot.key}")
            }
    }

    private fun tagIndex(key: String): Int? {
        _tags.component1().forEachIndexed { index, snapshot ->
            if (snapshot.key.equals(key))
                return index
        }
        return null
    }

}

object TagListener: ChildEventListener {
    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        TagRepository.addTag(snapshot)
        Log.d(ContentValues.TAG, "$snapshot was added to local repository")
    }
    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        TagRepository.changeTag(snapshot)
        Log.d(ContentValues.TAG, "$snapshot was changed in local repository")
    }
    override fun onChildRemoved(snapshot: DataSnapshot) {
        TagRepository.removeTag(snapshot)
        Log.d(ContentValues.TAG, "$snapshot was removed from local repository")
    }
    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
    override fun onCancelled(error: DatabaseError) {}
}
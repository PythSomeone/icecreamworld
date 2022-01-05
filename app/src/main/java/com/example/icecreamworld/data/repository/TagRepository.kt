package com.example.icecreamworld.data.repository

import android.content.ContentValues
import android.util.Log
import com.example.icecreamworld.data.Handler
import com.example.icecreamworld.data.RefName
import com.example.icecreamworld.model.Tag
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue


object TagRepository: Repository(Handler(RefName.Tags)) {

    fun addTag(tag: Tag) {
        data.value.forEach {
            if (it.getValue<Tag>()!!.name!!.capitalize() == tag.name)
                return
        }
        handler.addValue(
            tag.copy(name = tag.name!!.capitalize()))
    }
    fun changeTag(id: String, tag: Tag) {
        handler.changeValue(id, tag)
    }
    fun deleteTag(id: String) {
        handler.deleteValue(id)
    }

    fun getTag(id: String): Tag {
        val index = dataIndex(id)
        if (index != null)
            return data.value[index].getValue<Tag>() as Tag
        return Tag()
    }

    fun tagUsed(id: String) {
        val tag = getTag(id)
        handler.changeValue(id, tag.copy(numberOfUses = tag.numberOfUses.inc()))
    }

    fun listenToChanges() {
        handler.initializeListener(Listener)
    }

    private object Listener: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            addData(snapshot)
            Log.d(ContentValues.TAG, "$snapshot was added to local repository")
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            changeData(snapshot)
            Log.d(ContentValues.TAG, "$snapshot was changed in local repository")
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            removeData(snapshot)
            Log.d(ContentValues.TAG, "$snapshot was removed from local repository")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    }

}

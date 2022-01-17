package com.example.icecreamworld.data.repository

import android.content.ContentValues
import android.util.Log
import com.example.icecreamworld.data.Folder
import com.example.icecreamworld.data.Handler
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.model.Tag
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue
import java.util.*


object TagRepository : Repository(Handler(Folder.Tags)) {

    internal fun addTag(tag: Tag) {
        data.value.forEach {
            if (it.getValue<Tag>()!!.name!! == tag.name)
                return
        }
        handler.addValue(
            tag.copy(name = tag.name!!)
        )
    }

    internal fun changeTag(id: String, tag: Tag) {
        handler.changeValue(id, tag)
    }

    internal fun deleteTag(id: String) {
        handler.deleteValue(id)
    }

    fun getTag(id: String): Tag? {
        val index = dataIndex(id)
        if (index != null)
            return data.value[index].getValue<Tag>() as Tag
        return null
    }

    fun getId(name: String): String? {
        data.value.forEach {
            val tag = it.getValue<Tag>() as Tag
            if (tag.name == name)
                return it.key
        }
        return null
    }

    fun listenToChanges() {
        handler.initializeListener(Listener)
    }

    private object Listener : ChildEventListener {
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

class TagUses(
    private val shop: Shop,
    private val previousShop: Shop? = null
) {

    private val usedTags = countTagsUsed()

    fun increase() {
        usedTags.forEach {
            tagUsed(it, it.numberOfUses)
        }
    }

    fun decrease() {
        usedTags.forEach {
            tagUsed(it, -it.numberOfUses)
        }
    }

    private fun countTagsUsed(): MutableList<Tag> {
        val tagsUsed = mutableListOf<Tag>()

        for (product in shop.menu) {
            for (tag in product.tags) {
                if (tag.name != "")
                {
                    if (tagsUsed.isEmpty())
                        tagsUsed.add(tag.copy(numberOfUses = 1))
                    else {
                        var found = false
                        for (it in tagsUsed) {
                            if (it.name == tag.name) {
                                it.numberOfUses += 1
                                found = true
                                break
                            }
                        }
                        if (found.not())
                            tagsUsed.add(tag.copy(numberOfUses = 1))
                    }
                }
            }
        }
        if (previousShop != null) {
            for (product in previousShop.menu) {
                for (tag in product.tags) {
                    if (tagsUsed.isEmpty())
                        tagsUsed.add(tag.copy(numberOfUses = -1))
                    else {
                        var found = false
                        for (it in tagsUsed) {
                            if (it.name == tag.name) {
                                it.numberOfUses -= 1
                                found = true
                                break
                            }
                            if (found.not())
                                tagsUsed.add(tag.copy(numberOfUses = -1))
                        }
                    }
                }
            }
        }
        return tagsUsed
    }

    private fun tagUsed(tag: Tag, numberOfUses: Int) {
        if (tag.name == null)
            return
        val tagId = TagRepository.getId(tag.name)

        if (tagId == null)
            TagRepository.addTag(tag.copy(numberOfUses = tag.numberOfUses))
        else {
            val tag = TagRepository.getTag(tagId)
            TagRepository.changeTag(
                tagId,
                tag!!.copy(numberOfUses = tag.numberOfUses + numberOfUses)
            )
        }
    }

}

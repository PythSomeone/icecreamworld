package com.example.icecreamworld.data.repository

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.icecreamworld.data.handler.RefName
import com.google.firebase.database.DataSnapshot

open class Repository(val refName: RefName) {

    private val _data = mutableStateOf<MutableList<DataSnapshot>>(mutableListOf())
    val data: State<MutableList<DataSnapshot>> = _data

    protected fun addData(snapshot: DataSnapshot) {
        if (snapshot.key != null) {
            _data.component1().add(snapshot)
        }
    }
    protected fun changeData(snapshot: DataSnapshot) {
        snapshot.key?.let { dataIndex(it) }
            .run {
                if (this != null)
                    _data.component1().set(this, snapshot)
                else Log.d("Repository","There is no data with this key, ${snapshot.key}")
            }
    }
    protected fun removeData(snapshot: DataSnapshot) {
        snapshot.key?.let { dataIndex(it) }
            .run {
                if (this != null)
                    _data.component1().removeAt(this)
                else Log.d("Repository","There is no data with this key, ${snapshot.key}")
            }
    }

    private fun dataIndex(key: String): Int? {
        _data.component1().forEachIndexed { index, snapshot ->
            if (snapshot.key.equals(key))
                return index
        }
        return null
    }

}

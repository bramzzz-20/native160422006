package com.ubaya.studentproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.studentproject.model.Student

class DetailViewModel(app: Application):AndroidViewModel(app) {
    val studentLD = MutableLiveData<Student>()
    val TAG = "volleytag"
    private var queue: RequestQueue? = null

    fun update() {
        // diisi coding untuk simpan studentLD object ke server
        // pake volley
    }

    fun fetch(id:String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/LLMW"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                val sType = object : TypeToken<List<Student>>() {}.type
                val result = Gson().fromJson<List<Student>>(it, sType)
                val arrStudent = result as ArrayList<Student>
                studentLD.value = arrStudent.find { it.id == id } as Student
                //sukses
            },
            {
                //gagal
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)

    }
}
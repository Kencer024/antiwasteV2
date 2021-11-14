package com.example.antiwaste3_0.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "AntiWaste Homepage"
    }
    val text: LiveData<String> = _text
}
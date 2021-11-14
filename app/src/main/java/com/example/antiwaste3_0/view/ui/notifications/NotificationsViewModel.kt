package com.example.antiwaste3_0.view.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Rewards available\n(10 points each)"
    }
    val text: LiveData<String> = _text
}
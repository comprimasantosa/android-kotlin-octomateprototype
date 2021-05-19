package com.primasantosa.android.octomateprototype.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.primasantosa.android.octomateprototype.data.OctoRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: OctoRepository) : ViewModel() {
    private val _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String>
        get() = _name

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        _isLoading.value = false
    }

    fun login(body: Map<String, String>) {
        viewModelScope.launch {
            _isLoading.value = true
            _name.value = repository.postLogin(body).name
            _isLoading.value = false
        }
    }
}
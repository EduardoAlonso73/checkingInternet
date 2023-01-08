package com.example.checkintenet.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkintenet.data.Resposity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class mainViewModel : ViewModel() {
    private val repository: Resposity by lazy { Resposity() }
   // val listUser: MutableLiveData<Response<PostEnt>> by lazy { MutableLiveData() }
    val isResponseSuccessful: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    init {initGetUser()}


    fun initGetUser() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
                try {
                    repository.getUser()
                    isResponseSuccessful.postValue(true)

                }catch (e:Exception){

                    isResponseSuccessful.postValue(false)
                }finally {
                    isLoading.postValue(false)
                }
        }
    }


}
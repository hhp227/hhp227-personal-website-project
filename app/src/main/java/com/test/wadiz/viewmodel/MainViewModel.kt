package com.test.wadiz.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.test.wadiz.data.ListData
import com.test.wadiz.repo.RequestRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: RequestRepository) : ViewModel() {
    private val _list: MutableLiveData<List<ListData>> = MutableLiveData(emptyList())
    val list: LiveData<List<ListData>> get() = _list

    val isProgressBarGone = MutableLiveData(true)

    var currentQuery = ""

    fun requestSearch(type: String? = null) {
        isProgressBarGone.postValue(false)
        viewModelScope.launch {
            repository.requestSearch(currentQuery).also { response ->
                if ((200 until 300).contains(response.statusCode)) {
                    _list.postValue(
                        response.body
                            .list
                            .filter { data -> type?.let { data.type == it || data.type.contains(it) } ?: true }
                            .toList()
                    )
                    isProgressBarGone.postValue(true)
                }
            }
        }
    }
}

class MainViewModelFactory(private val repository: RequestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}
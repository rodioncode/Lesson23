package com.study.android.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.android.myapplication.domain.CatModel

class MainActivityViewModel : ViewModel() {

    private val _cats = MutableLiveData<CatModel>()
    val cats: LiveData<CatModel> get() = _cats

    fun loadUser(catId: String) {
        // Симуляция загрузки данных
        _cats.value = CatModel(catId, "https://teachmeskills.ru/", true)
    }
}
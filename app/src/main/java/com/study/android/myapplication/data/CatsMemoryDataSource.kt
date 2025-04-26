package com.study.android.myapplication.data

import com.study.android.myapplication.domain.CatModel

internal class CatMemoryDataSource {

    val cache: MutableList<CatModel> = mutableListOf()

    fun setLike(value: Boolean, id: String) {
        val cat = cache.find { it.id == id }
        val catIndex = cache.indexOf(cat)
        cache.set(catIndex, cat!!.copy(isLiked = value))
    }
}
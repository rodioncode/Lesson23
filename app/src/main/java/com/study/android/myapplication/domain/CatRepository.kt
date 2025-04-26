package com.study.android.myapplication.domain

interface CatRepository {
    suspend fun getCats(limit: Int): List<CatModel>
    suspend fun setLike(value: Boolean, id: String)
}
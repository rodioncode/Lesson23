package com.study.android.myapplication.domain

// *Model = Entity в Clean Architecture
data class CatModel(
    val id: String,
    val imageUrl: String,
    val isLiked: Boolean,
)
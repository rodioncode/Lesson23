package com.study.android.myapplication.data

import com.study.android.myapplication.domain.CatModel
import com.study.android.myapplication.domain.CatRepository

internal class CatRepositoryImpl(
    private val catRemoteDataSource: CatRemoteDataSource,
    private val catMemoryDataSource: CatMemoryDataSource,
) : CatRepository {

    override suspend fun getCats(limit: Int): List<CatModel> {
        val cats = catRemoteDataSource.getCats(limit)
            .mapToDomainModels(isLikedGetter = { catMemoryDataSource.getLike(it) })
        catMemoryDataSource.save(cats)
        return cats
    }

    override suspend fun setLike(value: Boolean, id: String) =
        catMemoryDataSource.setLike(value, id)
}
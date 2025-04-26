package com.study.android.myapplication.data

import com.study.android.myapplication.domain.CatModel

internal fun CatResponseModel.mapToDomainModel(isLiked: Boolean) =
    CatModel(id, url, isLiked)

internal fun List<CatResponseModel>.mapToDomainModels(
    isLikedGetter: (String) -> Boolean,
) = map { it.mapToDomainModel(isLiked = isLikedGetter(it.id)) }
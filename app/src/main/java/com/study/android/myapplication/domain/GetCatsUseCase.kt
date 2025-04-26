package com.study.android.myapplication.domain

interface GetCatsUseCase {
    fun invoke(): List<CatModel>
}

class GetCatsUseCaseImpl: GetCatsUseCase{
    override fun invoke(): List<CatModel> {
        TODO("Not yet implemented")
    }
}
package com.emilien.equiapp.di

import com.emilien.equiapp.data.auth.AuthRepositoryImpl
import com.emilien.equiapp.data.course.CourseRepositoryImpl
import com.emilien.equiapp.data.participation.ParticipationRepositoryImpl
import com.emilien.equiapp.domain.auth.AuthRepository
import com.emilien.equiapp.domain.auth.LoginUseCase
import com.emilien.equiapp.domain.auth.SignUpUseCase
import com.emilien.equiapp.domain.course.CourseRepository
import com.emilien.equiapp.domain.course.GetCoursesUseCase
import com.emilien.equiapp.domain.participation.ParticipationRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    singleOf(::CourseRepositoryImpl) bind CourseRepository::class
    singleOf(::ParticipationRepositoryImpl) bind ParticipationRepository::class
}

val domainModule = module {
    factoryOf(::LoginUseCase)
    factoryOf(::SignUpUseCase)
    factoryOf(::GetCoursesUseCase)
}

val sharedModule = module {
    includes(dataModule, domainModule)
}

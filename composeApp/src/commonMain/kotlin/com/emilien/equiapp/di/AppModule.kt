package com.emilien.equiapp.di

import com.emilien.equiapp.coursedetail.CourseDetailViewModel
import com.emilien.equiapp.coursedetail.DeclarePresenceUseCase
import com.emilien.equiapp.coursedetail.PresenceViewModel
import com.emilien.equiapp.data.CourseRepository
import com.emilien.equiapp.data.MockCourseRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf

val appModule = module {
    singleOf(::MockCourseRepository) bind CourseRepository::class
    factoryOf(::DeclarePresenceUseCase)
    
    viewModelOf(::CourseDetailViewModel)
    viewModel { (courseId: String) -> PresenceViewModel(courseId, get()) }
}

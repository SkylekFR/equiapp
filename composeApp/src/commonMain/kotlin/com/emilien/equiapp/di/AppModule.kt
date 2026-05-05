package com.emilien.equiapp.di

import com.emilien.equiapp.auth.login.LoginViewModel
import com.emilien.equiapp.coursedetail.CourseDetailViewModel
import com.emilien.equiapp.coursedetail.DeclarePresenceUseCase
import com.emilien.equiapp.coursedetail.PresenceViewModel
import com.emilien.equiapp.home.UpcomingCoursesViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    factoryOf(::DeclarePresenceUseCase)
    
    viewModelOf(::CourseDetailViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::UpcomingCoursesViewModel)
    viewModel { (courseId: String) -> PresenceViewModel(courseId, get()) }
}

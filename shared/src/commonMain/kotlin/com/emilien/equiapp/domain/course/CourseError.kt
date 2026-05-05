package com.emilien.equiapp.domain.course

sealed interface CourseError {
    sealed interface Network : CourseError {
        data object NoConnection : Network
        data object Timeout : Network
        data object ServerError : Network
    }
    sealed interface Business : CourseError {
        data object CourseNotFound : Business
        data object AccessDenied : Business
    }
    data object Unknown : CourseError
}

package com.emilien.equiapp.coursedetail

data class PresenceUiState(
    val isConfirmed: Boolean? = null,
    val comment: String = "",
    val error: String? = null,
    val isOptimistic: Boolean = false
)

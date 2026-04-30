package com.emilien.equiapp.coursedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emilien.equiapp.data.MockCourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PresenceViewModel(
    private val courseId: String,
    private val declarePresenceUseCase: DeclarePresenceUseCase = DeclarePresenceUseCase(MockCourseRepository())
) : ViewModel() {

    private val _uiState = MutableStateFlow(PresenceUiState())
    val uiState: StateFlow<PresenceUiState> = _uiState.asStateFlow()

    fun onIntent(intent: PresenceIntent) {
        when (intent) {
            is PresenceIntent.Initialize -> {
                _uiState.update { it.copy(isConfirmed = intent.isConfirmed, comment = intent.comment) }
            }
            is PresenceIntent.UpdateComment -> {
                _uiState.update { it.copy(comment = intent.comment) }
            }
            is PresenceIntent.SubmitPresence -> {
                submitPresence(intent.isPresent, intent.courseStartTimeMillis)
            }
        }
    }

    private fun submitPresence(isPresent: Boolean, courseStartTimeMillis: Long) {
        val previousPresence = _uiState.value.isConfirmed
        val currentComment = _uiState.value.comment
        
        // Optimistic update
        _uiState.update { it.copy(isConfirmed = isPresent, error = null, isOptimistic = true) }

        viewModelScope.launch {
            // For KMP common, we don't have a direct Clock without dependencies.
            // Using a mock current time for demonstration of the 24h rule.
            val mockCurrentTimeMillis = 1713715200000L // Example: Today

            val result = declarePresenceUseCase.execute(
                courseId = courseId,
                isPresent = isPresent,
                comment = currentComment,
                courseStartTimeMillis = courseStartTimeMillis,
                currentTimeMillis = mockCurrentTimeMillis
            )

            if (result.isFailure) {
                // Rollback on failure
                _uiState.update { 
                    it.copy(
                        isConfirmed = previousPresence,
                        error = result.exceptionOrNull()?.message,
                        isOptimistic = false
                    )
                }
            } else {
                _uiState.update { it.copy(isOptimistic = false) }
            }
        }
    }
}

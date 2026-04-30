package com.emilien.equiapp.coursedetail

sealed class PresenceIntent {
    data class SubmitPresence(val isPresent: Boolean, val courseStartTimeMillis: Long) : PresenceIntent()
    data class UpdateComment(val comment: String) : PresenceIntent()
    data class Initialize(val isConfirmed: Boolean?, val comment: String) : PresenceIntent()
}

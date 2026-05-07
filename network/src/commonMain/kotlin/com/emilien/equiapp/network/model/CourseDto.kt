package com.emilien.equiapp.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    @SerialName("id") val id: String,
    @SerialName("titre") val title: String?,
    @SerialName("moniteur_id") val teacher: String,
)

@Serializable
data class StudentDto(
    @SerialName("name") val name: String,
    @SerialName("horse") val horse: String? = null,
    @SerialName("status") val status: String
)

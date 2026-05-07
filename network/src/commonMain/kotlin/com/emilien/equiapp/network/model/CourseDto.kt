package com.emilien.equiapp.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    @SerialName("id") val id: String,
    @SerialName("titre") val title: String?,
    @SerialName("moniteur_id") val teacherId: String,
    @SerialName("participation") val participations: List<ParticipationDto> = emptyList()
)

@Serializable
data class ParticipationDto(
    @SerialName("id") val id: String,
    @SerialName("statut_presence") val presenceStatus: String,
    @SerialName("profiles") val student: ProfileDto? = null,
    @SerialName("cheval") val horse: HorseDto? = null
)

@Serializable
data class ProfileDto(
    @SerialName("full_name") val fullName: String? = null
)

@Serializable
data class HorseDto(
    @SerialName("nom") val name: String
)

@Serializable
data class StudentDto(
    @SerialName("name") val name: String,
    @SerialName("horse") val horse: String? = null,
    @SerialName("status") val status: String
)

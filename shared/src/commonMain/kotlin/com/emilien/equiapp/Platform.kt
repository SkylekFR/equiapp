package com.emilien.equiapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package com.emilien.equiapp.network.di

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            defaultRequest {
                url("https://zcqfjrtgzxkpjgqlhxre.supabase.co/rest/v1/")
                header("Content-Type", "application/json")
                // apiKey and bearer token should usually be injected or provided via a plugin
                // header("apikey", "SUPABASE_KEY")
                // header("Authorization", "Bearer SUPABASE_KEY")
            }
        }
    }
}

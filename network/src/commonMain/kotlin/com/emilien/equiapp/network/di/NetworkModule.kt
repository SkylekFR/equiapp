package com.emilien.equiapp.network.di

import com.emilien.equiapp.network.BuildConfig
import com.emilien.equiapp.network.AuthRemoteDataSource
import com.emilien.equiapp.network.CourseRemoteDataSource
import com.emilien.equiapp.network.SupabaseAuthRemoteDataSource
import com.emilien.equiapp.network.SupabaseCourseRemoteDataSource
import com.russhwolf.settings.Settings
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.logging.LogLevel
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import org.koin.dsl.module
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

val networkModule = module {
    single {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            defaultLogLevel = LogLevel.DEBUG
            requestTimeout = 30.seconds
            install(Auth)
            install(Postgrest)
        }
    }

    single { get<SupabaseClient>().auth }
    single { get<SupabaseClient>().postgrest }
    
    single { Settings() }
    
    single<AuthRemoteDataSource> { 
        SupabaseAuthRemoteDataSource(
            auth = get()
        )
    }

    single<CourseRemoteDataSource> {
        SupabaseCourseRemoteDataSource(
            postgrest = get()
        )
    }
}

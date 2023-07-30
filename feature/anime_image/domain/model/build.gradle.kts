@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.parcelize)
}

android {
    namespace = "com.lelestacia.lelenimev2.feature.anime_image.domain.model"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    //  Compose Navigation
    implementation(libs.compose.navigation)

    //  Junit
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)

    //  Moshi
    implementation(libs.moshi)
    ksp(libs.moshi.codegen)
}
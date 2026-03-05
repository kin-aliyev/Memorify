plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.feature_home"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true  // для API ключей если понадобится
    }
}

dependencies {
    // ─── Modules ──────────────────────────────────────────────
    implementation(project(":core:core-ui"))
    implementation(project(":core:core-domain"))

    // ─── DI ───────────────────────────────────────────────────
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // ─── Firebase ─────────────────────────────────────────────
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    // ─── Networking (AI API) ───────────────────────────────────
    implementation(libs.retrofit)             // ← добавь в toml
    implementation(libs.retrofit.gson)        // ← или kotlinx serialization converter
    implementation(libs.okhttp.logging)       // ← для дебага запросов

    // ─── Serialization ────────────────────────────────────────
    implementation(libs.kotlin.serialization.core)

    // ─── Compose ──────────────────────────────────────────────
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ─── Android ──────────────────────────────────────────────
    implementation(libs.androidx.core.ktx)

    // ─── Tests ────────────────────────────────────────────────
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
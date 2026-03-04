plugins {
    alias(libs.plugins.android.library)
//    alias(libs.plugins.hiltAndroid)
//    alias(libs.plugins.kotlinAndroidKsp)
}

android {
    namespace = "com.example.core_data"
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
}

dependencies {
    // Добавляй по мере необходимости:
    // implementation(project(":core:core-domain"))  — когда появятся shared репозитории
    // implementation(libs.hilt.android)             — когда появятся DI модули
    // ksp(libs.hilt.compiler)
    // implementation(platform(libs.firebase.bom))   — когда появится Firestore
    // implementation(libs.firebase.firestore)
    // implementation(libs.room.runtime)             — когда появится Room
}
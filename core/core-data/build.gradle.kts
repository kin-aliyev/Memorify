plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinAndroidKsp)
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
     implementation(project(":core:core-domain"))

     implementation(libs.hilt.android)
     ksp(libs.hilt.compiler)

     implementation(platform(libs.firebase.bom))
     implementation(libs.firebase.firestore)
     implementation(libs.firebase.auth)

    implementation(libs.kotlinx.coroutines.core)

}
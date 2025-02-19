plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.antoniourda.proyectoantonio"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.antoniourda.proyectoantonio"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.ui.ktx)

    // Firebase Dependencies
    implementation(platform("com.google.firebase:firebase-bom:33.8.0")) // Firebase BOM
    implementation("com.google.firebase:firebase-analytics-ktx") // Firebase Analytics
    implementation("com.google.firebase:firebase-auth-ktx") // Firebase Authentication
    implementation("com.google.firebase:firebase-database-ktx") // Firebase Realtime Database
    implementation("com.google.firebase:firebase-firestore-ktx") // Firebase Firestore

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

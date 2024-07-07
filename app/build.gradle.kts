plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.navigation.saveargs)
    alias(libs.plugins.android.serialization)
    kotlin("kapt")
}

android {
    namespace = "com.example.deliveryapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.deliveryapp"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")

    implementation("androidx.dynamicanimation:dynamicanimation-ktx:1.0.0-alpha03")
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    //SlideImage
    implementation(libs.imageslideshow)

    //ImageCircular
    implementation(libs.circleimageview)

    //NavComponent
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)

    kapt(libs.androidx.room.compiler)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)

    implementation(libs.kotlinx.serialization.json)

    //datastorage
    implementation(libs.androidx.datastore.preferences)

    // Fragment
    implementation( libs.androidx.fragment.ktx)
    // Activity
    implementation (libs.androidx.activity.ktx)
    // ViewModel
    implementation( libs.androidx.lifecycle.viewmodel.ktx)
    // LiveData
    implementation (libs.lifecycle.livedata.ktx)
    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.retrofit2.converter.gson)
    //Corrutinas
    implementation(libs.kotlinx.coroutines.core)

    //DaggerHilt
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    //Glide
    implementation(libs.glide)
    kapt(libs.compiler)

    //File
    implementation(libs.commons.io)
    //SplashScreen
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation("com.google.truth:truth:1.4.3")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("com.google.truth:truth:1.4.3")
}
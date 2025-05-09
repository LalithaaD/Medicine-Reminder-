plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.finalproject.medicinereminder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.finalproject.medicinereminder"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.play.services.wearable)
    {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation(libs.appcompat)
    {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation(libs.material)
    {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation(libs.activity)
    {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation(libs.constraintlayout)
    {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation(libs.wear)
    {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation(libs.room.compiler)
    {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation(libs.gson)
    {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
}
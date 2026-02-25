plugins {
    alias(libs.plugins.android.application)

}

android {
    namespace = "com.example.smratsee"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.smratsee"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    androidResources {
        noCompress += "tflite"
    }
}

dependencies {


    implementation ("com.google.android.gms:play-services-vision:20.1.3")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("org.tensorflow:tensorflow-lite:2.13.0")
    implementation ("org.tensorflow:tensorflow-lite-support:0.4.4")
    implementation ("org.tensorflow:tensorflow-lite-gpu:2.13.0")

    implementation ("org.pytorch:pytorch_android:1.13.1")
    implementation ("org.pytorch:pytorch_android_torchvision:1.13.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "kr.pe.ssun.myapplication"
    compileSdk = 34

    defaultConfig {
        val buildPropFile = rootProject.file("build.properties")
        val buildProperties = Properties().apply {
            if (buildPropFile.exists()) {
                load(FileInputStream(buildPropFile))
            }
        }

        applicationId = "kr.pe.ssun.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = buildProperties.getProperty("versionCode").toInt()
        versionName = buildProperties.getProperty("versionName")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
            val keystorePropFile = rootProject.file("keystore.properties")
            val keystoreProperties = Properties().apply {
                if (keystorePropFile.exists()) {
                    load(FileInputStream(keystorePropFile))
                }
            }
            val path = keystoreProperties.getProperty("releaseKeyStore")
            if (path != null) {
                keyAlias = keystoreProperties.getProperty("releaseKeyAlias")
                keyPassword = keystoreProperties.getProperty("releaseKeyPassword")
                storeFile = rootProject.file(keystoreProperties.getProperty("releaseKeyStore"))
                storePassword = keystoreProperties.getProperty("releaseStorePassword")
            }
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release") // 여기만
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
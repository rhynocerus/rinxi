plugins {
    id("com.android.application")
}

android {
    namespace = "com.rhynus.rinxi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rhynus.rinxi"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "0.2.0-dev"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

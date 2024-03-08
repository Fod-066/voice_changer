
plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}


android {
  namespace = "com.voice.monster"
  compileSdk = 33

  defaultConfig {
    applicationId = "com.tb.vm.x"
    minSdk = 24
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"

    ndk {
      abiFilters.addAll(mutableSetOf("arm64-v8a", "armeabi-v7a"))
    }

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  viewBinding {
    enable = true
  }
}

dependencies {
  val lifecycleVersion = "2.5.1"
  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.8.0")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
  implementation("com.github.iDeMonnnnnn.DeMon_Sound:FmodSound:1.1")
  implementation("com.github.iDeMonnnnnn.DeMon_Sound:SoundCoding:1.1")
  implementation("androidx.lifecycle:lifecycle-livedata-core-ktx:$lifecycleVersion")
  implementation("androidx.lifecycle:lifecycle-process:$lifecycleVersion")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
  implementation("androidx.activity:activity-ktx:1.5.5")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")
  implementation("com.guolindev.permissionx:permissionx:1.6.1")
}
// https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application") version libs.versions.android.gradle.plugin.get()
    alias(libs.plugins.dependency.guard)
}

android {
    compileSdk = 31
    defaultConfig {
        minSdk = 23
        targetSdk = 31
    }
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(project(":samples:jvm"))
    implementation(project(":samples:jvm_kmp4free"))
    implementation(project(":samples:multiplatform"))
    implementation(project(":samples:multiplatform_kmp4free"))
}

dependencyGuard {
    configuration("releaseRuntimeClasspath")
}

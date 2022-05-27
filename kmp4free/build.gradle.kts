// https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `kotlin-dsl`
    alias(libs.plugins.dokka)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.binaryCompatibilityValidator)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
    }
}

val VERSION_NAME: String by project
version = VERSION_NAME

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(8)
}

kotlin {
    explicitApi()
}

gradlePlugin {
    plugins {
        plugins.create("kmp4free") {
            id = "com.handstandsam.kmp4free"
            implementationClass = "com.handstandsam.kmp4free.Kmp4FreePlugin"
        }
    }
}

mavenPublish {
    sonatypeHost = com.vanniktech.maven.publish.SonatypeHost.S01
}

dependencies {
    compileOnly(gradleApi())
    implementation(libs.kotlin.gradle.plugin)
}

tasks.register("printVersionName") {
    doLast {
        println(VERSION_NAME)
    }
}

plugins {
    id("com.handstandsam.kmp4free")
}

dependencies {
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.kotlin.test.common)
    testImplementation(libs.truth)
}

project.extensions.findByType(
    org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension::class.java
)?.apply {
    if (project.findProperty("ios") == "true") {
        iosSimulatorArm64 {
            binaries.framework {
                baseName = project.name
            }
        }
    }
    if (project.findProperty("js") == "true") {
        js(IR) {
            browser()
        }
    }
}
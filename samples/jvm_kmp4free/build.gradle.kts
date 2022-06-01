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
    listOf(
        iosSimulatorArm64()
    ).forEach { nativeTarget ->
        nativeTarget.binaries.framework {
            baseName = "jvm_kmp4free"
        }
    }
}
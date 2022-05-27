// https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.handstandsam.kmp4free")
}

kotlin {
    sourceSets {
        maybeCreate("commonMain").apply {
            dependencies {
                implementation(libs.kotlin.coroutines)
            }
        }
        maybeCreate("commonTest").apply {
            dependencies {
                implementation(libs.kotlin.test.common)
                implementation(libs.kotlin.test.annotations.common)
            }
        }
        maybeCreate("jvmTest").apply {
            dependencies {
                implementation(libs.truth)
                implementation(libs.kotlin.test.junit5)
            }
        }
        maybeCreate("jsTest").apply {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

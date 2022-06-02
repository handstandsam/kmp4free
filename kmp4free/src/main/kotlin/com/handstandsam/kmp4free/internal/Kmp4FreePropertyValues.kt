package com.handstandsam.kmp4free.internal

import org.gradle.api.Project

/**
 * Helpers to access relevant Gradle properties in a clean way
 */
internal class Kmp4FreePropertyValues(private val project: Project) {

    val isMultiplatformEnabled: Boolean
        get() {
            return project.findProperty(GRADLE_PROPERTY_MULTIPLATFORM) == "true"
        }

    companion object {
        const val GRADLE_PROPERTY_MULTIPLATFORM = "kmp4free"
    }
}

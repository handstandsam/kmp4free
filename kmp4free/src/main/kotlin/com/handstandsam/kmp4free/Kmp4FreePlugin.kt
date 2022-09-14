package com.handstandsam.kmp4free

import com.handstandsam.kmp4free.internal.Kmp4FreeMagic
import com.handstandsam.kmp4free.internal.Kmp4FreePropertyValues
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Kotlin Multiplatform Convention Plugin that is swappable with the JVM Plugin
 */
public class Kmp4FreePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val logger = target.logger
        val isMultiplatformEnabled = Kmp4FreePropertyValues(target).isMultiplatformEnabled

        val kmp4FreeMagic = Kmp4FreeMagic(target)
        if (isMultiplatformEnabled) {
            // Use Kotlin Multiplatform Plugin
            logger.info("Applying Plugin org.jetbrains.kotlin.multiplatform to ${target.path}")
            target.plugins.apply("org.jetbrains.kotlin.multiplatform")
            kmp4FreeMagic.enableKotlinMultiplatform()
        } else {
            // Use Kotlin JVM Plugin
            logger.info("Applying Plugin org.jetbrains.kotlin.jvm to ${target.path}")
            target.plugins.apply("org.jetbrains.kotlin.jvm")
            kmp4FreeMagic.enableKotlinJvm()
        }

    }
}

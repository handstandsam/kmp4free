package com.handstandsam.kmp4free.internal

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Allows us to use the SourceSet Structure of a JVM Project in a Multiplatform Project
 */
internal class Kmp4FreeMagic(private val target: Project) {

    fun enableKotlinMultiplatform() {
        val multiplatformExtension =
            target.extensions.getByType(KotlinMultiplatformExtension::class.java)
        multiplatformExtension.apply {
            // Always enable JVM
            jvm()
        }

        // Extend Configurations and SourceSets
        Kmp4FreeSourceSetMagic(target).apply {
            extendConfigurationsAndSourceSets(
                extendsFromSourceSetName = "main",
                sourceSetName = "commonMain",
            )
            extendConfigurationsAndSourceSets(
                extendsFromSourceSetName = "test",
                sourceSetName = "jvmTest"
            )
        }

        // Create a "test" task that will map to "jvmTest"
        if (target.tasks.findByName("test") == null) {
            target.tasks.create("test").apply {
                dependsOn(target.tasks.findByName("jvmTest"))
                group = "Verification"
                description = "Alias for jvmTest"
            }
        }
    }


    /**
     * Allow us to specify dependencies as "commonMain", "commonTest", "jvmMain", "jvmTest"
     */
    fun enableKotlinJvm() {
        Kmp4FreeSourceSetMagic(target).apply {
            // Main Sources
            extendConfigurationsAndSourceSets(
                extendsFromSourceSetName = "commonMain",
                sourceSetName = "main",
            )
            extendConfigurationsAndSourceSets(
                extendsFromSourceSetName = "jvmMain",
                sourceSetName = "main",
            )

            // Test Sources
            extendConfigurationsAndSourceSets(
                extendsFromSourceSetName = "commonTest",
                sourceSetName = "test",
            )
            extendConfigurationsAndSourceSets(
                extendsFromSourceSetName = "jvmTest",
                sourceSetName = "test",
            )
        }
    }
}

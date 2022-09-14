package com.handstandsam.kmp4free.internal

import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

/**
 * Allows us to use the SourceSet Structure of a Multiplatform Project in a JVM Project
 */
internal class Kmp4FreeSourceSetMagic(
    target: Project
) {
    private val configurations: ConfigurationContainer = target.configurations

    private val logger = target.logger

    private val kotlinProjectExtension: KotlinProjectExtension =
        target.extensions.getByType(KotlinProjectExtension::class.java)

    /** We can assume this will be there when the kmp4free plugin is used because it adds the jvm target */
    private val javaPluginExtension: JavaPluginExtension =
        target.extensions.getByType(JavaPluginExtension::class.java)

    private fun addAdditionalSrcDirs(extendsFromSourceSetName: String, sourceSetName: String) {
        kotlinProjectExtension.sourceSets.findByName(sourceSetName)?.apply {
            logger.info("** SourceSets: $sourceSetName now includes sources from $extendsFromSourceSetName **")
            listOf(
                "src/$extendsFromSourceSetName/java",
                "src/$extendsFromSourceSetName/kotlin",
            ).forEach {
                kotlin.srcDir(it)
                logger.info("Added $it as a Kotlin sources for $sourceSetName")
            }
            logger.info("--------")
        }
    }

    private fun addAdditionalResourceDirs(extendsFromSourceSetName: String, sourceSetName: String) {
        val additionalResourceDir = "src/$extendsFromSourceSetName/resources"
        kotlinProjectExtension.sourceSets.findByName(sourceSetName)?.apply {
            resources.srcDir(additionalResourceDir)
            logger.info("Added $additionalResourceDir as Kotlin resources for $sourceSetName")
        }
        javaPluginExtension.sourceSets.findByName(sourceSetName)?.apply {
            resources.srcDir(additionalResourceDir)
            logger.info("Added $additionalResourceDir as Java resources for $sourceSetName")
        }
        logger.info("--------")
    }

    fun extendConfigurationsAndSourceSets(
        extendsFromSourceSetName: String,
        sourceSetName: String,
    ) {
        // Extend SourceSets
        logger.info("--------")
        logger.info("Mapping SourceSet $extendsFromSourceSetName -> $sourceSetName")
        addAdditionalSrcDirs(extendsFromSourceSetName, sourceSetName)
        addAdditionalResourceDirs(extendsFromSourceSetName, sourceSetName)

        extendConfigurations(extendsFromSourceSetName, sourceSetName)
    }

    private fun extendConfigurations(extendsFromSourceSetName: String, sourceSetName: String) {
        // Extend Configurations
        logger.info("** Configurations: $sourceSetName extendsFrom $extendsFromSourceSetName **")
        listOf(
            "api",
            "implementation",
            "compileOnly",
            "runtimeOnly",
            "apiDependenciesMetadata",
            "implementationDependenciesMetadata",
            "compileOnlyDependenciesMetadata",
            "runtimeOnlyDependenciesMetadata",
        ).forEach {
            val configurationName = if (sourceSetName == "main") {
                it
            } else {
                "${sourceSetName}${it.capitalized()}"
            }
            val extendsFromSourceSetTargetConfigurationName =
                if (extendsFromSourceSetName == "main") {
                    it
                } else {
                    "${extendsFromSourceSetName}${it.capitalized()}"
                }
            logger.info("$configurationName extendsFrom $extendsFromSourceSetTargetConfigurationName")
            configurations.maybeCreate(configurationName)
                .extendsFrom(
                    configurations.maybeCreate(
                        extendsFromSourceSetTargetConfigurationName
                    )
                )
        }
    }
}

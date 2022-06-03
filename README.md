# 🆓 kmp4free
[![LICENSE](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/handstandsam/kmp4free/blob/main/LICENSE)
[![Latest Snapshot](https://img.shields.io/badge/dynamic/xml?url=https://s01.oss.sonatype.org/content/repositories/snapshots/com/handstandsam/kmp4free/kmp4free/maven-metadata.xml&label=Latest%20Snapshot&color=blue&query=.//versioning/latest)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/handstandsam/com.handstandsam.kmp4free.gradle.plugin/)
[![CI](https://github.com/handstandsam/kmp4free/workflows/CI/badge.svg)](https://github.com/handstandsam/kmp4free/actions?query=branch%3Amain)

Allows you to toggle between Kotlin JVM Plugin -> Kotlin Multiplatform with a Gradle Property `kmp4free=true`.

This Gradle Plugin was built to support gradual adoption of Kotlin Multiplatform.  It's called `kmp4free` because you are able to take a normal Kotlin JVM module, and with a single line change, enable or disable the Kotlin Multiplatform Plugin.

There is always a risk of adding something new, especially with a large project. The introduction of a technology that is not at a stable version yet can block the adoption of a new technology.  The goal of this plugin is to reduce risk to a single line change.

## Plugin Installation
Add the Plugin on your project's `build.gradle.kts`
```kotlin
plugins {
    id("com.handstandsam.kmp4free") version "${latest_version}"
}
```

If you want to use a snapshot version, add the Snapshot Repo in your project's `settings.gradle.kts`
```kotlin
pluginManagement {
    repositories {
        // ...
        maven { url = "https://s01.oss.sonatype.org/content/repositories/snapshots/" }
    }
}
```

## Usage
Just replace `kotlin("jvm")` with `id("com.handstandsam.kmp4free")` in the `plugins` block of your module's `build.gradle.kts` file.

### Enabling Multiplatform
You can set `kmp4free=true` in your `gradle.properties` or send it in as a command-line parameter to gradle with `-Pkmp4free=true`.

This enables property the Kotlin Multiplatform Plugin, along with the additional changes required to support seamless switching between the Kotlin JVM Plugin.

Any code in the `main` sourceSet needs to be `commonMain` compatible when `multiplatform` is enabled, otherwise the build will fail about not being able to resolve JVM dependencies.  This isn't a bad thing though, as you will then be able to identify what you need to change to make your code multiplatform compatible.

#### SourceSet Mapping
* `src/main` ➡️ `src/commonMain`
* `src/test` ➡️ `src/jvmTest`

#### Configuration Mapping
* `implementation` ➡️ `commonMainImplementation`
* `api` ➡️ `commonMainApi`
* `testImplementation` ➡️ `jvmTestImplementation`
* `testApi` ➡️ `jvmTestApi`

When tests are already written with JVM Libraries like JUnit and Google's Truth library, it would be a lot of work to migrate those tests over to `commonTest`, so these tests will only run on the JVM with `kmp4free`.  Additionally, this task alias means that your scripts that run Gradle Tasks do not have to update either when the plugin is enabled, since the `test` task will be available.

#### Task Aliasing
* `:module:test` ➡️ `:module:jvmTest`


## Disabling Multiplatform
This uses the Kotlin JVM Plugin, and bypasses Kotlin Multiplatform entirely.

#### SourceSet Mapping
* `src/commonMain` ➡️ `src/main`
* `src/jvmMain` ➡️ `src/main`
* `src/commonTest` ➡️ `src/test`
* `src/jvmTest` ➡️ `src/test`


#### Configuration Mapping
* `commonMainImplementation` ➡️ `implementation`
* `commonMainApi` ➡️ `api`
* `commonTestImplementation` ➡️ `testImplementation`
* `commonTestApi` ➡️ `testApi`
* `jvmTestImplementation` ➡️ `testImplementation`
* `jvmTestApi` ➡️ `testApi`

You can set the following properties in your `gradle.properties`

## FAQ

#### Why do I need to use `maybeCreate`?
You may typically see source sets defined like `val commonMain by getting`.  Because with `kmp4free` we toggle the multiplatform plugin on/off, that Source Set will be sometimes be already there, and other times may not be. Therefore we use `maybeCreate("commonMain")` to avoid the error.

#### What Configurations are Mapped?
* api
* implementation
* compileOnly
* runtimeOnly
* apiDependenciesMetadata
* implementationDependenciesMetadata
* compileOnlyDependenciesMetadata
* runtimeOnlyDependenciesMetadata

#### Can I use `kmp4free` on an Android Library?

No. `kmp4free` is set up to work for Kotlin JVM Projects.  However, a lot of code in Android Libraries are probably not Android Specific, so you can extract that into another module, or if possible, you can change the module from an Android Library to a Kotlin JVM project.

A Kotlin JVM Module means you cannot have resources, an `AndroidManifest.xml` and not use Android APIs.  You can always create your own abstractions to bridge between different modules.

Many projects already have Kotlin JVM projects, while not requiring the use of the Kotlin Multiplatform Plugin.

#### How do I configure my own iOS and JS Targets?
You'd have to fork the plugin until #2 is implemented.  I'm thinking this would be a lambda in the plugin configuration block for each target.

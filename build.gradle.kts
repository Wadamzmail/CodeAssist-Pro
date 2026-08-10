// Top-level build file where you can add configuration options common to all sub-projects/modules.
@file:Suppress("UnstableApiUsage")

import com.itsaky.androidide.build.config.BuildConfig
import com.itsaky.androidide.plugins.AndroidIDEPlugin
import com.itsaky.androidide.plugins.conf.configureAndroidModule
import com.itsaky.androidide.plugins.conf.configureJavaModule
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins{
    id("build-logic.root-project")
}

buildscript {
    repositories {
        google()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://mvnrepository.com/") }
        maven { url = uri("https://central.sonatype.com/repository/maven-snapshots") }
        mavenCentral()
        maven { url = uri("https://www.jetbrains.com/intellij-repository/releases/") }
        maven { url = uri("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies/") }
    }

    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")
        classpath(libs.kotlin.serialization.plugin)
        classpath(libs.nav.safe.args.gradle.plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

project.group = BuildConfig.PACKAGE_NAME

subprojects {
    if (project != rootProject) {
        var group = project.parent!!.group
        if (project.parent != rootProject) {
            group = "${group}.${project.parent!!.name}"
        }
        project.group = group
    }


    afterEvaluate {
        apply { plugin(AndroidIDEPlugin::class.java) }
    }

    project.version = rootProject.version

    plugins.withId("com.android.library") {
        configureAndroidModule(libs.androidx.libDesugaring)
    }
    plugins.withId("java-library") { configureJavaModule() }
}


tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}

extra.apply {
    set("compileSdkVersion", 37)
    set("buildToolsVersion", "37.0.0")
    set("targetSdkVersion", 36)
    set("minSdkVersion", 26)
    set("applicationId", "dev.mutwakil.codeassist")
    set("versionCode", 1)
    set("versionName", "0.2.0-ALPHA02")
}

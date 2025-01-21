import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
    id("signing")
}

group = "tech.mobiledeveloper"   // Ваш groupId
version = "0.0.1"               // Текущая версия библиотеки
description = "Data Library" // Краткое описание

android {
    namespace = "tech.mobiledeveloper.dater"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    publishing {
        singleVariant("release")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

fun Project.loadSigningProperties(fileName: String = "signing.properties") {
    val signingPropsFile = rootProject.file(fileName)
    if (signingPropsFile.exists()) {
        println("Loading signing properties from $signingPropsFile")

        val signingProps = Properties()
        signingPropsFile.inputStream().use { input ->
            signingProps.load(input)
        }

        signingProps.forEach { key, value ->
            project.extensions.extraProperties.set(key.toString(), value)
        }
    } else {
        println("WARNING: $fileName not found! Signing and publishing might not work.")
    }
}

afterEvaluate {
    loadSigningProperties()

    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "tech.mobiledeveloper"
                artifactId = "dater"
                version = "0.0.2"

                pom {
                    name.set("Library for dates")
                    description.set("Choose your date")
                    url.set("https://github.com/AlexGladkov/maw_chapter3_block6_demo1.git")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            distribution.set("repo")
                        }
                    }

                    developers {
                        developer {
                            id.set("NeuraDev")
                            name.set("AlexGladkov")
                            email.set("mobiledevelopercourse@gmail.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:github.com/AlexGladkov/maw_chapter3_block6_demo1.git")
                        developerConnection.set("scm:git:ssh://github.com/AlexGladkov/maw_chapter3_block6_demo1.git")
                        url.set("https://github.com/AlexGladkov/maw_chapter3_block6_demo1")
                    }
                }
            }
        }
    }

    signing {
        // Обычно данные для ключа и пароля берем из gradle.properties:
        val signingKey = findProperty("signing.key") as String?    // Armor или base64
        val signingPassphrase = findProperty("signing.password") as String?

        if (signingKey != null) {
            useInMemoryPgpKeys(signingKey, signingPassphrase)
            sign(publishing.publications["release"])
        }
    }
}
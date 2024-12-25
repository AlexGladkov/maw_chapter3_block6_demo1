plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
    id("signing")
}

android {
    namespace = "tech.mobiledeveloper.dater"
    compileSdk = 34

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

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "tech.mobiledeveloper.dater"
                artifactId = "dater"
                version = "1.0.0"

                pom {
                    name.set("Library for dates")
                    description.set("Choose your date")
                    url.set("https://github.com/yourusername/my-awesome-library")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            distribution.set("repo")
                        }
                    }

                    developers {
                        developer {
                            id.set("yourusername")
                            name.set("Your Name")
                            email.set("youremail@example.com")
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
        sign(publishing.publications["release"])
    }
}
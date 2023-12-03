plugins {
    kotlin("multiplatform") version "1.9.20"
    id("org.jetbrains.compose") version "1.5.11"
    id("io.kotest.multiplatform") version "5.7.0"
    kotlin("plugin.serialization") version "1.9.21"
}

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("io.kotest:kotest-framework-engine:5.8.0")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("io.kotest:kotest-runner-junit5:5.7.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            }
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

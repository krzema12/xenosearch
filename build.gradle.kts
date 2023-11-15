plugins {
    kotlin("multiplatform") version "1.9.20"
    id("org.jetbrains.compose") version "1.5.10"
    id("io.kotest.multiplatform") version "5.7.0"
}

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(compose.runtime)
            }
        }

        val jsTest by getting {
            dependencies {
                implementation("io.kotest:kotest-framework-engine:5.7.0")
            }
        }
    }
}

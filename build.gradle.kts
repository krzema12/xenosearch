plugins {
    kotlin("multiplatform") version "1.9.22"
    id("org.jetbrains.compose") version "1.5.12"
    id("io.kotest.multiplatform") version "5.9.1"
    kotlin("plugin.serialization") version "1.9.22"
}

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport { enabled.set(true) }
                scssSupport { enabled.set(true) }
            }
        }
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
                implementation("dev.petuska:kmdc-data-table:0.1.2")
                implementation("dev.petuska:kmdc-dialog:0.1.2")
                implementation("dev.petuska:kmdc-textfield:0.1.2")
                implementation("dev.petuska:kmdc-typography:0.1.2")
                implementation("dev.petuska:kmdcx-icons:0.1.1")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("io.kotest:kotest-framework-engine:5.8.1")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("io.kotest:kotest-runner-junit5:5.9.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            }
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

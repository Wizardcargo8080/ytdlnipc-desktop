import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "21"
        }
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                implementation("com.google.code.gson:gson:2.10.1")
                implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.deniscerri.ytdl.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "YTDLnis"
            packageVersion = "1.0.0"
            description = "YTDLnis Desktop - Video/Audio Downloader"
            vendor = "Denis Cerri"
            
            windows {
                menuGroup = "YTDLnis"
                upgradeUuid = "b4e8e8e8-e8e8-e8e8-e8e8-e8e8e8e8e8e8"
                iconFile.set(project.file("src/jvmMain/resources/icons/ic_launcher.ico"))
            }
            
            linux {
                iconFile.set(project.file("src/jvmMain/resources/icons/ic_launcher.png"))
            }
            
            macOS {
                iconFile.set(project.file("src/jvmMain/resources/icons/ic_launcher.icns"))
            }
        }
    }
}

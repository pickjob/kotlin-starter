import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "starter.kotlin"
version = "1.0.0"

val library = extra

dependencies {
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(compose.desktop.currentOs)

    // compose multiplatform
    implementation("org.jetbrains.compose.components:components-splitpane:${library["compose.version"]}")
    implementation("org.jetbrains.androidx.navigation:navigation-compose:${library["navigation.version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${library["coroutines.swing.version"]}")

    // kolor
    implementation("com.materialkolor:material-kolor:${library["kolor.version"]}")

    // log4j
    implementation("org.tukaani:xz:${library["xz.version"]}")
    implementation("org.apache.commons:commons-compress:${library["commons.compress.version"]}")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:${library["log4j.kotlin.version"]}")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:${library["log4j.version"]}")
    implementation("org.apache.logging.log4j:log4j-core:${library["log4j.version"]}")

    // jackson
    implementation("com.fasterxml.jackson.core:jackson-core:${library["jackson.version"]}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${library["jackson.version"]}")
    implementation("com.fasterxml.jackson.core:jackson-annotations:${library["jackson.version"]}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${library["jackson.version"]}")

    // expose
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:${library["datetime.version"]}")
    implementation("org.xerial:sqlite-jdbc:${library["xerial.version"]}")
    implementation("org.jetbrains.exposed:exposed-core:${library["exposed.version"]}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${library["exposed.version"]}")
    implementation("org.jetbrains.exposed:exposed-dao:${library["exposed.version"]}")
    implementation("org.jetbrains.exposed:exposed-java-time:${library["exposed.version"]}")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:${library["exposed.version"]}")
    implementation("org.jetbrains.exposed:exposed-json:${library["exposed.version"]}")
}

kotlin {
    jvmToolchain("${library["jdk.version"]}".toInt())
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "kotlin-starter"
            packageVersion = "1.0.0"
        }
    }
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}
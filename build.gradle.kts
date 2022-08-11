plugins {
    kotlin("js") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.2")
}

kotlin {
    js(IR) {
//        moduleName = "web-exps"
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}

//task("copyStylesheets", Copy::class) {
//    from(kotlin.sourceSets["main"].resources) {
//        include("styles/**")
//    }
//    into("${rootProject.buildDir}/js/packages/${kotlin.js().moduleName}")
//    // kotlin { js { moduleName = "xyz" }} has to be set for this to work
//}
//tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinJsDce::class) {
//    dependsOn("copyStylesheets")
//}

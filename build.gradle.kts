import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktlint by configurations.creating
plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.5.20"

    kotlin("jvm") version "1.6.0"
}

group = "b.proxy"
version = "1.0-SNAPSHOT"

val springVersion = "2.6.2"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springVersion")
    //implementation("org.springframework.boot:spring-boot-starter-jooq:$springVersion")
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.jkcclemens:khttp:-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    ktlint("com.pinterest:ktlint:0.43.2") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}
val format by tasks.creating(JavaExec::class) {
    inputs.files("${project.buildDir}/reports/ktlint/")
    outputs.dir(project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt")))

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("-F", "src/**/*.kt")
}
tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
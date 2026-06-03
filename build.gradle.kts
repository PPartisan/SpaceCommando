plugins {
    kotlin("jvm") version "2.3.10"
    application
}

group = "com.github.ppartisan.spacecommando"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.github.ppartisan.spacecommando.MainKt")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:6.0.3")
    testImplementation("io.kotest:kotest-assertions-core:6.1.11")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.github.ppartisan.spacecommando.MainKt"
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
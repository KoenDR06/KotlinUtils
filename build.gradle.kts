plugins {
    kotlin("jvm") version "2.0.21"
}

group = "me.koendev"
version = "1.1.4"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

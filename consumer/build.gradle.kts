plugins {
    id("java")
}

group = "org.consumer"
version = "1.0-SNAPSHOT"

dependencies {
    // I want the consumer project to depend on lib, and then lib depends on the second coordinate
    // published in lib
    implementation(project(":lib"))
}

plugins {
    `java-library`
    `maven-publish`
    id("com.example.myPlugin")
}

group =  "com.example.group"
version = "1.0-SNAPSHOT"


dependencies {
    // I want to add a dependency on the second artifact produced in this library as a maven GAV :
    // so that the pom and module files of the main pom and module contain a dependency on it.
    // something like:
    // implementation("com.example.different:lib-two:$version")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

// We can publish multiple artifacts, but I want to have the coordinates of one artifact as a
// dependency of tbe other. I want to do this without post-processing the pom and module files.
// https://docs.gradle.org/current/userguide/publishing_maven.html#publishing_maven:repositories
publishing {
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
    publications {
        create<MavenPublication>("one") {
            groupId = group.toString()
            artifactId = "lib-one"
            version = version
            from(components["java"])
        }
        create<MavenPublication>("two") {
            groupId = "com.example.different"
            artifactId = "lib-two"
            version = version
            from(components["myComponent"])
        }
    }
}
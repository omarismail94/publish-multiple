plugins {
    `java-gradle-plugin`
}
dependencies {
    implementation(gradleApi())
}

gradlePlugin {
    plugins {
        create("myPlugin") {
            id = "com.example.myPlugin"
            implementationClass = "com.example.MyPlugin"
        }
    }
}

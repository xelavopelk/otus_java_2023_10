plugins {
    `java-library`
    java
    `maven-publish`
}
dependencies {
    //implementation(project(":L18-jdbc:demo"))
    implementation("ru.klepov:otus-l18-demo:0.0.1")
    implementation("ch.qos.logback:logback-classic")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ru.klepov"
            artifactId = "otus-l18-jdbc"
            version = "0.0.1"

            from(components["java"])
        }
    }
}
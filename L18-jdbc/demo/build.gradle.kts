plugins {
    `java-library`
    java
    `maven-publish`
}
dependencies {
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.flywaydb:flyway-core:9.16.3")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.postgresql:postgresql:42.6.0")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.3")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.3.1")

    testImplementation("org.testcontainers:junit-jupiter:1.19.1")
    testImplementation("org.testcontainers:postgresql:1.19.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ru.klepov"
            artifactId = "otus-l18-demo"
            version = "0.0.1"
            from(components["java"])
        }
    }
}

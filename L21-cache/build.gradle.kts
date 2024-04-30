repositories {
    mavenLocal()
    mavenCentral()
}
dependencies {
    implementation("ch.qos.logback:logback-classic")
    implementation("org.ehcache:ehcache")
    implementation("org.flywaydb:flyway-core")
    implementation("ru.klepov:otus-l18-jdbc:0.0.1")
    implementation("ru.klepov:otus-l18-demo:0.0.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

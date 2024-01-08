plugins {
  id("org.jetbrains.kotlin.jvm") version "1.9.22"
  id("org.jetbrains.kotlin.kapt") version "1.9.22"
  id("org.jetbrains.kotlin.plugin.spring") version "1.9.22"
  id("org.springframework.boot") version "3.1.3"
  id("io.spring.dependency-management") version "1.1.3"
  id("app.cash.sqldelight") version "2.1.0-SNAPSHOT"
  id("org.flywaydb.flyway") version "10.4.1"
  application
}

repositories {
  google()
  mavenCentral()
  maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-log4j2")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

  implementation("org.flywaydb:flyway-core:10.4.1")
  implementation("org.flywaydb:flyway-database-postgresql:10.4.1")
  implementation("org.postgresql:postgresql:42.2.27")
  implementation("app.cash.sqldelight:jdbc-driver:2.1.0-SNAPSHOT")
  implementation("app.cash.sqldelight:runtime:2.1.0-SNAPSHOT")

  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.0")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
  testImplementation("org.testcontainers:testcontainers:1.17.2")
  testImplementation("org.testcontainers:junit-jupiter:1.17.2")
  testImplementation("org.testcontainers:postgresql:1.17.2")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
  mainClass.set("codes.jun.ApplicationKt")
}

sqldelight {
  databases {
    create("PivotDatabase") {
      packageName.set("codes.jun.pivot")
      dialect("app.cash.sqldelight:postgresql-dialect:2.1.0-SNAPSHOT")

      deriveSchemaFromMigrations = true
      verifyMigrations = true

      migrationOutputDirectory = file("$buildDir/resources/main/db/migration")
      migrationOutputFileFormat = ".sql"
    }
  }
}

flyway {
  locations = arrayOf("$buildDir/resources/main/db/migration")
}

tasks.test.configure {
  useJUnitPlatform()
  dependsOn("generateMainPivotDatabaseMigrations")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.compileKotlin.configure {
  kotlinOptions {
    jvmTarget = "17"
  }
  dependsOn("generateMainPivotDatabaseMigrations")
}

tasks.compileTestKotlin.configure {
  kotlinOptions {
    jvmTarget = "17"
  }
}

configurations {
  all {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
  }
}

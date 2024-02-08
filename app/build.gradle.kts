plugins {
  id("org.jetbrains.kotlin.jvm") version "1.9.22"
  id("org.jetbrains.kotlin.kapt") version "1.9.22"
  id("org.jetbrains.kotlin.plugin.spring") version "1.9.22"
  id("org.springframework.boot") version "3.2.2"
  id("io.spring.dependency-management") version "1.1.4"
  id("app.cash.sqldelight") version "2.1.0-SNAPSHOT"
  id("org.flywaydb.flyway") version "10.7.2"
  id("org.openapi.generator") version "7.2.0"
  id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
  application
}

version = "0.0.1"
group = "codes.jun"

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
  implementation("org.springframework.security:spring-security-oauth2-resource-server")
  implementation("org.springframework.security:spring-security-oauth2-jose")

  implementation("org.flywaydb:flyway-core:10.7.2")
  implementation("org.flywaydb:flyway-database-postgresql:10.7.2")
  implementation("org.postgresql:postgresql:42.7.1")
  implementation("app.cash.sqldelight:jdbc-driver:2.1.0-SNAPSHOT")
  implementation("app.cash.sqldelight:runtime:2.1.0-SNAPSHOT")

  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")

  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
  implementation("org.springdoc:springdoc-openapi-starter-common:2.3.0")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
  testImplementation("org.testcontainers:testcontainers:1.19.4")
  testImplementation("org.testcontainers:junit-jupiter:1.19.4")
  testImplementation("org.testcontainers:postgresql:1.19.4")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
  mainClass.set("codes.jun.ApplicationKt")
}

sqldelight {
  databases {
    create("ZDatabase") {
      packageName.set("codes.jun.zdatabase")
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

openApi {
  outputDir.set(file("$buildDir/docs"))
  outputFileName.set("swagger.json")
}

openApiGenerate {
  inputSpec = "$buildDir/docs/swagger.json"
  generatorName = "typescript-fetch"
  outputDir = "$rootDir/api-client"
  additionalProperties = mapOf(
      "npmName" to "@z/api-client",
      "npmVersion" to "0.1.0",
      "supportsES6" to "true",
      "enumPropertyNaming" to "UPPERCASE",
      "modelPropertyNaming" to "original"
  )
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.openApiGenerate.configure {
  dependsOn("generateOpenApiDocs")
}

tasks.test.configure {
  useJUnitPlatform()
  dependsOn("generateMainZDatabaseMigrations")
}

tasks.compileKotlin.configure {
  kotlinOptions {
    jvmTarget = "17"
  }
  dependsOn("generateMainZDatabaseMigrations")
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

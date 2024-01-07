plugins {
  id("org.jetbrains.kotlin.jvm") version "1.9.22"
  id("org.jetbrains.kotlin.kapt") version "1.9.22"
  id("org.springframework.boot") version "3.1.3"
  id("io.spring.dependency-management") version "1.1.3"
  id("app.cash.sqldelight") version "2.0.1"
  application
}

repositories {
  google()
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-log4j2")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.postgresql:postgresql:42.2.27")
  implementation("app.cash.sqldelight:jdbc-driver:2.0.1")
  implementation("app.cash.sqldelight:runtime:2.0.1")

  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(20))
  }
}

application {
  mainClass.set("codes.jun.ApplicationKt")
}

sqldelight {
  databases {
    create("PivotDatabase") {
      packageName.set("codes.jun.pivot")
      dialect("app.cash.sqldelight:postgresql-dialect:2.0.1")
    }
  }
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}

configurations {
  all {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
  }
}

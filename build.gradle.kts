plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("org.springframework.boot") version "3.3.2" apply false
    id("io.spring.dependency-management") version "1.1.6" apply false
    kotlin("jvm") version "1.9.0" apply false
    kotlin("plugin.spring") version "1.9.0" apply false
    kotlin("plugin.jpa") version "1.9.0" apply false
}
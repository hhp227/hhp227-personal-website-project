val mybatisSpringBootStarterVersion = "2.2.2"
val mysqlVersion = "8.0.33"

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "2.7.18"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "kr.hhp227"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.security:spring-security-taglibs")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:$mybatisSpringBootStarterVersion")
	implementation("mysql:mysql-connector-java:$mysqlVersion")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.apache.tomcat.embed:tomcat-embed-jasper")
	implementation("javax.servlet:jstl:1.2")
	implementation("org.apache.tiles:tiles-jsp:3.0.8")
	implementation("org.webjars:jquery:3.3.1")
	implementation("org.webjars:bootstrap:3.3.5")
	implementation("org.webjars:jquery-validation:1.19.3")
	runtimeOnly("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

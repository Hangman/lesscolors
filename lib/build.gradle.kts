buildscript {
    project.version = "0.1.1"
    project.group = "de.pottgames.lesscolors"
}

plugins {
    id("java-library")
    id("maven-publish")
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.colormath:colormath:3.3.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

tasks {
    jar {
        manifest {
            attributes["Implementation-Title"] = project.name
            attributes["Implementation-Version"] = project.version
        }
    }
}

tasks.withType<JavaCompile> {
    options.setIncremental(true)
    options.encoding = "UTF-8"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])
        }
    }
}

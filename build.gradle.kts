plugins {
    id("java-library")
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("maven-publish")
}

group = "org.mmga"
version = "0.0.5"
tasks.compileJava {
    options.encoding = "UTF-8"
}
tasks.javadoc {
    options.encoding = "UTF-8"
}
tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc"))
}
tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/gradle-plugin")
    }
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.boot:spring-boot-starter-web") {
        // remove jackson
        exclude("org.springframework.book", "spring-boot-starter-json")
    }
    api("org.springframework.boot:spring-boot-starter-websocket")
    api("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
    // https://mvnrepository.com/artifact/com.alibaba.fastjson2/fastjson2
    api("com.alibaba.fastjson2:fastjson2:2.0.46")
    // https://mvnrepository.com/artifact/com.alibaba.fastjson2/fastjson2-extension
    implementation("com.alibaba.fastjson2:fastjson2-extension:2.0.46")
    // https://mvnrepository.com/artifact/com.alibaba.fastjson2/fastjson2-extension-spring6
    implementation("com.alibaba.fastjson2:fastjson2-extension-spring6:2.0.46")
    // https://mvnrepository.com/artifact/com.alibaba/druid-spring-boot-starter
    implementation("com.alibaba:druid-spring-boot-starter:1.2.23")
    // https://mvnrepository.com/artifact/com.auth0/java-jwt
    api("com.auth0:java-jwt:4.4.0")
    api("org.springdoc:springdoc-openapi-starter-webmvc-api:2.7.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
    api("org.springframework.boot:spring-boot-starter-aop")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
tasks.withType<Test> {
    useJUnitPlatform()
}
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "org.mmga"
            artifactId = "make-minecraft-great-again-spring-boot-starter"
            version = version

            artifact(tasks.named("javadocJar"))
            artifact(tasks.named("sourcesJar"))
            pom {
                name.set("MakeMinecraftGreatAgainSpringBootStarter")
                description.set("A Spring Boot Starter for mmga team.")

                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("wzp")
                        name.set("wzp")
                        email.set("minecraftwzpmc@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:https://wzpmc.cn:3000/MakeMinecraftGreatAgainTeam/MakeMinecraftGreatAgainSpringBootStarter.git")
                    developerConnection.set("scm:git:https://wzpmc.cn:3000/MakeMinecraftGreatAgainTeam/MakeMinecraftGreatAgainSpringBootStarter.git")
                    url.set("https://wzpmc.cn:3000/MakeMinecraftGreatAgainTeam/MakeMinecraftGreatAgainSpringBootStarter")
                }
            }
        }
    }

    repositories {
        maven {
            val releasesRepoUrl = uri("https://wzpmc.cn:90/repository/maven-releases")
            val snapshotsRepoUrl = uri("https://wzpmc.cn:90/repository/maven-snapshots")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            credentials {
                username = project.findProperty("repo.user") as String? ?: ""
                password = project.findProperty("repo.password") as String? ?: ""
            }
        }
    }
}
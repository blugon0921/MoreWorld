plugins {
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.5.9"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}


val buildPath = File("C:/Users/blugo/Desktop")
val mcVersion = "1.20.4"
val kommandVersion = "3.1.11"
val kotlinVersion = kotlin.coreLibrariesVersion


repositories {
    mavenCentral()
    maven("https://repo.blugon.kr/repository/maven-public/")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(kotlin("reflect"))
    paperweight.paperDevBundle("${mcVersion}-R0.1-SNAPSHOT")
    implementation("kr.blugon:pluginPlus:latest.release")
    compileOnly("xyz.icetang.lib:kommand-api:${kommandVersion}")
}

extra.apply {
    set("ProjectName", project.name)
    set("ProjectVersion", project.version)
    set("KotlinVersion", kotlinVersion)
    set("MinecraftVersion", mcVersion.split(".").subList(0, 2).joinToString("."))
    set("KommandVersion", kommandVersion)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }

    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
            expand(extra.properties)
        }
    }

    create<Jar>("buildPaper") {
        this.build()
    }

    shadowJar {
        this.build()
    }
}

fun Jar.build() {
    val file = File("./build/libs/${project.name}.jar")
    if(file.exists()) file.deleteOnExit()
    archiveBaseName.set(project.name) //Project Name
    archiveFileName.set("${project.name}.jar") //Build File Name
    archiveVersion.set(project.version.toString()) //Version
    from(sourceSets["main"].output)

    doLast {
        copy {
            from(archiveFile) //Copy from
            into(buildPath) //Copy to
        }
    }
}
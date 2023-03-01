plugins {
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

group = "kr.blugon.blugon09"
version = "1.1.1"
val buildLocation = File("C:/Files/Minecraft/Servers/\$plugins")
//val buildLocation = File("C:/Files/Minecraft/Servers/Default/plugins")


java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
//    implementation("io.github.monun:invfx-api:3.2.0")
    implementation("io.github.monun:kommand-api:3.0.0")
//    implementation("io.github.monun:tap-api:4.8.0")
}


tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(project.properties)
        }
    }

    jar {
        archiveVersion.set(project.version.toString())
        archiveBaseName.set(project.name)
        archiveFileName.set("${project.name}.jar")
        from(sourceSets["main"].output)

        doLast {
            copy {
                from(archiveFile)

                //Build Location
                into(buildLocation)
            }
        }
    }

    shadowJar {
        archiveVersion.set(project.version.toString())
        archiveBaseName.set(project.name)
        archiveFileName.set("${project.name}.jar")
        from(sourceSets["main"].output)

        doLast {
            copy {
                from(archiveFile)

                //Build Location
                into(buildLocation)
            }
        }
    }
}
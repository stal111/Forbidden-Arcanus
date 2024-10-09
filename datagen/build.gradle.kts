plugins {
    kotlin("jvm") version "2.0.20"
    id("java-library")
    id("eclipse")
    id("idea")
    id("maven-publish")
    id("net.neoforged.moddev") version "2.0.17-beta"
}

val mod_id: String by project
val parchment_mappings_version: String by project
val parchment_minecraft_version: String by project
val valhelsia_core_version: String by project
val minecraft_version: String by project
val dataforge_version: String by project

val mainProject: Project = project(":neoforge")
evaluationDependsOn(mainProject.path)

repositories {
    flatDir {
        dirs("libs")
    }
    maven {
        name = "DataForge"
        url = uri("https://maven.pkg.github.com/ValhelsiaTeam/DataForge")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)
kotlin.jvmToolchain(21)

neoForge {
    version = project.property("neo_version") as String

    parchment {
        mappingsVersion = parchment_mappings_version
        minecraftVersion = parchment_minecraft_version
    }

    mods.create(mod_id) {
        sourceSet(sourceSets.main.get())
        sourceSet(mainProject.sourceSets.main.get())
    }

    runs {

        create("data") {
            data()

            // Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
            programArguments.addAll("--mod", mod_id, "--all", "--output", mainProject.file("src/generated/resources/").absolutePath, "--existing", mainProject.file("src/main/resources/").absolutePath)
        }

        // applies to all the run configs above
        configureEach {
            // Recommended logging data for a userdev environment
            systemProperty("forge.logging.markers", "REGISTRIES")

            // Recommended logging level for the console
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }
}
dependencies {
    compileOnly(mainProject)

    implementation("net.valhelsia:valhelsia_core-neoforge-${minecraft_version}:${valhelsia_core_version}")

    implementation(interfaceInjectionData("net.valhelsia:dataforge:${dataforge_version}")!!)
}
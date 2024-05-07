import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

taboolib {
    subproject = true
}

dependencies {
    implementation(project(":project:common"))
    implementation(project(":project:common-core"))
    implementation(project(":project:module-applicative"))
    implementation(project(":project:module-bacikal"))
    implementation(project(":project:module-config"))
    implementation(project(":project:module-volatile"))
    implementation(project(":project:platform-bukkit"))
}

tasks {
    withType<ShadowJar> {
        archiveBaseName.set("Vulpecula-Premium")
        archiveClassifier.set("")
        destinationDirectory.set(file("${rootDir}/build/libs"))
        append("config.yml")
        append("lang/zh_CN.yml")
        append("kether.yml")
    }
    build {
        dependsOn(shadowJar)
    }
}

//publishing {
//    repositories {
//        maven {
//            url = uri("https://repo.tabooproject.org/repository/releases")
//            credentials {
//                username = project.findProperty("taboolibUsername").toString()
//                password = project.findProperty("taboolibPassword").toString()
//            }
//            authentication {
//                create<BasicAuthentication>("basic")
//            }
//        }
//    }
//    publications {
//        create<MavenPublication>("library") {
//            from(components["java"])
//            groupId = project.group.toString()
//        }
//    }
//}
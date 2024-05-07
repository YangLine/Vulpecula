
taboolib {
    subproject = true
}

dependencies {
    compileOnly(project(":project:module-applicative"))
    compileOnly(project(":project:module-config"))

    compileOnly("ink.ptms.core:v12000:12000:mapped")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4") // 协程
}
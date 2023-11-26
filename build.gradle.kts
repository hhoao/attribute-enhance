import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("java")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.org.jetbrains.dokka)
    alias(libs.plugins.io.gitlab.arturbosch.detekt)
}

group = "org.hhoao"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://mirrors.huaweicloud.com/repository/maven/") }
    // need connect external web
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/central") }
    maven { url = uri("https://plugins.gradle.org/m2/") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url= uri("https://repo.codemc.org/repository/maven-public/")}
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("com.github.seeseemelk:MockBukkit-v1.19:3.0.0")
    testImplementation("ch.qos.logback:logback-classic:1.4.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    compileOnly(libs.spigot.api)
    implementation(libs.nashorn.core)
    detektPlugins(libs.detekt.formatting)
    implementation(libs.guice)
    implementation(libs.sqlite.jdbc)
    implementation("org.apache.groovy:groovy:4.0.12")
    compileOnly("de.tr7zw:item-nbt-api-plugin:2.11.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.jar {
    val dir = project.properties["jarDestinationDir"]
    if (dir != null) {
        this.destinationDirectory.set(file(dir))
    }
}

detekt {
    allRules = true
    toolVersion = "1.23.0"
    if (file("config/detekt/detekt.yml").exists()) {
        config.setFrom(file("config/detekt/detekt.yml"))
    }
    buildUponDefaultConfig = true
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
    }
}

tasks.processResources {
    val sb = StringBuilder()
    sb.appendLine()
    configurations.all {
        if (dependencies.size > 0 && this.name == "implementation") {
            dependencies.forEach { dependency ->
                sb.appendLine("  - ${dependency.group}:${dependency.name}:${dependency.version}")
            }
        }
    }
    filesMatching("plugin.yml") {
        expand("version" to project.version, "libraries" to sb.toString())
    }
}

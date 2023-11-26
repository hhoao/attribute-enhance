package org.hhoa.aten.inject

import com.google.inject.AbstractModule
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.hhoa.aten.AttributeEnhance
import org.hhoa.aten.annotation.file.Config
import org.hhoa.aten.annotation.file.DataFolder
import org.hhoa.aten.annotation.file.ScriptFolder
import org.hhoa.aten.utils.FileUtils
import java.io.File

/**
 * SubDataFolder.
 * @param value folder name.
 */
enum class SubDataFolder(val value: String) {
    SCRIPT("script"),
}

/**
 * Plugin module to Guice Inject.
 *
 * @property plugin plugin
 * @constructor Create empty Plugin module
 */
class PluginModule(private val plugin: AttributeEnhance) : AbstractModule() {
    override fun configure() {
        bind(AttributeEnhance::class.java).toInstance(plugin)
        bind(JavaPlugin::class.java).toInstance(plugin)
        bind(Plugin::class.java).toInstance(plugin)
        bind(FileConfiguration::class.java).annotatedWith(Config("config"))
            .toInstance(plugin.config)
        bind(File::class.java).annotatedWith(DataFolder::class.java)
            .toInstance(FileUtils.getOrCreateExistDir(plugin.dataFolder))
        bind(File::class.java).annotatedWith(ScriptFolder::class.java)
            .toInstance(
                FileUtils.getOrCreateExistDir(File(plugin.dataFolder, SubDataFolder.SCRIPT.value))
            )
        bind(YamlConfiguration::class.java).annotatedWith(Config("format"))
            .toInstance(YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "format.yml")))
    }
}

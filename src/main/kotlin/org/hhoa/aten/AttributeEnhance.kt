package org.hhoa.aten

import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.Injector
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.hhoa.aten.annotation.file.Config
import org.hhoa.aten.inject.PluginModule
import org.hhoa.aten.inject.ScriptModule
import org.hhoa.aten.listeners.BlockListener
import org.hhoa.aten.listeners.EntityListener
import org.hhoa.aten.listeners.PlayerListener
import org.hhoa.aten.scripts.PluginScriptManager
import org.hhoa.aten.utils.FileUtils
import org.hhoa.aten.utils.LoggerFactory
import java.io.File

/**
 * Injector.
 */
lateinit var injector: Injector

/**
 * Entry of AttributeEnhance Plugin.
 */
open class AttributeEnhance : JavaPlugin() {
    @Config("format")
    @Inject
    lateinit var format: YamlConfiguration

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // 使用KillPlayer爆炸杀死玩家.
        if (command.name.endsWith("KillPlayer", true)) {
            val target: Player? = sender.server.getPlayer(args[0])
            if (target == null) {
                sender.sendMessage(args[0] + " is not currently online.")
                return true
            }
            target.health = 0.0
        }
        return false
    }

    override fun onLoad() {
        logger.info("AttributeEnhance onLoad")
    }

    override fun onEnable() {
        logger.info("AttributeEnhance onEnable")
        initPlugin()
    }

    private fun initPlugin() {
        LoggerFactory.setPlugin(this)
        initResources()
        initGuice()
        initEventListeners()
        initScriptModule()
    }

    private fun initEventListeners() {
        val pm = server.pluginManager
        val playerListener = injector.getInstance(PlayerListener::class.java)
        val entityListener = injector.getInstance(EntityListener::class.java)
        pm.registerEvents(playerListener, this)
        pm.registerEvents(entityListener, this)
    }

    private fun initGuice() {
        injector = Guice.createInjector(PluginModule(this), ScriptModule(this))
        injector.injectMembers(this)
    }

    private fun initScriptModule() {
        injector.getInstance(PluginScriptManager::class.java).loadScript()
    }

    private fun initResources() {
        val file = "preset/*"
        if (this::class.java.protectionDomain.codeSource.location != null) {
            FileUtils.copyJarResourcesToDirectory(
                File(this::class.java.protectionDomain.codeSource.location.path),
                dataFolder.path,
                file
            )
        } else {
            // for test
            val substring = file.substring(0, file.length - "/*".length)
            val resource = this.classLoader.getResource(substring)
            if (resource != null) {
                val file1 = File(resource.path)
                file1.listFiles()?.forEach {
                    FileUtils.copy(it, dataFolder)
                }
            }
        }
    }

    override fun onDisable() {
        logger.info("AttributeEnhance onDisable")
    }
}

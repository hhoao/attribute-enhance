package org.hhoa.aten.utils

import org.bukkit.plugin.Plugin
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

/**
 * LoggerFactory.
 *
 * @author hhoa
 * @since 2023/6/15
 **/

object LoggerFactory {
    private var plugin: Plugin? = null

    /**
     * Set plugin.
     *
     * @param plugin
     */
    fun setPlugin(plugin: Plugin) {
        this.plugin = plugin
    }

    private class PluginClassLogger(plugin: Plugin, val clazz: Class<in Nothing>) : Logger(plugin.name, null) {
        init {
            parent = plugin.server.logger
            level = Level.ALL
        }

        override fun log(logRecord: LogRecord) {
            logRecord.message = "[${clazz.simpleName}] ${logRecord.message}"
            super.log(logRecord)
        }
    }

    /**
     * Get logger.
     *
     * @param clazz
     * @return
     */
    fun getLogger(clazz: Class<in Nothing>): Logger {
        val logger = debug {
            if (plugin != null) {
                PluginClassLogger(plugin as Plugin, clazz)
            } else {
                Logger.getLogger(clazz.simpleName)
            }
        }
        return logger ?: plugin?.logger ?: Logger.getLogger(null)
    }
}

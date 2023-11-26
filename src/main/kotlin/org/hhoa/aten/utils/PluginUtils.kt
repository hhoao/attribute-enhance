package org.hhoa.aten.utils

import com.google.inject.Key
import de.tr7zw.nbtapi.NBTItem
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.hhoa.aten.annotation.file.Config
import org.hhoa.aten.injector

/**
 * PluginUtils.
 *
 * @author hhoa
 * @since 2023/6/15
 **/

/**
 * Check whether the plug-in is in debug mode.
 *
 * @return true when debug mode enabled.
 */
fun isDebug(): Boolean {
    val configKey = Key.get(FileConfiguration::class.java, Config("config"))
    val config = injector.getInstance(configKey)
    return config.getBoolean("options.debug")
}

/**
 * Debug to do.
 *
 * @param T Return type.
 * @param run The lambda of when debug enable to run.
 * @receiver
 * @return the lambda execute result
 */
fun <T> debug(run: () -> T): T? {
    if (isDebug()) {
        return run()
    }
    return null
}

fun getPlayerArmor(player: Player) {
    val inventory = player.inventory
    val armorContents = inventory.armorContents
    armorContents.forEach {
        val nbtItem = NBTItem(it)
    }
}

package org.hhoa.aten.inject

import com.google.inject.AbstractModule
import com.google.inject.TypeLiteral
import com.google.inject.name.Names
import org.bukkit.configuration.file.YamlConfiguration
import org.hhoa.aten.AttributeEnhance
import java.io.File


/**
 * ScriptModule.
 *
 * @author hhoa
 * @since 2023/6/14
 **/

class ScriptModule(private val plugin: AttributeEnhance) : AbstractModule() {
    override fun configure() {
        val loadConfiguration = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, "format.yml"))
        val mapList = loadConfiguration.getMapList("formats")
        val regexList = java.util.ArrayList<String>()
        mapList.forEach { formatMap ->
            val regex = formatMap["regex"] as String
            regexList.add(regex)
        }
        bind(object : TypeLiteral<ArrayList<String>>() {}).annotatedWith(Names.named("formatRegexes"))
            .toInstance(regexList)
    }
}

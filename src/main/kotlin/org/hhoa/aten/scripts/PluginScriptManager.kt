package org.hhoa.aten.scripts

import com.google.inject.Inject
import com.google.inject.name.Named
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.Event
import org.hhoa.aten.annotation.file.Config
import org.hhoa.aten.annotation.file.ScriptFolder
import org.hhoa.aten.annotation.script.Attribute
import org.hhoa.aten.annotation.script.Script
import org.hhoa.aten.api.AttributeEventProcessorScript
import org.hhoa.aten.api.EventProcessor
import org.hhoa.aten.api.EventProcessorScript
import org.hhoa.aten.listeners.EventRegister
import org.hhoa.aten.api.exception.MissingAnnotationException
import org.hhoa.aten.injector
import org.hhoa.aten.utils.LoggerFactory
import java.io.File
import java.util.concurrent.ForkJoinPool
import java.util.regex.Pattern
import javax.naming.directory.NoSuchAttributeException
import kotlin.time.Duration.Companion.seconds

/**
 * PluginScriptManager.
 *
 * @author hhoa
 * @since 2023/6/13
 **/

class PluginScriptManager @Inject constructor(
    @ScriptFolder private val scriptFolder: File,
    @Named("formatRegexes")
    var regexes: java.util.ArrayList<String>,
    @Config("config")
    var config: FileConfiguration,
) {
    private val groovyManager: GroovyManager = GroovyManager()
    private val commonPool = ForkJoinPool.commonPool()
    private val logger = LoggerFactory.getLogger(PluginScriptManager::class.java)

    /**
     * Load script.
     */
    fun loadScript() {
        loadScriptDir(scriptFolder)
    }

    private fun loadScriptFile(file: File) {
        if (file.name.endsWith(".groovy")) {
            commonPool.submit {
                val runCatching = runCatching {
                    val clazz = groovyManager.loadScript(file.absoluteFile)
                    loadScriptClass(clazz)
                }
                if (runCatching.isFailure) {
                    logger.warning("Load ${file.name} failed")
                    logger.warning(runCatching.exceptionOrNull()?.stackTraceToString())
                }
            }
        }
    }

    private fun loadScriptDir(file: File) {
        logger.info("Loading scripts...!")
        file.listFiles()?.forEach { childFile ->
            if (childFile.isDirectory) {
                loadScriptDir(childFile)
            } else {
                loadScriptFile(childFile)
            }
        }
        runBlocking {
            while (!commonPool.isQuiescent) {
                delay(1.seconds)
            }
            logger.info("Loaded all scripts!")
        }
    }

    @Throws(Exception::class)
    private fun initAttributeEventProcessorScript(clazzInstance: AttributeEventProcessorScript<Event>) {
        val attributeAnnotation = clazzInstance.javaClass.getDeclaredAnnotation(Attribute::class.java)
            ?: throw MissingAnnotationException(Attribute::class.java)
        val eventKey = attributeAnnotation.key
        val attributeContext = clazzInstance.attributeContext
        val attributeSession = config.getConfigurationSection("attributes.$eventKey")
        if (attributeSession != null) {
            attributeContext.key = eventKey
            attributeContext.name = attributeSession.getString("name") ?: eventKey
            if (attributeSession.contains("priority")) {
                clazzInstance.priority = attributeSession.getInt("priority")
            }
            attributeContext.labels.addAll(attributeSession.getStringList("labels"))
            if (attributeContext.name != null) {
                attributeContext.labels.add(attributeContext.name!!)
            }
            for (regex in regexes) {
                attributeContext.patterns.add(Pattern.compile(regex.replace("@key", eventKey)))
                for (label in attributeContext.labels) {
                    attributeContext.patterns.add(Pattern.compile(regex.replace("@key", label)))
                }
            }
            val messageConfigurationSession = attributeSession.getConfigurationSection("message")
            if (messageConfigurationSession != null && messageConfigurationSession.getBoolean("enable")) {
                attributeContext.messageFormat = messageConfigurationSession.getString("format")
            }
        } else {
            throw NoSuchAttributeException("${clazzInstance.javaClass.name} load failed, attributes.$eventKey")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun loadScriptClass(clazz: Class<in Nothing>) {
        val register = injector.getInstance(EventRegister::class.java)
        if (!EventProcessor::class.java.isAssignableFrom(clazz)) {
            return
        }
        val clazzInstance = clazz.getDeclaredConstructor().newInstance() as EventProcessor<Event>
        try {
            if (EventProcessorScript::class.java.isAssignableFrom(clazz)) {
                val scriptAnnotation = clazz.getDeclaredAnnotation(Script::class.java)
                clazzInstance as EventProcessorScript<Event>
                val scriptPriority = scriptAnnotation?.priority
                if (scriptPriority != null) {
                    clazzInstance.priority = scriptPriority
                }
                if (AttributeEventProcessorScript::class.java.isAssignableFrom(clazz)) {
                    initAttributeEventProcessorScript(clazzInstance as AttributeEventProcessorScript<Event>)
                }
                clazzInstance.priority =
                    scriptPriority
                        ?: throw NoSuchAttributeException("attribute.priority else Script(priority=?)")
                register.registerEventProcessorScript(clazzInstance )
            } else {
                TODO("Other Script Load")
            }
        } catch (e: NoSuchMethodException) {
            logger.warning("${e.message}\n${e.stackTraceToString()}")
        } catch (e: NoSuchAttributeException) {
            logger.warning("${e.message}\n${e.stackTraceToString()}")
        } catch (e: MissingAnnotationException) {
            logger.warning("${e.message}\n${e.stackTraceToString()}")
        }
    }
}

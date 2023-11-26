package org.hhoa.aten.listeners

import com.google.inject.Singleton
import org.bukkit.event.Event
import org.hhoa.aten.api.AttributeEventProcessorScript
import org.hhoa.aten.api.EventProcessorScript
import org.hhoa.aten.utils.LoggerFactory
import java.lang.reflect.ParameterizedType
import java.util.PriorityQueue
import java.util.concurrent.ConcurrentHashMap

/**
 * EventRegister.
 *
 * @author hhoa
 * @since 2023/6/14
 **/

@Singleton
class EventRegister {
    private val logger = LoggerFactory.getLogger(EventRegister::class.java)
    private val eventProcessorScriptMap: MutableMap<Class<Event>, MutableList<EventProcessorScript<Event>>> =
        ConcurrentHashMap()

    /**
     * Get processor container.
     *
     * @param clazz
     */
    private fun getProcessorScriptContainer(clazz: Class<Event>) =
        eventProcessorScriptMap.getOrPut(clazz) { ArrayList() }

    fun getSortedProcessorScriptContainer(clazz: Class<Event>) =
        eventProcessorScriptMap.getOrPut(clazz) { ArrayList() }.sorted()

    /**
     * Register.
     *
     * @param eventProcessorScript
     */
    @Suppress("UNCHECKED_CAST")
    fun registerEventProcessorScript(eventProcessorScript: EventProcessorScript<Event>) {
        val genericSuperclass = eventProcessorScript.javaClass.genericSuperclass
        if (genericSuperclass is ParameterizedType &&
            Event::class.java.isAssignableFrom(genericSuperclass.actualTypeArguments[0] as Class<in Nothing>)
        ) {
            val processorContainer =
                getProcessorScriptContainer(genericSuperclass.actualTypeArguments[0] as Class<Event>)
            processorContainer.add(eventProcessorScript)
            val msg = StringBuilder("register ${eventProcessorScript.javaClass.simpleName} script ")
            if (eventProcessorScript is AttributeEventProcessorScript) {
                if (eventProcessorScript.attributeContext.key != null) {
                    msg.append("with key '${eventProcessorScript.attributeContext.key}'")
                }
            }
            logger.info(msg.toString())
        }
    }
}

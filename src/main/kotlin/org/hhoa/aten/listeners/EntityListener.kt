package org.hhoa.aten.listeners

import com.google.inject.Inject
import com.google.inject.name.Named
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.EquipmentSlot
import org.hhoa.aten.AttributeEnhance
import org.hhoa.aten.api.DefaultRuntimeContext
import org.hhoa.aten.api.EventProcessorScript
import org.hhoa.aten.injector
import org.hhoa.aten.utils.LoggerFactory
import java.util.logging.Logger
import java.util.regex.Pattern

/**
 * EntityListener.
 *
 * @param plugin plugin
 * @author hhoa
 * @since 2023/6/10
 **/

class EntityListener @Inject constructor(
    val plugin: AttributeEnhance,
    @Named("formatRegexes") val formatRegexes: List<Pattern>
) : Listener {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun beforePrecess(event: EntityDamageByEntityEvent, eventProcessor: EventProcessorScript<Event>) {
        val damager = event.damager
        val map = mutableMapOf<String, String>()
        if (damager is Player) {
            val inventory = damager.inventory
            val equipmentSlots = EquipmentSlot.values()
            equipmentSlots.forEach { equipmentSlot ->
                val item = inventory.getItem(equipmentSlot)
                if (item != null) {
                    val lores = item.itemMeta?.lore
                    lores?.forEach { lore ->
                        val patterns = listOf<Pattern>();
                        patterns.forEach { pattern ->
                            val matcher = pattern.matcher(lore)
                            if (matcher.find()) {
                                map[matcher.group(0)] = matcher.group(1)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * On entity damage handle.
     *
     * @param event [EntityDamageByEntityEvent].
     */
    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        val runtimeContext = DefaultRuntimeContext()
        val eventRegister = injector.getInstance(EventRegister::class.java)
        val processorContainer = eventRegister.getSortedProcessorScriptContainer(event.javaClass)

        processorContainer.forEach { eventProcessor ->
            eventProcessor.process(event, runtimeContext)
        }
    }
}

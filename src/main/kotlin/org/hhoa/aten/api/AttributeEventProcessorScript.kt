package org.hhoa.aten.api

import org.bukkit.event.Event
import org.hhoa.aten.utils.StringUtils
import java.util.regex.Pattern

/**
 * AttributeEventProcessorScript.
 *
 * @author hhoa
 * @since 2023/6/20
 **/

abstract class AttributeEventProcessorScript<E : Event> : EventProcessorScript<E>() {
    fun getValue(lore: List<String>): Double {
        var value = 0.0
        lore.forEach {
            attributeContext.patterns.forEach { pattern ->
                val matcher = pattern.matcher(it)
                if (matcher.find()) {
                    val group = matcher.group(1)
                    if (group != null) {
                        value += StringUtils.parseNumber(group)
                    }
                }
            }
        }
        return value
    }

    /**
     * Attribute context.
     */
    var attributeContext: AttributeContext = AttributeContext()

    /**
     * Attribute context.
     *
     * @constructor Create empty Attribute context
     */
    class AttributeContext {
        /**
         * Message format.
         */
        var messageFormat: String? = null

        /**
         * Patterns.
         */
        val patterns: MutableSet<Pattern> = mutableSetOf()

        /**
         * Key.
         */
        var key: String? = null

        /**
         * Name.
         */
        var name: String? = null

        /**
         * Labels.
         */
        val labels: MutableSet<String> = mutableSetOf()
    }
}

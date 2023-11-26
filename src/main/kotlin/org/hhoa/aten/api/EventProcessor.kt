package org.hhoa.aten.api

import org.bukkit.event.Event

/**
 * Event processor.
 *
 * @param E
 * @constructor Create empty Event processor
 */

interface EventProcessor<E> : Comparable<EventProcessor<E>> where E : Event {
    /**
     * Priority.
     */
    var priority: Int

    /**
     * Process.
     *
     * @param event
     * @param runtimeContext
     */
    fun process(event: E, runtimeContext: RuntimeContext)

    override fun compareTo(other: EventProcessor<E>): Int {
        return if (this.priority > other.priority) {
            1
        } else if (this.priority < other.priority) {
            -1
        } else {
            0
        }
    }
}

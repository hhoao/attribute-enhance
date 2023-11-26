package org.hhoa.aten.api

import org.bukkit.event.Event

/**
 * EventProcessorScript.
 *
 * @author hhoa
 * @since 2023/6/21
 **/

abstract class EventProcessorScript<E : Event> : EventScript(), EventProcessor<E>

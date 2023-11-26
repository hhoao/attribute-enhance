package org.hhoa.aten.api

import org.hhoa.aten.utils.LoggerFactory

/**
 * EventScript.
 *
 * @author hhoa
 * @since 2023/6/21
 **/

abstract class EventScript {
    /**
     * Logger.
     */
    var logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Priority.
     */
    var priority: Int = 0
}

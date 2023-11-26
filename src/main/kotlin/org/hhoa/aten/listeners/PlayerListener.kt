package org.hhoa.aten.listeners

import com.google.inject.Inject
import org.bukkit.event.Listener
import org.hhoa.aten.AttributeEnhance
import org.hhoa.aten.utils.LoggerFactory
import java.util.logging.Logger

/**
 * PlayerListener.
 *
 * @author hhoa
 * @since 2023/6/10
 **/

class PlayerListener @Inject constructor(private val plugin: AttributeEnhance) : Listener {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)
}

package preset.script

import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.hhoa.aten.AttributeEnhance
import org.hhoa.aten.annotation.Attribute
import org.hhoa.aten.api.EventProcessor
import org.jetbrains.annotations.NotNull

/**
 * TestGroovy.
 *
 * @author hhoa
 * @since 2023/6/11
 * */
@Attribute("armor")
class TestGroovy1 extends EventProcessor<EntityDamageByEntityEvent> {
    @Override
    void process(@NotNull EntityDamageByEntityEvent event) {
        event.damage = 10
        ctx.logger.info("set damage ${event.damage}")
    }
}

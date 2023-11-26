package preset.script

import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.hhoa.aten.annotation.script.Attribute
import org.hhoa.aten.annotation.script.Priority
import org.hhoa.aten.annotation.script.Script
import org.hhoa.aten.api.AttributeEventProcessorScript
import org.hhoa.aten.api.RuntimeContext
import org.jetbrains.annotations.NotNull

import java.text.MessageFormat

/**
 * AttackAttributePreProcess
 *
 * @author hhoa
 * @since 2023/6/19 *               */

@Script(priority = Priority.MIN)
@Attribute(key = "attack")
class AttackAttributeEventPostProcessor extends AttributeEventProcessorScript<EntityDamageByEntityEvent> {
    @Override
    void process(@NotNull EntityDamageByEntityEvent event, @NotNull RuntimeContext runtimeContext) {
        if (attributeContext.messageFormat != null) {
            def message = MessageFormat.format(attributeContext.messageFormat, event.damage)
            event.damager.sendMessage(message)
        }
    }
}

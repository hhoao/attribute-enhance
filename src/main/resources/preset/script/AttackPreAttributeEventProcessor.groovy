package preset.script


import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.hhoa.aten.annotation.script.Priority
import org.hhoa.aten.annotation.script.Script
import org.hhoa.aten.api.EventProcessorScript
import org.hhoa.aten.api.RuntimeContext
import org.hhoa.aten.api.state.ValueStateDescriptor
import org.jetbrains.annotations.NotNull

/**
 * AttackAttributePreProcess
 *
 * @author hhoa
 * @since 2023/6/19 *
 * */

@Script(priority = Priority.MAX)
class PreAttackAttributeEventProcessor extends EventProcessorScript<EntityDamageByEntityEvent> {
    @Override
    void process(@NotNull EntityDamageByEntityEvent event, @NotNull RuntimeContext runtimeContext) {
        def state = runtimeContext.getState(new ValueStateDescriptor("testState"))
        state.update("testState")
    }
}

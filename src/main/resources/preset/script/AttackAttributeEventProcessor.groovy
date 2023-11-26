package preset.script

import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.hhoa.aten.annotation.script.Attribute
import org.hhoa.aten.annotation.script.Script
import org.hhoa.aten.api.AttributeEventProcessorScript
import org.hhoa.aten.api.RuntimeContext
import org.hhoa.aten.api.state.ValueStateDescriptor
import org.jetbrains.annotations.NotNull

/**
 * TestGroovy.
 *
 * @author hhoa
 * @since 2023/6/11
 * */

@Attribute(key = "attack")
@Script(priority = 0)
class AttackAttributeEventProcessor extends AttributeEventProcessorScript<EntityDamageByEntityEvent> {
    @Override
    void process(@NotNull EntityDamageByEntityEvent event, RuntimeContext runtimeContext) {
        def state = runtimeContext.getState(new ValueStateDescriptor("testState"))
        logger.info(state.value() as String)
        def damager = event.damager
        if (damager instanceof Player) {
            def inventory = damager.inventory
            def hand = inventory.getItemInMainHand()
            if (hand.hasItemMeta()) {
                def lore = hand.itemMeta.getLore()
                if (lore != null) {
                    def value = getValue(lore)
                    if (value != 0) {
                        event.damage = attack
                    }
                }
            }
        }
    }
}

package preset.script

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import org.hhoa.aten.annotation.Attribute
import org.hhoa.aten.api.EventProcessor
import org.jetbrains.annotations.NotNull

/**
 * TestGroovy.
 *
 * @author hhoa
 * @since 2023/6/11
 * */
@Attribute("attack")
class TestScript extends EventProcessor<EntityDamageByEntityEvent> {
    @Override
    void process(@NotNull EntityDamageByEntityEvent event) {
//        def instance = event.damager
//        if (instance instanceof Player) {
//            def inventory = instance.getInventory()
//            def diamond = new ItemStack(Material.DIAMOND)
//            List<Component> components = new ArrayList<>()
//            components.add(Component.text("testNbt"))
//            diamond.lore(components)
//            inventory.setItemInMainHand(diamond)
//            def hand = inventory.getItemInMainHand()
//            ctx.logger.info(hand.toString())
//        }
//        def damager = event.damager
//        damager.get
//        event.damage = 10
//        instance.logger.info("TestScript Execute")
//        ctx.logger.info("ctx.logger")
    }
}

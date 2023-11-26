package org.hhoa.aten

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.entity.PlayerMock
import net.kyori.adventure.text.NBTComponent
import org.bukkit.Keyed
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * AttributeEnhanceTest.
 *
 * @author hhoa
 * @since 2023/6/14
 **/


class AttributeEnhanceTest {
    private lateinit var server: ServerMock
    private lateinit var plugin: AttributeEnhance

    @BeforeEach
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(AttributeEnhance::class.java)
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    fun addSword(player: PlayerMock) {
        val inventory = player.inventory
        val sword = ItemStack(Material.DIAMOND_SWORD)
        val itemMeta = sword.itemMeta
        val lores = arrayListOf<String>()
        lores.add("攻击力: +10-+50")
        itemMeta?.lore = lores
        sword.itemMeta = itemMeta
        inventory.setItemInMainHand(sword)
    }

    fun addArmor(player: PlayerMock) {
        val inventory = player.inventory
        val chestPlate = ItemStack(Material.DIAMOND_CHESTPLATE, 1)
        val boots = ItemStack(Material.DIAMOND_BOOTS, 1)
        for (i in 0 until inventory.size) {
            inventory.setItem(i, chestPlate);
        }
//        inventory.chestplate = chestPlate
        inventory.boots = boots
        player.updateInventory()
        println(player.equipment.chestplate)
        println(player.getAttribute(Attribute.GENERIC_ARMOR)?.value)
    }

    @Test
    fun thisTestWillFail() {
        val entity = server.addPlayer()
        val damager = server.addPlayer()
        entity.registerAttribute(Attribute.GENERIC_ARMOR)
        addArmor(entity)
        entity.damage(10.0, damager)
//        println(entity.isDead)
//        println(entity.health)
//        println(entity.getAttribute(AttributeConfiguration.GENERIC_ARMOR)?.value)
//        println(entity.getAttribute(AttributeConfiguration.GENERIC_ARMOR)?.defaultValue)
//        println(entity.getAttribute(AttributeConfiguration.GENERIC_ARMOR)?.baseValue)
//        println(entity.getAttribute(AttributeConfiguration.GENERIC_ARMOR_TOUGHNESS)?.value)
//        println(entity.player.playerProfile.)
    }
}

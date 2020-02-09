package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.enchantment.ModEnchantment;
import com.stal111.forbidden_arcanus.item.EdelwoodBucketItem;
import com.stal111.forbidden_arcanus.util.Data;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEnchantments {

    public static final Enchantment PERMAFROST = register("permafrost", new EnchantmentBuilder(Enchantment.Rarity.UNCOMMON, ModEnchantmentType.EDELWOOD_BUCKET, EquipmentSlotType.MAINHAND).isTreasure().build());
    public static final Enchantment INDESTRUCTIBLE = register("indestructible", new EnchantmentBuilder(Enchantment.Rarity.RARE, EnchantmentType.BREAKABLE, EquipmentSlotType.MAINHAND).minAndMaxEnchantability(20, 60).build());

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        Main.LOGGER.debug("Registering Enchantments... [Total Count: " + Data.ENCHANTMENTS.size() + "]");
        Data.ENCHANTMENTS.forEach(enchantment -> event.getRegistry().register(enchantment));
    }

    private static <T extends Enchantment> T register(String name, T enchantment) {
        enchantment.setRegistryName(ModUtils.location(name));
        Data.ENCHANTMENTS.add(enchantment);
        return enchantment;
    }

    public static class ModEnchantmentType {
        public static final EnchantmentType EDELWOOD_BUCKET = EnchantmentType.create("EDELWOOD_BUCKET", item -> item instanceof EdelwoodBucketItem);
    }

    public static class EnchantmentBuilder {

        private ModEnchantment enchantment;

        public EnchantmentBuilder(Enchantment.Rarity rarity, EnchantmentType enchantmentType, EquipmentSlotType... equipmentSlotTypes) {
            this.enchantment = new ModEnchantment(rarity, enchantmentType, equipmentSlotTypes);
        }

        public EnchantmentBuilder maxLevel(int maxLevel) {
            this.enchantment.maxLevel = maxLevel;
            return this;
        }

        public EnchantmentBuilder minAndMaxEnchantability(int minEnchantability, int maxEnchantability) {
            this.enchantment.minEnchantability = minEnchantability;
            this.enchantment.maxEnchantability = maxEnchantability;
            return this;
        }

        public EnchantmentBuilder isTreasure() {
            enchantment.isTreasure = true;
            return this;
        }

        public ModEnchantment build() {
            return this.enchantment;
        }
    }
}

package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.enchantment.ModEnchantment;
import com.stal111.forbidden_arcanus.item.EdelwoodBucketItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = new DeferredRegister<>(ForgeRegistries.ENCHANTMENTS, Main.MOD_ID);

    public static final RegistryObject<Enchantment> PERMAFROST = register("permafrost", new EnchantmentBuilder(Enchantment.Rarity.UNCOMMON, ModEnchantmentType.EDELWOOD_BUCKET, EquipmentSlotType.MAINHAND).isTreasure().build());
    public static final RegistryObject<Enchantment> INDESTRUCTIBLE = register("indestructible", new EnchantmentBuilder(Enchantment.Rarity.RARE, ModEnchantmentType.BREAKABLE_NOT_ELYTRA, EquipmentSlotType.MAINHAND).minAndMaxEnchantability(20, 60).blacklistedEnchantments(Arrays.asList(Enchantments.UNBREAKING, Enchantments.MENDING)).build());

    private static <T extends Enchantment> RegistryObject<T> register(String name, T enchantment) {
        return ENCHANTMENTS.register(name, () -> enchantment);
    }

    public static class ModEnchantmentType {
        public static final EnchantmentType EDELWOOD_BUCKET = EnchantmentType.create("EDELWOOD_BUCKET", item -> item instanceof EdelwoodBucketItem);
        public static final EnchantmentType BREAKABLE_NOT_ELYTRA = EnchantmentType.create("BREAKABLE_NOT_ELYTRA", item -> item.isDamageable() && !(item instanceof ElytraItem));

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

        public EnchantmentBuilder blacklistedEnchantments(List<Enchantment> blacklistedEnchantments) {
            enchantment.blacklistedEnchantments = blacklistedEnchantments;
            return this;
        }

        public ModEnchantment build() {
            return this.enchantment;
        }
    }
}

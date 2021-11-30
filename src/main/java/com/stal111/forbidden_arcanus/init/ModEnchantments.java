package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.enchantment.ModEnchantment;
import com.stal111.forbidden_arcanus.item.EdelwoodBucketItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<Enchantment> PERMAFROST = register("permafrost", new EnchantmentBuilder(Enchantment.Rarity.UNCOMMON, EnchantmentCategories.EDELWOOD_BUCKET, EquipmentSlot.MAINHAND).isTreasure().build());

    private static <T extends Enchantment> RegistryObject<T> register(String name, T enchantment) {
        return ENCHANTMENTS.register(name, () -> enchantment);
    }

    public static class EnchantmentCategories {
        public static final EnchantmentCategory EDELWOOD_BUCKET = EnchantmentCategory.create("EDELWOOD_BUCKET", item -> item instanceof EdelwoodBucketItem);
        public static final EnchantmentCategory TOOL = EnchantmentCategory.create("EDELWOOD_BUCKET", item -> item instanceof EdelwoodBucketItem);
    }

    public static class EnchantmentBuilder {

        private final ModEnchantment enchantment;

        public EnchantmentBuilder(Enchantment.Rarity rarity, EnchantmentCategory enchantmentType, EquipmentSlot... equipmentSlotTypes) {
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

        public EnchantmentBuilder canApplyTogether(Predicate<Enchantment> predicate) {
            enchantment.canApplyTogether = predicate;
            return this;
        }

        public EnchantmentBuilder canBeVillagerTrade(boolean canBeTrade) {
            enchantment.canBeVillagerTrade = canBeTrade;
            return this;
        }

        public EnchantmentBuilder canGenerateInLoot(boolean canGenerate) {
            enchantment.canGenerateInLoot = canGenerate;
            return this;
        }

        public ModEnchantment build() {
            return this.enchantment;
        }
    }
}

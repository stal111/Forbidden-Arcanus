package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.enchantment.PermafrostEnchantment;
import com.stal111.forbidden_arcanus.common.item.EdelwoodBucketItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Mod Enchantments <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.ModEnchantments
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 */
public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<Enchantment> PERMAFROST = register("permafrost", new PermafrostEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategories.EDELWOOD_BUCKET, EquipmentSlot.MAINHAND));

    private static <T extends Enchantment> RegistryObject<T> register(String name, T enchantment) {
        return ENCHANTMENTS.register(name, () -> enchantment);
    }

    public static class EnchantmentCategories {
        public static final EnchantmentCategory EDELWOOD_BUCKET = EnchantmentCategory.create("EDELWOOD_BUCKET", item -> item instanceof EdelwoodBucketItem);
    }
}

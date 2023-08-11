package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.enchantment.PermafrostEnchantment;
import com.stal111.forbidden_arcanus.common.item.EdelwoodBucketItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 */
public class ModEnchantments implements RegistryClass {

    public static final MappedRegistryHelper<Enchantment> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.ENCHANTMENT);

    public static final RegistryEntry<Enchantment> PERMAFROST = HELPER.register("permafrost", () -> new PermafrostEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategories.EDELWOOD_BUCKET, EquipmentSlot.MAINHAND));

    public static class EnchantmentCategories {
        public static final EnchantmentCategory EDELWOOD_BUCKET = EnchantmentCategory.create("EDELWOOD_BUCKET", item -> item instanceof EdelwoodBucketItem);
    }
}

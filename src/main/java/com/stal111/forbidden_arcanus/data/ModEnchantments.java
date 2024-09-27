package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModEnchantmentDataComponents;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

public class ModEnchantments extends DatapackRegistryClass<Enchantment> {

    public static final DatapackRegistryHelper<Enchantment> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.ENCHANTMENT);

    public static final ResourceKey<Enchantment> SOUL_LOOTING = HELPER.createKey("soul_looting");

    public ModEnchantments(BootstrapContext<Enchantment> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> itemHolderGetter = context.lookup(Registries.ITEM);

        context.register(SOUL_LOOTING, Enchantment.enchantment(Enchantment.definition(itemHolderGetter.getOrThrow(ItemTags.SWORD_ENCHANTABLE), 2, 3, Enchantment.dynamicCost(15, 9), Enchantment.dynamicCost(65, 9), 4, EquipmentSlotGroup.MAINHAND))
                .withEffect(ModEnchantmentDataComponents.LOST_SOUL_SPAWN_CHANCE.get(), new AddValue(LevelBasedValue.perLevel(0.05F)))
                .build(SOUL_LOOTING.location()));
    }
}

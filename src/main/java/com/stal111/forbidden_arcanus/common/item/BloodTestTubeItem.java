package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceContainer;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.ItemEssenceData;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2021-07-08
 */
public class BloodTestTubeItem extends Item implements EssenceContainer {

    public static final int MAX_BLOOD = 3000;

    public static final ItemEssenceData DEFAULT_DATA = new ItemEssenceData(EssenceData.createEmpty(EssenceType.BLOOD, MAX_BLOOD), true);

    public BloodTestTubeItem(Properties properties) {
        super(properties);
    }

    public static ItemStack create(Item item, int blood) {
        ItemStack stack = new ItemStack(item);

        stack.set(ModDataComponents.ESSENCE_DATA, new ItemEssenceData(EssenceData.of(EssenceType.BLOOD, blood, MAX_BLOOD), true));

        return stack;
    }

    @NotNull
    @Override
    public String getDescriptionId(@NotNull ItemStack stack) {
        return ModItems.TEST_TUBE.get().getDescriptionId();
    }

    @Override
    public EssenceType getType(ItemStack stack) {
        return EssenceType.BLOOD;
    }
}

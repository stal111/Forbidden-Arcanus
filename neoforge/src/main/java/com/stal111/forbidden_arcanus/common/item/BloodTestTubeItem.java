package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.world.item.Item;

/**
 * @author stal111
 * @since 2021-07-08
 */
public class BloodTestTubeItem extends Item {

    public static final int MAX_BLOOD = 3000;

    public static final EssenceStorage DEFAULT_DATA = new EssenceStorage(EssenceValue.createEmpty(EssenceType.BLOOD), MAX_BLOOD, true);

    public BloodTestTubeItem(Properties properties) {
        super(properties);
    }

    //TODO
//    @NotNull
//    @Override
//    public String getDescriptionId(@NotNull ItemStack stack) {
//        return ModItems.TEST_TUBE.get().getDescriptionId();
//    }
}

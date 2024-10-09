package com.stal111.forbidden_arcanus.common.item.bucket;

import com.google.common.collect.ImmutableMap;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 18.12.2023
 */
public record BucketFamily(Map<Fluid, Supplier<? extends Item>> fluidBuckets, Supplier<? extends Item> milkBucket, Supplier<? extends Item> powderSnowBucket) {

    public static final BucketFamily EDELWOOD_BUCKET = new BucketFamily(ImmutableMap.of(
            Fluids.EMPTY, ModItems.EDELWOOD_BUCKET,
            Fluids.WATER, ModItems.EDELWOOD_WATER_BUCKET,
            Fluids.LAVA, ModItems.EDELWOOD_LAVA_BUCKET
    ), ModItems.EDELWOOD_MILK_BUCKET, ModItems.EDELWOOD_POWDER_SNOW_BUCKET);
}

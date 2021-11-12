package com.stal111.forbidden_arcanus.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import net.minecraft.world.item.Item.Properties;

public class ModSpawnEggItem<T extends Entity> extends SpawnEggItem {

    private final Supplier<EntityType<T>> type;

    public ModSpawnEggItem(Supplier<EntityType<T>> type, int primaryColor, int secondaryColor, Properties builder) {
        super(null, primaryColor, secondaryColor, builder);
        this.type = type;
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundTag p_208076_1_) {
        return type.get();
    }
}

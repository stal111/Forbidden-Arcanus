package com.stal111.forbidden_arcanus.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ModSpawnEggItem<T extends Entity> extends SpawnEggItem {

    private final Supplier<EntityType<T>> type;

    public ModSpawnEggItem(Supplier<EntityType<T>> type, int primaryColor, int secondaryColor, Properties builder) {
        super(null, primaryColor, secondaryColor, builder);
        this.type = type;
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_) {
        return type.get();
    }
}

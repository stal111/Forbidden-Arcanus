package com.stal111.forbidden_arcanus.common.entity;

import com.stal111.forbidden_arcanus.core.init.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2022-06-25
 */
public class ModChestBoat extends ChestBoat implements CustomBoat {

    private static final EntityDataAccessor<Integer> DATA_ID_WOOD_TYPE = SynchedEntityData.defineId(ModChestBoat.class, EntityDataSerializers.INT);

    public ModChestBoat(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public ModChestBoat(Level level, double x, double y, double z) {
        this(ModEntities.CHEST_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_WOOD_TYPE, 0);
    }

    @Override
    protected void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Type", 8)) {
            this.setWoodType(ModBoat.Type.byName(tag.getString("Type")));
        }
    }

    @Override
    protected void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Type", this.getWoodType().getSerializedName());
    }

    @Nonnull
    @Override
    public Item getDropItem() {
        return this.getWoodType().getChestBoatItem().get();
    }

    @Override
    public ModBoat.Type getWoodType() {
        return ModBoat.Type.byId(this.entityData.get(DATA_ID_WOOD_TYPE));
    }

    @Override
    public void setWoodType(ModBoat.Type type) {
        this.entityData.set(DATA_ID_WOOD_TYPE, type.ordinal());
    }

    @Nonnull
    @Override
    protected Component getTypeName() {
        return EntityType.CHEST_BOAT.getDescription();
    }
}

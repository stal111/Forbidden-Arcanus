package com.stal111.forbidden_arcanus.common.entity.darktrader;

import com.stal111.forbidden_arcanus.core.init.other.ModEntityDataSerializers;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-08-11
 */
public class DarkTrader extends Mob implements VariantHolder<DarkTraderVariant> {

    private static final EntityDataAccessor<DarkTraderVariant> DATA_VARIANT_ID = SynchedEntityData.defineId(DarkTrader.class, ModEntityDataSerializers.DARK_TRADER_VARIANT.get());

    public DarkTrader(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT_ID, DarkTraderVariant.BROOK.get());
    }

    @Override
    public void setVariant(@NotNull DarkTraderVariant variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    @Override
    public @NotNull DarkTraderVariant getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putString("variant", FARegistries.DARK_TRADER_VARIANT_REGISTRY.get().getKey(this.getVariant()).toString());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        DarkTraderVariant variant = FARegistries.DARK_TRADER_VARIANT_REGISTRY.get().getValue(ResourceLocation.tryParse(tag.getString("variant")));

        if (variant != null) {
            this.setVariant(variant);
        }
    }
}

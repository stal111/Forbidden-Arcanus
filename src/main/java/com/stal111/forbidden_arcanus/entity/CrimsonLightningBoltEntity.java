package com.stal111.forbidden_arcanus.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

/**
 * Crimson Lightning Bolt Entity
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.entity.CrimsonLightningBoltEntity
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-11
 */
public class CrimsonLightningBoltEntity extends LightningBoltEntity {

    public CrimsonLightningBoltEntity(EntityType<? extends LightningBoltEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

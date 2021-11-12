package com.stal111.forbidden_arcanus.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;

/**
 * Crimson Lightning Bolt Entity
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.entity.CrimsonLightningBoltEntity
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-11
 */
public class CrimsonLightningBoltEntity extends LightningBolt {

    public CrimsonLightningBoltEntity(EntityType<? extends LightningBolt> entityType, Level world) {
        super(entityType, world);
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

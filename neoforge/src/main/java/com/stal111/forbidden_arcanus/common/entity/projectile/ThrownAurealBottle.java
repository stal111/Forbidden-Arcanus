package com.stal111.forbidden_arcanus.common.entity.projectile;

import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.entity.lostsoul.AbstractLostSoul;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.network.clientbound.SpawnParticlePayload;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author stal111
 * @since 2022-10-22
 */
public class ThrownAurealBottle extends ThrowableItemProjectile {

    public ThrownAurealBottle(EntityType<? extends ThrownAurealBottle> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownAurealBottle(Level level, LivingEntity shooter, ItemStack stack) {
        super(ModEntities.AUREAL_BOTTLE.get(), shooter, level, stack);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.055F;
    }

    @Nonnull
    @Override
    protected Item getDefaultItem() {
        return ModItems.SPLASH_AUREAL_BOTTLE.get();
    }

    @Override
    protected void onHit(@Nonnull HitResult result) {
        super.onHit(result);

        if (this.level() instanceof ServerLevel serverLevel) {
            this.applySplash();

            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, new ChunkPos(this.blockPosition()), new SpawnParticlePayload(this.getX(), this.getY(), this.getZ(), 1));

            this.discard();
        }
    }

    private void applySplash() {
        AABB aabb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, aabb);

        if (list.isEmpty()) {
            return;
        }

        for (LivingEntity entity : list) {
            double distance = this.distanceToSqr(entity);

            if (distance < 16.0D) {
                if (entity instanceof ServerPlayer player) {
                    EssenceHelper.getEssenceProvider(player).ifPresent(provider -> {
                        provider.updateAmount(EssenceType.AUREAL, amount -> amount + 30);
                    });
                }

                if (AurealHelper.canEntityBeAureal(entity) && !entity.getPersistentData().getBooleanOr("aureal", false)) {
                    entity.getPersistentData().putBoolean("aureal", true);
                }

                if (entity instanceof AbstractLostSoul lostSoul) {
                    if (lostSoul.getType() == ModEntities.CORRUPT_LOST_SOUL.get()) {
                        lostSoul.convertTo(ModEntities.LOST_SOUL.get(), ConversionParams.single(lostSoul, true, true), converted -> {
                            EventHooks.onLivingConvert(lostSoul, converted);
                        });
                    } else if (lostSoul.getType() == ModEntities.LOST_SOUL.get()) {
                        lostSoul.convertTo(ModEntities.ENCHANTED_LOST_SOUL.get(), ConversionParams.single(lostSoul, true, true), converted -> {
                            EventHooks.onLivingConvert(lostSoul, converted);
                        });
                    }
                }
            }
        }
    }
}

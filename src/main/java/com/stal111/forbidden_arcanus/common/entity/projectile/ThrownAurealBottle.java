package com.stal111.forbidden_arcanus.common.entity.projectile;

import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import com.stal111.forbidden_arcanus.common.entity.lostsoul.LostSoul;
import com.stal111.forbidden_arcanus.common.network.NetworkHandler;
import com.stal111.forbidden_arcanus.common.network.clientbound.AddThrownAurealBottleParticle;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

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

    public ThrownAurealBottle(Level level, LivingEntity shooter) {
        super(ModEntities.AUREAL_BOTTLE.get(), shooter, level);
    }

    @Override
    protected float getGravity() {
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

        if (!this.level.isClientSide()) {
            this.applySplash();

            NetworkHandler.sendToTrackingChunk(this.level.getChunkAt(this.blockPosition()), new AddThrownAurealBottleParticle(this.getX(), this.getY(), this.getZ()));

            this.discard();
        }
    }

    private void applySplash() {
        AABB aabb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, aabb);

        if (list.isEmpty()) {
            return;
        }

        for (LivingEntity entity : list) {
            double distance = this.distanceToSqr(entity);

            if (distance < 16.0D) {
                if (entity instanceof Player player) {
                    AurealHelper.increaseAureal(player, 30);
                }

                if (AurealHelper.canEntityBeAureal(entity) && !entity.getPersistentData().getBoolean("aureal")) {
                    entity.getPersistentData().putBoolean("aureal", true);
                }

                if (entity instanceof LostSoul lostSoul) {
                    LostSoul.Variant variant = lostSoul.getVariant();

                    if (variant == LostSoul.Variant.CORRUPT_LOST_SOUL) {
                        lostSoul.setVariant(LostSoul.Variant.LOST_SOUL);
                    } else {
                        lostSoul.setVariant(LostSoul.Variant.ENCHANTED_LOST_SOUL);
                    }
                }
            }
        }
    }
}

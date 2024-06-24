package com.stal111.forbidden_arcanus.common.entity.projectile;

import com.stal111.forbidden_arcanus.core.config.ItemConfig;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Boom Arrow <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.entity.projectile.BoomArrow
 *
 * @author stal111
 * @version 1.18.1 - 2.0.1
 * @since 2021-12-16
 */
public class BoomArrow extends AbstractArrow {

    public BoomArrow(EntityType<? extends BoomArrow> entityType, Level level) {
        super(entityType, level);
    }

    public BoomArrow(Level level, LivingEntity shooter, ItemStack pickupStack, @Nullable ItemStack stack) {
        super(ModEntities.BOOM_ARROW.get(), shooter, level, pickupStack, stack);
    }

    public BoomArrow(Level level, double x, double y, double z, ItemStack pickupStack, @Nullable ItemStack stack) {
        super(ModEntities.BOOM_ARROW.get(), x, y, z, level, pickupStack, stack);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide() && !this.inGround) {
            this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.BOOM_ARROW.get());
    }

    @Override
    protected void doPostHurtEffects(@Nonnull LivingEntity entity) {
        super.doPostHurtEffects(entity);
        this.level().explode(this, entity.getX(), entity.getY(), entity.getZ(), ItemConfig.BOOM_ARROW_EXPLOSION_RADIUS.get(), ItemConfig.BOOM_ARROW_BLOCK_DAMAGE.get() ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE);
    }
}

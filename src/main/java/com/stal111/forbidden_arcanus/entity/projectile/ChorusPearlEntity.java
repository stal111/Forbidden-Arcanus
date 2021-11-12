package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class ChorusPearlEntity extends ThrowableItemProjectile {

    public ChorusPearlEntity(Level world, double x, double y, double z) {
        super(ModEntities.CHORUS_PEARL.get(), x, y, z, world);
    }

    public ChorusPearlEntity(Level world, LivingEntity thrower) {
        super(ModEntities.CHORUS_PEARL.get(), thrower, world);
    }

    public ChorusPearlEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void onHit(HitResult result) {
        if (this.level.isClientSide()) {
            return;
        }

        if (result.getType() == HitResult.Type.ENTITY) {
            if (!(((EntityHitResult) result).getEntity() instanceof LivingEntity)) {
                return;
            }
            LivingEntity entity = (LivingEntity) ((EntityHitResult) result).getEntity();

            if (entity instanceof Player) {
                if (((Player) entity).getAbilities().instabuild) {
                    this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.CHORUS_PEARL.get())));
                    this.level.broadcastEntityEvent(this, (byte) 3);
                    this.remove(RemovalReason.DISCARDED);
                    return;
                }
            }
            entity.hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);

            double d0 = entity.getX();
            double d1 = entity.getY();
            double d2 = entity.getZ();

            for (int i = 0; i < 16; ++i) {
                double d3 = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * 56.0D;
                double d4 = Mth.clamp(entity.getY() + (double) (entity.getRandom().nextInt(16) - 8), 0.0D, 256 - 1);
                double d5 = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * 56.0D;
                if (entity.isPassenger()) {
                    entity.stopRiding();
                }

                if (entity.randomTeleport(d3, d4, d5, true)) {
                    level.playSound(null, d0, d1, d2, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    entity.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                    break;
                }

            }
        } else {
            this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.CHORUS_PEARL.get())));
        }
        this.level.broadcastEntityEvent(this, (byte) 3);
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.CHORUS_PEARL.get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

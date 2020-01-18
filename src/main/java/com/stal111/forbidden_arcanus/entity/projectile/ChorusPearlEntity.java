package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages.SpawnEntity;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class ChorusPearlEntity extends ProjectileItemEntity {

    @SuppressWarnings("unchecked")
    public ChorusPearlEntity(World world) {
        super((EntityType<? extends ProjectileItemEntity>) ModEntities.CHORUS_PEARL.getEntityType(), world);
    }

    @SuppressWarnings("unchecked")
    public ChorusPearlEntity(World world, double x, double y, double z) {
        super((EntityType<? extends ProjectileItemEntity>) ModEntities.CHORUS_PEARL.getEntityType(), x, y, z, world);
    }

    @SuppressWarnings("unchecked")
    public ChorusPearlEntity(World world, LivingEntity thrower) {
        super((EntityType<? extends ProjectileItemEntity>) ModEntities.CHORUS_PEARL.getEntityType(), thrower, world);
    }

    @SuppressWarnings("unchecked")
    public ChorusPearlEntity(SpawnEntity packet, World world) {
        super((EntityType<? extends ProjectileItemEntity>) ModEntities.CHORUS_PEARL.getEntityType(), world);
    }

    public ChorusPearlEntity(EntityType<? extends ProjectileItemEntity> entityEntityType, World world) {
        super(entityEntityType, world);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.getType() == RayTraceResult.Type.ENTITY) {
                LivingEntity entity = (LivingEntity) ((EntityRayTraceResult) result).getEntity();
                if (entity instanceof PlayerEntity) {
                    if (((PlayerEntity) entity).abilities.isCreativeMode) {
                        this.world.addEntity(new ItemEntity(this.world, this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_(), new ItemStack(ModItems.CHORUS_PEARL.getItem())));
                        this.world.setEntityState(this, (byte)3);
                        this.remove();
                        return;
                    }
                }
                entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);

                double d0 = entity.func_226277_ct_();
                double d1 = entity.func_226278_cu_();
                double d2 = entity.func_226281_cx_();

                for (int i = 0; i < 16; ++i) {
                    double d3 = entity.func_226277_ct_() + (entity.getRNG().nextDouble() - 0.5D) * 56.0D;
                    double d4 = MathHelper.clamp(entity.func_226278_cu_() + (double) (entity.getRNG().nextInt(16) - 8), 0.0D, world.getActualHeight() - 1);
                    double d5 = entity.func_226281_cx_() + (entity.getRNG().nextDouble() - 0.5D) * 56.0D;
                    if (entity.isPassenger()) {
                        entity.stopRiding();
                    }

                    if (entity.attemptTeleport(d3, d4, d5, true)) {
                        world.playSound(null, d0, d1, d2, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        entity.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                        break;
                    }

                }
            } else {
                this.world.addEntity(new ItemEntity(this.world, this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_(), new ItemStack(ModItems.CHORUS_PEARL.getItem())));
            }
			this.world.setEntityState(this, (byte)3);
			this.remove();
        }
    }

    @Override
    protected Item func_213885_i() {
        return ModItems.CHORUS_PEARL.getItem();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

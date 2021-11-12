package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.config.ItemConfig;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.FMLPlayMessages;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class BoomArrowEntity extends AbstractArrow {

    @SuppressWarnings("unchecked")
    public BoomArrowEntity(Level world) {
        super((EntityType<? extends AbstractArrow>) ModEntities.BOOM_ARROW.get(), world);
    }

    @SuppressWarnings("unchecked")
    public BoomArrowEntity(double p_i48547_2_, double p_i48547_4_, double p_i48547_6_, Level world) {
        super((EntityType<? extends AbstractArrow>) ModEntities.BOOM_ARROW.get(), p_i48547_2_, p_i48547_4_, p_i48547_6_, world);
    }

    @SuppressWarnings("unchecked")
    public BoomArrowEntity(LivingEntity entity, Level world) {
        super((EntityType<? extends AbstractArrow>) ModEntities.BOOM_ARROW.get(), entity, world);
    }

    @SuppressWarnings("unchecked")
    public BoomArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, Level world) {
        super((EntityType<? extends AbstractArrow>) ModEntities.BOOM_ARROW.get(), world);
    }

    public BoomArrowEntity(EntityType<? extends BoomArrowEntity> entityEntityType, Level world) {
        super((EntityType<? extends AbstractArrow>) ModEntities.BOOM_ARROW.get(), world);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.BOOM_ARROW.get());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        Vec3 vec3d1 = this.getDeltaMovement();
        if (this.level.isClientSide() && this.random.nextDouble() >= 0.5) {
            this.level.addParticle(ParticleTypes.SMOKE, this.getX() - vec3d1.x, this.getY() - vec3d1.y + 0.05D, this.getZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
        }
        super.tick();
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        this.level.explode(this, entity.getX(), entity.getY(), entity.getZ(), ItemConfig.BOOM_ARROW_EXPLOSION_RADIUS.get(), ItemConfig.BOOM_ARROW_BLOCK_DAMAGE.get() ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
        super.doPostHurtEffects(entity);
    }
}

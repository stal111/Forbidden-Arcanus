package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.config.ItemConfig;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class BoomArrowEntity extends AbstractArrowEntity {

    @SuppressWarnings("unchecked")
    public BoomArrowEntity(World world) {
        super((EntityType<? extends AbstractArrowEntity>) ModEntities.BOOM_ARROW.get(), world);
    }

    @SuppressWarnings("unchecked")
    public BoomArrowEntity(double p_i48547_2_, double p_i48547_4_, double p_i48547_6_, World world) {
        super((EntityType<? extends AbstractArrowEntity>) ModEntities.BOOM_ARROW.get(), p_i48547_2_, p_i48547_4_, p_i48547_6_, world);
    }

    @SuppressWarnings("unchecked")
    public BoomArrowEntity(LivingEntity entity, World world) {
        super((EntityType<? extends AbstractArrowEntity>) ModEntities.BOOM_ARROW.get(), entity, world);
    }

    @SuppressWarnings("unchecked")
    public BoomArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super((EntityType<? extends AbstractArrowEntity>) ModEntities.BOOM_ARROW.get(), world);
    }

    public BoomArrowEntity(EntityType<Entity> entityEntityType, World world) {
        super((EntityType<? extends AbstractArrowEntity>) ModEntities.BOOM_ARROW.get(), world);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ModItems.BOOM_ARROW.get());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        Vector3d vec3d1 = this.getMotion();
        if (this.world.isRemote() && this.rand.nextDouble() >= 0.5) {
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y + 0.05D, this.getPosZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
        }
        super.tick();
    }

    @Override
    protected void arrowHit(LivingEntity entity) {
        this.world.createExplosion(this, entity.getPosX(), entity.getPosY(), entity.getPosZ(), ItemConfig.BOOM_ARROW_EXPLOSION_RADIUS.get(), ItemConfig.BOOM_ARROW_BLOCK_DAMAGE.get() ? Explosion.Mode.BREAK : Explosion.Mode.NONE);
        super.arrowHit(entity);
    }
}

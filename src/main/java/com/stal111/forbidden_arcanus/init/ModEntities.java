package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.projectile.BoomArrow;
import com.stal111.forbidden_arcanus.common.entity.projectile.DracoArcanusArrow;
import com.stal111.forbidden_arcanus.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.common.entity.projectile.EnergyBall;
import com.stal111.forbidden_arcanus.entity.projectile.SeedBulletEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Mod Entities
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.ModEntities
 *
 * @author stal111
 * @version 2.0.0
 */
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<EntityType<SeedBulletEntity>> SEED_BULLET = register("seed_bullet", EntityType.Builder.<SeedBulletEntity>of(SeedBulletEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).setTrackingRange(64));
    public static final RegistryObject<EntityType<EnergyBall>> ENERGY_BALL = register("energy_ball", EntityType.Builder.<EnergyBall>of(EnergyBall::new, MobCategory.MISC).sized(1.0F, 1.0F).setTrackingRange(64));
    public static final RegistryObject<EntityType<BoomArrow>> BOOM_ARROW = register("boom_arrow", EntityType.Builder.<BoomArrow>of(BoomArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<DracoArcanusArrow>> DRACO_ARCANUS_ARROW = register("draco_arcanus_arrow", EntityType.Builder.<DracoArcanusArrow>of(DracoArcanusArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<CrimsonLightningBoltEntity>> CRIMSON_LIGHTNING_BOLT = register("crimson_lightning_bolt", EntityType.Builder.of(CrimsonLightningBoltEntity::new, MobCategory.MISC).sized(0.0F, 0.0F).setTrackingRange(16).updateInterval(Integer.MAX_VALUE));

    public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        ResourceLocation location = new ResourceLocation(ForbiddenArcanus.MOD_ID, name);
        return ENTITY_TYPES.register(name, () -> builder.build(location.toString()));
    }

    @OnlyIn(Dist.CLIENT)
    public static void initModels() {
        //TODO
//        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SEED_BULLET.get(), ModSpriteRenderer::new);
//        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ENERGY_BALL.get(), EnergyBallRender::new);
//        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), CrimsonLightningBoltRenderer::new);
    }
}

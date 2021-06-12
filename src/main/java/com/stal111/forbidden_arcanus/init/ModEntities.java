package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.renderers.CrimsonLightningBoltRenderer;
import com.stal111.forbidden_arcanus.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.entity.PixieEntity;
import com.stal111.forbidden_arcanus.entity.projectile.*;
import com.stal111.forbidden_arcanus.entity.render.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
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

    public static final RegistryObject<EntityType<ChorusPearlEntity>> CHORUS_PEARL = register("chorus_pearl", EntityType.Builder.<ChorusPearlEntity>create(ChorusPearlEntity::new, EntityClassification.MISC).size(1.0F, 1.0F).setTrackingRange(64));
    public static final RegistryObject<EntityType<SeedBulletEntity>> SEED_BULLET = register("seed_bullet", EntityType.Builder.<SeedBulletEntity>create(SeedBulletEntity::new, EntityClassification.MISC).size(1.0F, 1.0F).setTrackingRange(64));
    public static final RegistryObject<EntityType<EnergyBallEntity>> ENERGY_BALL = register("energy_ball", EntityType.Builder.<EnergyBallEntity>create(EnergyBallEntity::new, EntityClassification.MISC).size(1.0F, 1.0F).setTrackingRange(64));
    public static final RegistryObject<EntityType<BoomArrowEntity>> BOOM_ARROW = register("boom_arrow", EntityType.Builder.<BoomArrowEntity>create(BoomArrowEntity::new, EntityClassification.MISC).size(1.0F, 1.0F).setTrackingRange(64));
    public static final RegistryObject<EntityType<DracoArcanusArrowEntity>> DRACO_ARCANUS_ARROW = register("draco_arcanus_arrow", EntityType.Builder.<DracoArcanusArrowEntity>create(DracoArcanusArrowEntity::new, EntityClassification.MISC).size(1.0F, 1.0F).setTrackingRange(64));
    public static final RegistryObject<EntityType<PixieEntity>> PIXIE = register("pixie", EntityType.Builder.<PixieEntity>create(PixieEntity::new, EntityClassification.AMBIENT).size(1.0F, 1.0F).setTrackingRange(64));
    public static final RegistryObject<EntityType<CrimsonLightningBoltEntity>> CRIMSON_LIGHTNING_BOLT = register("crimson_lightning_bolt", EntityType.Builder.create(CrimsonLightningBoltEntity::new, EntityClassification.MISC).size(0.0F, 0.0F).setTrackingRange(16).func_233608_b_(Integer.MAX_VALUE));

    public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        ResourceLocation location = new ResourceLocation(ForbiddenArcanus.MOD_ID, name);
        return ENTITY_TYPES.register(name, () -> builder.build(location.toString()));
    }

    @OnlyIn(Dist.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CHORUS_PEARL.get(), ModSpriteRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SEED_BULLET.get(), ModSpriteRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ENERGY_BALL.get(), EnergyBallRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BOOM_ARROW.get(), BoomArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.DRACO_ARCANUS_ARROW.get(), DracoArcanusArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.PIXIE.get(), PixieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), CrimsonLightningBoltRenderer::new);
    }
}

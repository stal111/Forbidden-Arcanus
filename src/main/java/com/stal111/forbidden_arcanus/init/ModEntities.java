package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.entity.PixieEntity;
import com.stal111.forbidden_arcanus.entity.projectile.*;
import com.stal111.forbidden_arcanus.entity.render.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<EntityType<?>> CHORUS_PEARL = register("chorus_pearl", ChorusPearlEntity::new, EntityClassification.MISC, 1, 1);
    public static final RegistryObject<EntityType<?>> SEED_BULLET = register("seed_bullet", SeedBulletEntity::new, EntityClassification.MISC, 1, 1);
    public static final RegistryObject<EntityType<?>> ENERGY_BALL = register("energy_ball", EnergyBallEntity::new, EntityClassification.MISC, 1, 1);
    public static final RegistryObject<EntityType<?>> BOOM_ARROW = register("boom_arrow", BoomArrowEntity::new, EntityClassification.MISC, 1, 1);
    public static final RegistryObject<EntityType<?>> DRACO_ARCANUS_ARROW = register("draco_arcanus_arrow", DracoArcanusArrowEntity::new, EntityClassification.MISC, 1, 1);
    public static final RegistryObject<EntityType<?>> PIXIE = register("pixie", PixieEntity::new, EntityClassification.AMBIENT, 1, 1);

    public static RegistryObject<EntityType<?>> register(String name, EntityType.IFactory<?> factory, EntityClassification entityClassification, float width, float height) {
        ResourceLocation location = new ResourceLocation(ForbiddenArcanus.MOD_ID, name);
        EntityType<?> entity = EntityType.Builder.create(factory, entityClassification)
                .size(width, height)
                .setTrackingRange(64)
                .setShouldReceiveVelocityUpdates(true)
                .setUpdateInterval(3)
                .build(location.toString()
                );
        return ENTITY_TYPES.register(name, () -> entity);
    }

    @OnlyIn(Dist.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CHORUS_PEARL.get(), ModSpriteRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SEED_BULLET.get(), ModSpriteRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<EnergyBallEntity>) ModEntities.ENERGY_BALL.get(), EnergyBallRender::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<BoomArrowEntity>) ModEntities.BOOM_ARROW.get(), BoomArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<DracoArcanusArrowEntity>) ModEntities.DRACO_ARCANUS_ARROW.get(), DracoArcanusArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<PixieEntity>) ModEntities.PIXIE.get(), PixieRenderer::new);
    }
}

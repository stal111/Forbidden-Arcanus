package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.projectile.*;
import com.stal111.forbidden_arcanus.entity.render.BoomArrowRender;
import com.stal111.forbidden_arcanus.entity.render.DracoArcanusArrowRender;
import com.stal111.forbidden_arcanus.entity.render.EnergyBallRender;
import com.stal111.forbidden_arcanus.entity.render.ModSpriteRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public enum ModEntities {
    CHORUS_PEARL(EntityType.Builder.create(EntityClassification.MISC).size(1, 1).setCustomClientFactory(ChorusPearlEntity::new).build(Main.MOD_ID + "")),
    SEED_BULLET(EntityType.Builder.create(EntityClassification.MISC).size(1, 1).setCustomClientFactory(SeedBulletEntity::new).build(Main.MOD_ID + "")),
    ENERGY_BALL(EntityType.Builder.create(EntityClassification.MISC).size(1, 1).setCustomClientFactory(EnergyBallEntity::new).build(Main.MOD_ID + "")),
    BOOM_ARROW(EntityType.Builder.create(EntityClassification.MISC).size(1, 1).setCustomClientFactory(BoomArrowEntity::new).build("")),
    DRACO_ARCANUS_ARROW(EntityType.Builder.create(EntityClassification.MISC).size(1, 1).setCustomClientFactory(DracoArcanusArrowEntity::new).build(""));

    private final EntityType<?> entityType;

    ModEntities(EntityType<?> entityType) {
        this.entityType = entityType;
    }

    public String getName() {
        return String.valueOf(this).toLowerCase();
    }

    public EntityType<?> get() {
        if (entityType.getRegistryName() == null) {
            entityType.setRegistryName(Main.MOD_ID, getName());
        }
        return entityType;
    }

    @OnlyIn(Dist.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CHORUS_PEARL.get(), ModSpriteRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SEED_BULLET.get(), ModSpriteRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<EnergyBallEntity>) ModEntities.ENERGY_BALL.get(), EnergyBallRender::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<BoomArrowEntity>) ModEntities.BOOM_ARROW.get(), BoomArrowRender::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<DracoArcanusArrowEntity>) ModEntities.DRACO_ARCANUS_ARROW.get(), DracoArcanusArrowRender::new);

    }
}

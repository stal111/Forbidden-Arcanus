package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.projectile.*;
import com.stal111.forbidden_arcanus.entity.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public enum ModEntities {
    CHORUS_PEARL(EntityType.Builder.create(EntityClassification.MISC).size(1, 1).setCustomClientFactory(ChorusPearlEntity::new).build(Main.MOD_ID + ":chorus_pearl")),
    SEED_BULLET(EntityType.Builder.create(EntityClassification.MISC).size(1, 1).setCustomClientFactory(SeedBulletEntity::new).build(Main.MOD_ID + ":seed_bullet")),
    ENERGY_BALL(EntityType.Builder.create(EntityClassification.MISC).size(1, 1).setCustomClientFactory(EnergyBallEntity::new).build(Main.MOD_ID + ":energy_ball")),
    BOOM_ARROW(EntityType.Builder.create(EntityClassification.MISC).size(1, 1).setCustomClientFactory(BoomArrowEntity::new).build("")),
    DRACO_ARCANUS_ARROW(EntityType.Builder.create(EntityClassification.MISC).size(1, 1).setCustomClientFactory(DracoArcanusArrowEntity::new).build("")),
    FROZEN_ARROW(EntityType.Builder.create(EntityClassification.MISC).size(1, 1).setCustomClientFactory(DracoArcanusArrowEntity::new).build(""));

    private final EntityType<?> entityType;

    ModEntities(EntityType<?> entityType) {
        this.entityType = entityType;
    }

    public String getName() {
        return String.valueOf(this).toLowerCase();
    }

    public EntityType<?> getEntityType() {
        if (entityType.getRegistryName() == null) {
            entityType.setRegistryName(Main.MOD_ID, getName());
        }
        return entityType;
    }

    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(CHORUS_PEARL.getEntityType(), ModSpriteRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SEED_BULLET.getEntityType(), ModSpriteRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<EnergyBallEntity>) ENERGY_BALL.getEntityType(), EnergyBallRender::new);
        Minecraft.getInstance().getRenderManager().func_229087_a_((EntityType<BoomArrowEntity>) BOOM_ARROW.getEntityType(), new BoomArrowRender(Minecraft.getInstance().getRenderManager()));
        Minecraft.getInstance().getRenderManager().func_229087_a_((EntityType<DracoArcanusArrowEntity>) DRACO_ARCANUS_ARROW.getEntityType(), new DracoArcanusArrowRender(Minecraft.getInstance().getRenderManager()));
        Minecraft.getInstance().getRenderManager().func_229087_a_((EntityType<FrozenArrowEntity>) FROZEN_ARROW.getEntityType(), new FrozenArrowRender(Minecraft.getInstance().getRenderManager()));

    }
}

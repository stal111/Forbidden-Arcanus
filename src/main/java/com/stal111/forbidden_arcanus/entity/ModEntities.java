package com.stal111.forbidden_arcanus.entity;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.projectile.ChorusPearlEntity;
import com.stal111.forbidden_arcanus.entity.projectile.SeedBulletEntity;

//import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;
//import com.stal111.forbidden_arcanus.entity.render.EnergyBallRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MOD_ID)
public class ModEntities {

	public static final EntityType<?>
			chorus_pearl = null,
			seed_bullet = null,
			energy_ball = null;

	public static void register(RegistryEvent.Register<EntityType<?>> registry) {
		registerAll(registry,
				EntityType.Builder.<ChorusPearlEntity>create(EntityClassification.MISC).size(1.0F, 1.0F).setTrackingRange(256).setCustomClientFactory(ChorusPearlEntity::new).build(Main.MOD_ID + ":chorus_pearl").setRegistryName(Main.MOD_ID, "chorus_pearl"),
		EntityType.Builder.<SeedBulletEntity>create(EntityClassification.MISC).size(1.0F, 1.0F).setTrackingRange(256).setCustomClientFactory(SeedBulletEntity::new).build(Main.MOD_ID + ":seed_bullet").setRegistryName(Main.MOD_ID, "seed_bullet"));
//				EntityType.Builder.create(EnergyBallEntity.class, EnergyBallEntity::new).tracker(256, 1, false).build(Main.MODID + ":energy_ball").setRegistryName(Main.MODID, "energy_ball"));
	}

	@OnlyIn(Dist.CLIENT)
	public static void initModels() {
		RenderingRegistry.registerEntityRenderingHandler(ChorusPearlEntity.class, manager -> new SpriteRenderer<ChorusPearlEntity>(manager, Minecraft.getInstance().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(SeedBulletEntity.class, manager -> new SpriteRenderer<SeedBulletEntity>(manager, Minecraft.getInstance().getItemRenderer()));
//		RenderingRegistry.registerEntityRenderingHandler(EnergyBallEntity.class, EnergyBallRender.FACTORY);
	}
	
	public static void registerAll(RegistryEvent.Register<EntityType<?>> registry, EntityType<?>... entities) {
		for (EntityType<?> entity : entities) {
			registry.getRegistry().register(entity);
		}
	}

}

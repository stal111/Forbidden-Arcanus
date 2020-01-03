package com.stal111.forbidden_arcanus.entity;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.projectile.ChorusPearlEntity;
import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;
import com.stal111.forbidden_arcanus.entity.projectile.SeedBulletEntity;

//import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;
//import com.stal111.forbidden_arcanus.entity.render.EnergyBallRender;
import com.stal111.forbidden_arcanus.entity.render.EnergyBallRender;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MOD_ID)
public class ModEntities {

	public static final EntityType<ChorusPearlEntity>
			chorus_pearl = null;
	public static final EntityType<SeedBulletEntity>
			seed_bullet = null;
	public static final EntityType<EnergyBallEntity>
			energy_ball = null;

	public static void register(RegistryEvent.Register<EntityType<?>> registry) {
		registerAll(registry,
				EntityType.Builder.<ChorusPearlEntity>create(EntityClassification.MISC).size(1.0F, 1.0F).setTrackingRange(256).setCustomClientFactory(ChorusPearlEntity::new).build(Main.MOD_ID + ":chorus_pearl").setRegistryName(Main.MOD_ID, "chorus_pearl"),
				EntityType.Builder.<SeedBulletEntity>create(EntityClassification.MISC).size(1.0F, 1.0F).setTrackingRange(256).setCustomClientFactory(SeedBulletEntity::new).build(Main.MOD_ID + ":seed_bullet").setRegistryName(Main.MOD_ID, "seed_bullet"),
				EntityType.Builder.<EnergyBallEntity>create(EntityClassification.MISC).size(1.0F, 1.0F).setTrackingRange(256).setCustomClientFactory(EnergyBallEntity::new).build(Main.MOD_ID + ":energy_ball").setRegistryName(Main.MOD_ID, "energy_ball"));
	}

	@OnlyIn(Dist.CLIENT)
	public static void initModels() {
		Minecraft.getInstance().getRenderManager().func_229087_a_(chorus_pearl, new SpriteRenderer<>(Minecraft.getInstance().getRenderManager(), Minecraft.getInstance().getItemRenderer()));
		Minecraft.getInstance().getRenderManager().func_229087_a_(seed_bullet, new SpriteRenderer<>(Minecraft.getInstance().getRenderManager(), Minecraft.getInstance().getItemRenderer()));
		Minecraft.getInstance().getRenderManager().func_229087_a_(energy_ball, new EnergyBallRender(Minecraft.getInstance().getRenderManager()));
	}
	
	public static void registerAll(RegistryEvent.Register<EntityType<?>> registry, EntityType<?>... entities) {
		for (EntityType<?> entity : entities) {
			registry.getRegistry().register(entity);
		}
	}
}

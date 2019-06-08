package com.stal111.forbidden_arcanus.entity;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.projectile.ChorusPearlEntity;
import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;
import com.stal111.forbidden_arcanus.entity.render.EnergyBallRender;
import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MODID)
public class ModEntities {

	public static final EntityType<?>
			chorus_pearl = null,
			energy_ball = null;
//
//	public static void register(RegistryEvent.Register<EntityType<?>> registry) {
//		registerAll(registry,
//				EntityType.Builder.create(ChorusPearlEntity.class, ChorusPearlEntity::new).tracker(256, 10, false).build(Main.MODID + ":chorus_pearl").setRegistryName(Main.MODID, "chorus_pearl"),
//				EntityType.Builder.create(EnergyBallEntity.class, EnergyBallEntity::new).tracker(256, 1, false).build(Main.MODID + ":energy_ball").setRegistryName(Main.MODID, "energy_ball"));
//	}
//
//	public static void initModels() {
//		RenderingRegistry.registerEntityRenderingHandler(ChorusPearlEntity.class, manager -> new RenderSprite<>(manager, ModItems.chorus_pearl, Minecraft.getInstance().getItemRenderer()));
//		RenderingRegistry.registerEntityRenderingHandler(EnergyBallEntity.class, EnergyBallRender.FACTORY);
//	}
//	
//	public static void registerAll(RegistryEvent.Register<EntityType<?>> registry, EntityType<?>... entities) {
//		for (EntityType<?> entity : entities) {
//			registry.getRegistry().register(entity);
//		}
//	}

}

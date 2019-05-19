package com.stal111.forbidden_arcanus.entity;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.projectile.ChorusPearlEntity;
import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSprite;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModEntities {

	public static EntityType<ChorusPearlEntity> TYPE_CHORUS_PEARL;

	public static void register(RegistryEvent.Register<EntityType<?>> registry) {
		
		TYPE_CHORUS_PEARL = EntityType.Builder.create(ChorusPearlEntity.class, ChorusPearlEntity::new).tracker(256, 10, false)
				.build(Main.MODID + ":chorus_pearl");
		TYPE_CHORUS_PEARL.setRegistryName(Main.MODID, "chorus_pearl");
		registry.getRegistry().register(TYPE_CHORUS_PEARL);
	}

	public static void initModels() {
		Main.LOGGER.info("ENTITY MODELS!");
		RenderingRegistry.registerEntityRenderingHandler(ChorusPearlEntity.class, manager -> new RenderSprite<>(manager, ModItems.chorus_pearl, Minecraft.getInstance().getItemRenderer()));
	}

}

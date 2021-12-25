package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ForbiddenArcanus.MOD_ID)
public class ModSounds {
	
	public static final SoundEvent
			dark_bolt_launch = null,
			dark_bolt_hit = null,
			pixi_activated = null,
			runic_tenebris_core_activated = null,
			runic_tenebris_core_ambient = null;
	
	public static void register(RegistryEvent.Register<SoundEvent> registry) {
		registerAll(registry,
				new SoundEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "dark_bolt_launch")).setRegistryName("dark_bolt_launch"),
				new SoundEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "dark_bolt_hit")).setRegistryName("dark_bolt_hit"),
				new SoundEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "pixi_activated")).setRegistryName("pixi_activated"),
				new SoundEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "runic_tenebris_core_activated")).setRegistryName("runic_tenebris_core_activated"),
				new SoundEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "runic_tenebris_core_ambient")).setRegistryName("runic_tenebris_core_ambient"));
	}
	
	public static void registerAll(RegistryEvent.Register<SoundEvent> registry, SoundEvent... sounds) {
		for (SoundEvent sound : sounds) {
			registry.getRegistry().register(sound);
		}
	}
}

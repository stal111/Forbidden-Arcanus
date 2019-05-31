package com.stal111.forbidden_arcanus.sound;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MODID)
public class ModSounds {
	
	public static final SoundEvent
			dark_bolt_launch = null,
			dark_bolt_hit = null,
			pixi_activated = null,
			runic_tenebris_core_activated = null,
			runic_tenebris_core_ambient = null;
	
	public static void register(RegistryEvent.Register<SoundEvent> registry) {
		registerAll(registry,
				new BasicSound("dark_bolt_launch").setRegistryName("dark_bolt_launch"),
				new BasicSound("dark_bolt_hit").setRegistryName("dark_bolt_hit"),
				new BasicSound("pixi_activated").setRegistryName("pixi_activated"),
				new BasicSound("runic_tenebris_core_activated").setRegistryName("runic_tenebris_core_activated"),
				new BasicSound("runic_tenebris_core_ambient").setRegistryName("runic_tenebris_core_ambient"));
	}
	
	public static void registerAll(RegistryEvent.Register<SoundEvent> registry, SoundEvent... sounds) {
		for (SoundEvent sound : sounds) {
			registry.getRegistry().register(sound);
		}
	}
}

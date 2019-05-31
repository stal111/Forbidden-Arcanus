package com.stal111.forbidden_arcanus.potion;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MODID)
public class ModPotions {
	
	public static final Potion
		fly = null,
		spectral_vision = null;
	
	public static void register(RegistryEvent.Register<Potion> registry) {
		registerAll(registry,
				new BasicPotion("fly", false, 0, 8376831),
				new BasicPotion("spectral_vision", false, 1, 8376831));
	}
	
	public static void registerAll(RegistryEvent.Register<Potion> registry, Potion... potions) {
		for (Potion potion : potions) {
			registry.getRegistry().register(potion);
		}
	}

}

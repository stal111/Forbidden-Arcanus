package com.stal111.forbidden_arcanus.potion;

import com.stal111.forbidden_arcanus.Main;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Main.MOD_ID)
public class ModPotions {

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Potion> registry) {
    }
}

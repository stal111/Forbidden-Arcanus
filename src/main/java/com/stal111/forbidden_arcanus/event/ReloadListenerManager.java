package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.common.loader.HephaestusForgeInputLoader;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ReloadListenerManager {

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(new HephaestusForgeInputLoader());
    }
}

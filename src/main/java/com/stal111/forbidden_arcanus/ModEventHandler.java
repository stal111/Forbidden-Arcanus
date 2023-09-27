package com.stal111.forbidden_arcanus;

import com.stal111.forbidden_arcanus.common.event.*;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.valhelsia.valhelsia_core.core.forge.ValhelsiaForgeEventHandler;

/**
 * @author stal111
 * @since 2022-11-02
 */
public final class ModEventHandler extends ValhelsiaForgeEventHandler {

    public ModEventHandler(IEventBus modEventBus) {
        super(modEventBus);
    }

    @Override
    public void registerModEvents(IEventBus eventBus) {
        FARegistries.register(eventBus);

        eventBus.register(new SpawnPlacementEvents());
        eventBus.register(new CreativeModeTabEvents());
        eventBus.register(new DatapackRegistryEvents());
    }

    @Override
    public void registerForgeEvents(IEventBus eventBus) {
        eventBus.register(new DeathEvents());
        eventBus.register(new TooltipEvents());
        eventBus.register(new TradeEvents());
    }
}

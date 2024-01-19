package com.stal111.forbidden_arcanus;

import com.stal111.forbidden_arcanus.common.event.*;
import net.neoforged.bus.api.IEventBus;
import net.valhelsia.valhelsia_core.core.neoforge.ValhelsiaForgeEventHandler;

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
        eventBus.register(new SpawnPlacementEvents());
        eventBus.register(new RegistryEvents());
    }

    @Override
    public void registerForgeEvents(IEventBus eventBus) {
        eventBus.register(new DeathEvents());
        eventBus.register(new TooltipEvents());
        eventBus.register(new TradeEvents());
    }
}

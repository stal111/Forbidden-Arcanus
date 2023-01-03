package com.stal111.forbidden_arcanus;

import com.stal111.forbidden_arcanus.common.event.CreativeModeTabEvents;
import com.stal111.forbidden_arcanus.common.event.DeathEvents;
import com.stal111.forbidden_arcanus.common.event.SpawnPlacementEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.valhelsia.valhelsia_core.core.ValhelsiaMod;

/**
 * @author stal111
 * @since 2022-11-02
 */
public final class ModEventHandler extends ValhelsiaMod.EventHandler {

    @Override
    public void registerModEvents(IEventBus eventBus) {
        eventBus.register(new SpawnPlacementEvents());
        eventBus.register(new CreativeModeTabEvents());
    }

    @Override
    public void registerForgeEvents(IEventBus eventBus) {
        eventBus.register(new DeathEvents());
    }
}

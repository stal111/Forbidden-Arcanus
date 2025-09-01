package com.stal111.forbidden_arcanus.datagen

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.valhelsia.dataforge.DataForge

@Mod(ForbiddenArcanus.MOD_ID)
class DataGenerators(eventBus: IEventBus) {

    init {
        DataForge.setup(ProviderCollector(), eventBus)
    }
}
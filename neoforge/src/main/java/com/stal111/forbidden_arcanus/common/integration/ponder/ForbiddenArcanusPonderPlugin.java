package com.stal111.forbidden_arcanus.common.integration.ponder;

import net.minecraft.resources.ResourceLocation;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;

import org.jetbrains.annotations.NotNull;

public class ForbiddenArcanusPonderPlugin implements PonderPlugin {

    @Override
    public @NotNull String getModId() {
        return ForbiddenArcanus.MOD_ID;
    }

    @Override
    public void registerScenes(@NotNull PonderSceneRegistrationHelper<ResourceLocation> helper) {
        FAPonderScenes.register(helper);
    }
}

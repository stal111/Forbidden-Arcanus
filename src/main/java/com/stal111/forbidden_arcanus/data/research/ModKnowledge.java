package com.stal111.forbidden_arcanus.data.research;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.research.DisplayInfo;
import com.stal111.forbidden_arcanus.common.research.FrameType;
import com.stal111.forbidden_arcanus.common.research.Knowledge;
import com.stal111.forbidden_arcanus.common.research.icon.ItemIcon;
import com.stal111.forbidden_arcanus.common.research.icon.TextureIcon;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

import java.util.List;

/**
 * @author stal111
 * @since 23.11.2023
 */
public class ModKnowledge extends DatapackRegistryClass<Knowledge> {

    public static final DatapackRegistryHelper<Knowledge> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.KNOWLEDGE);

    public static final ResourceKey<Knowledge> WELCOME = HELPER.createKey("welcome");
    public static final ResourceKey<Knowledge> FORBIDDENOMICON = HELPER.createKey("forbiddenomicon");
    public static final ResourceKey<Knowledge> RESEARCH_DESK = HELPER.createKey("research_desk");

    public ModKnowledge(BootstapContext<Knowledge> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstapContext<Knowledge> context) {
        var welcome = context.register(WELCOME, new Knowledge(new DisplayInfo(Component.literal("Welcome!"), Component.literal("The start of an adventure."), FrameType.DEFAULT, new TextureIcon(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/research/icon/feather.png")), 0, 0), List.of()));
        context.register(FORBIDDENOMICON, new Knowledge(new DisplayInfo(Component.literal("Forbiddenomicon"), Component.literal("The forbidden grimoire."), FrameType.ADVANCED, new ItemIcon(ModBlocks.FORBIDDENOMICON.get().asItem()), 2, 0), List.of(welcome)));
        context.register(RESEARCH_DESK, new Knowledge(new DisplayInfo(Component.literal("Desk"), Component.literal("More than elegant furniture."), FrameType.MASTER, new ItemIcon(ModBlocks.RESEARCH_DESK.get().asItem()), 0, 2), List.of(welcome)));
    }
}

package com.stal111.forbidden_arcanus.datagen.research

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.research.BuiltinKnowledge
import com.stal111.forbidden_arcanus.common.research.DisplayInfo
import com.stal111.forbidden_arcanus.common.research.FrameType
import com.stal111.forbidden_arcanus.common.research.Knowledge
import com.stal111.forbidden_arcanus.common.research.icon.ItemIcon
import com.stal111.forbidden_arcanus.common.research.icon.TextureIcon
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import net.minecraft.core.Holder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.network.chat.Component
import net.valhelsia.dataforge.RegistryDataProvider

object ModKnowledge : RegistryDataProvider<Knowledge> {

    override fun bootstrap(context: BootstrapContext<Knowledge>) {
        val welcome: Holder.Reference<Knowledge> = context.register(
            BuiltinKnowledge.WELCOME,
            Knowledge(
                DisplayInfo(
                    Component.literal("Welcome!"),
                    Component.literal("The start of an adventure."),
                    FrameType.DEFAULT,
                    TextureIcon(ForbiddenArcanus.identifier("textures/gui/research/icon/feather.png")),
                    0,
                    0
                ), mutableListOf<Holder<Knowledge>>()
            )
        )
        context.register(
            BuiltinKnowledge.FORBIDDENOMICON,
            Knowledge(
                DisplayInfo(
                    Component.literal("Forbiddenomicon"),
                    Component.literal("The forbidden grimoire."),
                    FrameType.ADVANCED,
                    ItemIcon(ModBlocks.FORBIDDENOMICON.get().asItem()),
                    2,
                    0
                ), listOf<Holder<Knowledge>>(welcome)
            )
        )
        context.register(
            BuiltinKnowledge.RESEARCH_DESK,
            Knowledge(
                DisplayInfo(
                    Component.literal("Desk"),
                    Component.literal("More than elegant furniture."),
                    FrameType.MASTER,
                    ItemIcon(ModBlocks.RESEARCH_DESK.get().asItem()),
                    0,
                    2
                ), listOf<Holder<Knowledge>>(welcome)
            )
        )
    }
}
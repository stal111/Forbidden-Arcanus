//package com.stal111.forbidden_arcanus.common.integration.ponder;
//
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.resources.ResourceLocation;
//
//import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
//import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryEntry;
//
//import com.stal111.forbidden_arcanus.core.init.ModBlocks;
//
//public class FAPonderScenes {
//
//    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper ) {
//        PonderSceneRegistrationHelper<BlockRegistryEntry<?>> HELPER = helper.withKeyFunction(block -> BuiltInRegistries.BLOCK.getKey(block.get()));
//
//        HELPER.forComponents(
//                ModBlocks.HEPHAESTUS_FORGE_TIER_1,
//                ModBlocks.DARKSTONE_PEDESTAL
//        ).addStoryBoard("hephaestus_forge", ForgeScenes::building);
//
//        HELPER.forComponents(
//                ModBlocks.HEPHAESTUS_FORGE_TIER_1,
//                ModBlocks.HEPHAESTUS_FORGE_TIER_2,
//                ModBlocks.HEPHAESTUS_FORGE_TIER_3,
//                ModBlocks.HEPHAESTUS_FORGE_TIER_4,
//                ModBlocks.HEPHAESTUS_FORGE_TIER_5,
//                ModBlocks.ARCANE_CRYSTAL_OBELISK
//        ).addStoryBoard("hephaestus_forge", ForgeScenes::usage);
//    }
//}

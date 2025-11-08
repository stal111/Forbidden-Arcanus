package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorages;
import com.stal111.forbidden_arcanus.common.item.AurealTankItem;
import com.stal111.forbidden_arcanus.common.item.enhancer.BuiltInEnhancers;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerHelper;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-08-07
 */
public class ModCreativeModeTabs implements RegistryClass {

    public static final MappedRegistryHelper<CreativeModeTab> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.CREATIVE_MODE_TAB);

    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> MAIN = HELPER.register("main", ValhelsiaCore.INSTANCE.createCreativeTab(builder -> {
                builder.icon(() -> new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()))
                        .title(Component.translatable("itemGroup.forbidden_arcanus.main"))
                        .displayItems((itemDisplayParameters, output) -> {
                            var lookup = itemDisplayParameters.holders();

                            output.accept(ModBlocks.DARKSTONE.get());
                            output.accept(ModBlocks.DARKSTONE_SLAB.get());
                            output.accept(ModBlocks.DARKSTONE_STAIRS.get());
                            output.accept(ModBlocks.DARKSTONE_WALL.get());
                            output.accept(ModBlocks.POLISHED_DARKSTONE.get());
                            output.accept(ModBlocks.POLISHED_DARKSTONE_SLAB.get());
                            output.accept(ModBlocks.POLISHED_DARKSTONE_STAIRS.get());
                            output.accept(ModBlocks.POLISHED_DARKSTONE_WALL.get());
                            output.accept(ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE.get());
                            output.accept(ModBlocks.POLISHED_DARKSTONE_BUTTON.get());
                            output.accept(ModBlocks.CHISELED_POLISHED_DARKSTONE.get());
                            output.accept(ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get());
                            output.accept(ModBlocks.POLISHED_DARKSTONE_BRICKS.get());
                            output.accept(ModBlocks.POLISHED_DARKSTONE_BRICK_SLAB.get());
                            output.accept(ModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS.get());
                            output.accept(ModBlocks.POLISHED_DARKSTONE_BRICK_WALL.get());
                            output.accept(ModBlocks.CRACKED_POLISHED_DARKSTONE_BRICKS.get());
                            output.accept(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get());
                            output.accept(ModBlocks.ARCANE_POLISHED_DARKSTONE.get());
                            output.accept(ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get());
                            output.accept(ModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS.get());
                            output.accept(ModBlocks.ARCANE_POLISHED_DARKSTONE_WALL.get());
                            output.accept(ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get());
                            output.accept(ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get());
                            output.accept(ModBlocks.DARKSTONE_PEDESTAL.get());
                            output.accept(ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get());
                            output.accept(ModBlocks.MORTAR.get());
                            output.accept(ModBlocks.CLIBANO_CORE.get());
                            for (HephaestusForgeLevel level : HephaestusForgeLevel.values()) {
                                ItemStack stack = new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get());
                                stack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(ModBlockStateProperties.FORGE_TIER, level));

                                output.accept(stack);
                            }
                            output.accept(ModBlocks.QUANTUM_CORE.get());
                            output.accept(ModBlocks.QUANTUM_INJECTOR.get());
                            output.accept(ModBlocks.ARCANE_CRYSTAL_ORE.get());
                            output.accept(ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get());
                            output.accept(ModBlocks.RUNIC_STONE.get());
                            output.accept(ModBlocks.RUNIC_DEEPSLATE.get());
                            output.accept(ModBlocks.RUNIC_DARKSTONE.get());
                            output.accept(ModBlocks.STELLA_ARCANUM.get());
                            output.accept(ModBlocks.ARCANE_CRYSTAL_BLOCK.get());
                            output.accept(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK.get());
                            output.accept(ModBlocks.RUNE_BLOCK.get());
                            output.accept(ModBlocks.STELLARITE_BLOCK.get());
                            output.accept(ModBlocks.DEORUM_BLOCK.get());
                            output.accept(ModBlocks.OBSIDIANSTEEL_BLOCK.get());
                            output.accept(ModBlocks.ARCANE_CRYSTAL_OBELISK.get());
                            output.accept(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_OBELISK.get());
                            output.accept(ModBlocks.DEORUM_GLASS.get());
                            output.accept(ModBlocks.RUNIC_GLASS.get());
                            output.accept(ModBlocks.DEORUM_GLASS_PANE.get());
                            output.accept(ModBlocks.RUNIC_GLASS_PANE.get());
                            output.accept(ModBlocks.DEORUM_LANTERN.get());
                            output.accept(ModBlocks.DEORUM_SOUL_LANTERN.get());
                            output.accept(ModBlocks.SOULLESS_SAND.get());
                            output.accept(ModBlocks.SOULLESS_SANDSTONE.get());
                            output.accept(ModBlocks.SOULLESS_SANDSTONE_SLAB.get());
                            output.accept(ModBlocks.SOULLESS_SANDSTONE_STAIRS.get());
                            output.accept(ModBlocks.SOULLESS_SANDSTONE_WALL.get());
                            output.accept(ModBlocks.CUT_SOULLESS_SANDSTONE.get());
                            output.accept(ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.get());
                            output.accept(ModBlocks.POLISHED_SOULLESS_SANDSTONE.get());
                            output.accept(ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.get());
                            output.accept(ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.get());
                            output.accept(ModBlocks.FUNGYSS.get());
                            output.accept(ModBlocks.AURUM_SAPLING.get());
                            output.accept(ModBlocks.GROWING_EDELWOOD.get());
                            output.accept(ModBlocks.FUNGYSS_BLOCK.get());
                            output.accept(ModBlocks.AURUM_LEAVES.get());
                            output.accept(ModBlocks.NUGGETY_AURUM_LEAVES.get());
                            output.accept(ModBlocks.FUNGYSS_STEM.get());
                            output.accept(ModBlocks.AURUM_LOG.get());
                            output.accept(ModBlocks.EDELWOOD_LOG.get());
                            output.accept(ModBlocks.CARVED_EDELWOOD_LOG.get());
                            output.accept(ModBlocks.EDELWOOD_BRANCH.get());
                            output.accept(ModBlocks.STRIPPED_AURUM_LOG.get());
                            output.accept(ModBlocks.FUNGYSS_HYPHAE.get());
                            output.accept(ModBlocks.AURUM_WOOD.get());
                            output.accept(ModBlocks.STRIPPED_AURUM_WOOD.get());
                            output.accept(ModBlocks.FUNGYSS_PLANKS.get());
                            output.accept(ModBlocks.AURUM_PLANKS.get());
                            output.accept(ModBlocks.EDELWOOD_PLANKS.get());
                            output.accept(ModBlocks.ARCANE_EDELWOOD_PLANKS.get());
                            output.accept(ModBlocks.FUNGYSS_SLAB.get());
                            output.accept(ModBlocks.AURUM_SLAB.get());
                            output.accept(ModBlocks.EDELWOOD_SLAB.get());
                            output.accept(ModBlocks.FUNGYSS_STAIRS.get());
                            output.accept(ModBlocks.EDELWOOD_STAIRS.get());
                            output.accept(ModBlocks.DEORUM_DOOR.get());
                            output.accept(ModBlocks.FUNGYSS_DOOR.get());
                            output.accept(ModBlocks.AURUM_DOOR.get());
                            output.accept(ModBlocks.EDELWOOD_DOOR.get());
                            output.accept(ModBlocks.ARCANE_EDELWOOD_DOOR.get());
                            output.accept(ModBlocks.DEORUM_TRAPDOOR.get());
                            output.accept(ModBlocks.FUNGYSS_TRAPDOOR.get());
                            output.accept(ModBlocks.AURUM_TRAPDOOR.get());
                            output.accept(ModBlocks.EDELWOOD_TRAPDOOR.get());
                            output.accept(ModBlocks.ARCANE_EDELWOOD_TRAPDOOR.get());
                            output.accept(ModBlocks.FUNGYSS_FENCE.get());
                            output.accept(ModBlocks.AURUM_FENCE.get());
                            output.accept(ModBlocks.EDELWOOD_FENCE.get());
                            output.accept(ModBlocks.FUNGYSS_FENCE_GATE.get());
                            output.accept(ModBlocks.AURUM_FENCE_GATE.get());
                            output.accept(ModBlocks.EDELWOOD_FENCE_GATE.get());
                            output.accept(ModBlocks.EDELWOOD_LADDER.get());
                            output.accept(ModBlocks.FUNGYSS_BUTTON.get());
                            output.accept(ModBlocks.AURUM_BUTTON.get());
                            output.accept(ModBlocks.EDELWOOD_BUTTON.get());
                            output.accept(ModBlocks.FUNGYSS_PRESSURE_PLATE.get());
                            output.accept(ModBlocks.AURUM_PRESSURE_PLATE.get());
                            output.accept(ModBlocks.EDELWOOD_PRESSURE_PLATE.get());
                            output.accept(ModBlocks.ARCANE_DRAGON_EGG.get());
                            output.accept(ModBlocks.DEORUM_CHAIN.get());
                            output.accept(ModBlocks.YELLOW_ORCHID.get());
                            output.accept(ModBlocks.MAGICAL_FARMLAND.get());

                            output.accept(ModItems.ARCANE_CRYSTAL.get());
                            output.accept(ModItems.CORRUPTED_ARCANE_CRYSTAL.get());
                            output.accept(ModItems.RUNE.get());
                            output.accept(ModItems.STELLARITE_PIECE.get());
                            output.accept(ModItems.CONDENSED_EXPERIENCE.get());
                            output.accept(ModItems.DARK_NETHER_STAR.get());
                            output.accept(ModItems.DEORUM_NUGGET.get());
                            output.accept(ModItems.DEORUM_INGOT.get());
                            output.accept(ModItems.OBSIDIANSTEEL_INGOT.get());
                            output.accept(ModItems.ARCANE_CRYSTAL_DUST.get());
                            output.accept(ModItems.MUNDABITUR_DUST.get());
                            output.accept(ModItems.CORRUPTI_DUST.get());
                            output.accept(ModItems.ARCANE_CRYSTAL_DUST_SPECK.get());
                            output.accept(ModItems.ARCANE_BONE_MEAL.get());
                            output.accept(ModItems.SOUL.get());
                            output.accept(ModItems.CORRUPT_SOUL.get());
                            output.accept(ModItems.ENCHANTED_SOUL.get());
                            output.accept(ModItems.AUREAL_BOTTLE.get());
                            output.accept(ModItems.SPLASH_AUREAL_BOTTLE.get());
                            output.accept(ModItems.ECTOPLASM_BOTTLE.get());
                            output.accept(ModItems.TEST_TUBE.get());
                            output.accept(EssenceHelper.createStorageItem(ModItems.BLOOD_TEST_TUBE.get(), EssenceStorages.BLOOD_TEST_TUBE_FULL));
                            output.accept(ModItems.APPLY_MODIFIER_SMITHING_TEMPLATE.get());
                            output.accept(ModItems.ETERNAL_STELLA.get());
                            output.accept(ModItems.TERRASTOMP_PRISM.get());
                            output.accept(ModItems.SEA_PRISM.get());
                            output.accept(ModItems.WHIRLWIND_PRISM.get());
                            output.accept(ModItems.SMELTER_PRISM.get());
                            output.accept(ModItems.FERROGNETIC_MIXTURE.get());
                            output.accept(ModItems.SOUL_BINDING_CRYSTAL.get());
                            output.accept(ModItems.AUREAL_WARDSTONE.get());
                            output.accept(ModItems.DARK_MATTER.get());
                            output.accept(ModItems.ENDER_PEARL_FRAGMENT.get());
                            output.accept(ModItems.DRAGON_SCALE.get());
                            output.accept(ModItems.SILVER_DRAGON_SCALE.get());
                            output.accept(ModItems.GOLDEN_DRAGON_SCALE.get());
                            output.accept(ModItems.AQUATIC_DRAGON_SCALE.get());
                            output.accept(ModItems.SPECTRAL_EYE_AMULET.get());
                            output.accept(ModItems.BAT_WING.get());
                            output.accept(ModItems.BAT_SOUP.get());
                            output.accept(ModItems.TENTACLE.get());
                            output.accept(ModItems.COOKED_TENTACLE.get());
                            output.accept(ModItems.EDELWOOD_STICK.get());
                            output.accept(ModItems.WAX.get());
                            output.accept(ModItems.SPAWNER_SCRAP.get());
                            output.accept(ModItems.QUANTUM_CATCHER.get());
                            ModItems.DYED_QUANTUM_CATCHERS.forEach((color, registryEntry) -> output.accept(registryEntry.get()));
                            output.accept(ModItems.BOSS_CATCHER.get());

                            output.accept(EssenceHelper.createStorageItem(ModItems.AUREAL_TANK.get(), EssenceType.AUREAL, 0, AurealTankItem.DEFAULT_CAPACITY));
                            output.accept(EssenceHelper.createStorageItem(ModItems.AUREAL_TANK.get(), EssenceType.AUREAL, AurealTankItem.DEFAULT_CAPACITY, AurealTankItem.DEFAULT_CAPACITY));
                            output.accept(EssenceHelper.createStorageItem(ModItems.AUREAL_TANK.get(), EssenceType.AUREAL, 0, AurealTankItem.MAX_CAPACITY));
                            output.accept(EssenceHelper.createStorageItem(ModItems.AUREAL_TANK.get(), EssenceType.AUREAL, AurealTankItem.MAX_CAPACITY, AurealTankItem.MAX_CAPACITY));

                            output.accept(ModBlocks.OBSIDIAN_SKULL.getSkull());
                            output.accept(ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull());
                            output.accept(ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull());
                            output.accept(ModBlocks.FADING_OBSIDIAN_SKULL.getSkull());
                            output.accept(ModBlocks.AUREALIC_OBSIDIAN_SKULL.getSkull());
                            output.accept(ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull());
                            output.accept(ModItems.UTREM_JAR.get());

                            output.accept(EssenceHelper.createStorageItem(ModItems.ESSENCE_UTREM_JAR.get(), EssenceType.AUREAL, 10000, 10000));
                            output.accept(EssenceHelper.createStorageItem(ModItems.ESSENCE_UTREM_JAR.get(), EssenceType.SOULS, 10000, 10000));
                            output.accept(EssenceHelper.createStorageItem(ModItems.ESSENCE_UTREM_JAR.get(), EssenceType.BLOOD, 10000, 10000));
                            output.accept(EssenceHelper.createStorageItem(ModItems.ESSENCE_UTREM_JAR.get(), EssenceType.EXPERIENCE, 10000, 10000));

                            output.accept(ModItems.EDELWOOD_BUCKET.get());
                            output.accept(ModItems.EDELWOOD_WATER_BUCKET.get());
                            output.accept(ModItems.EDELWOOD_LAVA_BUCKET.get());
                            output.accept(ModItems.EDELWOOD_MILK_BUCKET.get());
                            output.accept(ModItems.EDELWOOD_POWDER_SNOW_BUCKET.get());

                            output.accept(ModItems.BOOM_ARROW.get());
                            output.accept(ModItems.DRACO_ARCANUS_ARROW.get());
                            output.accept(ModItems.EDELWOOD_OIL.get());
//                            output.accept(ModItems.AURUM_BOAT.get());
//                            output.accept(ModItems.AURUM_CHEST_BOAT.get());
//                            output.accept(ModItems.EDELWOOD_BOAT.get());
//                            output.accept(ModItems.EDELWOOD_CHEST_BOAT.get());
                            output.accept(ModItems.DRACO_ARCANUS_STAFF.get());
//                            output.accept(ModItems.DRACO_ARCANUS_SWORD.get());
//                            output.accept(ModItems.DRACO_ARCANUS_SHOVEL.get());
//                            output.accept(ModItems.DRACO_ARCANUS_PICKAXE.get());
//                            output.accept(ModItems.DRACO_ARCANUS_AXE.get());
//                            output.accept(ModItems.DRACO_ARCANUS_HOE.get());
                            output.accept(ModItems.DRACO_ARCANUS_SCEPTER.get());
                            output.accept(ModItems.MAGIC_WAND.get());
                            output.accept(ModItems.DRACO_ARCANUS_HELMET.get());
                            output.accept(ModItems.DRACO_ARCANUS_CHESTPLATE.get());
                            output.accept(ModItems.DRACO_ARCANUS_LEGGINGS.get());
                            output.accept(ModItems.DRACO_ARCANUS_BOOTS.get());
                            output.accept(ModItems.TYR_HELMET.get());
                            output.accept(ModItems.TYR_CHESTPLATE.get());
                            output.accept(ModItems.TYR_LEGGINGS.get());
                            output.accept(ModItems.TYR_BOOTS.get());
//                            output.accept(ModItems.MORTEM_HELMET.get());
//                            output.accept(ModItems.MORTEM_CHESTPLATE.get());
//                            output.accept(ModItems.MORTEM_LEGGINGS.get());
//                            output.accept(ModItems.MORTEM_BOOTS.get());

                            lookup.lookup(FARegistries.ENHANCER_DEFINITION).ifPresent(enhancerLookup -> {
                                output.accept(EnhancerHelper.createEnhancerItem(enhancerLookup, ModItems.ARTISAN_RELIC, BuiltInEnhancers.ARTISAN_RELIC));
                                output.accept(EnhancerHelper.createEnhancerItem(enhancerLookup, ModItems.CRESCENT_MOON, BuiltInEnhancers.CRESCENT_MOON));
                                output.accept(EnhancerHelper.createEnhancerItem(enhancerLookup, ModItems.CRIMSON_STONE, BuiltInEnhancers.CRIMSON_STONE));
                                output.accept(EnhancerHelper.createEnhancerItem(enhancerLookup, ModItems.SOUL_CRIMSON_STONE, BuiltInEnhancers.SOUL_CRIMSON_STONE));
                                output.accept(EnhancerHelper.createEnhancerItem(enhancerLookup, ModItems.ELEMENTARIUM, BuiltInEnhancers.ELEMENTARIUM));
                                output.accept(EnhancerHelper.createEnhancerItem(enhancerLookup, ModItems.DIVINE_PACT, BuiltInEnhancers.DIVINE_PACT));
                                output.accept(EnhancerHelper.createEnhancerItem(enhancerLookup, ModItems.MALEDICTUS_PACT, BuiltInEnhancers.MALEDICTUS_PACT));
                            });

                            output.accept(ModItems.SOUL_EXTRACTOR.get());
                            output.accept(ModItems.BLACKSMITH_GAVEL_HEAD.get());
                            output.accept(ModItems.WOODEN_BLACKSMITH_GAVEL.get());
                            output.accept(ModItems.STONE_BLACKSMITH_GAVEL.get());
                            output.accept(ModItems.GOLDEN_BLACKSMITH_GAVEL.get());
                            output.accept(ModItems.IRON_BLACKSMITH_GAVEL.get());
                            output.accept(ModItems.DIAMOND_BLACKSMITH_GAVEL.get());
                            output.accept(ModItems.NETHERITE_BLACKSMITH_GAVEL.get());
                        });
            })
    );
}

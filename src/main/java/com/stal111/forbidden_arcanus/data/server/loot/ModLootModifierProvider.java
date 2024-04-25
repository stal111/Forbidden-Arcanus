package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.loot.BlacksmithGavelLootModifier;
import com.stal111.forbidden_arcanus.common.loot.MagicalFarmlandLootModifier;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;

import java.util.List;

/**
 * @author stal111
 * @since 2021-04-10
 */
public class ModLootModifierProvider extends GlobalLootModifierProvider {

    public static final ResourceKey<LootTable> ZOMBIE_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/zombie_additions"));
    public static final ResourceKey<LootTable> DROWNED_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/drowned_additions"));
    public static final ResourceKey<LootTable> SPAWNER_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "blocks/additions/spawner_additions"));
    public static final ResourceKey<LootTable> ENDERMAN_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/enderman_additions"));
    public static final ResourceKey<LootTable> BAT_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/bat_additions"));
    public static final ResourceKey<LootTable> SQUID_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/squid_additions"));
    public static final ResourceKey<LootTable> ENDER_DRAGON_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/ender_dragon_additions"));
    public static final ResourceKey<LootTable> SIMPLE_DUNGEON_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/simple_dungeon_additions"));
    public static final ResourceKey<LootTable> ABANDONED_MINESHAFT_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/abandoned_mineshaft_additions"));
    public static final ResourceKey<LootTable> END_CITY_TREASURE_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/end_city_treasure_additions"));
    public static final ResourceKey<LootTable> ARTISAN_RELIC_ADDITION = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/artisan_relic_addition"));
    public static final ResourceKey<LootTable> CRIMSON_STONE_ADDITION = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/crimson_stone_addition"));
    public static final ResourceKey<LootTable> ELEMENTARIUM_ADDITION = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/elementarium_addition"));
    public static final ResourceKey<LootTable> MALEDICTUS_PACT_ADDITION = ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/maledictus_pact_addition"));

    public ModLootModifierProvider(DataProviderContext context) {
        super(context.output(), context.lookupProvider(), ForbiddenArcanus.MOD_ID);
    }

    @Override
    protected void start() {
        // Entities
        this.add("zombie_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.002F, 0.001F).build(),
                        LootTableIdCondition.builder(new ResourceLocation("entities/zombie")).build()
                }, ZOMBIE_ADDITIONS)
        );
        this.add("drowned_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.002F, 0.001F).build(),
                        LootTableIdCondition.builder(new ResourceLocation("entities/drowned")).build()
                }, DROWNED_ADDITIONS)
        );
        this.add("spawner_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))))).build(),
                        LootTableIdCondition.builder(new ResourceLocation("blocks/spawner")).build()
                }, SPAWNER_ADDITIONS)
        );
        this.add("enderman_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("entities/enderman")).build()
                }, ENDERMAN_ADDITIONS)
        );
        this.add("bat_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("entities/bat")).build()
                }, BAT_ADDITIONS)
        );
        this.add("squid_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootItemRandomChanceCondition.randomChance(0.7F).build(),
                        LootTableIdCondition.builder(new ResourceLocation("entities/squid")).build()
                }, SQUID_ADDITIONS)
        );
        this.add("ender_dragon_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("entities/ender_dragon")).build()
                }, ENDER_DRAGON_ADDITIONS)
        );

        // Chests
        this.add("simple_dungeon_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/simple_dungeon")).build()
                }, SIMPLE_DUNGEON_ADDITIONS)
        );
        this.add("abandoned_mineshaft_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/abandoned_mineshaft")).build()
                }, ABANDONED_MINESHAFT_ADDITIONS)
        );
        this.add("end_city_treasure_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/end_city_treasure")).build()
                }, END_CITY_TREASURE_ADDITIONS)
        );
//        this.add("artisan_relic_addition",
//                new AppendLootTableModifier(new LootItemCondition[] {
//                        AlternativeLootItemCondition.alternative(
//                                LootTableIdCondition.builder(new ResourceLocation("chests/village/village_armorer")),
//                                LootTableIdCondition.builder(new ResourceLocation("chests/village/village_toolsmith")),
//                                LootTableIdCondition.builder(new ResourceLocation("chests/village/village_weaponsmith"))
//                        ).build()
//                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/artisan_relic_addition"))
//        );
        this.add("crimson_stone_addition",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/pillager_outpost")).build()
                }, CRIMSON_STONE_ADDITION)
        );
//        this.add("elementarium_addition",
//                new AppendLootTableModifier(new LootItemCondition[] {
//                        AlternativeLootItemCondition.alternative(
//                                LootTableIdCondition.builder(new ResourceLocation("chests/jungle_temple")),
//                                LootTableIdCondition.builder(new ResourceLocation("chests/desert_pyramid")),
//                                LootTableIdCondition.builder(new ResourceLocation("chests/underwater_ruin_small")),
//                                LootTableIdCondition.builder(new ResourceLocation("chests/underwater_ruin_big"))
//                        ).build()
//                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/elementarium_addition"))
//        );
        this.add("maledictus_pact",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/bastion_treasure")).build()
                }, MALEDICTUS_PACT_ADDITION)
        );

        // Items
//        this.add("fiery_modifier",
//                new FieryLootModifier(new LootItemCondition[] {
//                        new MatchTool(new ModifierItemPredicate(ModItemModifiers.FIERY.get()))
//                })
//        );
        this.add("blacksmith_gavel_ore_doubling",
                new BlacksmithGavelLootModifier(new LootItemCondition[] {
                        LootItemRandomChanceCondition.randomChance(0.3F).build()
                })
        );

        // Blocks
        this.add("magical_farmland_crop_doubling", new MagicalFarmlandLootModifier(new LootItemCondition[]{}));
    }
}
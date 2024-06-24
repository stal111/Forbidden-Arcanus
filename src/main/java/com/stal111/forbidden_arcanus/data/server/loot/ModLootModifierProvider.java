package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.advancements.critereon.FAItemSubPredicates;
import com.stal111.forbidden_arcanus.common.advancements.critereon.ItemModifierPredicate;
import com.stal111.forbidden_arcanus.common.loot.BlacksmithGavelLootModifier;
import com.stal111.forbidden_arcanus.common.loot.FieryLootModifier;
import com.stal111.forbidden_arcanus.common.loot.MagicalFarmlandLootModifier;
import com.stal111.forbidden_arcanus.core.init.other.ModItemModifiers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
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

    public static final ResourceKey<LootTable> ZOMBIE_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("entities/additions/zombie_additions"));
    public static final ResourceKey<LootTable> DROWNED_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("entities/additions/drowned_additions"));
    public static final ResourceKey<LootTable> SPAWNER_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("blocks/additions/spawner_additions"));
    public static final ResourceKey<LootTable> ENDERMAN_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("entities/additions/enderman_additions"));
    public static final ResourceKey<LootTable> BAT_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("entities/additions/bat_additions"));
    public static final ResourceKey<LootTable> SQUID_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("entities/additions/squid_additions"));
    public static final ResourceKey<LootTable> ENDER_DRAGON_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("entities/additions/ender_dragon_additions"));
    public static final ResourceKey<LootTable> SIMPLE_DUNGEON_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("chests/additions/simple_dungeon_additions"));
    public static final ResourceKey<LootTable> ABANDONED_MINESHAFT_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("chests/additions/abandoned_mineshaft_additions"));
    public static final ResourceKey<LootTable> END_CITY_TREASURE_ADDITIONS = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("chests/additions/end_city_treasure_additions"));
    public static final ResourceKey<LootTable> ARTISAN_RELIC_ADDITION = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("chests/additions/artisan_relic_addition"));
    public static final ResourceKey<LootTable> CRIMSON_STONE_ADDITION = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("chests/additions/crimson_stone_addition"));
    public static final ResourceKey<LootTable> ELEMENTARIUM_ADDITION = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("chests/additions/elementarium_addition"));
    public static final ResourceKey<LootTable> MALEDICTUS_PACT_ADDITION = ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location("chests/additions/maledictus_pact_addition"));

    public ModLootModifierProvider(DataProviderContext context) {
        super(context.output(), context.lookupProvider(), ForbiddenArcanus.MOD_ID);
    }

    @Override
    protected void start() {
        // Entities
        this.add("zombie_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.002F, 0.001F).build(),
                        LootTableIdCondition.builder(EntityType.ZOMBIE.getDefaultLootTable().location()).build()
                }, ZOMBIE_ADDITIONS)
        );
        this.add("drowned_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.002F, 0.001F).build(),
                        LootTableIdCondition.builder(EntityType.DROWNED.getDefaultLootTable().location()).build()
                }, DROWNED_ADDITIONS)
        );
        this.add("spawner_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))))))).build(),
                        LootTableIdCondition.builder(Blocks.SPAWNER.getLootTable().location()).build()
                }, SPAWNER_ADDITIONS)
        );
        this.add("enderman_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(EntityType.ENDERMAN.getDefaultLootTable().location()).build()
                }, ENDERMAN_ADDITIONS)
        );
        this.add("bat_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(EntityType.BAT.getDefaultLootTable().location()).build()
                }, BAT_ADDITIONS)
        );
        this.add("squid_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootItemRandomChanceCondition.randomChance(0.7F).build(),
                        LootTableIdCondition.builder(EntityType.SQUID.getDefaultLootTable().location()).build()
                }, SQUID_ADDITIONS)
        );
        this.add("ender_dragon_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(EntityType.ENDER_DRAGON.getDefaultLootTable().location()).build()
                }, ENDER_DRAGON_ADDITIONS)
        );

        // Chests
        this.add("simple_dungeon_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(BuiltInLootTables.SIMPLE_DUNGEON.location()).build()
                }, SIMPLE_DUNGEON_ADDITIONS)
        );
        this.add("abandoned_mineshaft_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(BuiltInLootTables.ABANDONED_MINESHAFT.location()).build()
                }, ABANDONED_MINESHAFT_ADDITIONS)
        );
        this.add("end_city_treasure_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(BuiltInLootTables.END_CITY_TREASURE.location()).build()
                }, END_CITY_TREASURE_ADDITIONS)
        );
        this.add("artisan_relic_addition",
                new AddTableLootModifier(new LootItemCondition[] {
                        AnyOfCondition.anyOf(
                                LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_ARMORER.location()),
                                LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_TOOLSMITH.location()),
                                LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_WEAPONSMITH.location())
                        ).build()
                }, ARTISAN_RELIC_ADDITION)
        );
        this.add("crimson_stone_addition",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(BuiltInLootTables.PILLAGER_OUTPOST.location()).build()
                }, CRIMSON_STONE_ADDITION)
        );
        this.add("elementarium_addition",
                new AddTableLootModifier(new LootItemCondition[] {
                        AnyOfCondition.anyOf(
                                LootTableIdCondition.builder(BuiltInLootTables.JUNGLE_TEMPLE.location()),
                                LootTableIdCondition.builder(BuiltInLootTables.DESERT_PYRAMID.location()),
                                LootTableIdCondition.builder(BuiltInLootTables.UNDERWATER_RUIN_SMALL.location()),
                                LootTableIdCondition.builder(BuiltInLootTables.UNDERWATER_RUIN_BIG.location())
                        ).build()
                }, ELEMENTARIUM_ADDITION)
        );
        this.add("maledictus_pact",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(BuiltInLootTables.BASTION_TREASURE.location()).build()
                }, MALEDICTUS_PACT_ADDITION)
        );

        // Items
        this.add("fiery_modifier",
                new FieryLootModifier(new LootItemCondition[] {
                        MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(FAItemSubPredicates.MODIFIER.get(), ItemModifierPredicate.modifier(ModItemModifiers.FIERY))).build()
                })
        );
        this.add("blacksmith_gavel_ore_doubling",
                new BlacksmithGavelLootModifier(new LootItemCondition[] {
                        LootItemRandomChanceCondition.randomChance(0.3F).build()
                })
        );

        // Blocks
        this.add("magical_farmland_crop_doubling", new MagicalFarmlandLootModifier(new LootItemCondition[]{}));
    }
}
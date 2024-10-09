package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.advancements.critereon.FAItemSubPredicates;
import com.stal111.forbidden_arcanus.common.advancements.critereon.ItemModifierPredicate;
import com.stal111.forbidden_arcanus.common.loot.BlacksmithGavelLootModifier;
import com.stal111.forbidden_arcanus.common.loot.FieryLootModifier;
import com.stal111.forbidden_arcanus.common.loot.MagicalFarmlandLootModifier;
import com.stal111.forbidden_arcanus.data.ModItemModifiers;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
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

    public ModLootModifierProvider(DataProviderContext context) {
        super(context.output(), context.lookupProvider(), ForbiddenArcanus.MOD_ID);
    }

    @Override
    protected void start() {
        // Entities
        this.add("spawner_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))))))).build(),
                        LootTableIdCondition.builder(Blocks.SPAWNER.getLootTable().location()).build()
                }, ModBlockLootAdditions.SPAWNER_SCRAP_ADDITION)
        );
        this.add("enderman_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(EntityType.ENDERMAN.getDefaultLootTable().location()).build()
                }, ModEntityLootAdditions.ENDER_PEARL_FRAGMENT_ADDITION)
        );
        this.add("bat_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(EntityType.BAT.getDefaultLootTable().location()).build()
                }, ModEntityLootAdditions.BAT_WING_ADDITION)
        );
        this.add("squid_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootItemRandomChanceCondition.randomChance(0.7F).build(),
                        LootTableIdCondition.builder(EntityType.SQUID.getDefaultLootTable().location()).build()
                }, ModEntityLootAdditions.TENTACLE_ADDITION)
        );
        this.add("ender_dragon_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(EntityType.ENDER_DRAGON.getDefaultLootTable().location()).build()
                }, ModEntityLootAdditions.DRAGON_SCALE_ADDITION)
        );

        // Chests
        this.add("simple_dungeon_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(BuiltInLootTables.SIMPLE_DUNGEON.location()).build()
                }, ModChestLootAdditions.AUREAL_BOTTLE_ADDITION)
        );
        this.add("end_city_treasure_additions",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(BuiltInLootTables.END_CITY_TREASURE.location()).build()
                }, ModChestLootAdditions.DRAGON_SCALE_ADDITION)
        );
        this.add("artisan_relic_addition",
                new AddTableLootModifier(new LootItemCondition[] {
                        AnyOfCondition.anyOf(
                                LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_ARMORER.location()),
                                LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_TOOLSMITH.location()),
                                LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_WEAPONSMITH.location())
                        ).build()
                }, ModChestLootAdditions.ARTISAN_RELIC_ADDITION)
        );
        this.add("crimson_stone_addition",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(BuiltInLootTables.PILLAGER_OUTPOST.location()).build()
                }, ModChestLootAdditions.CRIMSON_STONE_ADDITION)
        );
        this.add("elementarium_addition",
                new AddTableLootModifier(new LootItemCondition[] {
                        AnyOfCondition.anyOf(
                                LootTableIdCondition.builder(BuiltInLootTables.JUNGLE_TEMPLE.location()),
                                LootTableIdCondition.builder(BuiltInLootTables.DESERT_PYRAMID.location()),
                                LootTableIdCondition.builder(BuiltInLootTables.UNDERWATER_RUIN_SMALL.location()),
                                LootTableIdCondition.builder(BuiltInLootTables.UNDERWATER_RUIN_BIG.location())
                        ).build()
                }, ModChestLootAdditions.ELEMENTARIUM_ADDITION)
        );
        this.add("maledictus_pact",
                new AddTableLootModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(BuiltInLootTables.BASTION_TREASURE.location()).build()
                }, ModChestLootAdditions.MALEDICTUS_PACT_ADDITION)
        );

        // Items
        this.add("fiery_modifier",
                new FieryLootModifier(new LootItemCondition[] {
                        MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(FAItemSubPredicates.MODIFIER.get(), ItemModifierPredicate.modifier(this.registries.holderOrThrow(ModItemModifiers.FIERY)))).build()
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
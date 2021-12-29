package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.loot.BlacksmithGavelLootModifier;
import com.stal111.forbidden_arcanus.common.loot.FieryLootModifier;
import com.stal111.forbidden_arcanus.common.loot.MagicalFarmlandLootModifier;
import com.stal111.forbidden_arcanus.common.predicate.ModifierItemPredicate;
import com.stal111.forbidden_arcanus.core.init.other.ModItemModifiers;
import com.stal111.forbidden_arcanus.core.init.other.ModLootModifiers;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.valhelsia.valhelsia_core.common.loot.modifiers.AppendLootTableModifier;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaLootModifiers;

/**
 * Mod Loot Modifiers <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.loot.ModLootModifierProvider
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-04-10
 */
public class ModLootModifierProvider extends GlobalLootModifierProvider {

    public ModLootModifierProvider(DataGenerator gen) {
        super(gen, ForbiddenArcanus.MOD_ID);
    }

    @Override
    protected void start() {
        // Entities
        this.add("zombie_additions", ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new LootItemCondition[] {
                        LootItemRandomChanceCondition.randomChance(0.002F).build(),
                        LootTableIdCondition.builder(new ResourceLocation("entities/zombie")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/zombie_additions"))
        );
        this.add("drowned_additions", ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new LootItemCondition[] {
                        LootItemRandomChanceCondition.randomChance(0.002F).build(),
                        LootTableIdCondition.builder(new ResourceLocation("entities/drowned")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/drowned_additions"))
        );
        this.add("spawner_additions", ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new LootItemCondition[] {
                        InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(2))))).build(),
                        LootTableIdCondition.builder(new ResourceLocation("blocks/spawner")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "blocks/additions/spawner_additions"))
        );
        this.add("enderman_additions", ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("entities/enderman")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/enderman_additions"))
        );
        this.add("bat_additions", ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("entities/bat")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/bat_additions"))
        );
        this.add("squid_additions", ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new LootItemCondition[] {
                        LootItemRandomChanceCondition.randomChance(0.7F).build(),
                        LootTableIdCondition.builder(new ResourceLocation("entities/squid")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/squid_additions"))
        );
        this.add("ender_dragon_additions", ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("entities/ender_dragon")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/ender_dragon_additions"))
        );

        // Chests
        this.add("simple_dungeon_additions", ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/simple_dungeon")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/simple_dungeon_additions"))
        );
        this.add("simple_dungeon_additions", ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/simple_dungeon")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/simple_dungeon_additions"))
        );
        this.add("abandoned_mineshaft_additions", ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/abandoned_mineshaft")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/abandoned_mineshaft_additions"))
        );
        this.add("end_city_treasure_additions", ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/end_city_treasure")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "chests/additions/end_city_treasure_additions"))
        );

        // Items
        this.add("fiery_modifier", ModLootModifiers.FIERY.get(),
                new FieryLootModifier(new LootItemCondition[] {
                        new MatchTool(new ModifierItemPredicate(ModItemModifiers.FIERY.get()))
                })
        );
        this.add("blacksmith_gavel_ore_doubling", ModLootModifiers.BLACKSMITH_GAVEL.get(),
                new BlacksmithGavelLootModifier(new LootItemCondition[] {
                        LootItemRandomChanceCondition.randomChance(0.3F).build()
                })
        );

        // Blocks
        this.add("magical_farmland_crop_doubling", ModLootModifiers.MAGICAL_FARMLAND.get(), new MagicalFarmlandLootModifier(new LootItemCondition[]{}));
    }
}
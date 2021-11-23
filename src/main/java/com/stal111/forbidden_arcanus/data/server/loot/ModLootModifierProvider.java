package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.loot.BlacksmithGavelLootModifier;
import com.stal111.forbidden_arcanus.common.loot.InfernumPickaxeLootModifier;
import com.stal111.forbidden_arcanus.common.loot.MagicalFarmlandLootModifier;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.other.ModLootModifiers;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.resources.ResourceLocation;
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
        this.add("blacksmith_gavel_ore_doubling", ModLootModifiers.BLACKSMITH_GAVEL.get(),
                new BlacksmithGavelLootModifier(new LootItemCondition[] {
                        LootItemRandomChanceCondition.randomChance(0.3F).build()
                })
        );
//        this.add("infernum_smelting", ModLootModifiers.INFERNUM_SMELTING.get(),
//                new InfernumPickaxeLootModifier(new LootItemCondition[] {
//                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModItems.INFERNUM_PICKAXE.get())).build()
//                })
//        );
        this.add("magical_farmland_crop_doubling", ModLootModifiers.MAGICAL_FARMLAND.get(), new MagicalFarmlandLootModifier(new LootItemCondition[]{}));
    }
}
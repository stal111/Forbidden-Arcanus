package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.valhelsia.valhelsia_core.init.ValhelsiaLootModifiers;
import net.valhelsia.valhelsia_core.util.AppendLootTableModifier;

/**
 * Mod Loot Modifiers
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.loot.ModLootModifierProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-04-10
 */
public class ModLootModifierProvider extends GlobalLootModifierProvider {

    public ModLootModifierProvider(DataGenerator gen) {
        super(gen, ForbiddenArcanus.MOD_ID);
    }

    @Override
    protected void start() {
        add("zombie_additions",
                ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new ILootCondition[] {
                        RandomChance.builder(0.002F).build(),
                        LootTableIdCondition.builder(new ResourceLocation("entities/zombie")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/zombie_additions"))
        );
        add("drowned_additions",
                ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new ILootCondition[] {
                        RandomChance.builder(0.002F).build(),
                        LootTableIdCondition.builder(new ResourceLocation("entities/drowned")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "entities/additions/drowned_additions"))
        );
        add("spawner_additions",
                ValhelsiaLootModifiers.APPEND_LOOT_MODIFIER.get(),
                new AppendLootTableModifier(new ILootCondition[] {
                        MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.exactly(1)))).build(),
                        LootTableIdCondition.builder(new ResourceLocation("blocks/spawner")).build()
                }, new ResourceLocation(ForbiddenArcanus.MOD_ID, "blocks/additions/spawner_additions"))
        );
    }
}

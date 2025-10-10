package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * @author stal111
 * @since 01.06.2024
 */
public record ForgeDataCache(ArrayList<IngredientEntry> cachedIngredients, ItemStack mainIngredient, List<Holder<EnhancerDefinition>> enhancers) {

    public static final Codec<ForgeDataCache> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IngredientEntry.CODEC.listOf().xmap(ArrayList::new, UnaryOperator.identity()).fieldOf("ingredients").forGetter(ForgeDataCache::cachedIngredients),
            ItemStack.OPTIONAL_CODEC.fieldOf("main_ingredient").forGetter(ForgeDataCache::mainIngredient),
            EnhancerDefinition.CODEC.listOf().fieldOf("enhancers").forGetter(ForgeDataCache::enhancers)
    ).apply(instance, ForgeDataCache::new));

    public ForgeDataCache setMainIngredient(ItemStack mainIngredient) {
        return new ForgeDataCache(this.cachedIngredients, mainIngredient, this.enhancers);
    }

    public ForgeDataCache setEnhancers(List<Holder<EnhancerDefinition>> enhancers) {
        return new ForgeDataCache(this.cachedIngredients, this.mainIngredient, enhancers);
    }

    public HolderSet<EnhancerDefinition> getEnhancers() {
        return HolderSet.direct(UnaryOperator.identity(), this.enhancers);
    }

    public void setIngredient(BlockPos pos, ItemStack stack) {
        this.cachedIngredients.removeIf(entry -> entry.pos().equals(pos));

        if (!stack.isEmpty()) {
            this.cachedIngredients.add(new IngredientEntry(pos, stack));
        }
    }

    public List<ItemStack> getIngredients() {
        return this.cachedIngredients.stream().map(IngredientEntry::stack).toList();
    }

    public record IngredientEntry(BlockPos pos, ItemStack stack) {
        public static final Codec<IngredientEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPos.CODEC.fieldOf("pos").forGetter(IngredientEntry::pos),
                ItemStack.SINGLE_ITEM_CODEC.fieldOf("stack").forGetter(IngredientEntry::stack)
        ).apply(instance, IngredientEntry::new));
    }
}

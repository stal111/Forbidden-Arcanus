package com.stal111.forbidden_arcanus.common.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2021-09-25
 */
public class MagicalFarmlandLootModifier extends LootModifier {

    public static final MapCodec<MagicalFarmlandLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> codecStart(instance).apply(instance, MagicalFarmlandLootModifier::new));

    public MagicalFarmlandLootModifier(LootItemCondition[] conditions, int priority) {
        super(conditions, priority);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) {
            return generatedLoot;
        }

        BlockState state = context.getOptionalParameter(LootContextParams.BLOCK_STATE);
        Vec3 pos = context.getOptionalParameter(LootContextParams.ORIGIN);

        if (state == null || pos == null || !state.is(BlockTags.CROPS) || state.is(ModTags.Blocks.MAGICAL_FARMLAND_BLACKLISTED)) {
            return generatedLoot;
        }

        if (state.getBlock() instanceof CropBlock crop && !crop.isMaxAge(state)) {
            return generatedLoot;
        }

        Level level = context.getLevel();


        if (level.getBlockState(BlockPos.containing(pos).below()).is(ModBlocks.MAGICAL_FARMLAND.get())) {
            for (int i = 0; i < generatedLoot.size(); i++) {
                ItemStack stack = generatedLoot.get(i);

                if (!stack.is(ModTags.Items.MAGICAL_FARMLAND_BLACKLISTED)) {
                    generatedLoot.add(stack.copy());
                }
            }
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}

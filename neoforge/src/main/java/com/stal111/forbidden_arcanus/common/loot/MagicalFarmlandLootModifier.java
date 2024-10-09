package com.stal111.forbidden_arcanus.common.loot;

import com.google.common.base.Suppliers;
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
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2021-09-25
 */
public class MagicalFarmlandLootModifier extends LootModifier {

    public static final Supplier<MapCodec<MagicalFarmlandLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(instance -> codecStart(instance).apply(instance, MagicalFarmlandLootModifier::new)));

    /**
     * Constructs a LootModifier.
     *
     * @param conditions the ILootConditions that need to be matched before the loot is modified.
     */
    public MagicalFarmlandLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        Vec3 pos = context.getParamOrNull(LootContextParams.ORIGIN);
        Level level = context.getLevel();

        if (state == null || pos == null || !state.is(BlockTags.CROPS) || state.is(ModTags.Blocks.MAGICAL_FARMLAND_BLACKLISTED)) {
            return generatedLoot;
        }

        if (level.getBlockState(BlockPos.containing(pos).below()).is(ModBlocks.MAGICAL_FARMLAND.get())) {
            if (state.getBlock() instanceof CropBlock crop && !crop.isMaxAge(state)) {
                return generatedLoot;
            }
            generatedLoot.addAll(generatedLoot.stream().filter(stack -> !stack.is(ModTags.Items.MAGICAL_FARMLAND_BLACKLISTED)).toList());
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}

package com.stal111.forbidden_arcanus.common.loot;

import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Magical Farmland Loot Modifier <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.loot.MagicalFarmlandLootModifier
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-09-25
 */
public class MagicalFarmlandLootModifier extends LootModifier {

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
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        Vec3 pos = context.getParamOrNull(LootContextParams.ORIGIN);
        Level world = context.getLevel();

        if (state == null || pos == null || !BlockTags.CROPS.contains(state.getBlock()) || ModTags.Blocks.MAGICAL_FARMLAND_BLACKLISTED.contains(state.getBlock())) {
            return generatedLoot;
        }

        if (world.getBlockState(new BlockPos(pos).below()).getBlock() == ModBlocks.MAGICAL_FARMLAND.get()) {
            if (state.getBlock() instanceof CropBlock && !((CropBlock) state.getBlock()).isMaxAge(state)) {
                return generatedLoot;
            }
            generatedLoot.addAll(new ArrayList<>(generatedLoot).stream().filter(stack -> !ModTags.Items.MAGICAL_FARMLAND_BLACKLISTED.contains(stack.getItem())).collect(Collectors.toList()));
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<MagicalFarmlandLootModifier> {

        @Override
        public MagicalFarmlandLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            return new MagicalFarmlandLootModifier(conditions);
        }

        @Override
        public JsonObject write(MagicalFarmlandLootModifier instance) {
            return super.makeConditions(instance.conditions);
        }
    }
}

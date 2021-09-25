package com.stal111.forbidden_arcanus.common.loot;

import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
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
    public MagicalFarmlandLootModifier(ILootCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.get(LootParameters.BLOCK_STATE);
        Vector3d pos = context.get(LootParameters.field_237457_g_);
        World world = context.getWorld();

        if (state == null || pos == null || !BlockTags.CROPS.contains(state.getBlock()) || ModTags.Blocks.MAGICAL_FARMLAND_BLACKLISTED.contains(state.getBlock())) {
            return generatedLoot;
        }

        if (world.getBlockState(new BlockPos(pos).down()).getBlock() == ModBlocks.MAGICAL_FARMLAND.getBlock()) {
            if (state.getBlock() instanceof CropsBlock && !((CropsBlock) state.getBlock()).isMaxAge(state)) {
                return generatedLoot;
            }
            generatedLoot.addAll(new ArrayList<>(generatedLoot).stream().filter(stack -> !ModTags.Items.MAGICAL_FARMLAND_BLACKLISTED.contains(stack.getItem())).collect(Collectors.toList()));
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<MagicalFarmlandLootModifier> {

        @Override
        public MagicalFarmlandLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            return new MagicalFarmlandLootModifier(conditions);
        }

        @Override
        public JsonObject write(MagicalFarmlandLootModifier instance) {
            return super.makeConditions(instance.conditions);
        }
    }
}

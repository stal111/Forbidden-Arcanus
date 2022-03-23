package com.stal111.forbidden_arcanus.common.loot;

import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Magical Farmland Loot Modifier <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.loot.MagicalFarmlandLootModifier
 *
 * @author stal111
 * @version 1.18.2 - 2.0.0
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
        Level level = context.getLevel();

        if (state == null || pos == null || !state.is(BlockTags.CROPS) || state.is(ModTags.Blocks.MAGICAL_FARMLAND_BLACKLISTED)) {
            return generatedLoot;
        }

        if (level.getBlockState(new BlockPos(pos).below()).is(ModBlocks.MAGICAL_FARMLAND.get())) {
            if (state.getBlock() instanceof CropBlock crop && !crop.isMaxAge(state)) {
                return generatedLoot;
            }
            generatedLoot.addAll(generatedLoot.stream().filter(stack -> !stack.is(ModTags.Items.MAGICAL_FARMLAND_BLACKLISTED)).toList());
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

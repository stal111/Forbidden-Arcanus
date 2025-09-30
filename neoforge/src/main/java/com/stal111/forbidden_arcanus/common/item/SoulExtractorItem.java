package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * Soul Extractor Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.SoulExtractorItem
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class SoulExtractorItem extends Item {

    private static final int USE_DURATION = 35;

    public SoulExtractorItem(Item.Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (player == null || player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }

        if (this.isValidBlock(level, pos, player)) {
            player.startUsingItem(context.getHand());

            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player player) || player.isShiftKeyDown()) {
            return stack;
        }

        BlockPos pos = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY).getBlockPos();

        if (!this.isValidBlock(level, pos, player)) {
            return stack;
        }

        RandomSource random = player.getRandom();

        stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        player.awardStat(Stats.ITEM_USED.get(this));

        for (int i = 0; i < 4; i++) {
            level.addParticle(ModParticles.SOUL.get(), pos.getX() + random.nextFloat(), pos.getY() + 1, pos.getZ() + random.nextFloat(), 1, 1, 1);
        }

        if (!level.isClientSide()) {
            level.setBlockAndUpdate(pos, ModBlocks.SOULLESS_SAND.get().defaultBlockState());
            level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, new ItemStack(ModItems.SOUL.get())));
        }

        return stack;
    }

    /**
     * Checks if player interactions are allowed at the targeted pos and whether the block is Soul Sand.
     */
    private boolean isValidBlock(Level level, BlockPos pos, Player player) {
        return level.mayInteract(player, pos) && level.getBlockState(pos).is(Blocks.SOUL_SAND);
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack stack, int remainingUseDuration) {
        if (!(livingEntity instanceof Player player) || player.isShiftKeyDown()) {
            livingEntity.stopUsingItem();

            return;
        }

        BlockPos pos = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY).getBlockPos();

        if (this.isValidBlock(level, pos, player) && player.getRandom().nextInt(6) == 1) {
            level.levelEvent(player, 2001, pos, Block.getId(level.getBlockState(pos)));
        }
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Nonnull
    @Override
    public ItemUseAnimation getUseAnimation(@Nonnull ItemStack stack) {
        return ItemUseAnimation.BOW;
    }


    @Override
    public boolean canDestroyBlock(ItemStack stack, BlockState state, Level level, BlockPos pos, LivingEntity entity) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        tooltipAdder.accept(Component.translatable("tooltip." + ForbiddenArcanus.MOD_ID + ".soul_extractor").withStyle(ChatFormatting.GRAY));
    }
}

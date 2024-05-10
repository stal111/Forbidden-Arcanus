package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.item.component.StoredEntity;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * @author stal111
 */
public class QuantumCatcherItem extends Item {

    public QuantumCatcherItem(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        return getData(stack).map(storedEntity -> {
            BlockPos pos = context.getClickedPos();
            Player player = context.getPlayer();

            Entity entity = storedEntity.createEntity(level);

            if (!level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) {
                pos = pos.relative(context.getClickedFace());
            }

            if (entity == null || !level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) {
                return InteractionResult.FAIL;
            }

            entity.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);

            if (context.getPlayer() != null) {
                entity.lookAt(EntityAnchorArgument.Anchor.EYES, context.getPlayer().position());
            }
            level.addFreshEntity(entity);

            clearEntity(level, player, player == null ? pos : player.blockPosition(), stack);

            return InteractionResult.CONSUME;
        }).orElse(super.useOn(context));
    }

    public InteractionResult onEntityInteract(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        Level level = player.getCommandSenderWorld();

        if (target instanceof Player || target.getType().is(ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED)) {
            return InteractionResult.PASS;
        }


        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (getData(stack).isEmpty() && target.isAlive()) {
            if (stack.getCount() != 1) {
                ItemStackUtils.shrinkStack(player, stack);

                ItemStack newStack = new ItemStack(ModItems.QUANTUM_CATCHER.get());
                setEntity(level, player, target, newStack);

                if (!player.addItem(newStack)) {
                    player.drop(newStack, false);
                }
            } else {
                setEntity(level, player, target, stack);
            }

            target.discard();

            player.swing(hand);

            return InteractionResult.CONSUME;
        }

        return super.interactLivingEntity(stack, player, target, hand);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        getData(stack).ifPresent(storedEntity -> {
            storedEntity.addToTooltip(context, components::add, flag);
        });
    }

    @Override
    public boolean shouldCauseReequipAnimation(@NotNull ItemStack oldStack, @NotNull ItemStack newStack, boolean slotChanged) {
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    private static void setEntity(Level level, Player player, LivingEntity entity, ItemStack stack) {
        stack.set(ModDataComponents.STORED_ENTITY, StoredEntity.of(entity));

        level.playSound(player, player.getX() + 0.5D, player.getY() + 0.5D, player.getZ() + 0.5D, ModSounds.QUANTUM_CATCHER_PICK_UP.get(), SoundSource.PLAYERS, 0.75F, level.getRandom().nextFloat() * 0.15F + 0.9F);
    }

    private static Optional<StoredEntity> getData(ItemStack stack) {
        StoredEntity storedEntity = stack.get(ModDataComponents.STORED_ENTITY);

       return storedEntity == StoredEntity.EMPTY ? Optional.empty() : Optional.ofNullable(storedEntity);
    }

    private static void clearEntity(Level level, @Nullable Player player, BlockPos pos, ItemStack stack) {
        level.playSound(player, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, ModSounds.QUANTUM_CATCHER_RELEASE.get(), SoundSource.PLAYERS, 0.75F, level.getRandom().nextFloat() * 0.15F + 0.9F);

        stack.set(ModDataComponents.STORED_ENTITY, StoredEntity.EMPTY);
    }
}
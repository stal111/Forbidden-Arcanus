package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Quantum Catcher Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.QuantumCatcherItem
 *
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
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (this.getEntity(stack, level) != null) {
            Entity entity = this.getEntity(stack, level);

            if (!level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) {
                pos = pos.relative(context.getClickedFace());
            }

            if (entity == null || !level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) {
                return InteractionResult.FAIL;
            }

            if (!level.isClientSide()) {
                entity.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);

                if (context.getPlayer() != null) {
                    entity.lookAt(EntityAnchorArgument.Anchor.EYES, context.getPlayer().position());
                }
                level.addFreshEntity(entity);
            }

            this.clearEntity(level, player, player == null ? pos : player.blockPosition(), stack);

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useOn(context);
    }

    public InteractionResult onEntityInteract(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        Level level = player.getCommandSenderWorld();

        if (target instanceof Player || target.getType().is(ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED)) {
            return InteractionResult.PASS;
        }

        if (this.getEntity(stack, level) == null && target.isAlive()) {
            if (stack.getCount() != 1) {
                ItemStackUtils.shrinkStack(player, stack);

                ItemStack newStack = new ItemStack(ModItems.QUANTUM_CATCHER.get());
                this.setEntity(level, player, target, newStack);

                if (!player.addItem(newStack)) {
                    player.drop(newStack, false);
                }
            } else {
                this.setEntity(level, player, target, stack);
            }

            target.discard();

            player.swing(hand);

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.interactLivingEntity(stack, player, target, hand);
    }

    //TODO
//    @Override
//    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
//        if (level != null && getEntity(stack, level) != null)  {
//            Entity entity = this.getEntity(stack, level);
//
//            if (entity == null) {
//                return;
//            }
//
//            MutableComponent textComponent = Component.translatable("tooltip.forbidden_arcanus.entity")
//                    .append(": ")
//                    .append(Component.literal(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()));
//
//            if (this.getEntityName(stack) != null)  {
//                textComponent.append(" (").append(Objects.requireNonNull(this.getEntityName(stack))).append(")");
//            }
//
//            textComponent.withStyle(ChatFormatting.GRAY);
//
//            tooltip.add(textComponent);
//        }
//    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    private void setEntity(Level level, Player player, Entity entity, ItemStack stack) {
        entity.stopRiding();
        entity.ejectPassengers();

        CompoundTag entityTag = new CompoundTag();
        ResourceLocation name = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());

        entityTag.putString("entity", name.toString());
        if (entity.hasCustomName()) {
            entityTag.putString("name", Objects.requireNonNull(entity.getCustomName()).getString());
        }
        entity.save(entityTag);

        //TODO
//        CompoundTag itemNBT = stack.getOrCreateTag();
//        itemNBT.put("entity", entityTag);

        level.playSound(player, player.getX() + 0.5D, player.getY() + 0.5D, player.getZ() + 0.5D, ModSounds.QUANTUM_CATCHER_PICK_UP.get(), SoundSource.PLAYERS, 0.75F, level.getRandom().nextFloat() * 0.15F + 0.9F);
    }

    private Entity getEntity(ItemStack stack, Level level) {
        //TODO
        CompoundTag itemTag = null;

        if (itemTag == null) {
            return null;
        }

        CompoundTag entityTag = itemTag.getCompound("entity");
        EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(entityTag.getString("entity")));

        Entity entity = entityType.create(level);

        if (level instanceof ServerLevel && entity != null) {
            entity.load(entityTag);
        }

        return entity;
    }

    private Component getEntityName(ItemStack stack) {
        //TODO
        CompoundTag itemTag = null;

        if (itemTag == null) {
            return null;
        }

        if (itemTag.contains("entity")) {
            CompoundTag entityTag = itemTag.getCompound("entity");

            if (entityTag.contains("name")) {
                return Component.literal(entityTag.getString("name"));
            }
        }
        return null;
    }

    private void clearEntity(Level level, @Nullable Player player, BlockPos pos, ItemStack stack) {
        level.playSound(player, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, ModSounds.QUANTUM_CATCHER_RELEASE.get(), SoundSource.PLAYERS, 0.75F, level.getRandom().nextFloat() * 0.15F + 0.9F);

        //stack.setTag(null);
    }
}
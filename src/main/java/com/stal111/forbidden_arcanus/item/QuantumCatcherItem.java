package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

/**
 * Quantum Catcher Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.QuantumCatcherItem
 *
 * @author stal111
 * @version 2.0.0
 */
public class QuantumCatcherItem extends Item {

    public QuantumCatcherItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();

        if (this.getEntity(stack, world) != null) {
            Entity entity = this.getEntity(stack, world);

            if (!world.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) {
                pos = pos.relative(context.getClickedFace());
            }

            if (entity == null || !world.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) {
                return InteractionResult.FAIL;
            }

            if (!world.isClientSide()) {
                entity.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);

                if (context.getPlayer() != null) {
                    entity.lookAt(EntityAnchorArgument.Anchor.EYES, context.getPlayer().position());
                }
                world.addFreshEntity(entity);
            }

            this.clearEntity(stack);

            return InteractionResult.sidedSuccess(world.isClientSide());
        }

        return super.useOn(context);
    }

    public InteractionResult onEntityInteract(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        Level world = player.getCommandSenderWorld();

        if (target instanceof Player || ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED.contains(target.getType())) {
            return InteractionResult.PASS;
        }

        if (this.getEntity(stack, world) == null && target.isAlive()) {
            if (stack.getCount() != 1) {
                ItemStackUtils.shrinkStack(player, stack);

                ItemStack newStack = new ItemStack(ModItems.QUANTUM_CATCHER.get());
                this.setEntity(target, newStack);

                if (!player.addItem(newStack)) {
                    player.drop(newStack, false);
                }
            } else {
                this.setEntity(target, stack);
            }

            target.remove(Entity.RemovalReason.DISCARDED);

            return InteractionResult.sidedSuccess(world.isClientSide());
        }

        return super.interactLivingEntity(stack, player, target, hand);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level world, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (world != null && getEntity(stack, world) != null)  {
            Entity entity = this.getEntity(stack, world);

            if (entity != null) {
                MutableComponent textComponent = new TranslatableComponent("tooltip.forbidden_arcanus.entity").append(": ").append(new TextComponent(Objects.requireNonNull(entity.getType().getRegistryName()).toString()));

                if (this.getEntityName(stack) != null)  {
                    textComponent.append(" (").append(Objects.requireNonNull(this.getEntityName(stack))).append(")");
                }

                textComponent.withStyle(ChatFormatting.GRAY);

                tooltip.add(textComponent);
            }
        }
    }

    private void setEntity(Entity entity, ItemStack stack) {
        entity.stopRiding();
        entity.ejectPassengers();

        CompoundTag entityNBT = new CompoundTag();

        if (entity.getType().getRegistryName() == null) return;

        entityNBT.putString("entity", entity.getType().getRegistryName().toString());
        if (entity.hasCustomName()) {
            entityNBT.putString("name", Objects.requireNonNull(entity.getCustomName()).getString());
        }
        entity.save(entityNBT);

        CompoundTag itemNBT = stack.getOrCreateTag();
        itemNBT.put("entity", entityNBT);
    }

    private Entity getEntity(ItemStack stack, Level world) {
        CompoundTag itemNBT = stack.getTag();

        if (itemNBT == null) return null;

        CompoundTag entityNBT = itemNBT.getCompound("entity");
        EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entityNBT.getString("entity")));

        if (entityType == null) return null;

        Entity entity = entityType.create(world);

        if (world instanceof ServerLevel && entity != null) {
            entity.load(entityNBT);
        }

        return entity;
    }

    private Component getEntityName(ItemStack stack) {
        CompoundTag itemNBT = stack.getTag();

        if (itemNBT == null) return null;

        if (itemNBT.contains("entity")) {
            CompoundTag entityNBT = itemNBT.getCompound("entity");

            if (entityNBT.contains("name")) {
                return new TextComponent(entityNBT.getString("name"));
            }
        }
        return null;
    }

    private void clearEntity(ItemStack stack) {
        stack.setTag(null);
    }
}
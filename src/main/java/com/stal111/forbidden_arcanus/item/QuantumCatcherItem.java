package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.util.ItemStackUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

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
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack stack = context.getItem();
        BlockPos pos = context.getPos();
        World world = context.getWorld();

        if (this.getEntity(stack, world) != null) {
            Entity entity = this.getEntity(stack, world);

            if (!world.getBlockState(pos).isReplaceable(new BlockItemUseContext(context))) {
                pos = pos.offset(context.getFace());
            }

            if (entity == null || !world.getBlockState(pos).isReplaceable(new BlockItemUseContext(context))) {
                return ActionResultType.FAIL;
            }

            if (!world.isRemote()) {
                entity.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);

                if (context.getPlayer() != null) {
                    entity.lookAt(EntityAnchorArgument.Type.EYES, context.getPlayer().getPositionVec());
                }
                world.addEntity(entity);
            }

            this.clearEntity(stack);

            return ActionResultType.func_233537_a_(world.isRemote());
        }

        return super.onItemUse(context);
    }

    public ActionResultType onEntityInteract(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        World world = player.getEntityWorld();

        if (target instanceof PlayerEntity || ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED.contains(target.getType())) {
            return ActionResultType.PASS;
        }

        if (this.getEntity(stack, world) == null && target.isAlive()) {
            if (stack.getCount() != 1) {
                ItemStackUtils.shrinkStack(player, stack);

                ItemStack newStack = new ItemStack(ModItems.QUANTUM_CATCHER.get());
                this.setEntity(target, newStack);

                if (!player.addItemStackToInventory(newStack)) {
                    player.dropItem(newStack, false);
                }
            } else {
                this.setEntity(target, stack);
            }

            target.remove(true);

            return ActionResultType.func_233537_a_(world.isRemote());
        }

        return super.itemInteractionForEntity(stack, player, target, hand);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        if (world != null && getEntity(stack, world) != null)  {
            Entity entity = this.getEntity(stack, world);

            if (entity != null) {
                IFormattableTextComponent textComponent = new TranslationTextComponent("tooltip.forbidden_arcanus.entity").appendString(": ").append(new StringTextComponent(Objects.requireNonNull(entity.getType().getRegistryName()).toString()));

                if (this.getEntityName(stack) != null)  {
                    textComponent.appendString(" (").append(Objects.requireNonNull(this.getEntityName(stack))).appendString(")");
                }

                textComponent.mergeStyle(TextFormatting.GRAY);

                tooltip.add(textComponent);
            }
        }
    }

    private void setEntity(Entity entity, ItemStack stack) {
        entity.stopRiding();
        entity.removePassengers();

        CompoundNBT entityNBT = new CompoundNBT();

        if (entity.getType().getRegistryName() == null) return;

        entityNBT.putString("entity", entity.getType().getRegistryName().toString());
        if (entity.hasCustomName()) {
            entityNBT.putString("name", Objects.requireNonNull(entity.getCustomName()).getString());
        }
        entity.writeUnlessPassenger(entityNBT);

        CompoundNBT itemNBT = stack.getOrCreateTag();
        itemNBT.put("entity", entityNBT);
    }

    private Entity getEntity(ItemStack stack, World world) {
        CompoundNBT itemNBT = stack.getTag();

        if (itemNBT == null) return null;

        CompoundNBT entityNBT = itemNBT.getCompound("entity");
        EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entityNBT.getString("entity")));

        if (entityType == null) return null;

        Entity entity = entityType.create(world);

        if (world instanceof ServerWorld && entity != null) {
            entity.read(entityNBT);
        }

        return entity;
    }

    private ITextComponent getEntityName(ItemStack stack) {
        CompoundNBT itemNBT = stack.getTag();

        if (itemNBT == null) return null;

        if (itemNBT.contains("entity")) {
            CompoundNBT entityNBT = itemNBT.getCompound("entity");

            if (entityNBT.contains("name")) {
                return new StringTextComponent(entityNBT.getString("name"));
            }
        }
        return null;
    }

    private void clearEntity(ItemStack stack) {
        stack.setTag(null);
    }
}
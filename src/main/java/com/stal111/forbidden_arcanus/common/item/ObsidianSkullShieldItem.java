package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.renderer.item.ObsidianSkullShieldItemRenderer;
import com.stal111.forbidden_arcanus.common.item.counter.ObsidianSkullCounter;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.NonNullLazy;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterCapability;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterImpl;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Obsidian Skull Shield Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.ObsidianSkullShieldItem
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-12
 */
public class ObsidianSkullShieldItem extends Item {

    private static final ResourceLocation COUNTER = new ResourceLocation(ForbiddenArcanus.MOD_ID, "tick_counter");

    private static final int USE_DURATION = 72000;

    public ObsidianSkullShieldItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
    }

    @Nonnull
    @Override
    public UseAnim getUseAnimation(@Nonnull ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return USE_DURATION;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        player.startUsingItem(hand);

        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof LivingEntity livingEntity) {
            stack.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
                CompoundTag tag = new CompoundTag();

                if (livingEntity.getLastDamageSource() != null) {
                    tag.putString("DamageSource", livingEntity.getLastDamageSource().getMsgId());
                }

                this.getCounter(counterCapability).tick(tag);
            });
        }
        super.inventoryTick(stack, level, entity, itemSlot, isSelected);
    }

    public static boolean shouldProtectFromDamage(DamageSource damageSource, Inventory inventory) {
        if (!ObsidianSkullItem.DAMAGE_SOURCES.contains(damageSource)) {
            return false;
        }

        int slot = -1;

        for(int i = 0; i < inventory.items.size(); ++i) {
            if (!inventory.items.get(i).isEmpty() && ItemStack.isSame(ModItems.Stacks.OBSIDIAN_SKULL_SHIELD, inventory.items.get(i))) {
                slot = i;
            }
        }

        if (slot != -1) {
            CounterCapability counterCapability = inventory.getItem(slot).getCapability(CounterProvider.CAPABILITY).orElse(new CounterImpl());

            return counterCapability.getCounter(COUNTER).getValue() < ObsidianSkullItem.OBSIDIAN_SKULL_PROTECTION_TIME;
        }

        return false;
    }

    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, ItemStack repair) {
        return repair.is(ModItems.OBSIDIAN_INGOT.get()) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".obsidian_skull_shield").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            private final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> new ObsidianSkullShieldItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer.get();
            }
        });
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag) {
        return new CounterProvider(Collections.singletonList(new ObsidianSkullCounter(COUNTER)));
    }

    private SimpleCounter getCounter(CounterCapability counterCapability) {
        return counterCapability.getCounter(COUNTER);
    }
}

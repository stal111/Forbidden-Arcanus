package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.renderer.item.ObsidianSkullShieldItemRenderer;
import com.stal111.forbidden_arcanus.common.item.counter.ObsidianSkullCounter;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.NonNullLazy;
import net.valhelsia.valhelsia_core.api.common.counter.SerializableCounter;
import net.valhelsia.valhelsia_core.api.common.counter.capability.CounterCapability;
import net.valhelsia.valhelsia_core.api.common.counter.capability.CounterCreator;
import net.valhelsia.valhelsia_core.api.common.counter.capability.CounterImpl;
import net.valhelsia.valhelsia_core.api.common.counter.capability.CounterProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author stal111
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
        if (!damageSource.is(DamageTypeTags.IS_FIRE)) {
            return false;
        }

        if (inventory.contains(ModItems.Stacks.ETERNAL_OBSIDIAN_SKULL)) {
            return true;
        }

        ItemStack stack = getSkullWithLowestCounter(inventory);

        if (stack.isEmpty()) {
            return false;
        }

        return getCounterValue(stack) < ObsidianSkullItem.OBSIDIAN_SKULL_PROTECTION_TIME;
    }

    public static ItemStack getSkullWithLowestCounter(Inventory inventory) {
        ItemStack skull = ItemStack.EMPTY;

        for (NonNullList<ItemStack> nonNullList : inventory.compartments) {
            for (ItemStack stack : nonNullList) {
                if (!stack.isEmpty() && stack.is(ModItems.OBSIDIAN_SKULL_SHIELD.get())) {
                    if (skull.isEmpty() || getCounterValue(skull) > getCounterValue(stack)) {
                        skull = stack;
                    }
                }
            }
        }

        return skull;
    }

    public static int getCounterValue(ItemStack stack) {
        return stack.getCapability(CounterProvider.CAPABILITY).orElse(new CounterImpl()).getCounter(COUNTER).getValue();
    }

    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, ItemStack repair) {
        return repair.is(ModItems.OBSIDIAN_INGOT.get()) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip." + ForbiddenArcanus.MOD_ID + ".obsidian_skull_shield").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void initializeClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> new ObsidianSkullShieldItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer.get();
            }
        });
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag) {
        return new CounterProvider(CounterCreator.of(ObsidianSkullCounter::new, COUNTER));
    }

    private SerializableCounter getCounter(CounterCapability counterCapability) {
        return counterCapability.getCounter(COUNTER);
    }
}

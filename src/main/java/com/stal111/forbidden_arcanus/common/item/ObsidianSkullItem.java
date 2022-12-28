package com.stal111.forbidden_arcanus.common.item;

import com.google.common.collect.ImmutableList;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.renderer.item.ObsidianSkullItemRenderer;
import com.stal111.forbidden_arcanus.common.item.counter.ObsidianSkullCounter;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.NonNullLazy;
import net.valhelsia.valhelsia_core.common.capability.counter.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author stal111
 * @since 2021-02-11
 */
public class ObsidianSkullItem extends StandingAndWallBlockItem {

    public static final int OBSIDIAN_SKULL_PROTECTION_TIME = 600;

    private static final ResourceLocation COUNTER = new ResourceLocation(ForbiddenArcanus.MOD_ID, "tick_counter");

    public static final List<DamageSource> DAMAGE_SOURCES = ImmutableList.of(DamageSource.LAVA, DamageSource.IN_FIRE, DamageSource.ON_FIRE, DamageSource.HOT_FLOOR);

    private final boolean eternal;

    public ObsidianSkullItem(Block floorBlock, Block wallBlock, boolean eternal, Properties properties) {
        super(floorBlock, wallBlock, properties, Direction.DOWN);
        this.eternal = eternal;
    }

    public static DispenseItemBehavior getDispenseBehavior() {
        return new OptionalDispenseItemBehavior() {
            @Nonnull
            @Override
            protected ItemStack execute(@Nonnull BlockSource source, @Nonnull ItemStack stack) {
                this.setSuccess(ArmorItem.dispenseArmor(source, stack));
                return stack;
            }
        };
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof LivingEntity livingEntity && !this.eternal) {
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
        if (!DAMAGE_SOURCES.contains(damageSource)) {
            return false;
        }

        if (inventory.contains(ModItems.Stacks.ETERNAL_OBSIDIAN_SKULL)) {
            return true;
        }

        ItemStack stack = getSkullWithLowestCounter(inventory);

        if (stack.isEmpty()) {
            return false;
        }

        return getCounterValue(stack) < OBSIDIAN_SKULL_PROTECTION_TIME;
    }

    public static ItemStack getSkullWithLowestCounter(Inventory inventory) {
        ItemStack skull = ItemStack.EMPTY;

        for (NonNullList<ItemStack> nonNullList : inventory.compartments) {
            for (ItemStack stack : nonNullList) {
                if (!stack.isEmpty() && stack.is(ModItems.OBSIDIAN_SKULL.get())) {
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
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip." + ForbiddenArcanus.MOD_ID + (eternal ? ".eternal_" : ".") + "obsidian_skull").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void initializeClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> new ObsidianSkullItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer.get();
            }
        });
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag) {
        if (!this.eternal) {
            return new CounterProvider(CounterCreator.of(ObsidianSkullCounter::new, COUNTER));
        }
        return null;
    }

    private SimpleCounter getCounter(CounterCapability counterCapability) {
        return counterCapability.getCounter(COUNTER);
    }
}

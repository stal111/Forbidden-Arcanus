package com.stal111.forbidden_arcanus.common.block.skull;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SkullBlock;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * @author stal111
 * @since 10.09.2023
 */
public enum ObsidianSkullType implements SkullBlock.Type, StringRepresentable {
    DEFAULT("obsidian_skull", TickFunctions.DEFAULT, entity -> true),
    CRACKED("cracked_obsidian_skull", TickFunctions.DEFAULT, entity -> true),
    FRAGMENTED("fragmented_obsidian_skull", TickFunctions.DEFAULT, entity -> true),
    FADING("fading_obsidian_skull", TickFunctions.DEFAULT, entity -> true),
    AUREALIC("aurealic_obsidian_skull", TickFunctions.AUREALIC, entity -> {
        return EssenceHelper.getEssenceProvider(entity).map(provider -> provider.getAmount(EssenceType.AUREAL) > 0).orElse(false);
    }),
    ETERNAL("eternal_obsidian_skull", TickFunctions.EMPTY, entity -> true);

    private final String name;
    private final TickFunction tickFunction;
    private final Predicate<LivingEntity> shouldProtect;

    ObsidianSkullType(String name, TickFunction tickFunction, Predicate<LivingEntity> shouldProtect) {
        this.name = name;
        this.tickFunction = tickFunction;
        this.shouldProtect = shouldProtect;
    }

    public ResourceLocation getTextureLocation() {
        return ForbiddenArcanus.location("textures/block/obsidian_skull/" + this.getSerializedName() + ".png");
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public void tick(ItemStack stack, Player player) {
        this.tickFunction.tick(this, stack, player);
    }

    public boolean shouldProtect(LivingEntity livingEntity) {
        return this.shouldProtect.test(livingEntity);
    }

    public static class TickFunctions {

        public static final UniformInt STAGE_DURATION = UniformInt.of(8, 13);

        public static final TickFunction EMPTY = (type, stack, player) -> {};

        public static final TickFunction DEFAULT = (type, stack, player) -> {
            int remainingTicks = stack.getOrDefault(ModDataComponents.TICKS_TILL_NEXT_STAGE, STAGE_DURATION.sample(player.getRandom()) * 20);

            remainingTicks--;

            if (remainingTicks <= 0) {
                player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObsidianSkullItem.NEXT_SKULL_STAGE.get(type)));

                player.level().playSound(player, player.blockPosition(), ModSounds.OBSIDIAN_SKULL_CRACK.get(), player.getSoundSource(), 0.9F, player.level().getRandom().nextFloat() * 0.15F + 0.9F);

                return;
            }

            stack.set(ModDataComponents.TICKS_TILL_NEXT_STAGE, remainingTicks);
        };

        public static final TickFunction AUREALIC = (type, stack, player) -> {
            EssenceHelper.getEssenceProvider(player).ifPresent(provider -> {
                provider.updateAmount(EssenceType.AUREAL, amount -> amount - 1);
            });
        };
    }

    @FunctionalInterface
    public interface TickFunction {
        void tick(ObsidianSkullType type, ItemStack stack, Player player);
    }
}

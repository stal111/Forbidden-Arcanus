package com.stal111.forbidden_arcanus.common.block.skull;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
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
    DEFAULT("obsidian_skull", UpdateFunctions.DEFAULT, entity -> true),
    CRACKED("cracked_obsidian_skull", UpdateFunctions.DEFAULT, entity -> true),
    FRAGMENTED("fragmented_obsidian_skull", UpdateFunctions.DEFAULT, entity -> true),
    FADING("fading_obsidian_skull", UpdateFunctions.DEFAULT, entity -> true),
    AUREALIC("aurealic_obsidian_skull", UpdateFunctions.AUREALIC, entity -> entity.getCapability(AurealProvider.CAPABILITY).map(aureal -> aureal.getAureal() > 0).orElse(false)),
    ETERNAL("eternal_obsidian_skull", UpdateFunctions.EMPTY, entity -> true);

    private final String name;
    private UpdateFunction updateFunction;
    private Predicate<LivingEntity> shouldProtect;

    ObsidianSkullType(String name, UpdateFunction updateFunction, Predicate<LivingEntity> shouldProtect) {
        this.name = name;
        this.updateFunction = updateFunction;
        this.shouldProtect = shouldProtect;
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/obsidian_skull/" + this.getSerializedName() + ".png");
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public void tick(ItemStack stack, Player player) {
        this.updateFunction.tick(this, stack, player);
    }

    public boolean shouldProtect(LivingEntity livingEntity) {
        return this.shouldProtect.test(livingEntity);
    }

    public static class UpdateFunctions {

        public static final UniformInt STAGE_DURATION = UniformInt.of(8, 13);
        private static final String TAG_REMAINING_TICKS = "remaining_ticks";

        public static final UpdateFunction EMPTY = (type, stack, player) -> {};

        public static final UpdateFunction DEFAULT = (type, stack, player) -> {
            CompoundTag tag = stack.getOrCreateTag();
            int remainingTicks = tag.contains(TAG_REMAINING_TICKS) ? tag.getInt(TAG_REMAINING_TICKS) : STAGE_DURATION.sample(player.getRandom()) * 20;

            remainingTicks--;

            if (remainingTicks <= 0) {
                player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObsidianSkullItem.NEXT_SKULL_STAGE.get(type)));

                player.playSound(SoundEvents.ALLAY_DEATH);

                return;
            }

            tag.putInt(TAG_REMAINING_TICKS, remainingTicks);
        };

        public static final UpdateFunction AUREALIC = (type, stack, player) -> {
            player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
                aureal.decreaseAureal(1);
            });
        };
    }

    @FunctionalInterface
    public interface UpdateFunction {
        void tick(ObsidianSkullType type, ItemStack stack, Player player);
    }
}

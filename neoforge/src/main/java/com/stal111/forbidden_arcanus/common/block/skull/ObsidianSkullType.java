package com.stal111.forbidden_arcanus.common.block.skull;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.core.ClientAsset;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SkullBlock;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 10.09.2023
 */
public enum ObsidianSkullType implements SkullBlock.Type {
    DEFAULT("obsidian_skull", false, TickFunctions.DEFAULT),
    CRACKED("cracked_obsidian_skull", false, TickFunctions.DEFAULT),
    FRAGMENTED("fragmented_obsidian_skull", false, TickFunctions.DEFAULT),
    FADING("fading_obsidian_skull", false, TickFunctions.DEFAULT),
    AUREALIC("aurealic_obsidian_skull", true, TickFunctions.AUREALIC),
    ETERNAL("eternal_obsidian_skull", true, TickFunctions.EMPTY);

    public static final Codec<ObsidianSkullType> CODEC = StringRepresentable.fromValues(ObsidianSkullType::values);

    private final String name;
    private final ClientAsset.ResourceTexture texture;
    private final boolean detailedModel;
    private final TickFunction tickFunction;

    ObsidianSkullType(String name, boolean detailedModel, TickFunction tickFunction) {
        this.name = name;
        this.texture = ForbiddenArcanus.clientAsset("block/obsidian_skull/" + name);
        this.detailedModel = detailedModel;
        this.tickFunction = tickFunction;
        TYPES.put(name, this);
    }

    public ClientAsset.ResourceTexture getTexture() {
        return this.texture;
    }

    public boolean hasDetailedModel() {
        return this.detailedModel;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public void tick(ItemStack stack, Player player) {
        this.tickFunction.tick(this, stack, player);
    }

    public static class TickFunctions {

        public static final UniformInt STAGE_DURATION = UniformInt.of(8, 13);

        public static final TickFunction EMPTY = (type, stack, player) -> {
        };

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
            EssenceHelper.addEssence(player, EssenceType.AUREAL, -1);
        };
    }

    @FunctionalInterface
    public interface TickFunction {
        void tick(ObsidianSkullType type, ItemStack stack, Player player);
    }
}

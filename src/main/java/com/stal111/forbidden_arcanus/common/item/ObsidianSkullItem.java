package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.skull.ObsidianSkullType;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author stal111
 * @since 2021-02-11
 */
public class ObsidianSkullItem extends StandingAndWallBlockItem {

    public static final UniformInt STAGE_DURATION = UniformInt.of(8, 13);
    private static final String TAG_REMAINING_TICKS = "remaining_ticks";

    private static final Map<ObsidianSkullType, Block> NEXT_SKULL_STAGE = Util.make(new EnumMap<>(ObsidianSkullType.class), map -> {
        map.put(ObsidianSkullType.DEFAULT, ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull());
        map.put(ObsidianSkullType.CRACKED, ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull());
        map.put(ObsidianSkullType.FRAGMENTED, ModBlocks.FADING_OBSIDIAN_SKULL.getSkull());
        map.put(ObsidianSkullType.FADING, Blocks.SKELETON_SKULL);
    });

    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new OptionalDispenseItemBehavior() {
        @Override
        protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
            this.setSuccess(ArmorItem.dispenseArmor(source, stack));
            return super.execute(source, stack);
        }
    };

    private final ObsidianSkullType type;

    public ObsidianSkullItem(ObsidianSkullType type, Block floorBlock, Block wallBlock, boolean eternal, Properties properties) {
        super(floorBlock, wallBlock, properties, Direction.DOWN);
        this.type = type;
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!player.isOnFire()) {
            return;
        }

        CompoundTag tag = stack.getOrCreateTag();
        int remainingTicks = tag.contains(TAG_REMAINING_TICKS) ? tag.getInt(TAG_REMAINING_TICKS) : STAGE_DURATION.sample(player.getRandom()) * 20;

        remainingTicks--;

        if (remainingTicks <= 0) {
            player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(NEXT_SKULL_STAGE.get(this.type)));

            player.playSound(SoundEvents.ALLAY_DEATH);

            return;
        }

        tag.putInt(TAG_REMAINING_TICKS, remainingTicks);
    }
}

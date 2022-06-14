package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Blacksmith Gavel Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.BlacksmithGavelItem
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-09-17
 */
public class BlacksmithGavelItem extends PickaxeItem implements RitualStarterItem {

    private final int ritualUses;

    public BlacksmithGavelItem(Tier tier, int attackDamage, float attackSpeed, int ritualUses, Properties builder) {
        super(tier, attackDamage, attackSpeed, builder);
        this.ritualUses = ritualUses;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level world, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip." + ForbiddenArcanus.MOD_ID + ".remaining_ritual_uses").append(": " + this.getRemainingUses(stack)).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public int getRitualUses() {
        return this.ritualUses;
    }

    @Override
    public int getRemainingUses(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();

        if (tag.contains("RemainingRitualUses")) {
            return tag.getInt("RemainingRitualUses");
        }

        return this.getRitualUses();
    }

    @Override
    public void setRemainingUses(ItemStack stack, int remainingUses) {
        stack.getOrCreateTag().putInt("RemainingRitualUses", remainingUses);
    }
}

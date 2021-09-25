package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Blacksmith Gavel Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.BlacksmithGavelItem
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-09-17
 */
public class BlacksmithGavelItem extends PickaxeItem implements IRitualStarterItem {

    private final int ritualUses;

    public BlacksmithGavelItem(IItemTier tier, int attackDamage, float attackSpeed, int ritualUses, Properties builder) {
        super(tier, attackDamage, attackSpeed, builder);
        this.ritualUses = ritualUses;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".remaining_ritual_uses").appendString(": " + this.getRemainingUses(stack)).mergeStyle(TextFormatting.GRAY));
    }

    @Override
    public int getRitualUses() {
        return this.ritualUses;
    }

    @Override
    public int getRemainingUses(ItemStack stack) {
        CompoundNBT compound = stack.getOrCreateTag();

        if (compound.contains("RemainingRitualUses")) {
            return compound.getInt("RemainingRitualUses");
        }

        return this.getRitualUses();
    }

    @Override
    public void setRemainingUses(ItemStack stack, int remainingUses) {
        stack.getOrCreateTag().putInt("RemainingRitualUses", remainingUses);
    }
}

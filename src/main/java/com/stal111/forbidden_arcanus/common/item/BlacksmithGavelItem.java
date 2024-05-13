package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author stal111
 * @since 2021-09-17
 */
public class BlacksmithGavelItem extends PickaxeItem implements RitualStarterItem {

    private static final String TOOLTIP_KEY = Util.makeDescriptionId("tooltip", new ResourceLocation(ForbiddenArcanus.MOD_ID, "remaining_ritual_uses"));


    public BlacksmithGavelItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        components.add(Component.translatable(TOOLTIP_KEY, this.getRemainingUses(stack)).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public int getRitualUses(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.RITUAL_USES, 0);
    }

    @Override
    public int getRemainingUses(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.REMAINING_RITUAL_USES, this.getRitualUses(stack));
    }

    @Override
    public void setRemainingUses(ItemStack stack, int remainingUses) {
        stack.set(ModDataComponents.REMAINING_RITUAL_USES, remainingUses);
    }

    @Override
    public void playAdditionalEffect(Level level, BlockPos pos, Player player) {
        level.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, ModSounds.BLACKSMITH_GAVEL_RITUAL_START.get(), SoundSource.PLAYERS, 0.85F, level.getRandom().nextFloat() * 0.15F + 0.9F);
    }
}

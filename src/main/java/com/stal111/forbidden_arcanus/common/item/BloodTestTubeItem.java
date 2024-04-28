package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.EssenceContainer;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * @author stal111
 * @since 2021-07-08
 */
public class BloodTestTubeItem extends Item implements EssenceContainer {

    public static final int MAX_BLOOD = 3000;

    public static final EssenceData DEFAULT_DATA = EssenceData.createEmpty(EssenceType.BLOOD, MAX_BLOOD);

    public BloodTestTubeItem(Properties properties) {
        super(properties);
    }

    public static ItemStack create(Item item, int blood) {
        ItemStack stack = new ItemStack(item);

        stack.set(ModDataComponents.ESSENCE_DATA, new EssenceData(EssenceType.BLOOD, blood, MAX_BLOOD));

        return stack;
    }

    @NotNull
    @Override
    public String getDescriptionId(@NotNull ItemStack stack) {
        return ModItems.TEST_TUBE.get().getDescriptionId();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        EssenceData data = stack.get(ModDataComponents.ESSENCE_DATA);

        if (data != null) {
            data.addToTooltip(context, component -> components.add(this.getType(stack).getComponent().copy().withStyle(ChatFormatting.GRAY).append(component)), flag);
        }
    }

    @Override
    public EssenceType getType(ItemStack stack) {
        return EssenceType.BLOOD;
    }

    @Override
    public Optional<ItemStack> getEmptyStack() {
        return Optional.of(new ItemStack(ModItems.TEST_TUBE.get()));
    }
}

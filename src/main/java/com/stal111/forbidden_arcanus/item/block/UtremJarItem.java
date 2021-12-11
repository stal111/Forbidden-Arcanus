package com.stal111.forbidden_arcanus.item.block;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

import net.valhelsia.valhelsia_core.common.item.filler.TargetItemGroupFiller;

/**
 * Liquid Filled Utrem Jar Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.block.LiquidFilledUtremJarItem
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-24
 */
public class UtremJarItem extends BlockItem {

    public UtremJarItem(Block block, Properties builder) {
        super(block, builder);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        new TargetItemGroupFiller(() -> ModBlocks.PIXIE_UTREM_JAR.get().asItem(), true).fill(new ItemStack(this), group, items);
        new TargetItemGroupFiller(() -> ModBlocks.PIXIE_UTREM_JAR.get().asItem(), true).fill(withFluid(Fluids.WATER), group, items);
        new TargetItemGroupFiller(() -> ModBlocks.PIXIE_UTREM_JAR.get().asItem(), true).fill(withFluid(Fluids.LAVA), group, items);
    }

    private ItemStack withFluid(Fluid fluid) {
        ItemStack stack = new ItemStack(this);

        CompoundTag compound = new CompoundTag();
        compound.putString("FluidName", fluid.getRegistryName().toString());
        compound.putInt("Amount", 1000);

        stack.getOrCreateTagElement("BlockEntityTag").put("Fluid", compound);

        return stack;
    }

    public static Fluid getFluid(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundTag tag = stack.getOrCreateTagElement("BlockEntityTag");
            if (tag.contains("Fluid")) {
                CompoundTag compound = (CompoundTag) tag.get("Fluid");
                return ForgeRegistries.FLUIDS.getValue(new ResourceLocation(compound.getString("FluidName")));
            }
        }
        return Fluids.EMPTY;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, worldIn, tooltip, flag);
        if (getFluid(stack) != Fluids.EMPTY) {
            tooltip.add(new TranslatableComponent(getFluid(stack).getAttributes().getTranslationKey()).withStyle(ChatFormatting.GRAY));
        }
    }
}

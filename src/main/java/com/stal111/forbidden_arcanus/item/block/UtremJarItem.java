package com.stal111.forbidden_arcanus.item.block;

import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.item.filler.TargetItemGroupFiller;

import javax.annotation.Nullable;
import java.util.List;

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
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        new TargetItemGroupFiller(() -> NewModBlocks.PIXIE_UTREM_JAR.get().asItem(), true).fill(new ItemStack(this), group, items);
        new TargetItemGroupFiller(() -> NewModBlocks.PIXIE_UTREM_JAR.get().asItem(), true).fill(withFluid(Fluids.WATER), group, items);
        new TargetItemGroupFiller(() -> NewModBlocks.PIXIE_UTREM_JAR.get().asItem(), true).fill(withFluid(Fluids.LAVA), group, items);
    }

    private ItemStack withFluid(Fluid fluid) {
        ItemStack stack = new ItemStack(this);

        CompoundNBT compound = new CompoundNBT();
        compound.putString("FluidName", fluid.getRegistryName().toString());
        compound.putInt("Amount", 1000);

        stack.getOrCreateChildTag("BlockEntityTag").put("Fluid", compound);

        return stack;
    }

    public static Fluid getFluid(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundNBT tag = stack.getOrCreateChildTag("BlockEntityTag");
            if (tag.contains("Fluid")) {
                CompoundNBT compound = (CompoundNBT) tag.get("Fluid");
                return ForgeRegistries.FLUIDS.getValue(new ResourceLocation(compound.getString("FluidName")));
            }
        }
        return Fluids.EMPTY;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, worldIn, tooltip, flag);
        if (getFluid(stack) != Fluids.EMPTY) {
            tooltip.add(new TranslationTextComponent(getFluid(stack).getAttributes().getTranslationKey()).mergeStyle(TextFormatting.GRAY));
        }
    }
}

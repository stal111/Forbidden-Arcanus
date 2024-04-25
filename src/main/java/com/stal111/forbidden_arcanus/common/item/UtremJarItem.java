package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Utrem Jar Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.UtremJarItem
 *
 * @author stal111
 * @since 2021-02-24
 */
public class UtremJarItem extends BlockItem {

    public UtremJarItem(Block block, Properties builder) {
        super(block, builder);
    }

    private ItemStack withFluid(Fluid fluid) {
        ItemStack stack = new ItemStack(this);

        CompoundTag tag = new CompoundTag();
        tag.putString("FluidName", BuiltInRegistries.FLUID.getKey(fluid).toString());
        tag.putInt("Amount", 1000);

        //TODO
        //stack.getOrCreateTagElement("BlockEntityTag").put("Fluid", tag);

        return stack;
    }

    public Fluid getFluid(ItemStack stack) {
//        if (stack.hasTag()) {
//            CompoundTag tag = stack.getOrCreateTagElement("BlockEntityTag");
//
//            if (tag.contains("Fluid")) {
//                CompoundTag compound = tag.getCompound("Fluid");
//
//                return BuiltInRegistries.FLUID.get(new ResourceLocation(compound.getString("FluidName")));
//            }
//        }
        return Fluids.EMPTY;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);

        Fluid fluid = this.getFluid(stack);

        if (fluid != Fluids.EMPTY) {
            components.add(Component.translatable(fluid.getFluidType().getDescriptionId()).withStyle(ChatFormatting.GRAY));
        }
    }
}

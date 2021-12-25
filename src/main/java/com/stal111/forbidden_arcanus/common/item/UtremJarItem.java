package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.common.item.filler.TargetItemGroupFiller;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * Utrem Jar Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.UtremJarItem
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-02-24
 */
public class UtremJarItem extends BlockItem {

    public UtremJarItem(Block block, Properties builder) {
        super(block, builder);
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab tab, @Nonnull NonNullList<ItemStack> items) {
        new TargetItemGroupFiller(() -> ModBlocks.PIXIE_UTREM_JAR.get().asItem(), true).fill(new ItemStack(this), tab, items);
        new TargetItemGroupFiller(() -> ModBlocks.PIXIE_UTREM_JAR.get().asItem(), true).fill(this.withFluid(Fluids.WATER), tab, items);
        new TargetItemGroupFiller(() -> ModBlocks.PIXIE_UTREM_JAR.get().asItem(), true).fill(this.withFluid(Fluids.LAVA), tab, items);
    }

    private ItemStack withFluid(Fluid fluid) {
        ItemStack stack = new ItemStack(this);

        CompoundTag tag = new CompoundTag();
        tag.putString("FluidName", Objects.requireNonNull(fluid.getRegistryName()).toString());
        tag.putInt("Amount", 1000);

        stack.getOrCreateTagElement("BlockEntityTag").put("Fluid", tag);

        return stack;
    }

    public Fluid getFluid(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundTag tag = stack.getOrCreateTagElement("BlockEntityTag");

            if (tag.contains("Fluid")) {
                CompoundTag compound = tag.getCompound("Fluid");

                return ForgeRegistries.FLUIDS.getValue(new ResourceLocation(compound.getString("FluidName")));
            }
        }
        return Fluids.EMPTY;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        Fluid fluid = this.getFluid(stack);

        if (fluid != Fluids.EMPTY) {
            tooltip.add(new TranslatableComponent(fluid.getAttributes().getTranslationKey()).withStyle(ChatFormatting.GRAY));
        }
    }
}

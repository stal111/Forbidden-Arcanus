package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class EdelwoodFishBucketItem extends EdelwoodBucketItem {

    private final EntityType<?> fishType;

    public EdelwoodFishBucketItem(EntityType<?> fishType, Fluid containedFluidIn, int capacity, Properties builder) {
        super(containedFluidIn, capacity, builder);
        this.fishType = fishType;
    }

    @Override
    public void onLiquidPlaced(World world, ItemStack stack, BlockPos pos) {
        if (!world.isRemote()) {
            this.placeFish(world, stack, pos);
        }
        super.onLiquidPlaced(world, stack, pos);
    }

    @Override
    protected ItemStack emptyBucket(ItemStack stack, PlayerEntity player) {
        if (!player.abilities.isCreativeMode) {
            int fullness = ICapacityBucket.getFullness(stack);
            if ((fullness - 1) > 0) {
                return ICapacityBucket.setFullness(ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_WATER_BUCKET.get())), fullness - 1);
            }
            return ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_BUCKET.get()));
        }
        return stack;
    }

    protected void playEmptySound(@Nullable PlayerEntity player, IWorld world, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    private void placeFish(World world, ItemStack stack, BlockPos pos) {
        Entity entity = this.fishType.spawn(world, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity instanceof AbstractFishEntity) {
            ((AbstractFishEntity)entity).setFromBucket(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        if (this.fishType == EntityType.TROPICAL_FISH) {
            CompoundNBT compoundNBT = stack.getTag();
            if (compoundNBT != null && compoundNBT.contains("BucketVariantTag", 3)) {
                int variant = compoundNBT.getInt("BucketVariantTag");
                TextFormatting[] lvt_7_1_ = new TextFormatting[]{TextFormatting.ITALIC, TextFormatting.GRAY};
                String lvt_8_1_ = "color.minecraft." + TropicalFishEntity.func_212326_d(variant);
                String lvt_9_1_ = "color.minecraft." + TropicalFishEntity.func_212323_p(variant);

                for(int lvt_10_1_ = 0; lvt_10_1_ < TropicalFishEntity.SPECIAL_VARIANTS.length; ++lvt_10_1_) {
                    if (variant == TropicalFishEntity.SPECIAL_VARIANTS[lvt_10_1_]) {
                        tooltip.add((new TranslationTextComponent(TropicalFishEntity.func_212324_b(lvt_10_1_))).applyTextStyles(lvt_7_1_));
                        return;
                    }
                }

                tooltip.add((new TranslationTextComponent(TropicalFishEntity.func_212327_q(variant))).applyTextStyles(lvt_7_1_));
                ITextComponent textComponent = new TranslationTextComponent(lvt_8_1_);
                if (!lvt_8_1_.equals(lvt_9_1_)) {
                    textComponent.appendText(", ").appendSibling(new TranslationTextComponent(lvt_9_1_));
                }

                textComponent.applyTextStyles(lvt_7_1_);
                tooltip.add(textComponent);
            }
        }
    }
}

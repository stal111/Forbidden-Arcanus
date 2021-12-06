package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.config.ItemConfig;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nullable;
import java.util.List;

public class EdelwoodFishBucketItem extends EdelwoodBucketItem {

    private final EntityType<?> fishType;

    public EdelwoodFishBucketItem(EntityType<?> fishType, Fluid containedFluidIn, Properties builder) {
        super(containedFluidIn, builder);
        this.fishType = fishType;
    }

    @Override
    public void onLiquidPlaced(Level world, ItemStack stack, BlockPos pos) {
        if (!world.isClientSide()) {
            this.placeFish((ServerLevel) world, stack, pos);
        }
        super.onLiquidPlaced(world, stack, pos);
    }

    @Override
    protected ItemStack emptyBucket(ItemStack stack, Player player) {
        if (!player.getAbilities().instabuild) {
            int fullness = ICapacityBucket.getFullness(stack);
            if ((fullness - 1) > 0) {
                return ICapacityBucket.setFullness(ItemStackUtils.transferEnchantments(stack, new ItemStack(NewModItems.EDELWOOD_WATER_BUCKET.get())), fullness - 1);
            }
            return ItemStackUtils.transferEnchantments(stack, new ItemStack(NewModItems.EDELWOOD_BUCKET.get()));
        }
        return stack;
    }

    protected void playEmptySound(@Nullable Player player, LevelAccessor world, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    private void placeFish(ServerLevel world, ItemStack stack, BlockPos pos) {
        Entity entity = this.fishType.spawn(world, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (entity instanceof AbstractFish) {
            ((AbstractFish)entity).setFromBucket(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        if (this.fishType == EntityType.TROPICAL_FISH) {
            CompoundTag compoundnbt = stack.getTag();
            if (compoundnbt != null && compoundnbt.contains("BucketVariantTag", 3)) {
                int i = compoundnbt.getInt("BucketVariantTag");
                ChatFormatting[] atextformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
                String s = "color.minecraft." + TropicalFish.getBaseColor(i);
                String s1 = "color.minecraft." + TropicalFish.getPatternColor(i);

                for(int j = 0; j < TropicalFish.COMMON_VARIANTS.length; ++j) {
                    if (i == TropicalFish.COMMON_VARIANTS[j]) {
                        tooltip.add((new TranslatableComponent(TropicalFish.getPredefinedName(j))).withStyle(atextformatting));
                        return;
                    }
                }

                tooltip.add((new TranslatableComponent(TropicalFish.getFishTypeName(i))).withStyle(atextformatting));
                MutableComponent iformattabletextcomponent = new TranslatableComponent(s);
                if (!s.equals(s1)) {
                    iformattabletextcomponent.append(", ").append(new TranslatableComponent(s1));
                }

                iformattabletextcomponent.withStyle(atextformatting);
                tooltip.add(iformattabletextcomponent);
            }
        }
    }

    @Override
    public int getCapacity() {
        return ItemConfig.EDELWOOD_WATER_BUCKET_CAPACITY.get();
    }
}

package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.config.ItemConfig;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.valhelsia.valhelsia_core.util.ItemStackUtils;

import javax.annotation.Nullable;
import java.util.List;

public class EdelwoodSoupBucketItem extends Item implements ICapacityBucket {

    public EdelwoodSoupBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            int fullness = ICapacityBucket.getFullness(stack);
            ItemStack stack1 = super.onItemUseFinish(stack, world, entity);
            if (!player.abilities.isCreativeMode) {
                if ((fullness - 1) > 0) {
                    return ICapacityBucket.setFullness(ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_MUSHROOM_STEW_BUCKET.get())), fullness - 1);
                }
                return ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_BUCKET.get()));
            }
            return stack1;
        }
        return super.onItemUseFinish(stack, world, entity);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new StringTextComponent(" "));
        super.addInformation(stack, world, tooltip, flag);
    }

    @Override
    public int getCapacity() {
        return ItemConfig.EDELWOOD_SOUP_BUCKET_CAPACITY.get();
    }
}

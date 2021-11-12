package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.config.ItemConfig;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nullable;
import java.util.List;

public class EdelwoodSoupBucketItem extends Item implements ICapacityBucket {

    public EdelwoodSoupBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            int fullness = ICapacityBucket.getFullness(stack);
            ItemStack stack1 = super.finishUsingItem(stack, world, entity);
            if (!player.getAbilities().instabuild) {
                if ((fullness - 1) > 0) {
                    return ICapacityBucket.setFullness(ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_MUSHROOM_STEW_BUCKET.get())), fullness - 1);
                }
                return ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_BUCKET.get()));
            }
            return stack1;
        }
        return super.finishUsingItem(stack, world, entity);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TextComponent(" "));
        tooltip.add(new TextComponent(" "));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public int getCapacity() {
        return ItemConfig.EDELWOOD_SOUP_BUCKET_CAPACITY.get();
    }
}

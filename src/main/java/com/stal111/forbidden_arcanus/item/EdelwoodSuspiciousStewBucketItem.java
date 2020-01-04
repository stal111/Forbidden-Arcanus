package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class EdelwoodSuspiciousStewBucketItem extends SuspiciousStewItem {

    public EdelwoodSuspiciousStewBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);
        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("Effects", 9)) {
            ListNBT listnbt = compoundnbt.getList("Effects", 10);

            for(int i = 0; i < listnbt.size(); ++i) {
                int j = 160;
                CompoundNBT compoundnbt1 = listnbt.getCompound(i);
                if (compoundnbt1.contains("EffectDuration", 3)) {
                    j = compoundnbt1.getInt("EffectDuration");
                }

                Effect effect = Effect.get(compoundnbt1.getByte("EffectId"));
                if (effect != null) {
                    entityLiving.addPotionEffect(new EffectInstance(effect, j));
                }
            }
        }

        return entityLiving instanceof PlayerEntity && ((PlayerEntity)entityLiving).abilities.isCreativeMode ? itemstack : ItemStackUtils.transferEnchantments(stack, ModItems.EDELWOOD_BUCKET.getStack());
    }
}

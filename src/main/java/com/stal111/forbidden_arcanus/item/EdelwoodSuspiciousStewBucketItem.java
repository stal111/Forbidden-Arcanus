package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

public class EdelwoodSuspiciousStewBucketItem extends SuspiciousStewItem {

    public EdelwoodSuspiciousStewBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        ItemStack itemstack = super.finishUsingItem(stack, worldIn, entityLiving);
        CompoundTag compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("Effects", 9)) {
            ListTag listnbt = compoundnbt.getList("Effects", 10);

            for(int i = 0; i < listnbt.size(); ++i) {
                int j = 160;
                CompoundTag compoundnbt1 = listnbt.getCompound(i);
                if (compoundnbt1.contains("EffectDuration", 3)) {
                    j = compoundnbt1.getInt("EffectDuration");
                }

                MobEffect effect = MobEffect.byId(compoundnbt1.getByte("EffectId"));
                if (effect != null) {
                    entityLiving.addEffect(new MobEffectInstance(effect, j));
                }
            }
        }

        return entityLiving instanceof Player && ((Player)entityLiving).getAbilities().instabuild ? itemstack : ItemStackUtils.transferEnchantments(stack, new ItemStack(NewModItems.EDELWOOD_BUCKET.get()));
    }
}

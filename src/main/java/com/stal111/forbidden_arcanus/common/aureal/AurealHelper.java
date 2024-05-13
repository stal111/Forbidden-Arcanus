package com.stal111.forbidden_arcanus.common.aureal;

import com.stal111.forbidden_arcanus.common.entity.lostsoul.LostSoul;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;

/**
 * Aureal Helper <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.aureal.AurealHelper
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-01-27
 */
public class AurealHelper {

    public static boolean canEntityBeAureal(LivingEntity livingEntity) {
        MobCategory category = livingEntity.getType().getCategory();

        if (livingEntity instanceof LostSoul) {
            return false;
        }

        return category == MobCategory.AMBIENT || category == MobCategory.CREATURE;
    }
}
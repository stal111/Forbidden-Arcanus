package com.stal111.forbidden_arcanus.common.entity.lostsoul;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EnchantedLostSoul extends AbstractLostSoul {

    public EnchantedLostSoul(EntityType<? extends AbstractLostSoul> entityType, Level level) {
        super(entityType, level, 253 << 16 | 225 << 8 | 238);
    }
}

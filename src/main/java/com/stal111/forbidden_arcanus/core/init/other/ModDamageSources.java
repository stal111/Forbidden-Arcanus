package com.stal111.forbidden_arcanus.core.init.other;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.player.Player;

/**
 * @author stal111
 * @since 2022-10-01
 */
public class ModDamageSources {

    public static final String ID_EXTRACT_SOUL = "forbidden_arcanus:extractSoul";

    public static DamageSource extractSoul(Player player) {
        return new EntityDamageSource(ID_EXTRACT_SOUL, player).bypassArmor();
    }
}

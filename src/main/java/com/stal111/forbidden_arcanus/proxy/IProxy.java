package com.stal111.forbidden_arcanus.proxy;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IProxy {

    void init();
    Level getClientWorld();
    Player getClientPlayer();
}

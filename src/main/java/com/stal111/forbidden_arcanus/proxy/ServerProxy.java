package com.stal111.forbidden_arcanus.proxy;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerProxy implements IProxy {

    @Override
    public void init() {
    }

    @Override
    public Level getClientWorld() {
        throw new IllegalStateException("Only run on the client");
    }

    @Override
    public Player getClientPlayer() {
        throw new IllegalStateException("Only run on the client");
    }
}

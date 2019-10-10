package com.stal111.forbidden_arcanus.proxy;

import com.stal111.forbidden_arcanus.block.tileentity.DarkBeaconTileEntity;
import com.stal111.forbidden_arcanus.block.tileentity.ModSignTileEntity;
import com.stal111.forbidden_arcanus.block.tileentity.container.ModContainers;
import com.stal111.forbidden_arcanus.block.tileentity.render.DarkBeaconTileEntityRenderer;
import com.stal111.forbidden_arcanus.block.tileentity.render.ModSignTileEntityRenderer;
import com.stal111.forbidden_arcanus.block.tileentity.screen.DarkBeaconScreen;
import com.stal111.forbidden_arcanus.entity.ModEntities;
import com.stal111.forbidden_arcanus.init.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ModEntities.initModels();
        ClientRegistry.bindTileEntitySpecialRenderer(ModSignTileEntity.class, new ModSignTileEntityRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(DarkBeaconTileEntity.class, new DarkBeaconTileEntityRenderer());
        ScreenManager.registerFactory(ModContainers.dark_beacon, DarkBeaconScreen::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}

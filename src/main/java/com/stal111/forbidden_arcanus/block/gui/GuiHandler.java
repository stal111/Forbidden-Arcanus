package com.stal111.forbidden_arcanus.block.gui;

import javax.annotation.Nullable;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.util.IGuiTile;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class GuiHandler {
	
	@Nullable
    public static GuiScreen getClientGuiElement(FMLPlayMessages.OpenContainer message) {
        PacketBuffer buf = message.getAdditionalData();
        BlockPos pos = buf.readBlockPos();

        World world = Main.proxy.getClientWorld();
        EntityPlayer entityPlayer = Main.proxy.getClientPlayer();
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof IGuiTile)
            return ((IGuiTile) te).createGui(entityPlayer);

        return null;
    }

}

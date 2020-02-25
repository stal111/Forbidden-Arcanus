package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.tileentity.ModSignTileEntity;
import com.stal111.forbidden_arcanus.util.Data;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Main.MOD_ID);

    public static final RegistryObject<TileEntityType<ModSignTileEntity>> SIGN = register("sign", TileEntityType.Builder.create(ModSignTileEntity::new, ModBlocks.EDELWOOD_SIGN.getBlock(), ModBlocks.EDELWOOD_WALL_SIGN.getBlock(), ModBlocks.CHERRYWOOD_SIGN.getBlock(), ModBlocks.CHERRYWOOD_WALL_SIGN.getBlock(), ModBlocks.MYSTERYWOOD_SIGN.getBlock(), ModBlocks.MYSTERYWOOD_WALL_SIGN.getBlock()).build(null));

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, TileEntityType<T> tileEntityType) {
        return TILE_ENTITIES.register(name, () -> tileEntityType);
    }
}

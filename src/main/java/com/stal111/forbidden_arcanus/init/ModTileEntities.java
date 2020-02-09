package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.tileentity.ModSignTileEntity;
import com.stal111.forbidden_arcanus.util.Data;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTileEntities {

    public static final TileEntityType<ModSignTileEntity> SIGN = register("sign", TileEntityType.Builder.create(ModSignTileEntity::new, ModBlocks.EDELWOOD_SIGN.getBlock(), ModBlocks.EDELWOOD_WALL_SIGN.getBlock(), ModBlocks.CHERRYWOOD_SIGN.getBlock(), ModBlocks.CHERRYWOOD_WALL_SIGN.getBlock(), ModBlocks.MYSTERYWOOD_SIGN.getBlock(), ModBlocks.MYSTERYWOOD_WALL_SIGN.getBlock()).build(null));

    @SubscribeEvent
    public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event) {
        Main.LOGGER.debug("Registering Tile Entity Types... [Total Count: " + Data.TILE_ENTITY_TYPES.size() + "]");
        Data.TILE_ENTITY_TYPES.forEach(tileEntityType -> event.getRegistry().register(tileEntityType));
    }

    private static <T extends TileEntityType<?>> T register(String name, T tileEntityType) {
        tileEntityType.setRegistryName(ModUtils.location(name));
        Data.TILE_ENTITY_TYPES.add(tileEntityType);
        return tileEntityType;
    }
}

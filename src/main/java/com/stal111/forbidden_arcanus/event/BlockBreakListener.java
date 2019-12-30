package com.stal111.forbidden_arcanus.event;


import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockBreakListener {

	@SubscribeEvent
	public static void onBlockBrocken(BlockEvent.BreakEvent event) {
		if (!event.getWorld().isRemote()) {
			ItemStack stack = event.getPlayer().getHeldItemMainhand();
			BlockState state = event.getState();
			World world = (World) event.getWorld();
			BlockPos pos = event.getPos();
			if (state.getBlock() instanceof CropsBlock) {
				if (world.getBlockState(pos.down()).getBlock() == ModBlocks.MAGICAL_FARMLAND.getBlock()) {
					if (!event.getPlayer().abilities.isCreativeMode) {
						for (ItemStack itemStack : state.getBlock().getDrops(state, new LootContext.Builder((ServerWorld) world).withParameter(LootParameters.TOOL, stack).withParameter(LootParameters.POSITION, pos))) {
							world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
						}
					}
				}
			}
		}
	}
}

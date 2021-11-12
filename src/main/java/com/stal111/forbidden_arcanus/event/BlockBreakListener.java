package com.stal111.forbidden_arcanus.event;


import com.stal111.forbidden_arcanus.block.StellaArcanumBlock;
import com.stal111.forbidden_arcanus.config.BlockConfig;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockBreakListener {

	@SubscribeEvent
	public static void onBlockBroken(BlockEvent.BreakEvent event) {
		ItemStack stack = event.getPlayer().getMainHandItem();
		if (!event.getWorld().isClientSide()) {
			BlockState state = event.getState();

			if (state.getBlock() instanceof StellaArcanumBlock) {
				if (BlockConfig.STELLA_ARCANUM_EXPLODE.get()) {
					boolean shouldExplode = true;

					if (BlockConfig.STELLA_ARCANUM_ONLY_EXPLODE_WRONG_TOOL.get()) {
						//if (stack.getToolTypes().contains(ToolType.PICKAXE) && stack.getHarvestLevel(ToolType.PICKAXE, event.getPlayer(), state) >= 3) {
							//shouldExplode = false;
						//}
					}

					if (shouldExplode) {
						StellaArcanumBlock.explode = true;
					}
					StellaArcanumBlock.world = (Level) event.getWorld();
				}
			}
		}
	}
}

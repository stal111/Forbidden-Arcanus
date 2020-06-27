package com.stal111.forbidden_arcanus.event;


import com.stal111.forbidden_arcanus.block.StellaArcanumBlock;
import com.stal111.forbidden_arcanus.config.BlockConfig;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockBreakListener {

	@SubscribeEvent
	public static void onBlockBroken(BlockEvent.BreakEvent event) {
		ItemStack stack = event.getPlayer().getHeldItemMainhand();
		if (!event.getWorld().isRemote()) {
			BlockState state = event.getState();
			World world = (World) event.getWorld();
			BlockPos pos = event.getPos();
			PlayerEntity player = event.getPlayer();

			if (state.getBlock() instanceof CropsBlock) {
				if (world.getBlockState(pos.down()).getBlock() == ModBlocks.MAGICAL_FARMLAND.getBlock() && ((CropsBlock) state.getBlock()).isMaxAge(state)) {
					if (!event.getPlayer().abilities.isCreativeMode) {
						for (ItemStack itemStack : state.getBlock().getDrops(state, new LootContext.Builder((ServerWorld) world).withParameter(LootParameters.TOOL, stack).withParameter(LootParameters.POSITION, pos))) {
							if (!itemStack.getItem().getRegistryName().getPath().contains("seed")) {
								world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
							}
						}
					}
				}
			}

			if (state.getBlock() instanceof StellaArcanumBlock) {
				if (BlockConfig.STELLA_ARCANUM_EXPLODE.get()) {
					boolean shouldExplode = true;

					if (BlockConfig.STELLA_ARCANUM_ONLY_EXPLODE_WRONG_TOOL.get()) {
						if (stack.getToolTypes().contains(ToolType.PICKAXE) && stack.getHarvestLevel(ToolType.PICKAXE, player, state) >= 3) {
							shouldExplode = false;
						}
					}

					if (shouldExplode) {
						StellaArcanumBlock.explode = true;
					}
				}
			}
		}
	}
}

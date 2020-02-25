package com.stal111.forbidden_arcanus.event;


import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
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
	public static void onBlockBroken(BlockEvent.BreakEvent event) {
		ItemStack stack = event.getPlayer().getHeldItemMainhand();
		if (!event.getWorld().isRemote()) {
			BlockState state = event.getState();
			World world = (World) event.getWorld();
			BlockPos pos = event.getPos();
			PlayerEntity player = event.getPlayer();
			if (EnchantmentHelper.getEnchantments(stack).containsKey(ModEnchantments.INDESTRUCTIBLE.get())) {
				if (stack.getMaxDamage() - stack.getDamage() <= 1 && !event.getPlayer().abilities.isCreativeMode) {
					event.setCanceled(true);
				}
			}
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
		}
	}
}

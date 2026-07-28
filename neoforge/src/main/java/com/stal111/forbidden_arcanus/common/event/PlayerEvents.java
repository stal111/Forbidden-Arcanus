package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.block.entity.EssenceStorageBlockEntity;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.entity.attribute.FAAttributes;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.input.EssenceInput;
import com.stal111.forbidden_arcanus.common.item.QuantumCatcherItem;
import com.stal111.forbidden_arcanus.common.item.modifier.SoulboundInventory;
import com.stal111.forbidden_arcanus.core.config.BlockConfig;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.other.ModAttachmentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Player Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.event.PlayerEvents
 *
 * @author stal111
 * @since 2021-11-28
 */
public class PlayerEvents {

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();

        if (player.onClimbable() && !player.isCrouching() && player.getInBlockState().is(ModBlocks.EDELWOOD_LADDER.get())) {
            double multiplier = BlockConfig.EDELWOOD_LADDER_SPEED.get();

            if (!player.horizontalCollision) {
                multiplier *= 0.3F;
            }

            player.move(MoverType.SELF, new Vec3(0.0D, player.getDeltaMovement().y * multiplier, 0.0D));
        }

        if (!player.level().isClientSide() && player.level().getGameTime() % 100 == 0) {
            EssenceHelper.addEssence(player, EssenceType.AUREAL, (int) player.getAttributeValue(FAAttributes.AUREAL_REGENERATION));
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();

        if (event.getTarget() instanceof LivingEntity entity && stack.getItem() instanceof QuantumCatcherItem item) {
            event.setCancellationResult(item.onEntityInteract(stack, player, entity));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            SoulboundInventory inventory = player.getData(ModAttachmentTypes.SOULBOUND_INVENTORY);

            for (SoulboundInventory.Entry entry : inventory.entries()) {
                player.getInventory().setItem(entry.slot(), entry.stack());
            }
        }
    }

    @SubscribeEvent
    public void onUseItemOnBlock(UseItemOnBlockEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (state.is(Blocks.CAULDRON)) {
            for (EssenceType type : EssenceType.values()) {
                EssenceInput.findValidInput(event.getItemStack(), type).ifPresent(essenceInput -> {
                    if (!level.isClientSide()) {
                        level.setBlockAndUpdate(pos, ModBlocks.ESSENCE_CAULDRON.get().defaultBlockState().setValue(ModBlockStateProperties.ESSENCE_TYPE, type));

                        if (level.getBlockEntity(pos) instanceof EssenceStorageBlockEntity blockEntity) {
                            blockEntity.tryFillWithEssence(essenceInput, event.getItemStack(), event.getPlayer(), event.getHand());
                        }
                    }

                    event.cancelWithResult(InteractionResult.SUCCESS);
                });
            }
        }
    }
}

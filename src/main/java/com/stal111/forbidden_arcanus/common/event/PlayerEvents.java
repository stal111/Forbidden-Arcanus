package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.item.QuantumCatcherItem;
import com.stal111.forbidden_arcanus.common.network.clientbound.UpdateEssencePayload;
import com.stal111.forbidden_arcanus.core.config.BlockConfig;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

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
    public void onPlayerJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof ServerPlayer player) {
            EssenceHelper.getEssenceProvider(player).ifPresent(provider -> {
                PacketDistributor.sendToPlayer(player, new UpdateEssencePayload(provider.asStorage(EssenceType.AUREAL)));
            });
        }
    }
}

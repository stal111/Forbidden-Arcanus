package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import com.stal111.forbidden_arcanus.common.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.common.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.common.aureal.consequence.Consequence;
import com.stal111.forbidden_arcanus.common.item.QuantumCatcherItem;
import com.stal111.forbidden_arcanus.core.config.BlockConfig;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Player Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.event.PlayerEvents
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-11-28
 */
@Mod.EventBusSubscriber
public class PlayerEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;

            if (player.onClimbable() && !player.isCrouching() && player.getFeetBlockState().is(ModBlocks.EDELWOOD_LADDER.get())) {
                double multiplier = BlockConfig.EDELWOOD_LADDER_SPEED.get();

                if (!player.horizontalCollision) {
                    multiplier *= 0.3F;
                }

                player.move(MoverType.SELF, new Vec3(0.0D, player.getDeltaMovement().y * multiplier, 0.0D));
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();

        if (event.getTarget() instanceof LivingEntity entity && stack.getItem() instanceof QuantumCatcherItem item) {
            if (item.onEntityInteract(stack, player, entity, event.getHand()).consumesAction()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player player = event.getEntity();
            Player original = event.getOriginal();

            original.reviveCaps();

            IAureal capability = AurealHelper.getCapability(original);

            player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
                aureal.setAureal(capability.getAureal());
                aureal.setCorruption(capability.getCorruption());
                aureal.setCorruptionTimer(capability.getCorruptionTimer());

                for (Consequence consequence : capability.getActiveConsequences()) {
                    aureal.addActiveConsequence(consequence);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof ServerPlayer player) {
            AurealHelper.sendAurealUpdatePacket(player);
        }
    }
}

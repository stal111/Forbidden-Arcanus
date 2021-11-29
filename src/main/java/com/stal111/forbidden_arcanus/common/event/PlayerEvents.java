package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.config.BlockConfig;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Player Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.event.PlayerEvents
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-28
 */
@Mod.EventBusSubscriber
public class PlayerEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;

            if (player.onClimbable() && !player.isCrouching() && player.getFeetBlockState().is(NewModBlocks.EDELWOOD_LADDER.get())) {
                if (player.zza > 0.0f) {
                    player.move(MoverType.SELF, new Vec3(0.0, BlockConfig.EDELWOOD_LADDER_SPEED.get(), 0.0));
                } else {
                    player.move(MoverType.SELF, new Vec3(0.0, -BlockConfig.EDELWOOD_LADDER_SPEED.get(), 0.0));
                }
            }
        }
    }
}

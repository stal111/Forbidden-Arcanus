package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ResiduesStorage;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerCache;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.network.NetworkHandler;
import com.stal111.forbidden_arcanus.common.network.clientbound.UpdateResidueTypesPacket;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Datapack Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.event.DatapackEvents
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-21
 */
@Mod.EventBusSubscriber
public class DatapackEvents {

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() != null) {
            syncData(event.getPlayer());
        } else {
            event.getPlayerList().getPlayers().forEach(DatapackEvents::syncData);
        }
    }

    private static void syncData(ServerPlayer player) {
        NetworkHandler.sendTo(player, new UpdateResidueTypesPacket(ResiduesStorage.RESIDUE_TYPES));
    }

    @SubscribeEvent
    public static void onTagsUpdate(TagsUpdatedEvent event) {
        FARegistries.ITEM_MODIFIER_REGISTRY.get().getValues().forEach(ItemModifier::clearCachedValidItems);

        EnhancerCache.cacheEnhancers(event.getRegistryAccess());
    }
}

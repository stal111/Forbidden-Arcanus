package com.stal111.forbidden_arcanus.common.aureal;

import com.stal111.forbidden_arcanus.common.aureal.capability.AurealImpl;
import com.stal111.forbidden_arcanus.common.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.common.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.common.aureal.consequence.Consequence;
import com.stal111.forbidden_arcanus.common.aureal.consequence.Consequences;
import com.stal111.forbidden_arcanus.common.entity.lostsoul.LostSoul;
import com.stal111.forbidden_arcanus.common.network.NetworkHandler;
import com.stal111.forbidden_arcanus.common.network.clientbound.UpdateAurealPacket;
import com.stal111.forbidden_arcanus.core.config.AurealConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.common.util.NeedsStoring;

import java.util.Objects;

/**
 * Aureal Helper <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.aureal.AurealHelper
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-01-27
 */
public class AurealHelper {

    public static IAureal getCapability(Player player) {
        return player.getCapability(AurealProvider.CAPABILITY).orElse(new AurealImpl());
    }

    public static int getAureal(Player player) {
        return getCapability(player).getAureal();
    }

    public static void increaseAureal(Player player, int amount) {
        getCapability(player).increaseAureal(amount);

        sendAurealUpdatePacket(player);
    }

    public static void increaseCorruption(Player player, int amount) {
        IAureal aureal = getCapability(player);
        RandomSource random = player.getRandom();

        for (int i = 0; i < amount; i++) {
            aureal.increaseCorruption(1);

            int corruption = aureal.getCorruption();

            if (corruption <= 10 || AurealConfig.DISABLE_CONSEQUENCES.get()) {
                continue;
            }

            if (random.nextDouble() < (corruption + 3) / 185.0F) {
                Consequence consequence = Consequences.getRandomConsequence(random).create();
                consequence.tick(player);

                if (consequence instanceof NeedsStoring) {
                    aureal.addActiveConsequence(consequence);
                }
            }
        }

        sendAurealUpdatePacket(player);
    }

    public static void playerTick(Player player) {
        IAureal aureal = AurealHelper.getCapability(player);

        aureal.tickActiveConsequences(player);

        if (!AurealConfig.NATURAL_CORRUPTION_DECREASEMENT.get()) {
            return;
        }

        if (aureal.getCorruption() >= 1) {
            aureal.setCorruptionTimer(aureal.getCorruptionTimer() + 1);

            if (aureal.getCorruptionTimer() >= AurealConfig.NATURAL_CORRUPTION_DECREASEMENT_TIME.get()) {
                aureal.setCorruption(aureal.getCorruption() - 1);
                aureal.setCorruptionTimer(0);

                sendAurealUpdatePacket(player);
            }
        } else if (aureal.getCorruptionTimer() != 0) {
            aureal.setCorruptionTimer(0);
        }
    }

    /**
     * Sends an {@link UpdateAurealPacket} to the given player.
     * This method should only be called server-side.
     *
     * @param player the player the packet gets sent to.
     */
    public static void sendAurealUpdatePacket(Player player) {
        NetworkHandler.sendTo(player, new UpdateAurealPacket(player));
    }

    public static CompoundTag save(CompoundTag tag, IAureal aureal) {
        tag.putInt("Corruption", aureal.getCorruption());
        tag.putInt("CorruptionTimer", aureal.getCorruptionTimer());
        tag.putInt("Aureal", aureal.getAureal());

        if (!aureal.getActiveConsequences().isEmpty()) {
            ListTag consequences = new ListTag();

            for (Consequence consequence : aureal.getActiveConsequences()){
                if (consequence instanceof NeedsStoring needsStoring && needsStoring.shouldBeSaved()) {
                    CompoundTag consequenceTag = new CompoundTag();

                    consequenceTag.putString("Type", consequence.getName().toString());
                    consequences.add(needsStoring.save(consequenceTag));
                }
            }

            tag.put("ActiveConsequences", consequences);
        }

        return tag;
    }

    public static IAureal load(CompoundTag tag, IAureal aureal) {
        aureal.setCorruption(tag.getInt("Corruption"));
        aureal.setCorruptionTimer(tag.getInt("CorruptionTimer"));
        aureal.setAureal(tag.getInt("Aureal"));

        if (tag.contains("ActiveConsequences")) {
            ListTag consequences = tag.getList("ActiveConsequences", 10);

            for (Tag listEntry : consequences) {
                if (listEntry instanceof CompoundTag consequenceTag) {
                    ResourceLocation name = new ResourceLocation(consequenceTag.getString("Type"));
                    Consequence consequence = Objects.requireNonNull(Consequences.getByName(name)).create();

                    if (consequence instanceof NeedsStoring needsStoring) {
                        needsStoring.load(consequenceTag);
                    }

                    aureal.addActiveConsequence(consequence);
                }
            }
        }

        return aureal;
    }

    public static boolean canEntityBeAureal(LivingEntity livingEntity) {
        MobCategory category = livingEntity.getType().getCategory();

        if (livingEntity instanceof LostSoul) {
            return false;
        }

        return category == MobCategory.AMBIENT || category == MobCategory.CREATURE;
    }
}
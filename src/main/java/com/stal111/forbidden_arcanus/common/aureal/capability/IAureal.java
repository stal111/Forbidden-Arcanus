package com.stal111.forbidden_arcanus.common.aureal.capability;

import com.stal111.forbidden_arcanus.common.aureal.consequence.Consequence;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 * Aureal Interface <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.aureal.capability.IAureal
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-01-26
 */
public interface IAureal {
    int getCorruption();
    void setCorruption(int corruption);
    boolean increaseCorruption(int increasement);
    boolean decreaseCorruption(int decreasement);
    int getCorruptionTimer();
    void setCorruptionTimer(int timer);

    int getAureal();
    void setAureal(int aureal);
    boolean increaseAureal(int increasement);
    boolean decreaseAureal(int decreasement);

    void addActiveConsequence(Consequence consequence);
    void removeActiveConsequence(Consequence consequence);
    void tickActiveConsequences(Player player);
    List<Consequence> getActiveConsequences();
}
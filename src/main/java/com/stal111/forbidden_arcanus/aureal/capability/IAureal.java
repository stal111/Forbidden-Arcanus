package com.stal111.forbidden_arcanus.aureal.capability;

import com.stal111.forbidden_arcanus.aureal.consequence.IConsequence;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 * Aureal Interface
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.capability.IAureal
 *
 * @author stal111
 * @version 16.2.0
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

    void addActiveConsequence(IConsequence consequence);
    void removeActiveConsequence(IConsequence consequence);
    void updateActiveConsequences(Player player);
    List<IConsequence> getActiveConsequences();
}
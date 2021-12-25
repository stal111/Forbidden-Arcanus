package com.stal111.forbidden_arcanus.common.aureal.capability;

import com.stal111.forbidden_arcanus.common.aureal.consequence.Consequence;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Aureal Impl <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.aureal.capability.AurealImpl
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-01-26
 */
public class AurealImpl implements IAureal {

    private int corruption = 0;
    private int corruptionTimer = 0;
    private int aureal = 0;

    private final List<Consequence> activeConsequences = new ArrayList<>();

    @Override
    public int getCorruption() {
        return this.corruption;
    }

    @Override
    public void setCorruption(int corruption) {
        this.corruption = corruption;
    }

    @Override
    public boolean increaseCorruption(int increasement) {
        if (this.corruption >= 100) {
            return false;
        }
        this.corruption = Math.min(this.corruption + increasement, 100);
        return true;
    }

    @Override
    public boolean decreaseCorruption(int decreasement) {
        if (this.corruption == 0) {
            return false;
        }
        this.corruption = Math.max(this.corruption - decreasement, 0);
        return true;
    }

    @Override
    public int getCorruptionTimer() {
        return this.corruptionTimer;
    }

    @Override
    public void setCorruptionTimer(int timer) {
        this.corruptionTimer = timer;
    }

    @Override
    public int getAureal() {
        return this.aureal;
    }

    @Override
    public void setAureal(int aureal) {
        this.aureal = aureal;
    }

    @Override
    public boolean increaseAureal(int increasement) {
        if (this.aureal >= 200) {
            return false;
        }
        this.aureal = Math.min(this.aureal + increasement, 200);
        return true;
    }

    @Override
    public boolean decreaseAureal(int decreasement) {
        if (this.aureal == 0) {
            return false;
        }
        this.aureal = Math.max(this.aureal - decreasement, 0);
        return true;
    }

    @Override
    public void addActiveConsequence(Consequence consequence) {
        for (Consequence activeConsequence : this.activeConsequences) {
            if (activeConsequence.getType() == consequence.getType()) {
                return;
            }
        }
        this.activeConsequences.add(consequence);
    }

    @Override
    public void removeActiveConsequence(Consequence consequence) {
        this.activeConsequences.remove(consequence);
    }

    @Override
    public void tickActiveConsequences(Player player) {
        for (Consequence consequence : this.getActiveConsequences()) {
            consequence.tick(player);
        }
    }

    @Override
    public List<Consequence> getActiveConsequences() {
        return this.activeConsequences;
    }
}

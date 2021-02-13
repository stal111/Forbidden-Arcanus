package com.stal111.forbidden_arcanus.aureal.capability;

import com.stal111.forbidden_arcanus.aureal.consequence.IConsequence;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Aureal Impl
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.capability.AurealImpl
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-26
 */
public class AurealImpl implements IAureal {

    private int corruption = 0;
    private int corruptionTimer = 0;
    private int aureal = 0;

    private final List<IConsequence> activeConsequences = new ArrayList<>();

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
    public void addActiveConsequence(IConsequence consequence) {
        for (IConsequence activeConsequence : this.activeConsequences) {
            if (activeConsequence.getType() == consequence.getType()) {
                return;
            }
        }
        this.activeConsequences.add(consequence);
    }

    @Override
    public void removeActiveConsequence(IConsequence consequence) {
        this.activeConsequences.remove(consequence);
    }

    @Override
    public void updateActiveConsequences(PlayerEntity player) {
        if (!getActiveConsequences().isEmpty()) {
            for (int i = 0; i < getActiveConsequences().size(); i++) {
                getActiveConsequences().get(i).tick(player);
            }
        }
    }

    @Override
    public List<IConsequence> getActiveConsequences() {
        return this.activeConsequences;
    }
}

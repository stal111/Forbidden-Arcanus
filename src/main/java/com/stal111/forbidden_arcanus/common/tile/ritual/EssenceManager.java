package com.stal111.forbidden_arcanus.common.tile.ritual;

import com.stal111.forbidden_arcanus.common.tile.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.tile.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.util.ISavedData;
import net.minecraft.nbt.CompoundNBT;

/**
 * Essence Manager
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.ritual.EssenceManager
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-10
 */
public class EssenceManager implements ISavedData {

    private final HephaestusForgeTileEntity tileEntity;

    private int aureal = 0;
    private int corruption = 0;
    private int souls = 0;
    private int blood = 0;
    private int experience = 0;

    public EssenceManager(HephaestusForgeTileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    public HephaestusForgeTileEntity getTileEntity() {
        return this.tileEntity;
    }

    public HephaestusForgeLevel getLevel() {
        return this.getTileEntity().getLevel();
    }

    public int getAureal() {
        return this.aureal;
    }

    public void setAureal(int aureal) {
        this.aureal = aureal;
    }

    public void increaseAureal(int aureal) {
        if (this.getAureal() + aureal >= this.getLevel().getMaxAureal()) {
            this.setAureal(this.getLevel().getMaxAureal());
            return;
        }
        this.setAureal(this.getAureal() + aureal);
    }

    public void decreaseAureal(int aureal) {
        this.setAureal(Math.max(this.getAureal() - aureal, 0));
    }

    public int getCorruption() {
        return this.corruption;
    }

    public void setCorruption(int corruption) {
        this.corruption = corruption;
    }

    public void increaseCorruption(int corruption) {
        if (this.getCorruption() + corruption >= this.getLevel().getMaxCorruption()) {
            this.setCorruption(this.getLevel().getMaxCorruption());
            return;
        }
        this.setCorruption(this.getCorruption() + corruption);
    }

    public void decreaseCorruption(int corruption) {
        this.setCorruption(Math.max(this.getCorruption() - corruption, 0));
    }

    public int getSouls() {
        return this.souls;
    }

    public void setSouls(int souls) {
        this.souls = souls;
    }

    public void increaseSouls(int souls) {
        if (this.getSouls() + souls >= this.getLevel().getMaxSouls()) {
            this.setSouls(this.getLevel().getMaxSouls());
            return;
        }
        this.setSouls(this.getSouls() + souls);
    }

    public void decreaseSouls(int souls) {
        this.setSouls(Math.max(this.getSouls() - souls, 0));
    }

    public int getBlood() {
        return this.blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public void increaseBlood(int blood) {
        if (this.getBlood() + blood >= this.getLevel().getMaxBlood()) {
            this.setBlood(this.getLevel().getMaxBlood());
            return;
        }
        this.setBlood(this.getBlood() + blood);
    }

    public void decreaseBlood(int blood) {
        this.setBlood(Math.max(this.getBlood() - blood, 0));
    }

    public int getExperience() {
        return this.experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void increaseExperience(int experience) {
        if (this.getExperience() + experience >= this.getLevel().getMaxExperience()) {
            this.setExperience(this.getLevel().getMaxExperience());
            return;
        }
        this.setExperience(this.getExperience() + experience);
    }

    public void decreaseExperience(int experience) {
        this.setExperience(Math.max(this.getExperience() - experience, 0));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("Aureal", this.getAureal());
        compound.putInt("Corruption", this.getCorruption());
        compound.putInt("Souls", this.getSouls());
        compound.putInt("Blood", this.getBlood());
        compound.putInt("Experience", this.getExperience());

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        this.setAureal(compound.getInt("Aureal"));
        this.setCorruption(compound.getInt("Corruption"));
        this.setSouls(compound.getInt("Souls"));
        this.setBlood(compound.getInt("Blood"));
        this.setExperience(compound.getInt("Experience"));
    }
}

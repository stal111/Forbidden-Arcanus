package com.stal111.forbidden_arcanus.common.tile.forge.ritual;

import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;

/**
 * Ritual Essences
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualEssences
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-10
 */
public class RitualEssences {

    private final int aureal;
    private final int corruption;
    private final int souls;
    private final int blood;
    private final int experience;

    public RitualEssences(int aureal, int corruption, int souls, int blood, int experience) {
        this.aureal = aureal;
        this.corruption = corruption;
        this.souls = souls;
        this.blood = blood;
        this.experience= experience;
    }

    public boolean checkEssences(HephaestusForgeTileEntity tileEntity) {
        EssenceManager manager = tileEntity.getEssenceManager();

        if (manager.getAureal() < this.getAureal()) {
            return false;
        } else if (manager.getCorruption() < this.getCorruption()) {
            return false;
        } else if (manager.getSouls() < this.getSouls()) {
            return false;
        } else if (manager.getBlood() < this.getBlood()) {
            return false;
        }
        return manager.getExperience() >= this.getExperience();
    }

    public void reduceEssences(HephaestusForgeTileEntity tileEntity) {
        EssenceManager manager = tileEntity.getEssenceManager();

        manager.decreaseAureal(this.getAureal());
        manager.decreaseCorruption(this.getCorruption());
        manager.decreaseSouls(this.getSouls());
        manager.decreaseBlood(this.getBlood());
        manager.decreaseExperience(this.getExperience());
    }

    public int getAureal() {
        return aureal;
    }

    public int getCorruption() {
        return corruption;
    }

    public int getSouls() {
        return souls;
    }

    public int getBlood() {
        return blood;
    }

    public int getExperience() {
        return experience;
    }

    public int getFromName(String name) {
        switch (name) {
            case "Aureal":
                return this.getAureal();
            case "Souls":
                return this.getSouls();
            case "Blood":
                return this.getBlood();
            case "Experience":
                return this.experience;
            default:
                return 0;
        }
    }
}

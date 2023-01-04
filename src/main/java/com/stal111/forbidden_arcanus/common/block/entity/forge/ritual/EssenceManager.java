package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.valhelsia.valhelsia_core.common.util.NeedsStoring;

import java.util.HashMap;
import java.util.Map;

/**
 * Essence Manager <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.EssenceManager
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-10
 */
public class EssenceManager implements NeedsStoring {

    private final HephaestusForgeBlockEntity blockEntity;

    private int aureal = 0;
    private int corruption = 0;
    private int souls = 0;
    private int blood = 0;
    private int experience = 0;

    private final Map<LivingEntity, Float> cachedHealth = new HashMap<>();

    public EssenceManager(HephaestusForgeBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    public HephaestusForgeBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public HephaestusForgeLevel getLevel() {
        return this.getBlockEntity().getForgeLevel();
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
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("Aureal", this.getAureal());
        tag.putInt("Corruption", this.getCorruption());
        tag.putInt("Souls", this.getSouls());
        tag.putInt("Blood", this.getBlood());
        tag.putInt("Experience", this.getExperience());

        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        this.setAureal(tag.getInt("Aureal"));
        this.setCorruption(tag.getInt("Corruption"));
        this.setSouls(tag.getInt("Souls"));
        this.setBlood(tag.getInt("Blood"));
        this.setExperience(tag.getInt("Experience"));
    }

    public void tick() {
        for (LivingEntity entity : this.blockEntity.getEntities()) {
            if (this.cachedHealth.containsKey(entity)) {
                float healthDifference = this.cachedHealth.get(entity) - entity.getHealth();

                if (healthDifference > 0) {
                    this.increaseBlood((int) healthDifference * 20);
                }
            }
        }

        this.cachedHealth.clear();
        for (LivingEntity entity : this.blockEntity.getEntities()) {
            this.cachedHealth.put(entity, entity.getHealth());
        }
    }
}

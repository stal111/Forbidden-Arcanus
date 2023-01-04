package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.valhelsia.valhelsia_core.common.util.NeedsStoring;

import java.util.EnumMap;

/**
 * Essence Manager <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.EssenceManager
 *
 * @author stal111
 * @since 2021-07-10
 */
public class EssenceManager implements NeedsStoring {

    private final HephaestusForgeBlockEntity blockEntity;

    private final EnumMap<EssenceType, Integer> essences = new EnumMap<>(EssenceType.class);

    private final Object2FloatArrayMap<LivingEntity> cachedHealth = new Object2FloatArrayMap<>();

    public EssenceManager(HephaestusForgeBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    public HephaestusForgeBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public HephaestusForgeLevel getLevel() {
        return this.getBlockEntity().getForgeLevel();
    }

    public int getEssence(EssenceType type) {
        return this.essences.getOrDefault(type, 0);
    }

    public void setEssence(EssenceType type, int value) {
        this.essences.put(type, value);
    }

    public void increaseEssence(EssenceType type, int amount) {
        this.setEssence(type, Math.min(this.getLevel().getMaxAmount(type), this.getEssence(type) + amount));
    }

    public int getAureal() {
        return this.getEssence(EssenceType.AUREAL);
    }

    public void setAureal(int aureal) {
        this.setEssence(EssenceType.AUREAL, aureal);
    }

    public void decreaseAureal(int aureal) {
        this.setAureal(Math.max(this.getAureal() - aureal, 0));
    }

    public int getSouls() {
        return this.getEssence(EssenceType.SOULS);
    }

    public void setSouls(int souls) {
        this.setEssence(EssenceType.SOULS, souls);
    }

    public void decreaseSouls(int souls) {
        this.setSouls(Math.max(this.getSouls() - souls, 0));
    }

    public int getBlood() {
        return this.getEssence(EssenceType.BLOOD);
    }

    public void setBlood(int blood) {
        this.setEssence(EssenceType.BLOOD, blood);
    }

    public void decreaseBlood(int blood) {
        this.setBlood(Math.max(this.getBlood() - blood, 0));
    }

    public int getExperience() {
        return this.getEssence(EssenceType.EXPERIENCE);
    }

    public void setExperience(int experience) {
        this.setEssence(EssenceType.EXPERIENCE, experience);
    }

    public void decreaseExperience(int experience) {
        this.setExperience(Math.max(this.getExperience() - experience, 0));
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        for (EssenceType type : EssenceType.values()) {
            tag.putInt(type.getSerializedName(), this.getEssence(type));
        }

        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        for (EssenceType type : EssenceType.values()) {
            this.setEssence(type, tag.getInt(type.getSerializedName()));
        }
    }

    public void tick() {
        for (LivingEntity entity : this.blockEntity.getEntities()) {
            if (this.cachedHealth.containsKey(entity)) {
                float healthDifference = this.cachedHealth.getFloat(entity) - entity.getHealth();

                if (healthDifference > 0) {
                    this.increaseEssence(EssenceType.BLOOD, (int) healthDifference * 20);
                }
            }
        }

        this.cachedHealth.clear();

        for (LivingEntity entity : this.blockEntity.getEntities()) {
            this.cachedHealth.put(entity, entity.getHealth());
        }
    }
}

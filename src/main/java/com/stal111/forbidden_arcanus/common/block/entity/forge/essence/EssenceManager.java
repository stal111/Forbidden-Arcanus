package com.stal111.forbidden_arcanus.common.block.entity.forge.essence;

import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.valhelsia.valhelsia_core.common.util.NeedsStoring;

import java.util.List;
import java.util.function.Consumer;

/**
 * Essence Manager <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.EssenceManager
 *
 * @author stal111
 * @since 2021-07-10
 */
public class EssenceManager implements NeedsStoring {

    private static final int ENTITY_CHECK_RADIUS = 5;

    private final EssencesStorage essences = new EssencesStorage();
    private final Object2FloatArrayMap<LivingEntity> cachedHealth = new Object2FloatArrayMap<>();
    private EssencesDefinition maxEssences;

    private final Consumer<EssencesDefinition> onChanged;

    public EssenceManager(EssencesDefinition maxEssences, Consumer<EssencesDefinition> onChanged) {
        this.maxEssences = maxEssences;
        this.onChanged = onChanged;
    }

    public void setMaxEssences(EssencesDefinition maxEssences) {
        this.maxEssences = maxEssences;
    }

    public EssencesStorage getStorage() {
        return this.essences;
    }

    public EssencesDefinition getCurrentEssences() {
        return this.getStorage().immutable();
    }

    public int getEssence(EssenceType type) {
        return this.essences.getOrDefault(type, 0);
    }

    public void setEssence(EssenceType type, int value) {
        this.setEssence(type, value, true);
    }

    public void setEssence(EssenceType type, int value, boolean changed) {
        this.essences.put(type, value);

        if (changed) {
            this.onChanged.accept(this.essences.immutable());
        }
    }

    public void increaseEssence(EssenceType type, int amount) {
        this.setEssence(type, Math.min(this.maxEssences.get(type), this.getEssence(type) + amount));
    }

    public int getAureal() {
        return this.getEssence(EssenceType.AUREAL);
    }

    public void setAureal(int aureal) {
        this.setEssence(EssenceType.AUREAL, aureal);
    }

    public int getSouls() {
        return this.getEssence(EssenceType.SOULS);
    }

    public void setSouls(int souls) {
        this.setEssence(EssenceType.SOULS, souls);
    }

    public int getBlood() {
        return this.getEssence(EssenceType.BLOOD);
    }

    public void setBlood(int blood) {
        this.setEssence(EssenceType.BLOOD, blood);
    }

    public int getExperience() {
        return this.getEssence(EssenceType.EXPERIENCE);
    }

    public void setExperience(int experience) {
        this.setEssence(EssenceType.EXPERIENCE, experience);
    }

    public boolean isEssenceFull(EssenceType type) {
        return this.getEssence(type) >= this.maxEssences.get(type);
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
            this.setEssence(type, tag.getInt(type.getSerializedName()), false);
        }
    }

    public void tick(Level level, BlockPos pos) {
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(ENTITY_CHECK_RADIUS));

        for (LivingEntity entity : entities) {
            if (this.cachedHealth.containsKey(entity)) {
                float healthDifference = this.cachedHealth.getFloat(entity) - entity.getHealth();

                if (healthDifference > 0) {
                    this.increaseEssence(EssenceType.BLOOD, (int) healthDifference * 20);
                }
            }
        }

        this.cachedHealth.clear();

        for (LivingEntity entity : entities) {
            this.cachedHealth.put(entity, entity.getHealth());
        }
    }
}

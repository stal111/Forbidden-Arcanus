package com.stal111.forbidden_arcanus.common.block.entity.forge.essence;

import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import com.stal111.forbidden_arcanus.common.essence.MultiEssenceStorage;
import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Consumer;

/**
 * Essence Manager <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.EssenceManager
 *
 * @author stal111
 * @since 2021-07-10
 */
public class EssenceManager {

    private static final int ENTITY_CHECK_RADIUS = 5;

    private MultiEssenceStorage storage;

    private final Object2FloatArrayMap<LivingEntity> cachedHealth = new Object2FloatArrayMap<>();
    private EssencesDefinition maxEssences;

    private final Consumer<EssencesDefinition> onChanged;

    public EssenceManager(EssencesDefinition maxEssences, Consumer<EssencesDefinition> onChanged) {
        this.maxEssences = maxEssences;
        this.onChanged = onChanged;

        this.storage = MultiEssenceStorage.empty(maxEssences);
    }

    public void setMaxEssences(EssencesDefinition maxEssences) {
        this.maxEssences = maxEssences;
    }

    public EssencesDefinition getCurrentEssences() {
        return this.storage.getSnapshot();
    }

    public EssenceStorage getStorage(EssenceType type) {
        return this.storage.getStorage(type);
    }

    public int getEssence(EssenceType type) {
        return this.storage.getAmount(type);
    }

    public void setEssence(EssenceType type, int value) {
        this.setEssence(type, value, true);
    }

    public void setEssence(EssenceType type, int value, boolean changed) {
        this.storage = this.storage.setAmount(type, value);

        if (changed) {
            this.onChanged.accept(this.getCurrentEssences());
        }
    }

    public void increaseEssence(EssenceType type, int amount) {
        this.setEssence(type, Math.min(this.maxEssences.get(type), this.getEssence(type) + amount));
    }

    public boolean isEssenceFull(EssenceType type) {
        return this.getEssence(type) >= this.maxEssences.get(type);
    }

    public void save(ValueOutput output) {
        output.store("essences", MultiEssenceStorage.CODEC, this.storage);
    }

    public void load(ValueInput input) {
        input.read("essences", MultiEssenceStorage.CODEC).ifPresent(storage -> this.storage = storage);
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

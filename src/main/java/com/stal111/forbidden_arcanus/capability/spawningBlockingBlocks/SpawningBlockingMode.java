package com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks;

import net.minecraft.entity.EntityClassification;

import java.util.Arrays;
import java.util.List;

public enum SpawningBlockingMode {
    ALL,
    MONSTER(EntityClassification.MONSTER),
    CREATURE(EntityClassification.CREATURE, EntityClassification.AMBIENT),
    WATER(EntityClassification.WATER_AMBIENT, EntityClassification.WATER_CREATURE);

    private final List<EntityClassification> entityClassifications;

    SpawningBlockingMode(EntityClassification... entityClassification) {
        this.entityClassifications = Arrays.asList(entityClassification);
    }

    public List<EntityClassification> getEntityClassifications() {
        return entityClassifications;
    }
}

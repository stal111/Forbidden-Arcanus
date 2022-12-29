package com.stal111.forbidden_arcanus;

import com.google.common.collect.ImmutableList;
import com.stal111.forbidden_arcanus.core.init.*;
import com.stal111.forbidden_arcanus.core.init.other.ModActivities;
import com.stal111.forbidden_arcanus.core.init.other.ModLootModifiers;
import com.stal111.forbidden_arcanus.core.init.world.ModStructurePieces;
import com.stal111.forbidden_arcanus.core.init.world.ModStructureSets;
import com.stal111.forbidden_arcanus.core.init.world.ModStructureTypes;
import com.stal111.forbidden_arcanus.core.init.world.ModStructures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.core.registry.RegistryCollector;
import net.valhelsia.valhelsia_core.core.registry.helper.EntityRegistryHelper;

/**
 * @author stal111
 * @since 2022-12-29
 */
public class ModRegistries extends RegistryCollector {

    public ModRegistries(String modId) {
        super(modId);
    }

    @Override
    protected void collect() {
        this.addBlockHelper(ModBlocks::new);
        this.addItemHelper(ModItems::new);
        this.addMappedHelper(Registries.ENTITY_TYPE, EntityRegistryHelper::new, ModEntities::new);
        this.addMappedHelper(Registries.STRUCTURE_TYPE, ModStructureTypes::new);
        this.addMappedHelper(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ModLootModifiers::new);
        this.addMappedHelper(Registries.STRUCTURE_PIECE, ModStructurePieces::new);
        this.addMappedHelper(Registries.SOUND_EVENT, ModSounds::new);
        this.addMappedHelper(Registries.PARTICLE_TYPE, ModParticles::new);
        this.addMappedHelper(Registries.MEMORY_MODULE_TYPE, ModMemoryModules::new);
        this.addMappedHelper(Registries.ACTIVITY, ModActivities::new);

        this.addDatapackHelper(Registries.STRUCTURE, (dataProviderInfo, bootstapContext) -> ImmutableList.of(new ModStructures(dataProviderInfo, (BootstapContext<Structure>) bootstapContext)));
        this.addDatapackHelper(Registries.STRUCTURE_SET, (dataProviderInfo, bootstapContext) -> ImmutableList.of(new ModStructureSets(dataProviderInfo, (BootstapContext<StructureSet>) bootstapContext)));

    }
}

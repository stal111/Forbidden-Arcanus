package com.stal111.forbidden_arcanus.util;

import com.google.common.collect.Lists;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.*;

public class Data {

    public static final List<DeferredRegister<?>> registerList = Lists.newArrayList();

    public static final List<Effect> EFFECTS = new LinkedList<>();
    public static final List<Enchantment> ENCHANTMENTS = new LinkedList<>();
    public static final List<TileEntityType<?>> TILE_ENTITY_TYPES = new LinkedList<>();
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ForbiddenArcanus.MOD_ID);

    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> createDeferred(IForgeRegistry<T> registry) {
        DeferredRegister<T> deferred = DeferredRegister.create(registry, ForbiddenArcanus.MOD_ID);
        registerList.add(deferred);
        return deferred;
    }

    public void subscribeEvents(IEventBus bus) {
        registerList.forEach(def -> def.register(bus));
    }
}

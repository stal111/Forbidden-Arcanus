package com.stal111.forbidden_arcanus.core.registry;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResultType;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2023-02-18
 */
public class FARegistries {

    public static final ResourceKey<Registry<RitualResultType<?>>> RITUAL_RESULT_TYPE = FARegistries.createRegistryKey("ritual_results");
    public static final ResourceKey<Registry<ItemModifier>> ITEM_MODIFIER = FARegistries.createRegistryKey("item_modifiers");

    private static final DeferredRegister<RitualResultType<?>> RITUAL_RESULT_DEFERRED_REGISTER = DeferredRegister.create(FARegistries.RITUAL_RESULT_TYPE, ForbiddenArcanus.MOD_ID);
    private static final DeferredRegister<ItemModifier> ITEM_MODIFIER_DEFERRED_REGISTER = DeferredRegister.create(FARegistries.ITEM_MODIFIER, ForbiddenArcanus.MOD_ID);

    public static final Supplier<IForgeRegistry<RitualResultType<?>>> RITUAL_RESULT_TYPE_REGISTRY = RITUAL_RESULT_DEFERRED_REGISTER.makeRegistry(RegistryBuilder::new);
    public static final Supplier<IForgeRegistry<ItemModifier>> ITEM_MODIFIER_REGISTRY = ITEM_MODIFIER_DEFERRED_REGISTER.makeRegistry(RegistryBuilder::new);

    public static void register(IEventBus modEventBus) {
        FARegistries.RITUAL_RESULT_DEFERRED_REGISTER.register(modEventBus);
        FARegistries.ITEM_MODIFIER_DEFERRED_REGISTER.register(modEventBus);
    }

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name) {
        return ResourceKey.createRegistryKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, name));
    }
}

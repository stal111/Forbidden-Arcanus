package com.stal111.forbidden_arcanus.core.init.world;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.feature.treedecorators.CherryVinesDecorator;
import com.stal111.forbidden_arcanus.common.world.feature.treedecorators.LeafCarpetDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Mod Tree Decorators <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.core.init.world.ModTreeDecorators
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-04-11
 */
public class ModTreeDecorators {

    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<TreeDecoratorType<LeafCarpetDecorator>> LEAF_CARPET_DECORATOR = register("leaf_carpet_decorator", LeafCarpetDecorator.CODEC);
    public static final RegistryObject<TreeDecoratorType<CherryVinesDecorator>> CHERRY_VINES_DECORATOR = register("cherry_vines_decorator", CherryVinesDecorator.CODEC);

    private static<T extends TreeDecorator> RegistryObject<TreeDecoratorType<T>> register(String name, Codec<T> codec) {
        return TREE_DECORATORS.register(name, () -> new TreeDecoratorType<>(codec));
    }

    public static void load() {

    }
}

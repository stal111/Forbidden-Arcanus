package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @author stal111
 * @since 28.04.2024
 */
public class EssenceUtremJarItem extends BlockItem {

    public EssenceUtremJarItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return IClientItemExtensions.super.getCustomRenderer();
            }
        });
    }
}

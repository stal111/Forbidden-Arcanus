package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.client.renderer.block.EssenceUtremJarRenderer;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.ItemEssenceData;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.util.Lazy;
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

    public static ItemStack create(EssenceType type, Item item, int amount) {
        ItemStack stack = new ItemStack(item);

        stack.set(ModDataComponents.ESSENCE_DATA, new ItemEssenceData(EssenceData.of(type, amount, 10000), true));

        return stack;
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private final Lazy<BlockEntityWithoutLevelRenderer> renderer = Lazy.of(() -> new EssenceUtremJarRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer.get();
            }
        });
    }
}

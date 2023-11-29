package com.stal111.forbidden_arcanus.common.research.icon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 25.11.2023
 */
public final class ItemIcon implements IconProvider {

    public static final Codec<ItemIcon> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(info -> {
                return info.item;
            })
    ).apply(instance, ItemIcon::new));

    private final Item item;

    public ItemIcon(Item item) {
        this.item = item;
    }

    @Override
    public void renderIcon(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.renderFakeItem(new ItemStack(this.item), x, y);
    }
}

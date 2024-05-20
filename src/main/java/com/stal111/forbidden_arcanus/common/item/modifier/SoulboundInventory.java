package com.stal111.forbidden_arcanus.common.item.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stal111
 * @since 19.05.2024
 */
public record SoulboundInventory(List<Entry> entries) {

    public static final Codec<SoulboundInventory> CODEC = Entry.CODEC.listOf().xmap(SoulboundInventory::new, SoulboundInventory::entries);

    public static SoulboundInventory create() {
        return new SoulboundInventory(new ArrayList<>());
    }

    public void add(int slot, ItemStack stack) {
        this.entries.add(new Entry(slot, stack));
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    public record Entry(int slot, ItemStack stack) {

        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("slot").forGetter(Entry::slot),
                ItemStack.CODEC.fieldOf("stack").forGetter(Entry::stack)
        ).apply(instance, Entry::new));
    }
}

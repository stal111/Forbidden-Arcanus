package com.stal111.forbidden_arcanus.common.item.counter;

import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter;

/**
 * Obsidian Skull Counter <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.counter.ObsidianSkullCounter
 *
 * @author stal111
 * @version 1.18.1 - 2.0.0
 * @since 2021-12-19
 */
public class ObsidianSkullCounter extends SimpleCounter {

    public ObsidianSkullCounter(ResourceLocation name) {
        super(name);
    }

    @Override
    public void tick(CompoundTag tag) {
        String damageSource = tag.getString("DamageSource");
        boolean flag = ObsidianSkullItem.DAMAGE_SOURCES.stream().anyMatch(source -> source.getMsgId().equals(damageSource));

        if (flag && this.getValue() < ObsidianSkullItem.OBSIDIAN_SKULL_PROTECTION_TIME) {
            this.increase();
        } else if (!flag && this.getValue() > 0) {
            this.decrease();
        }
    }
}

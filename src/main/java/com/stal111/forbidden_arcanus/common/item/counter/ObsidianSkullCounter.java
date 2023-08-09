package com.stal111.forbidden_arcanus.common.item.counter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.api.common.counter.SerializableCounter;

/**
 * Obsidian Skull Counter <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.counter.ObsidianSkullCounter
 *
 * @author stal111
 * @since 2021-12-19
 */
public class ObsidianSkullCounter extends SerializableCounter {

    public ObsidianSkullCounter(ResourceLocation name) {
        super(name);
    }

    @Override
    public void tick(CompoundTag tag) {
        String damageSource = tag.getString("DamageSource");

        //TODO
//        boolean flag = ObsidianSkullItem.DAMAGE_SOURCES.stream().anyMatch(source -> source.getMsgId().equals(damageSource));
//
//        if (flag && this.getValue() < ObsidianSkullItem.OBSIDIAN_SKULL_PROTECTION_TIME) {
//            this.increase();
//        } else if (!flag && this.getValue() > 0) {
//            this.decrease();
//        }
    }
}

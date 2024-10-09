package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 2022-06-02
 */
public enum ClibanoFireType implements StringRepresentable {
    FIRE("fire", 13, 1.0D, null),
    SOUL_FIRE("soul_fire", 9, 1.5D, ModTags.Items.CLIBANO_CREATES_SOUL_FIRE),
    ENCHANTED_FIRE("enchanted_fire", 14, 2.5D, ModTags.Items.CLIBANO_CREATES_ENCHANTED_FIRE);

    public static final StringRepresentable.EnumCodec<ClibanoFireType> CODEC = StringRepresentable.fromEnum(ClibanoFireType::values);

    private final String name;
    private final int lightLevel;
    private final double cookingSpeedMultiplier;
    private final TagKey<Item> tagKey;

    ClibanoFireType(String name, int lightLevel, double cookingSpeedMultiplier, @Nullable TagKey<Item> tagKey) {
        this.name = name;
        this.lightLevel = lightLevel;
        this.cookingSpeedMultiplier = cookingSpeedMultiplier;
        this.tagKey = tagKey;
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return this.name;
    }

    public int getLightLevel() {
        return this.lightLevel;
    }

    public double getCookingSpeedMultiplier() {
        return this.cookingSpeedMultiplier;
    }

    @Nullable
    public TagKey<Item> getTagKey() {
        return this.tagKey;
    }

    public static ClibanoFireType fromItem(ItemStack stack) {
        for (ClibanoFireType type : ClibanoFireType.values()) {
            if (type.getTagKey() != null && stack.is(type.getTagKey())) {
                return type;
            }
        }
        return ClibanoFireType.FIRE;
    }
}

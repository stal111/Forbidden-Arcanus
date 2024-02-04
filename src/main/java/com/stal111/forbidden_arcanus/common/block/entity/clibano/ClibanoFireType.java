package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.stal111.forbidden_arcanus.common.block.properties.ClibanoCenterType;
import com.stal111.forbidden_arcanus.common.block.properties.ClibanoSideType;
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
    FIRE("fire", 1.0D, null, ClibanoCenterType.FRONT_FIRE, ClibanoSideType.FIRE),
    SOUL_FIRE("soul_fire", 1.5D, ModTags.Items.CLIBANO_CREATES_SOUL_FIRE, ClibanoCenterType.FRONT_SOUL_FIRE, ClibanoSideType.SOUL_FIRE),
    ENCHANTED_FIRE("enchanted_fire", 2.5D, ModTags.Items.CLIBANO_CREATES_ENCHANTED_FIRE, ClibanoCenterType.FRONT_ENCHANTED_FIRE, ClibanoSideType.ENCHANTED_FIRE);

    public static final StringRepresentable.EnumCodec<ClibanoFireType> CODEC = StringRepresentable.fromEnum(ClibanoFireType::values);

    private final String name;
    private final double cookingSpeedMultiplier;
    private final TagKey<Item> tagKey;

    private final ClibanoCenterType centerType;
    private final ClibanoSideType sideType;

    ClibanoFireType(String name, double cookingSpeedMultiplier, @Nullable TagKey<Item> tagKey, ClibanoCenterType centerType, ClibanoSideType sideType) {
        this.name = name;
        this.cookingSpeedMultiplier = cookingSpeedMultiplier;
        this.tagKey = tagKey;
        this.centerType = centerType;
        this.sideType = sideType;
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return this.name;
    }

    public double getCookingSpeedMultiplier() {
        return this.cookingSpeedMultiplier;
    }

    @Nullable
    public TagKey<Item> getTagKey() {
        return this.tagKey;
    }

    public ClibanoCenterType getCenterType() {
        return this.centerType;
    }

    public ClibanoSideType getSideType() {
        return this.sideType;
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

package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author stal111
 * @since 09.05.2024
 */
public record StoredEntity(CustomData data) implements TooltipProvider {

    private static final List<String> IGNORED_TAGS  = Arrays.asList(
            "Air",
            "Brain",
            "FallDistance",
            "FallFlying",
            "Fire",
            "HurtByTimestamp",
            "HurtTime",
            "Motion",
            "OnGround",
            "PortalCooldown",
            "Pos",
            "Rotation",
            "SleepingX",
            "SleepingY",
            "SleepingZ",
            "Passengers",
            "leash",
            "UUID"
    );

    public static final Codec<StoredEntity> CODEC = CustomData.CODEC_WITH_ID.xmap(StoredEntity::new, StoredEntity::data);
    public static final StreamCodec<ByteBuf, StoredEntity> STREAM_CODEC = CustomData.STREAM_CODEC.map(StoredEntity::new, StoredEntity::data);

    private static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");
    private static final MapCodec<Component> DISPLAY_NAME_FIELD_CODEC = ComponentSerialization.FLAT_CODEC.fieldOf("CustomName");

    private static final String STORED_ENTITY_KEY = Util.makeDescriptionId("item", new ResourceLocation(ForbiddenArcanus.MOD_ID, "stored_entity"));
    private static final String STORED_ENTITY_WITH_NAME_KEY = Util.makeDescriptionId("item", new ResourceLocation(ForbiddenArcanus.MOD_ID, "stored_entity.with_name"));

    public static StoredEntity of(LivingEntity entity) {
        entity.stopRiding();
        entity.ejectPassengers();

        CompoundTag tag = new CompoundTag();
        entity.save(tag);

        IGNORED_TAGS.forEach(tag::remove);

        return new StoredEntity(CustomData.of(tag));
    }

    public Entity createEntity(Level level) {
        return EntityType.loadEntityRecursive(this.data.copyTag(), level, Function.identity());
    }

    public Optional<EntityType<?>> getEntityType() {
        return  this.data.read(ENTITY_TYPE_FIELD_CODEC).result();
    }

    public Optional<Component> getDisplayName() {
        return this.data.read(DISPLAY_NAME_FIELD_CODEC).result();
    }

    @Override
    public void addToTooltip(Item.@NotNull TooltipContext context, @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        this.getEntityType().map(type -> Component.translatable(type.getDescriptionId())).ifPresent(type -> {
            MutableComponent component = this.getDisplayName()
                    .map(name -> Component.translatable(STORED_ENTITY_WITH_NAME_KEY, type, name))
                    .orElse(Component.translatable(STORED_ENTITY_KEY, type));

            component.withStyle(ChatFormatting.GRAY);

            consumer.accept(component);
        });
    }
}

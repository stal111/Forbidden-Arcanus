package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * @author stal111
 * @since 09.05.2024
 */
public record StoredEntity(CustomData data, @Nullable EntityType<?> type, @Nullable Component name) {

    public static final Codec<EntityType<?>> ENTITY_TYPE_CODEC = ResourceLocation.CODEC.xmap(BuiltInRegistries.ENTITY_TYPE::get, BuiltInRegistries.ENTITY_TYPE::getKey);
    public static final StreamCodec<ByteBuf, EntityType<?>> ENTITY_TYPE_STREAM_CODEC = ResourceLocation.STREAM_CODEC.map(BuiltInRegistries.ENTITY_TYPE::get, BuiltInRegistries.ENTITY_TYPE::getKey);

    public static final StoredEntity EMPTY = new StoredEntity(CustomData.EMPTY, null, null);

    public static final Codec<StoredEntity> FULL_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CustomData.CODEC.fieldOf("data").forGetter(StoredEntity::data),
            ENTITY_TYPE_CODEC.optionalFieldOf("type", null).forGetter(StoredEntity::type),
            ComponentSerialization.CODEC.optionalFieldOf("name", null).forGetter(StoredEntity::name)
    ).apply(instance, StoredEntity::new));

    public static final Codec<StoredEntity> CODEC = Codec.withAlternative(FULL_CODEC,
            RecordCodecBuilder.create(instance -> instance.group(
                    CustomData.CODEC.fieldOf("data").forGetter(StoredEntity::data),
                    ENTITY_TYPE_CODEC.optionalFieldOf("type", null).forGetter(StoredEntity::type)
            ).apply(instance, (data, type) -> new StoredEntity(data, type, null)))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, StoredEntity> STREAM_CODEC = StreamCodec.composite(
            ENTITY_TYPE_STREAM_CODEC,
            StoredEntity::type,
            ComponentSerialization.STREAM_CODEC,
            StoredEntity::name,
            (type, component) -> new StoredEntity(CustomData.EMPTY, type, component)
    );

    public static StoredEntity of(LivingEntity entity) {
        entity.stopRiding();
        entity.ejectPassengers();

        CompoundTag tag = new CompoundTag();
        entity.save(tag);

        return new StoredEntity(CustomData.of(tag), entity.getType(), entity.getCustomName());
    }

    public Entity createEntity(Level level) {
        return EntityType.loadEntityRecursive(this.data.copyTag(), level, Function.identity());
    }
}

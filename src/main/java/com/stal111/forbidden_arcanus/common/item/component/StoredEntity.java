package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
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

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author stal111
 * @since 09.05.2024
 */
public record StoredEntity(CustomData data, Optional<EntityType<?>> type, Optional<Component> name) implements TooltipProvider {

    public static final Codec<EntityType<?>> ENTITY_TYPE_CODEC = ResourceLocation.CODEC.xmap(BuiltInRegistries.ENTITY_TYPE::get, BuiltInRegistries.ENTITY_TYPE::getKey);
    public static final StreamCodec<ByteBuf, EntityType<?>> ENTITY_TYPE_STREAM_CODEC = ResourceLocation.STREAM_CODEC.map(BuiltInRegistries.ENTITY_TYPE::get, BuiltInRegistries.ENTITY_TYPE::getKey);

    public static final StoredEntity EMPTY = new StoredEntity(CustomData.EMPTY, Optional.empty(), Optional.empty());

    public static final Codec<StoredEntity> FULL_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CustomData.CODEC.fieldOf("data").forGetter(StoredEntity::data),
            ENTITY_TYPE_CODEC.optionalFieldOf("type").forGetter(StoredEntity::type),
            ComponentSerialization.FLAT_CODEC.optionalFieldOf("name").forGetter(StoredEntity::name)
    ).apply(instance, StoredEntity::new));

    public static final Codec<StoredEntity> CODEC = Codec.withAlternative(FULL_CODEC,
            RecordCodecBuilder.create(instance -> instance.group(
                    CustomData.CODEC.fieldOf("data").forGetter(StoredEntity::data),
                    ENTITY_TYPE_CODEC.optionalFieldOf("type").forGetter(StoredEntity::type)
            ).apply(instance, (data, type) -> new StoredEntity(data, type, Optional.empty())))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, StoredEntity> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ENTITY_TYPE_STREAM_CODEC),
            StoredEntity::type,
            ByteBufCodecs.optional(ComponentSerialization.STREAM_CODEC),
            StoredEntity::name,
            (type, component) -> new StoredEntity(CustomData.EMPTY, type, component)
    );

    private static final String STORED_ENTITY_KEY = Util.makeDescriptionId("item", new ResourceLocation(ForbiddenArcanus.MOD_ID, "stored_entity"));
    private static final String STORED_ENTITY_WITH_NAME_KEY = Util.makeDescriptionId("item", new ResourceLocation(ForbiddenArcanus.MOD_ID, "stored_entity.with_name"));

    public static StoredEntity of(LivingEntity entity) {
        entity.stopRiding();
        entity.ejectPassengers();

        CompoundTag tag = new CompoundTag();
        entity.save(tag);

        return new StoredEntity(CustomData.of(tag), Optional.of(entity.getType()), Optional.ofNullable(entity.getCustomName()));
    }

    public Entity createEntity(Level level) {
        return EntityType.loadEntityRecursive(this.data.copyTag(), level, Function.identity());
    }

    @Override
    public void addToTooltip(Item.@NotNull TooltipContext context, @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        this.type.map(type -> Component.translatable(type.getDescriptionId())).ifPresent(type -> {
            MutableComponent component = this.name
                    .map(name -> Component.translatable(STORED_ENTITY_WITH_NAME_KEY, type, name))
                    .orElse(Component.translatable(STORED_ENTITY_KEY, type));

            component.withStyle(ChatFormatting.GRAY);

            consumer.accept(component);
        });
    }
}

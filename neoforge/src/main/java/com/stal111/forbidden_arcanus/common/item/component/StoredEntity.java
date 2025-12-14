package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.Util;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueOutput;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author stal111
 * @since 09.05.2024
 */
//TODO: Use TypedEntityData?
public record StoredEntity(CustomData data) implements TooltipProvider {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final List<String> IGNORED_TAGS = Arrays.asList(
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

    public static final Codec<StoredEntity> CODEC = CustomData.CODEC.xmap(StoredEntity::new, StoredEntity::data);
    public static final StreamCodec<ByteBuf, StoredEntity> STREAM_CODEC = CustomData.STREAM_CODEC.map(StoredEntity::new, StoredEntity::data);

    private static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");
    private static final MapCodec<Component> DISPLAY_NAME_FIELD_CODEC = ComponentSerialization.CODEC.fieldOf("CustomName");

    private static final String STORED_ENTITY_KEY = Util.makeDescriptionId("item", ForbiddenArcanus.identifier("stored_entity"));
    private static final String STORED_ENTITY_WITH_NAME_KEY = Util.makeDescriptionId("item", ForbiddenArcanus.identifier("stored_entity.with_name"));

    public static StoredEntity of(LivingEntity entity) {
        entity.stopRiding();
        entity.ejectPassengers();

        CustomData customData;

        try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(entity.problemPath(), LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(scopedCollector, entity.registryAccess());

            entity.save(output);

            IGNORED_TAGS.forEach(output::discard);

            customData = CustomData.of(output.buildResult());
        }

        return new StoredEntity(customData);
    }

    @Nullable
    public Entity createEntity(Level level) {
        return EntityType.loadEntityRecursive(this.data.copyTag(), level, EntitySpawnReason.SPAWN_ITEM_USE, EntityProcessor.NOP);
    }

    public Optional<EntityType<?>> getEntityType() {
        return this.data.copyTag().read(ENTITY_TYPE_FIELD_CODEC);
    }

    public Optional<Component> getDisplayName() {
        return this.data.copyTag().read(DISPLAY_NAME_FIELD_CODEC);
    }


    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
        this.getEntityType().map(type -> Component.translatable(type.getDescriptionId())).ifPresent(type -> {
            MutableComponent component = this.getDisplayName()
                    .map(name -> Component.translatable(STORED_ENTITY_WITH_NAME_KEY, type, name))
                    .orElse(Component.translatable(STORED_ENTITY_KEY, type));

            component.withStyle(ChatFormatting.GRAY);

            tooltipAdder.accept(component);
        });
    }
}

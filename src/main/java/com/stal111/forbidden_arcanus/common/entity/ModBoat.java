package com.stal111.forbidden_arcanus.common.entity;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2022-06-25
 */
public class ModBoat extends Boat implements CustomBoat {

    private static final EntityDataAccessor<Integer> DATA_ID_WOOD_TYPE = SynchedEntityData.defineId(ModBoat.class, EntityDataSerializers.INT);

    public ModBoat(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public ModBoat(Level level, double x, double y, double z) {
        this(ModEntities.BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_WOOD_TYPE, 0);
    }

    @Override
    protected void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Type", 8)) {
            this.setWoodType(Type.byName(tag.getString("Type")));
        }
    }

    @Override
    protected void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Type", this.getWoodType().getSerializedName());
    }

    @Nonnull
    @Override
    public Item getDropItem() {
        return this.getWoodType().getBoatItem().get();
    }

    @Override
    public Type getWoodType() {
        return Type.byId(this.entityData.get(DATA_ID_WOOD_TYPE));
    }

    @Override
    public void setWoodType(Type type) {
        this.entityData.set(DATA_ID_WOOD_TYPE, type.ordinal());
    }

    @Nonnull
    @Override
    protected Component getTypeName() {
        return EntityType.BOAT.getDescription();
    }

    public enum Type implements StringRepresentable {
        AURUM("aurum", ModItems.AURUM_BOAT, ModItems.AURUM_CHEST_BOAT),
        EDELWOOD("edelwood", ModItems.EDELWOOD_BOAT, ModItems.EDELWOOD_CHEST_BOAT);
       // FUNGYSS("fungyss", ModItems.FUNGYSS_BOAT, ModItems.FUNGYSS_CHEST_BOAT);

        private final String name;
        private final Supplier<BoatItem> boatItem;
        private final Supplier<BoatItem> chestBoatItem;

        Type(String name, Supplier<BoatItem> boatItem, Supplier<BoatItem> chestBoatItem) {
            this.name = name;
            this.boatItem = boatItem;
            this.chestBoatItem = chestBoatItem;
        }

        @Nonnull
        @Override
        public String getSerializedName() {
            return this.name;
        }

        public Supplier<BoatItem> getBoatItem() {
            return this.boatItem;
        }

        public Supplier<BoatItem> getChestBoatItem() {
            return this.chestBoatItem;
        }

        public ResourceLocation getTexture(boolean hasChest) {
            if (hasChest) {
                return new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/chest_boat/" + this.name + ".png");
            }
            return new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/boat/" + this.name + ".png");
        }

        public String getModelLocation() {
            return "boat/" + this.name;
        }

        public String getChestModelLocation() {
            return "chest_boat/" + this.name;
        }

        public static Type byId(int id) {
            Type[] values = values();
            if (id < 0 || id >= values.length) {
                id = 0;
            }

            return values[id];
        }

        public static Type byName(String name) {
            Type[] values = values();

            for (Type value : values) {
                if (value.getSerializedName().equals(name)) {
                    return value;
                }
            }

            return values[0];
        }
    }
}

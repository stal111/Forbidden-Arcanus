package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.EssenceManager;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu;
import com.stal111.forbidden_arcanus.common.inventory.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.inventory.input.HephaestusForgeInputs;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.valhelsia.valhelsia_core.common.block.entity.MenuCreationContext;
import net.valhelsia.valhelsia_core.common.block.entity.ValhelsiaContainerBlockEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Hephaestus Forge Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity
 *
 * @author stal111
 * @since 2021-06-18
 */
public class HephaestusForgeBlockEntity extends ValhelsiaContainerBlockEntity {

    private final ContainerData hephaestusForgeData;
    private final RitualManager ritualManager = new RitualManager(this);
    private final EssenceManager essenceManager = new EssenceManager(this);
    private final MagicCircle magicCircle = new MagicCircle(this.ritualManager);
    private List<LivingEntity> entities = new ArrayList<>();

    private int displayCounter;

    public HephaestusForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEPHAESTUS_FORGE.get(), pos, state, 9, (slot, stack) -> {
            return true;
        });
        this.hephaestusForgeData = new ContainerData() {
            @Override
            public int get(int index) {
                EssenceManager manager = HephaestusForgeBlockEntity.this.getEssenceManager();

                return switch (index) {
                    case 0 -> manager.getAureal();
                    case 1 -> manager.getSouls();
                    case 2 -> manager.getBlood();
                    case 3 -> manager.getExperience();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                EssenceManager manager = HephaestusForgeBlockEntity.this.getEssenceManager();

                switch (index) {
                    case 0 -> manager.setAureal(value);
                    case 1 -> manager.setSouls(value);
                    case 2 -> manager.setBlood(value);
                    case 3 -> manager.setExperience(value);
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, HephaestusForgeBlockEntity blockEntity) {
        blockEntity.magicCircle.tick();
        blockEntity.displayCounter++;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, HephaestusForgeBlockEntity blockEntity) {
        for (int i = 5; i <= 8; i++) {
            ItemStack stack = blockEntity.getStack(i);

            if (stack.isEmpty()) {
                continue;
            }

            EssenceType inputType = blockEntity.getInputTypeFromSlot(i);

            if (inputType == null) {
                continue;
            }

            HephaestusForgeInput input = blockEntity.getInput(stack, inputType);

            if (input != null) {
                blockEntity.fillWith(inputType, stack, input, i);

                blockEntity.setChanged();
            }
        }

        if (level.getGameTime() % 80 == 0) {
            ((HephaestusForgeBlock) state.getBlock()).updateState(state, level, pos);
        }

        if (level.getGameTime() % 20 == 0) {
            blockEntity.entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(5, 5, 5));

            blockEntity.essenceManager.tick();
        }

        blockEntity.ritualManager.tick((ServerLevel) level, pos);
    }

    private EssenceType getInputTypeFromSlot(int slot) {
        return switch (slot) {
            case 5 -> EssenceType.AUREAL;
            case 6 -> EssenceType.SOULS;
            case 7 -> EssenceType.BLOOD;
            case 8 -> EssenceType.EXPERIENCE;
            default -> null;
        };
    }

    @Nullable
    private HephaestusForgeInput getInput(ItemStack stack, EssenceType inputType) {
        if (this.isTypeFull(inputType)) {
            return null;
        }

        return HephaestusForgeInputs.getInputs().stream().filter(input -> input.canInput(inputType, stack)).findFirst().orElse(null);
    }

    private boolean isTypeFull(EssenceType inputType) {
        HephaestusForgeLevel level = this.getForgeLevel();
        EssenceManager manager = this.getEssenceManager();

        return switch (inputType) {
            case AUREAL -> manager.getAureal() >= level.getMaxAureal();
            case SOULS -> manager.getSouls() >= level.getMaxSouls();
            case BLOOD -> manager.getBlood() >= level.getMaxBlood();
            case EXPERIENCE -> manager.getExperience() >= level.getMaxExperience();
        };
    }

    public HephaestusForgeLevel getForgeLevel() {
        return HephaestusForgeLevel.getFromIndex(this.getBlockState().getValue(HephaestusForgeBlock.TIER));
    }

    public ContainerData getHephaestusForgeData() {
        return this.hephaestusForgeData;
    }

    public EssenceManager getEssenceManager() {
        return essenceManager;
    }

    public MagicCircle getMagicCircle() {
        return magicCircle;
    }

    public List<LivingEntity> getEntities() {
        return entities;
    }

    public void fillWith(EssenceType essenceType, ItemStack stack, HephaestusForgeInput input, int slot) {
        int value = input.getInputValue(essenceType, stack, Objects.requireNonNull(this.getLevel()).getRandom());

        this.getEssenceManager().increaseEssence(essenceType, value);

        input.finishInput(essenceType, stack, this, slot, value);
    }

    public RitualManager getRitualManager() {
        return ritualManager;
    }

    public int getDisplayCounter() {
        return displayCounter;
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag tag) {
        super.saveAdditional(tag);

        this.saveInventory(tag);

        tag.put("Ritual", this.getRitualManager().save(new CompoundTag()));
        tag.put("Essences", this.getEssenceManager().save(new CompoundTag()));
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);

        this.loadInventory(tag);

        this.getRitualManager().load(tag.getCompound("Ritual"));
        this.getEssenceManager().load(tag.getCompound("Essences"));
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public AABB getRenderBoundingBox() {
        AABB boundingBox = new AABB(this.getBlockPos()).expandTowards(0.0D, 1.0D, 0.0D);

        if (this.getRitualManager().isRitualActive()) {
            boundingBox = boundingBox.inflate(2.5F, 0.0F, 2.5D);
        }
        return boundingBox;
    }

    @Nonnull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.forbidden_arcanus.hephaestus_forge");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull MenuCreationContext creationContext) {
        return new HephaestusForgeMenu(containerId, this.getItemStackHandler(), this.getHephaestusForgeData(), creationContext);
    }
}

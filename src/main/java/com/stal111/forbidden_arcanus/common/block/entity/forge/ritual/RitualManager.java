package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.google.common.base.Suppliers;
import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleController;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceModifier;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesStorage;
import com.stal111.forbidden_arcanus.common.block.pedestal.effect.PedestalEffectTrigger;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerAccessor;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.valhelsia.valhelsia_core.api.common.util.SerializableComponent;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Ritual Manager <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualManager
 *
 * @author stal111
 * @since 2021-07-09
 */
public class RitualManager implements SerializableComponent {

    public static final String TAG_ACTIVE_RITUAL = "active_ritual";

    public static final int PEDESTAL_ITEM_HEIGHT = 140;
    public static final int LIGHTNING_COUNTER_THRESHOLD = 300;

    public static final Vec3i[] PEDESTAL_OFFSETS = {
            new Vec3i(-3, 0, 0),
            new Vec3i(-2, 0, -2),
            new Vec3i(0, 0, -3),
            new Vec3i(2, 0, -2),
            new Vec3i(3, 0, 0),
            new Vec3i(2, 0, 2),
            new Vec3i(0, 0, 3),
            new Vec3i(-2, 0, 2)
    };

    private final MainIngredientAccessor mainIngredientAccessor;
    private final EnhancerAccessor enhancerAccessor;
    private final MagicCircleController magicCircleController;

    private ServerLevel level;
    private BlockPos pos;
    private int forgeTier;

    private final HashMap<BlockPos, ItemStack> cachedIngredients = new HashMap<>();
    private @Nullable Holder<Ritual> validRitual;
    //We need to wrap this in a Supplier because the ActiveRitualData cannot be deserialized during #load as the level is not set yet
    private Supplier<ActiveRitualData> activeRitualData = Suppliers.memoize(() -> null);

    public RitualManager(MainIngredientAccessor accessor, EnhancerAccessor enhancerAccessor, MagicCircleController circleController, int forgeTier) {
        this.mainIngredientAccessor = accessor;
        this.enhancerAccessor = enhancerAccessor;
        this.magicCircleController = circleController;
        this.forgeTier = forgeTier;
    }

    public void setup(ServerLevel level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    public void setForgeTier(int forgeTier) {
        this.forgeTier = forgeTier;
    }

    public Optional<Holder<Ritual>> getValidRitual() {
        return Optional.ofNullable(this.validRitual);
    }

    private Optional<ActiveRitualData> getActiveRitualData() {
        return Optional.ofNullable(this.activeRitualData.get());
    }

    public void setActiveRitual(@Nullable Holder<Ritual> ritual) {
        this.activeRitualData = Suppliers.memoize(() -> ritual != null ? ActiveRitualData.create(ritual) : null);

        int duration = ritual != null ? ritual.value().duration() : 0;

        this.level.blockEvent(this.pos, this.level.getBlockState(this.pos).getBlock(), HephaestusForgeBlockEntity.UPDATE_RITUAL_DURATION, duration);
    }

    public boolean isRitualActive() {
        return this.level != null && this.getActiveRitualData().isPresent();
    }

    public void updateIngredient(BlockPos pos, ItemStack stack, EssencesDefinition definition) {
        this.cachedIngredients.put(pos, stack);

        if (this.isRitualActive()) {
            this.failRitual();
        }

        this.updateValidRitual(definition);
    }

    public void updateValidRitual(EssencesDefinition definition) {
        boolean oldValue = this.validRitual != null;

        for (Holder<Ritual> ritual : this.level.registryAccess().registryOrThrow(FARegistries.RITUAL).holders().toList()) {
            if (this.canStartRitual(ritual, definition)) {
                if (!oldValue) {
                    this.updateRitualIndicator(true);
                }

                this.validRitual = ritual;

                return;
            }
        }

        this.validRitual = null;

        if (oldValue && !this.isRitualActive()) {
            this.updateRitualIndicator(false);
        }
    }

    public boolean canStartRitual(Holder<Ritual> ritual, EssencesDefinition definition) {
        List<EssenceModifier> modifiers = this.enhancerAccessor.getEnhancers().stream()
                .flatMap(enhancerDefinition -> enhancerDefinition.getEffects(EnhancerTarget.HEPHAESTUS_FORGE))
                .filter(effect -> effect instanceof EssenceModifier)
                .map(effect -> (EssenceModifier) effect)
                .toList();

        EssencesDefinition updatedEssences = ritual.value().essences().applyModifiers(modifiers);
        Ritual.RitualStartContext context = Ritual.RitualStartContext.of(this.level, this.pos, this.forgeTier, this.cachedIngredients.values(), this.mainIngredientAccessor.get(), this.enhancerAccessor.getEnhancers());

        return definition.hasMoreThan(updatedEssences) && ritual.value().canStart(context);
    }

    public boolean startRitual(EssencesStorage storage) {
        return this.getValidRitual().map(ritual -> {
            this.setActiveRitual(ritual);

            this.magicCircleController.createMagicCircle(this.level, this.pos, ritual.value().magicCircleType());

            storage.reduce(ritual.value().essences());

            this.forEachPedestal(PedestalBlockEntity::hasStack, blockEntity -> {
                blockEntity.setItemHeightTarget(PEDESTAL_ITEM_HEIGHT);
            });

            return true;
        }).orElse(false);
    }

    public void tick() {
        ActiveRitualData data = this.getActiveRitualData().orElse(null);

        if (data == null) {
            return;
        }

        RandomSource random = this.level.getRandom();
        float progress = data.calculateRitualProgress();

        data.incrementCounter();

        this.handleLightningCounter(data);

        this.cachedIngredients.forEach((blockPos, stack) -> {
            this.addItemParticles(blockPos, Math.min(PedestalBlockEntity.DEFAULT_ITEM_HEIGHT + data.getCounter(), PEDESTAL_ITEM_HEIGHT), stack);
        });

        if (progress == 0.5F && random.nextDouble() <= this.getFailureChance() * 2) {
            CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), level);
            entity.setPos(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            entity.setVisualOnly(true);

            level.addFreshEntity(entity);

            data.incrementLightningCounter();

            this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalBlockEntity -> {
                if (random.nextBoolean()) {
                    ItemStack stack = pedestalBlockEntity.getStack().copy();
                    BlockPos pedestalPos = pedestalBlockEntity.getBlockPos();

                    level.addFreshEntity(new ItemEntity(level, pedestalPos.getX() + 0.5, pedestalPos.getY() + pedestalBlockEntity.getItemHeight() / 100.0F, pedestalPos.getZ() + 0.5, stack));
                    pedestalBlockEntity.clearStack(null, PedestalEffectTrigger.RITUAL_FINISHED);
                }
            });
        }

        if (progress == 1.0F) {
            if (random.nextDouble() > this.getFailureChance()) {
                this.finishRitual(data);
            } else {
                this.failRitual();
            }
        }
    }

    private void handleLightningCounter(ActiveRitualData data) {
        if (data.getLightningCounter() != 0) {
            data.incrementLightningCounter();

            if (data.getLightningCounter() == LIGHTNING_COUNTER_THRESHOLD) {
                List<ItemStack> list = new ArrayList<>();

                this.forEachPedestal(PedestalBlockEntity::hasStack, pedestalBlockEntity -> list.add(pedestalBlockEntity.getStack()));

                if (!data.getRitual().checkIngredients(list, this.mainIngredientAccessor.get())) {
                    this.failRitual();
                }
            }
        }
    }

    public void finishRitual(ActiveRitualData data) {
        data.getRitual().result().apply(this.mainIngredientAccessor, this.level, this.pos, this.forgeTier);

        this.reset();
    }

    public void failRitual() {
        ItemStack stack = this.mainIngredientAccessor.get();

        this.reset();

        if (!stack.isEmpty()) {
            this.level.addFreshEntity(new ItemEntity(this.level, this.pos.getX() + 0.5, this.pos.getY() + 1, this.pos.getZ() + 0.5, stack));

            this.mainIngredientAccessor.set(ItemStack.EMPTY);
        }

        this.level.sendParticles(ModParticles.HUGE_MAGIC_EXPLOSION.get(), this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, 0, 1.0D, 0.0D, 0.0D, 0.0D);
        this.level.playSound(null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 4.0F, (1.0F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F) * 0.7F);
    }

    private void clearPedestals() {
        this.forEachPedestal(PedestalBlockEntity::hasStack, blockEntity -> blockEntity.clearStack(null, PedestalEffectTrigger.RITUAL_FINISHED));

        this.cachedIngredients.clear();
    }

    private void addItemParticles(BlockPos pedestalPos, int itemHeight, ItemStack stack) {
        double posX = pedestalPos.getX() + 0.5D;
        double posY = pedestalPos.getY() + 0.1D + itemHeight / 100.0F;
        double posZ = pedestalPos.getZ() + 0.5D;
        double xSpeed = 0.1D * (this.pos.getX() - pedestalPos.getX());
        double ySpeed = 0.22D;
        double zSpeed = 0.1D * (this.pos.getZ() - pedestalPos.getZ());

        if (this.level.getRandom().nextDouble() < 0.6D) {
            this.level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), posX, posY, posZ, 0, xSpeed, ySpeed, zSpeed, 0.9D);
        }
    }

    private void reset() {
        this.validRitual = null;
        this.setActiveRitual(null);
        this.magicCircleController.removeMagicCircle(this.level, this.pos);
        this.updateRitualIndicator(false);
        this.clearPedestals();
    }

    public double getFailureChance() {
        //TODO
        return 0.0D;
        //return ((this.getBlockEntity().getEssenceManager().getCorruption() + 5) / (float) this.getBlockEntity().getForgeLevel().getMaxCorruption()) / 2;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        this.getActiveRitualData().flatMap(data -> {
            return ActiveRitualData.CODEC.encodeStart(RegistryOps.create(NbtOps.INSTANCE, this.level.registryAccess()), this.activeRitualData.get()).result();
        }).ifPresent(dataTag -> {
            tag.put(TAG_ACTIVE_RITUAL, dataTag);
        });

        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains(TAG_ACTIVE_RITUAL)) {
            this.activeRitualData = Suppliers.memoize(() -> {
                return ActiveRitualData.CODEC.parse(RegistryOps.create(NbtOps.INSTANCE, this.level.registryAccess()), tag).resultOrPartial(System.err::println).orElse(null);
            });
        }
    }

    public void forEachPedestal(Predicate<PedestalBlockEntity> predicate, Consumer<PedestalBlockEntity> consumer) {
        for (BlockPos pos : this.cachedIngredients.keySet()) {
            if (this.level.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity && predicate.test(blockEntity)) {
                consumer.accept(blockEntity);
            }
        }
    }

    private void updateRitualIndicator(boolean show) {
        this.level.blockEvent(this.pos, this.level.getBlockState(this.pos).getBlock(), HephaestusForgeBlockEntity.UPDATE_RITUAL_INDICATOR, BooleanUtils.toInteger(show));
    }

    public interface MainIngredientAccessor {
        ItemStack get();
        void set(ItemStack stack);
    }
}

package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

/**
 * Edelwood Mob Bucket Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.EdelwoodMobBucketItem
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-12-07
 */
public class EdelwoodMobBucketItem extends EdelwoodBucketItem {

    private final Supplier<? extends EntityType<?>> entityType;
    private final Supplier<? extends SoundEvent> emptySound;

    public EdelwoodMobBucketItem(Supplier<EntityType<?>> entityType, Supplier<Fluid> fluid, Properties properties) {
        this(entityType, fluid, null, properties);
    }

    public EdelwoodMobBucketItem(Supplier<EntityType<?>> entityType, Supplier<Fluid> fluid, Supplier<SoundEvent> emptySound, Properties properties) {
        super(fluid, properties);
        this.entityType = entityType;
        this.emptySound = emptySound;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, this.getFluid() == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onBucketUse(player, level, stack, hitResult);

        if (ret != null) {
            return ret;
        }

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        }

        BlockPos pos = hitResult.getBlockPos();
        Direction direction = hitResult.getDirection();
        BlockPos relativePos = pos.relative(direction);

        if (!level.mayInteract(player, pos) || !player.mayUseItemAt(relativePos, direction, stack)) {
            return InteractionResultHolder.fail(stack);
        }

        if (this.getFluid() == Fluids.EMPTY) {
            this.checkExtraContent(player, level, stack, pos);

            return InteractionResultHolder.sidedSuccess(this.getEmptyBucket(), level.isClientSide());
        }

        return super.use(level, player, hand);
    }

    @Override
    public void checkExtraContent(@Nullable Player player, @Nonnull Level level, @Nonnull ItemStack stack, @Nonnull BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            this.spawn(serverLevel, stack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    @Override
    protected void playEmptySound(@Nullable Player player, @Nonnull LevelAccessor level, @Nonnull BlockPos pos) {
        if (this.emptySound != null) {
            level.playSound(player, pos, this.emptySound.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
    }

    protected void spawn(ServerLevel level, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.get().spawn(level, stack, null, pos, MobSpawnType.BUCKET, true, false);

        if (entity instanceof Bucketable bucketable) {
            bucketable.loadFromBucketTag(stack.getOrCreateTag());
            bucketable.setFromBucket(true);
        }
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> components, @Nonnull TooltipFlag isAdvanced) {
        if (this.entityType.get() == EntityType.TROPICAL_FISH) {
            CompoundTag tag = stack.getTag();

            if (tag != null && tag.contains("BucketVariantTag", 3)) {
                int i = tag.getInt("BucketVariantTag");
                ChatFormatting[] chatFormatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
                String s = "color.minecraft." + TropicalFish.getBaseColor(i);
                String s1 = "color.minecraft." + TropicalFish.getPatternColor(i);

                for(int j = 0; j < TropicalFish.COMMON_VARIANTS.length; ++j) {
                    if (i == TropicalFish.COMMON_VARIANTS[j]) {
                        components.add(Component.translatable(TropicalFish.getPredefinedName(j)).withStyle(chatFormatting));
                        return;
                    }
                }

                components.add((Component.translatable(TropicalFish.getFishTypeName(i))).withStyle(chatFormatting));
                MutableComponent component = Component.translatable(s);
                if (!s.equals(s1)) {
                    component.append(", ").append(Component.translatable(s1));
                }

                component.withStyle(chatFormatting);
                components.add(component);
            }
        }
    }

    public ItemStack getFluidBucket() {
        if (this.getFluid() == Fluids.WATER) {
            return new ItemStack(ModItems.EDELWOOD_WATER_BUCKET.get());
        } else {
            return new ItemStack(ModItems.EDELWOOD_LAVA_BUCKET.get());
        }
    }

    @Override
    protected boolean canBurn(ItemStack stack) {
        return stack.is(ModItems.EDELWOOD_MAGMA_CUBE_BUCKET.get());
    }
}

package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceProvider;
import com.stal111.forbidden_arcanus.common.item.component.StoredEntity;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * @author stal111
 */
public class QuantumCatcherItem extends Item {

    public QuantumCatcherItem(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();

        return getData(stack).map(storedEntity -> {
            Level level = context.getLevel();

            if (!level.isClientSide()) {
                if (!this.summonEntity(storedEntity, context)) {
                    return InteractionResult.FAIL;
                }
            }

            playSound(level, context.getPlayer(), context.getClickedPos(), false);

            return InteractionResult.sidedSuccess(level.isClientSide());
        }).orElse(super.useOn(context));
    }

    private boolean summonEntity(StoredEntity storedEntity, UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Entity entity = storedEntity.createEntity(level);

        if (!level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) {
            pos = pos.relative(context.getClickedFace());
        }

        if (entity == null || !level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(context))) {
            return false;
        }

        entity.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);

        if (context.getPlayer() != null) {
            entity.lookAt(EntityAnchorArgument.Anchor.EYES, context.getPlayer().position());
        }
        level.addFreshEntity(entity);

        context.getItemInHand().remove(ModDataComponents.STORED_ENTITY);

        return true;
    }

    public InteractionResult onEntityInteract(ItemStack stack, Player player, LivingEntity target) {
        Level level = player.getCommandSenderWorld();
        int cost = calculateAurealCost(target);
        EssenceProvider essenceProvider = EssenceHelper.getEssenceProvider(player).orElseThrow();

        if (!this.isValidEntity(target) || getData(stack).isPresent()) {
            return InteractionResult.PASS;
        }

        if (essenceProvider.getAmount(EssenceType.AUREAL) < cost) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide()) {
            if (stack.getCount() != 1) {
                ItemStackUtils.shrinkStack(player, stack);

                stack = new ItemStack(ModItems.QUANTUM_CATCHER.get());

                if (!player.addItem(stack)) {
                    player.drop(stack, false);
                }
            }

            stack.set(ModDataComponents.STORED_ENTITY, StoredEntity.of(target));

            target.discard();

            essenceProvider.updateAmount(EssenceType.AUREAL, amount -> amount - cost);
        }

        playSound(level, player, target.blockPosition(), true);

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    public boolean isValidEntity(LivingEntity entity) {
        return !entity.getType().is(ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED) && entity.isAlive();
    }

    public static int calculateAurealCost(LivingEntity entity) {
        int health = Math.round(entity.getMaxHealth());

        if (entity.getType().getCategory().isFriendly()) {
            health = health >> 1;
        } else {
            health = (int) (health * 1.1F);
        }

        return health;
    }

    private static void playSound(Level level, @Nullable Player player, BlockPos pos, boolean release) {
        BlockPos soundPos = player == null ? pos : player.blockPosition();

        level.playSound(player, soundPos.getX() + 0.5D, soundPos.getY() + 0.5D, soundPos.getZ() + 0.5D, release ? ModSounds.QUANTUM_CATCHER_RELEASE.get() : ModSounds.QUANTUM_CATCHER_PICK_UP.get(), SoundSource.PLAYERS, 0.75F, level.getRandom().nextFloat() * 0.15F + 0.9F);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        getData(stack).ifPresent(storedEntity -> {
            storedEntity.addToTooltip(context, components::add, flag);
        });
    }

    private static Optional<StoredEntity> getData(ItemStack stack) {
       return Optional.ofNullable(stack.get(ModDataComponents.STORED_ENTITY));
    }
}
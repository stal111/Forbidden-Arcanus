package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jspecify.annotations.Nullable;

/**
 * @author stal111
 * @since 2022-12-30
 */
public class FerrogneticMixtureItem extends Item {

    public FerrogneticMixtureItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (state.is(ModBlocks.DARKSTONE_PEDESTAL.get())) {
            Player player = context.getPlayer();
            ItemStack stack = context.getItemInHand();
            BlockState newState = ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get().withPropertiesOf(state);

            this.playEffects(player, level, pos);

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                player.awardStat(Stats.ITEM_USED.get(this));
            }

            level.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
            stack.consume(1, player);

            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    private void playEffects(@Nullable Player player, Level level, BlockPos pos) {
        ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.MAGNETIC_GLOW.get(), UniformInt.of(3, 5));

        level.playSound(player, pos, ModSounds.FERROGNETIC_MIXTURE_APPLY.get(), SoundSource.BLOCKS, 0.75F, 0.9F + level.getRandom().nextFloat() * 0.1F);
    }
}

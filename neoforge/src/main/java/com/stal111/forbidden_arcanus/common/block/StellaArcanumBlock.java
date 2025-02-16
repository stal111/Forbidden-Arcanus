package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.core.config.BlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StellaArcanumBlock extends Block {

    public StellaArcanumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (tool.getEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.SILK_TOUCH)) == 0) {
            DamageSource damageSource = level.damageSources().explosion(null, null);
            level.explode(null, damageSource, null, pos.getCenter(), BlockConfig.STELLA_ARCANUM_EXPLOSION_RADIUS.get(), false, BlockConfig.STELLA_ARCANUM_BLOCK_DAMAGE.get() ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
        }

        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }
}

package com.stal111.forbidden_arcanus.common.item.mundabitur;

import com.stal111.forbidden_arcanus.core.init.ModSounds;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 */
public class MundabiturDustItem extends Item {

    public MundabiturDustItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        var transformContext = TransformPatternInteraction.TransformPatternContext.of(
                context.getLevel(),
                context.getPlayer(),
                context.getClickedPos(),
                context.getHand(),
                context.getClickedFace()
        );

        return this.tryInteract(context.getItemInHand(), TransformPatternInteraction.class, transformContext);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
        var context = EntityInteraction.EntityInteractionContext.of(player, target, hand);

        return this.tryInteract(stack, EntityInteraction.class, context);
    }

    private <T extends MundabiturInteraction.Context> InteractionResult tryInteract(ItemStack stack, Class<? extends MundabiturInteraction<T>> clazz, T context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        for (MundabiturInteraction<?> interaction : FARegistries.MUNDABITUR_INTERACTION_REGISTRY) {
            if (!clazz.isInstance(interaction)) {
                continue;
            }

            var prepared = clazz.cast(interaction).prepare(context);

            if (prepared.isPresent()) {
                if (!level.isClientSide()) {
                    prepared.get().run();

                    stack.consume(1, player);

                    if (player != null) {
                        player.awardStat(Stats.ITEM_USED.get(this));
                    }
                }

                level.playSound(player, context.getPos(), ModSounds.MUNDABITUR_DUST_USE.get(), SoundSource.PLAYERS, 1.0F, level.getRandom().nextFloat() * 0.15F + 0.9F);

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}

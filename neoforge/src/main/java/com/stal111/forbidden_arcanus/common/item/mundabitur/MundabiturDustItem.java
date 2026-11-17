package com.stal111.forbidden_arcanus.common.item.mundabitur;

import com.google.common.base.Suppliers;
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

import java.util.List;
import java.util.function.Supplier;

/**
 * @author stal111
 */
public class MundabiturDustItem extends Item {

    private static final Supplier<List<TransformPatternInteraction>> BLOCK_INTERACTIONS = Suppliers.memoize(() -> getInteractions(TransformPatternInteraction.class));
    private static final Supplier<List<EntityInteraction>> ENTITY_INTERACTIONS = Suppliers.memoize(() -> getInteractions(EntityInteraction.class));

    public MundabiturDustItem(Properties properties) {
        super(properties);
    }

    private static <T extends MundabiturInteraction<?>> List<T> getInteractions(Class<T> type) {
        return FARegistries.MUNDABITUR_INTERACTION_REGISTRY.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
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

        return this.tryInteract(context.getItemInHand(), BLOCK_INTERACTIONS.get(), transformContext);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
        var context = EntityInteraction.EntityInteractionContext.of(player, target, hand);

        return this.tryInteract(stack, ENTITY_INTERACTIONS.get(), context);
    }

    private <T extends MundabiturInteraction.Context> InteractionResult tryInteract(ItemStack stack, List<? extends MundabiturInteraction<T>> interactions, T context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        for (MundabiturInteraction<T> interaction : interactions) {
            var prepared = interaction.prepare(context);

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

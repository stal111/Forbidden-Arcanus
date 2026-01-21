package com.stal111.forbidden_arcanus.client.model;

import com.stal111.forbidden_arcanus.client.animation.ForbiddenomiconAnimation;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

/**
 * @author stal111
 * @since 30.10.2023
 */
public class DeskForbiddenomiconModel<T extends Entity> extends AbstractForbiddenomiconModel<T> {

    private final KeyframeAnimation stillAnimation;
    private final KeyframeAnimation openingAnimation;
    private final KeyframeAnimation closingAnimation;
    private final KeyframeAnimation levitateAnimation;
    private final KeyframeAnimation openStillAnimation;
    private final KeyframeAnimation pageAnimation;

    public DeskForbiddenomiconModel(ModelPart root) {
        super(root);
        this.stillAnimation = ForbiddenomiconAnimation.CLOSED.bake(root);
        this.openingAnimation = ForbiddenomiconAnimation.OPENING.bake(root);
        this.closingAnimation = ForbiddenomiconAnimation.CLOSING.bake(root);
        this.levitateAnimation = ForbiddenomiconAnimation.LEVITATE_DESK.bake(root);
        this.openStillAnimation = ForbiddenomiconAnimation.OPEN_STILL.bake(root);
        this.pageAnimation = ForbiddenomiconAnimation.FLIP_PAGE.bake(root);
    }

    @Override
    public void setupAnim(State state) {
        super.setupAnim(state);

        this.stillAnimation.apply(state.stillAnimation(), state.ageInTicks());
        this.openingAnimation.apply(state.openingAnimation(), state.ageInTicks());
        this.closingAnimation.apply(state.closingAnimation(), state.ageInTicks());
        this.levitateAnimation.apply(state.levitateAnimation(), state.ageInTicks());
        this.openStillAnimation.apply(state.levitateAnimation(), state.ageInTicks());
        this.pageAnimation.apply(state.pageAnimation(), state.ageInTicks());
    }
}

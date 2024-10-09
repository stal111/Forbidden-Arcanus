package com.stal111.forbidden_arcanus.client.model;

/**
 * @author stal111
 * @since 2022-07-31
 */
//public class DracoAurumWingsModel<T extends Player> extends HierarchicalModel<T> implements CosmeticsModel<T> {
//
//    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, "draco_deorum_wings"), "main");
//    private final ModelPart wings;
//
//	public DracoAurumWingsModel() {
//        ModelPart modelPart = this.getModelSet().bakeLayer(LAYER_LOCATION);
//
//        this.wings = modelPart.getChild("wings");
//    }
//
//    @Nonnull
//    @Override
//    public ModelPart root() {
//        return this.wings;
//    }
//
//    public static LayerDefinition createBodyLayer() {
//        MeshDefinition meshdefinition = new MeshDefinition();
//        PartDefinition partdefinition = meshdefinition.getRoot();
//
//        PartDefinition wings = partdefinition.addOrReplaceChild("wings", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -12.0F, 2.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
//                .texOffs(31, 1).addBox(-2.0F, -6.0F, 2.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
//
//        PartDefinition wing_left = wings.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(0.0F, -2.0F, -1.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.15F)).mirror(false)
//                .texOffs(25, 51).mirror().addBox(1.0F, 1.0F, 0.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -9.0F, 3.0F, -0.4363F, 1.0472F, 0.0F));
//
//        PartDefinition forearm_left = wing_left.addOrReplaceChild("forearm_left", CubeListBuilder.create().texOffs(5, 0).mirror().addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.0F, 5.0F, 0.8727F, 0.0F, 0.0F));
//
//        forearm_left.addOrReplaceChild("thumb_left_r1", CubeListBuilder.create().texOffs(20, 1).mirror().addBox(-0.5F, -2.5F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, 7.5F, 0.5236F, 0.0F, 0.0F));
//
//        PartDefinition Finger0_left = forearm_left.addOrReplaceChild("Finger0_left", CubeListBuilder.create().texOffs(0, 13).mirror().addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -2.0F, 10.0F, -0.3491F, 0.6545F, 0.0F));
//
//        Finger0_left.addOrReplaceChild("2_r1", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(0.2F, 1.0F, 0.0F, 0.0F, 10.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.0F, -1.0F, 0.0F, 0.0F, 0.1309F));
//
//        Finger0_left.addOrReplaceChild("1_r1", CubeListBuilder.create().texOffs(2, 25).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.0F, 10.0F, -0.3491F, 0.0F, 0.0F));
//
//        PartDefinition Finger1_left = forearm_left.addOrReplaceChild("Finger1_left", CubeListBuilder.create().texOffs(17, 13).mirror().addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, -0.8727F, 0.3491F, 0.0F));
//
//        Finger1_left.addOrReplaceChild("2_r2", CubeListBuilder.create().texOffs(0, 33).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));
//
//        Finger1_left.addOrReplaceChild("1_r2", CubeListBuilder.create().texOffs(17, 22).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, 8.0F, -0.3491F, 0.0F, 0.0F));
//
//        PartDefinition Finger2_left = forearm_left.addOrReplaceChild("Finger2_left", CubeListBuilder.create().texOffs(30, 13).mirror().addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
//                .texOffs(0, 46).mirror().addBox(-0.4F, 0.0F, -1.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, -1.5708F, 0.0F, 0.0F));
//
//        Finger2_left.addOrReplaceChild("1_r3", CubeListBuilder.create().texOffs(30, 20).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, 6.0F, -0.3491F, 0.0F, 0.0F));
//
//        PartDefinition wing_right = wings.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, -2.0F, -1.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.15F))
//                .texOffs(25, 51).addBox(-1.0F, 1.0F, 0.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -9.0F, 3.0F, -0.4363F, -1.0472F, 0.0F));
//
//        PartDefinition forearm_right = wing_right.addOrReplaceChild("forearm_right", CubeListBuilder.create().texOffs(5, 0).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 5.0F, 0.8727F, 0.0F, 0.0F));
//
//        forearm_right.addOrReplaceChild("thumb_right_r1", CubeListBuilder.create().texOffs(20, 1).addBox(-0.5F, -2.5F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 7.5F, 0.5236F, 0.0F, 0.0F));
//
//        PartDefinition Finger0_right = forearm_right.addOrReplaceChild("Finger0_right", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, 10.0F, -0.3491F, -0.6109F, 0.0F));
//
//        Finger0_right.addOrReplaceChild("2_r3", CubeListBuilder.create().texOffs(0, 20).addBox(-0.2F, 1.0F, 0.0F, 0.0F, 10.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -1.0F, 0.0F, 0.0F, -0.1309F));
//
//        Finger0_right.addOrReplaceChild("1_r4", CubeListBuilder.create().texOffs(2, 25).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 10.0F, -0.3491F, 0.0F, 0.0F));
//
//        PartDefinition Finger1_right = forearm_right.addOrReplaceChild("Finger1_right", CubeListBuilder.create().texOffs(17, 13).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, -0.8727F, -0.3491F, 0.0F));
//
//        Finger1_right.addOrReplaceChild("2_r4", CubeListBuilder.create().texOffs(0, 33).addBox(0.0F, 0.0F, -1.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));
//
//        Finger1_right.addOrReplaceChild("1_r5", CubeListBuilder.create().texOffs(17, 22).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 8.0F, -0.3491F, 0.0F, 0.0F));
//
//        PartDefinition Finger2_right = forearm_right.addOrReplaceChild("Finger2_right", CubeListBuilder.create().texOffs(30, 13).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
//                .texOffs(0, 46).addBox(0.4F, 0.0F, -1.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, -1.5708F, 0.0F, 0.0F));
//
//        Finger2_right.addOrReplaceChild("1_r6", CubeListBuilder.create().texOffs(30, 20).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 6.0F, -0.3491F, 0.0F, 0.0F));
//
//        return LayerDefinition.create(meshdefinition, 64, 64);
//    }
//
//    @Override
//    public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        this.animate(DracoAurumWingsAnimation.WALK.getAnimationState(entity.getUUID()), DracoAurumWingsAnimation.WALK.getDefinition(), ageInTicks);
//        this.animate(DracoAurumWingsAnimation.RUN.getAnimationState(entity.getUUID()), DracoAurumWingsAnimation.RUN.getDefinition(), ageInTicks);
//        this.animate(DracoAurumWingsAnimation.FLY.getAnimationState(entity.getUUID()), DracoAurumWingsAnimation.FLY.getDefinition(), ageInTicks, 0.5F);
//        this.animate(DracoAurumWingsAnimation.SNEAK.getAnimationState(entity.getUUID()), DracoAurumWingsAnimation.SNEAK.getDefinition(), ageInTicks);
//    }
//
//    @Override
//    public HierarchicalModel<T> getModel() {
//        return this;
//    }
//
//    @Override
//    public void setPosition(PoseStack stack) {
//        stack.translate(0.0D, -0.75D, 0.0D);
//    }
//}

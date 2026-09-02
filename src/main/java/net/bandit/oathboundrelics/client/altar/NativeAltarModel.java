package net.bandit.oathboundrelics.client.altar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

import java.util.HashMap;
import java.util.Map;

/** Native Minecraft model generated from the original Blockbench altar project. */
public final class NativeAltarModel {
    private final ModelPart root;
    private final Map<String, ModelPart> bones = new HashMap<>();

    private NativeAltarModel(ModelPart root) {
        this.root = root;
        index(root);
    }

    private void index(ModelPart part) {
        // populated explicitly because ModelPart does not expose its child-name map
    }

    public static NativeAltarModel body() { return new NativeAltarModel(bakeBody()); }
    public static NativeAltarModel lightRed() { return new NativeAltarModel(bakeLightRed()); }
    public static NativeAltarModel lightGreen() { return new NativeAltarModel(bakeLightGreen()); }
    public static NativeAltarModel lightBlue() { return new NativeAltarModel(bakeLightBlue()); }
    public static NativeAltarModel lightPurple() { return new NativeAltarModel(bakeLightPurple()); }
    public static NativeAltarModel auraRed() { return new NativeAltarModel(bakeAuraRed()); }
    public static NativeAltarModel auraGreen() { return new NativeAltarModel(bakeAuraGreen()); }
    public static NativeAltarModel auraBlue() { return new NativeAltarModel(bakeAuraBlue()); }
    public static NativeAltarModel auraPurple() { return new NativeAltarModel(bakeAuraPurple()); }

    public void registerBone(String name, String... path) {
        ModelPart p = root;
        for (String s : path) p = p.getChild(s);
        bones.put(name, p);
    }

    public ModelPart bone(String name) { return bones.get(name); }
    public void resetPose() { root.getAllParts().forEach(ModelPart::resetPose); }
    public void render(PoseStack poseStack, VertexConsumer consumer, int light, int overlay) { root.render(poseStack, consumer, light, overlay); }


    private static ModelPart bakeBody() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition minecraftRoot = mesh.getRoot();
        PartDefinition g0 = minecraftRoot.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.000000F, 0.000000F, 0.000000F));
        PartDefinition g1 = g0.addOrReplaceChild("big_ring2", CubeListBuilder.create(), PartPose.offset(0.200001F, -5.102700F, -0.128193F));
        PartDefinition g2 = g1.addOrReplaceChild("light", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.017300F, -0.000000F));
        PartDefinition g3 = g0.addOrReplaceChild("big_ring", CubeListBuilder.create(), PartPose.offset(0.035534F, -1.550000F, 0.000000F));
        PartDefinition g4 = g3.addOrReplaceChild("light2", CubeListBuilder.create(), PartPose.offset(0.029310F, -6.065000F, 0.285973F));
        PartDefinition g5 = g3.addOrReplaceChild("light3", CubeListBuilder.create(), PartPose.offset(0.046009F, -13.546667F, 0.460078F));
        PartDefinition g6 = g3.addOrReplaceChild("hitbox", CubeListBuilder.create(), PartPose.offset(-0.081145F, -23.531800F, -0.248101F));
        PartDefinition g7 = g3.addOrReplaceChild("innerpool", CubeListBuilder.create(), PartPose.offset(0.000000F, -22.050000F, 0.000000F));
        PartDefinition g8 = g3.addOrReplaceChild("pool", CubeListBuilder.create(), PartPose.offset(0.000000F, -21.000000F, 0.000000F));
        PartDefinition g9 = g3.addOrReplaceChild("ball", CubeListBuilder.create(), PartPose.offset(-0.102201F, -33.616667F, 0.066667F));
        PartDefinition g10 = g9.addOrReplaceChild("orb", CubeListBuilder.create(), PartPose.offset(0.066667F, -0.333333F, -0.066667F));
        PartDefinition g11 = g9.addOrReplaceChild("particles", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        g3.addOrReplaceChild("cube_0", CubeListBuilder.create().texOffs(60, 72).addBox(-8.000000F, -3.100000F, -10.000000F, 10.000000F, 4.000000F, 20.000000F), PartPose.offsetAndRotation(-16.035534F, 0.550000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_1", CubeListBuilder.create().texOffs(0, 24).addBox(-4.500000F, -1.500000F, -10.000000F, 43.000000F, 4.000000F, 20.000000F), PartPose.offsetAndRotation(-13.782486F, -0.950000F, -13.889087F, 0.00000000F, -0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_2", CubeListBuilder.create().texOffs(54, 96).addBox(-4.500000F, -1.500000F, -10.000000F, 7.000000F, 4.000000F, 20.000000F), PartPose.offsetAndRotation(-13.782486F, -0.950000F, 13.889087F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_3", CubeListBuilder.create().texOffs(100, 72).addBox(-4.500000F, -1.610000F, -10.000000F, 20.000000F, 4.000000F, 10.000000F), PartPose.offsetAndRotation(-5.393398F, -0.950000F, -14.142136F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_4", CubeListBuilder.create().texOffs(88, 96).addBox(-4.500000F, -1.600000F, -11.000000F, 20.000000F, 4.000000F, 10.000000F), PartPose.offsetAndRotation(-5.393398F, -0.950000F, 25.142136F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_5", CubeListBuilder.create().texOffs(0, 0).addBox(-21.500000F, -2.000000F, -10.000000F, 43.000000F, 4.000000F, 20.000000F), PartPose.offsetAndRotation(1.974874F, -0.450000F, -1.868272F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_6", CubeListBuilder.create().texOffs(0, 70).addBox(-2.000000F, -3.100000F, -10.000000F, 10.000000F, 4.000000F, 20.000000F), PartPose.offsetAndRotation(16.248737F, 0.550000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_7", CubeListBuilder.create().texOffs(0, 94).addBox(-2.500000F, -1.400000F, -10.000000F, 7.000000F, 4.000000F, 20.000000F), PartPose.offsetAndRotation(13.995689F, -1.050000F, 13.889087F, 0.00000000F, -0.78539816F, 0.00000000F));
        g1.addOrReplaceChild("cube_8", CubeListBuilder.create().texOffs(33, 137).addBox(-5.814000F, -2.091000F, -7.140000F, 6.000000F, 2.000000F, 15.000000F), PartPose.offsetAndRotation(-12.292601F, 1.093700F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g1.addOrReplaceChild("cube_9", CubeListBuilder.create().texOffs(129, 135).addBox(-3.213000F, -0.929000F, -7.140000F, 6.000000F, 2.000000F, 15.000000F), PartPose.offsetAndRotation(-10.276808F, -0.048700F, -10.723425F, 0.00000000F, -0.78539816F, 0.00000000F));
        g1.addOrReplaceChild("cube_10", CubeListBuilder.create().texOffs(102, 133).addBox(-3.213000F, -0.929000F, -7.140000F, 6.000000F, 2.000000F, 15.000000F), PartPose.offsetAndRotation(-10.785925F, -0.048700F, 10.339308F, 0.00000000F, 0.78539816F, 0.00000000F));
        g1.addOrReplaceChild("cube_11", CubeListBuilder.create().texOffs(148, 0).addBox(-3.213000F, -1.020000F, -7.140000F, 15.000000F, 2.000000F, 6.000000F), PartPose.offsetAndRotation(-4.287000F, 0.022700F, -10.904102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g1.addOrReplaceChild("cube_12", CubeListBuilder.create().texOffs(66, 48).addBox(-4.213000F, -1.020000F, -7.712000F, 15.000000F, 2.000000F, 7.000000F), PartPose.offsetAndRotation(-3.287000F, 0.022700F, 18.881102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g1.addOrReplaceChild("cube_13", CubeListBuilder.create().texOffs(75, 122).addBox(-2.785000F, -0.929000F, -7.140000F, 6.000000F, 2.000000F, 15.000000F), PartPose.offsetAndRotation(10.275394F, -0.048700F, -10.722011F, 0.00000000F, 0.78539816F, 0.00000000F));
        g1.addOrReplaceChild("cube_14", CubeListBuilder.create().texOffs(48, 120).addBox(-0.286000F, -2.091000F, -7.140000F, 6.000000F, 2.000000F, 15.000000F), PartPose.offsetAndRotation(12.392601F, 1.093700F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g1.addOrReplaceChild("cube_15", CubeListBuilder.create().texOffs(116, 44).addBox(-2.785000F, -0.929000F, -7.140000F, 6.000000F, 2.000000F, 15.000000F), PartPose.offsetAndRotation(10.784511F, -0.048700F, 10.337894F, 0.00000000F, -0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_16", CubeListBuilder.create().texOffs(0, 164).addBox(-8.785000F, -1.929000F, -1.538773F, 6.000000F, 2.000000F, 10.000000F), PartPose.offsetAndRotation(8.515559F, -4.601400F, -13.159390F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_17", CubeListBuilder.create().texOffs(153, 158).addBox(-8.785000F, -1.929000F, -5.538773F, 6.000000F, 2.000000F, 10.000000F), PartPose.offsetAndRotation(10.582045F, -4.601400F, 11.678690F, 0.00000000F, -0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_18", CubeListBuilder.create().texOffs(52, 157).addBox(2.785000F, -1.929000F, -5.538773F, 6.000000F, 2.000000F, 10.000000F), PartPose.offsetAndRotation(-10.523530F, -4.601400F, 11.678690F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_19", CubeListBuilder.create().texOffs(55, 59).addBox(3.063174F, -1.948600F, -3.538773F, 25.000000F, 2.000000F, 11.000000F), PartPose.offsetAndRotation(-15.533916F, -4.601400F, -1.668333F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_20", CubeListBuilder.create().texOffs(150, 71).addBox(-9.634926F, -1.948600F, -5.538773F, 11.000000F, 2.000000F, 8.000000F), PartPose.offsetAndRotation(4.164184F, -4.601400F, 10.402735F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_21", CubeListBuilder.create().texOffs(148, 125).addBox(-9.563858F, -1.948600F, -3.560599F, 11.000000F, 2.000000F, 8.000000F), PartPose.offsetAndRotation(4.093115F, -4.601400F, -8.739401F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_22", CubeListBuilder.create().texOffs(156, 135).addBox(2.785000F, -1.929000F, -4.461227F, 6.000000F, 2.000000F, 10.000000F), PartPose.offsetAndRotation(-10.523530F, -4.601400F, -11.092904F, 0.00000000F, -0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_23", CubeListBuilder.create().texOffs(0, 64).addBox(24.063174F, -2.958600F, -0.538773F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.533916F, -4.601400F, -1.668333F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_24", CubeListBuilder.create().texOffs(0, 64).addBox(2.063174F, -2.958600F, -0.538773F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.533916F, -4.601400F, -1.668333F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_25", CubeListBuilder.create().texOffs(0, 56).addBox(-6.634926F, -1.948600F, -1.538773F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(4.164184F, -5.601400F, 10.402735F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_26", CubeListBuilder.create().texOffs(0, 48).addBox(-2.500000F, -1.500000F, -2.500000F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(0.029258F, -6.050000F, -10.800000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_27", CubeListBuilder.create().texOffs(150, 16).addBox(7.785000F, -12.929000F, -2.538773F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.130132F, -8.601400F, 11.260245F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_28", CubeListBuilder.create().texOffs(103, 150).addBox(8.671038F, -12.938600F, -2.538773F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.141781F, -8.601400F, 4.448756F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_29", CubeListBuilder.create().texOffs(150, 16).addBox(10.785000F, -12.929000F, -2.538773F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-5.892073F, -8.601400F, -6.820475F, 0.00000000F, -0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_30", CubeListBuilder.create().texOffs(150, 16).addBox(7.785000F, -12.929000F, -2.461227F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-10.715918F, -8.601400F, -11.753989F, 0.00000000F, -0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_31", CubeListBuilder.create().texOffs(150, 16).addBox(10.785000F, -12.929000F, -3.002030F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-6.923880F, -8.601400F, 6.709137F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_32", CubeListBuilder.create().texOffs(148, 86).addBox(8.671038F, -12.938600F, -4.461227F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.141781F, -8.601400F, -3.528286F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_33", CubeListBuilder.create().texOffs(0, 118).addBox(10.785000F, -12.958600F, -2.538773F, 17.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-19.205489F, -8.601400F, -0.500992F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_34", CubeListBuilder.create().texOffs(148, 170).addBox(4.785000F, -3.929000F, -3.538773F, 4.000000F, 4.000000F, 9.000000F), PartPose.offsetAndRotation(-11.655282F, -19.601400F, 10.492454F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_35", CubeListBuilder.create().texOffs(167, 148).addBox(7.671038F, -3.998600F, 3.601244F, 9.000000F, 4.000000F, 4.000000F), PartPose.offsetAndRotation(-12.081145F, -19.531800F, 3.369374F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_36", CubeListBuilder.create().texOffs(49, 169).addBox(16.785000F, -7.929000F, -3.538773F, 4.000000F, 4.000000F, 9.000000F), PartPose.offsetAndRotation(-6.245651F, -15.601400F, -7.588266F, 0.00000000F, -0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_37", CubeListBuilder.create().texOffs(169, 30).addBox(4.880384F, -3.929000F, -5.461227F, 4.000000F, 4.000000F, 9.000000F), PartPose.offsetAndRotation(-11.655282F, -19.601400F, -10.371181F, 0.00000000F, -0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_38", CubeListBuilder.create().texOffs(122, 168).addBox(16.785000F, -7.929000F, -5.461227F, 4.000000F, 4.000000F, 9.000000F), PartPose.offsetAndRotation(-6.245651F, -15.601400F, 7.709539F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_39", CubeListBuilder.create().texOffs(162, 8).addBox(7.671038F, -3.998600F, -7.601244F, 9.000000F, 4.000000F, 4.000000F), PartPose.offsetAndRotation(-12.081145F, -19.531800F, -3.248101F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_40", CubeListBuilder.create().texOffs(23, 167).addBox(8.671038F, -3.998600F, 0.398756F, 4.000000F, 4.000000F, 9.000000F), PartPose.offsetAndRotation(-1.717184F, -19.531800F, -4.884140F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_41", CubeListBuilder.create().texOffs(75, 166).addBox(-3.328962F, -3.998600F, 0.398756F, 4.000000F, 4.000000F, 9.000000F), PartPose.offsetAndRotation(-7.445105F, -19.531800F, -4.792099F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_42", CubeListBuilder.create().texOffs(0, 72).addBox(7.600000F, -6.500000F, -1.000000F, 3.000000F, 13.000000F, 2.000000F), PartPose.offsetAndRotation(0.249012F, -13.030400F, 0.230430F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_43", CubeListBuilder.create().texOffs(10, 72).addBox(-10.600000F, -6.500000F, -1.000000F, 3.000000F, 13.000000F, 2.000000F), PartPose.offsetAndRotation(0.249012F, -13.030400F, 0.230430F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_44", CubeListBuilder.create().texOffs(10, 72).addBox(-10.784546F, -6.500000F, -0.730430F, 3.000000F, 13.000000F, 2.000000F), PartPose.offsetAndRotation(0.249012F, -13.030400F, 0.230430F, 0.00000000F, -0.78539816F, 0.00000000F));
        g10.addOrReplaceChild("cube_45", CubeListBuilder.create().texOffs(131, 152).addBox(-4.000000F, -3.900000F, -4.000000F, 8.000000F, 8.000000F, 8.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_46", CubeListBuilder.create().texOffs(156, 109).addBox(0.500000F, -3.650000F, -13.000000F, 10.000000F, 6.000000F, 7.000000F), PartPose.offsetAndRotation(-5.393398F, -0.950000F, -14.142136F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_47", CubeListBuilder.create().texOffs(28, 154).addBox(0.500000F, -3.650000F, 34.000000F, 10.000000F, 6.000000F, 7.000000F), PartPose.offsetAndRotation(-5.393398F, -0.950000F, -14.142136F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_48", CubeListBuilder.create().texOffs(148, 55).addBox(4.000000F, -5.150000F, -5.000000F, 7.000000F, 6.000000F, 10.000000F), PartPose.offsetAndRotation(16.248737F, 0.550000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_49", CubeListBuilder.create().texOffs(143, 39).addBox(-43.000000F, -5.150000F, -5.000000F, 7.000000F, 6.000000F, 10.000000F), PartPose.offsetAndRotation(16.248737F, 0.550000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_50", CubeListBuilder.create().texOffs(0, 72).addBox(7.415454F, -6.500000F, -0.730430F, 3.000000F, 13.000000F, 2.000000F), PartPose.offsetAndRotation(0.249012F, -13.030400F, 0.230430F, 0.00000000F, -0.78539816F, 0.00000000F));
        g8.addOrReplaceChild("cube_51", CubeListBuilder.create().texOffs(106, 0).addBox(-7.000000F, -2.000000F, -8.000000F, 14.000000F, 2.000000F, 14.000000F), PartPose.offsetAndRotation(-0.000000F, 1.000000F, 1.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g2.addOrReplaceChild("cube_52", CubeListBuilder.create().texOffs(43, 96).addBox(-2.785000F, -1.073600F, -7.140000F, 6.000000F, 0.050000F, 15.000000F), PartPose.offsetAndRotation(10.784511F, 0.968600F, 10.337894F, 0.00000000F, -0.78539816F, 0.00000000F));
        g2.addOrReplaceChild("cube_53", CubeListBuilder.create().texOffs(31, 94).addBox(-0.286000F, -2.216000F, -7.140000F, 6.000000F, 0.050000F, 15.000000F), PartPose.offsetAndRotation(12.392601F, 2.111000F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g2.addOrReplaceChild("cube_54", CubeListBuilder.create().texOffs(19, 94).addBox(-2.785000F, -1.073600F, -7.140000F, 6.000000F, 0.050000F, 15.000000F), PartPose.offsetAndRotation(10.275394F, 0.968600F, -10.722011F, 0.00000000F, 0.78539816F, 0.00000000F));
        g2.addOrReplaceChild("cube_55", CubeListBuilder.create().texOffs(109, 61).addBox(-4.213000F, -1.145000F, -7.712000F, 15.000000F, 0.050000F, 7.000000F), PartPose.offsetAndRotation(-3.287000F, 1.040000F, 18.881102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g2.addOrReplaceChild("cube_56", CubeListBuilder.create().texOffs(94, 86).addBox(-3.213000F, -1.145000F, -7.140000F, 15.000000F, 0.050000F, 6.000000F), PartPose.offsetAndRotation(-4.287000F, 1.040000F, -10.904102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g2.addOrReplaceChild("cube_57", CubeListBuilder.create().texOffs(49, 72).addBox(-3.213000F, -1.073600F, -7.140000F, 6.000000F, 0.050000F, 15.000000F), PartPose.offsetAndRotation(-10.785925F, 0.968600F, 10.339308F, 0.00000000F, 0.78539816F, 0.00000000F));
        g2.addOrReplaceChild("cube_58", CubeListBuilder.create().texOffs(37, 72).addBox(-3.213000F, -1.073600F, -7.140000F, 6.000000F, 0.050000F, 15.000000F), PartPose.offsetAndRotation(-10.276808F, 0.968600F, -10.723425F, 0.00000000F, -0.78539816F, 0.00000000F));
        g2.addOrReplaceChild("cube_59", CubeListBuilder.create().texOffs(25, 70).addBox(-5.814000F, -2.216000F, -7.140000F, 6.000000F, 0.050000F, 15.000000F), PartPose.offsetAndRotation(-12.292601F, 2.111000F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g4.addOrReplaceChild("cube_60", CubeListBuilder.create().texOffs(0, 32).addBox(-6.634926F, -2.048600F, -1.532735F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(4.134874F, 0.463600F, 10.116761F, 0.00000000F, 0.00000000F, 0.00000000F));
        g4.addOrReplaceChild("cube_61", CubeListBuilder.create().texOffs(0, 6).addBox(24.068383F, -3.048600F, -0.538773F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.563226F, 1.463600F, -1.954307F, 0.00000000F, 0.00000000F, 0.00000000F));
        g4.addOrReplaceChild("cube_62", CubeListBuilder.create().texOffs(0, 24).addBox(-2.500000F, -1.600000F, -2.550000F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-0.000052F, 0.015000F, -11.085973F, 0.00000000F, 0.00000000F, 0.00000000F));
        g4.addOrReplaceChild("cube_63", CubeListBuilder.create().texOffs(0, 6).addBox(2.048383F, -3.048600F, -0.530667F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.563226F, 1.463600F, -1.954307F, 0.00000000F, 0.00000000F, 0.00000000F));
        g5.addOrReplaceChild("cube_64", CubeListBuilder.create().texOffs(75, 143).addBox(8.606247F, -12.938600F, -4.471714F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.187789F, 4.945267F, -3.988365F, 0.00000000F, 0.00000000F, 0.00000000F));
        g5.addOrReplaceChild("cube_65", CubeListBuilder.create().texOffs(108, 110).addBox(-8.415045F, -8.000000F, -3.500000F, 17.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(0.033502F, -0.013333F, 0.000156F, 0.00000000F, 0.00000000F, 0.00000000F));
        g5.addOrReplaceChild("cube_66", CubeListBuilder.create().texOffs(0, 141).addBox(8.671038F, -12.938600F, -2.448756F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.187789F, 4.945267F, 3.988677F, 0.00000000F, 0.00000000F, 0.00000000F));
        g7.addOrReplaceChild("cube_67", CubeListBuilder.create().texOffs(0, 0).addBox(-3.000000F, -2.375000F, -4.000000F, 6.000000F, 0.050000F, 6.000000F), PartPose.offsetAndRotation(-0.000000F, 2.050000F, 1.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g10.addOrReplaceChild("cube_68", CubeListBuilder.create().texOffs(0, 180).addBox(-4.000000F, -3.900000F, -4.000000F, 8.000000F, 8.000000F, 8.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        return LayerDefinition.create(mesh, 256, 256).bakeRoot();
    }

    private static ModelPart bakeLightRed() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition minecraftRoot = mesh.getRoot();
        PartDefinition g0 = minecraftRoot.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.000000F, 0.000000F, 0.000000F));
        PartDefinition g1 = g0.addOrReplaceChild("big_ring2", CubeListBuilder.create(), PartPose.offset(0.200001F, -5.102700F, -0.128193F));
        PartDefinition g2 = g1.addOrReplaceChild("light", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.017300F, -0.000000F));
        PartDefinition g3 = g2.addOrReplaceChild("light_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g4 = g2.addOrReplaceChild("light_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g5 = g2.addOrReplaceChild("light_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g6 = g2.addOrReplaceChild("light_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g7 = g0.addOrReplaceChild("big_ring", CubeListBuilder.create(), PartPose.offset(0.035534F, -1.550000F, 0.000000F));
        PartDefinition g8 = g7.addOrReplaceChild("light2", CubeListBuilder.create(), PartPose.offset(0.029310F, -6.065000F, 0.285973F));
        PartDefinition g9 = g8.addOrReplaceChild("light2_red", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g10 = g8.addOrReplaceChild("light2_green", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g11 = g8.addOrReplaceChild("light2_blue", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g12 = g8.addOrReplaceChild("light2_purple", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g13 = g7.addOrReplaceChild("light3", CubeListBuilder.create(), PartPose.offset(0.046009F, -13.546667F, 0.460078F));
        PartDefinition g14 = g13.addOrReplaceChild("light3_red", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g15 = g13.addOrReplaceChild("light3_green", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g16 = g13.addOrReplaceChild("light3_blue", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g17 = g13.addOrReplaceChild("light3_purple", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g18 = g7.addOrReplaceChild("hitbox", CubeListBuilder.create(), PartPose.offset(-0.081145F, -23.531800F, -0.248101F));
        PartDefinition g19 = g7.addOrReplaceChild("pool", CubeListBuilder.create(), PartPose.offset(0.000000F, -21.000000F, 0.000000F));
        PartDefinition g20 = g19.addOrReplaceChild("pool_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g21 = g19.addOrReplaceChild("pool_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g22 = g19.addOrReplaceChild("pool_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g23 = g19.addOrReplaceChild("pool_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g24 = g19.addOrReplaceChild("innerpool", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.050000F, 0.000000F));
        PartDefinition g25 = g24.addOrReplaceChild("innerpool_red", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g26 = g24.addOrReplaceChild("innerpool_green", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g27 = g24.addOrReplaceChild("innerpool_blue", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g28 = g24.addOrReplaceChild("innerpool_purple", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g29 = g7.addOrReplaceChild("ball", CubeListBuilder.create(), PartPose.offset(-0.102201F, -33.616667F, 0.066667F));
        PartDefinition g30 = g29.addOrReplaceChild("orb", CubeListBuilder.create(), PartPose.offset(0.066667F, -0.333333F, -0.066667F));
        PartDefinition g31 = g30.addOrReplaceChild("orb_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g32 = g30.addOrReplaceChild("orb_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g33 = g30.addOrReplaceChild("orb_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g34 = g30.addOrReplaceChild("orb_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g35 = g29.addOrReplaceChild("particles", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        g31.addOrReplaceChild("cube_0", CubeListBuilder.create().texOffs(131, 152).addBox(-4.000000F, -3.900000F, -4.000000F, 8.000000F, 8.000000F, 8.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g20.addOrReplaceChild("cube_1", CubeListBuilder.create().texOffs(106, 0).addBox(-7.000000F, -1.000000F, -7.000000F, 14.000000F, 2.000000F, 14.000000F), PartPose.offsetAndRotation(-0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_2", CubeListBuilder.create().texOffs(43, 96).addBox(-2.785000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(10.784511F, 0.968600F, 10.337894F, 0.00000000F, -0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_3", CubeListBuilder.create().texOffs(31, 94).addBox(-0.286000F, -2.261000F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(12.392601F, 2.111000F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_4", CubeListBuilder.create().texOffs(19, 94).addBox(-2.785000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(10.275394F, 0.968600F, -10.722011F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_5", CubeListBuilder.create().texOffs(109, 61).addBox(-4.213000F, -1.190000F, -7.712000F, 15.000000F, 0.020000F, 7.000000F), PartPose.offsetAndRotation(-3.287000F, 1.040000F, 18.881102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_6", CubeListBuilder.create().texOffs(94, 86).addBox(-3.213000F, -1.190000F, -7.140000F, 15.000000F, 0.020000F, 6.000000F), PartPose.offsetAndRotation(-4.287000F, 1.040000F, -10.904102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g3.addOrReplaceChild("cube_7", CubeListBuilder.create().texOffs(49, 72).addBox(-3.213000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-10.785925F, 0.968600F, 10.339308F, 0.00000000F, 0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_8", CubeListBuilder.create().texOffs(37, 72).addBox(-3.213000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-10.276808F, 0.968600F, -10.723425F, 0.00000000F, -0.78539816F, 0.00000000F));
        g3.addOrReplaceChild("cube_9", CubeListBuilder.create().texOffs(25, 70).addBox(-5.814000F, -2.261000F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-12.292601F, 2.111000F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g9.addOrReplaceChild("cube_10", CubeListBuilder.create().texOffs(0, 32).addBox(-6.634926F, -1.968600F, -1.532735F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(4.134918F, 0.463600F, 10.116735F, 0.00000000F, 0.00000000F, 0.00000000F));
        g9.addOrReplaceChild("cube_11", CubeListBuilder.create().texOffs(0, 6).addBox(24.068383F, -2.968600F, -0.538773F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.563183F, 1.463600F, -1.954333F, 0.00000000F, 0.00000000F, 0.00000000F));
        g9.addOrReplaceChild("cube_12", CubeListBuilder.create().texOffs(0, 24).addBox(-2.500000F, -1.520000F, -2.550000F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-0.000008F, 0.015000F, -11.086000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g9.addOrReplaceChild("cube_13", CubeListBuilder.create().texOffs(0, 6).addBox(2.048383F, -2.968600F, -0.530667F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.563183F, 1.463600F, -1.954333F, 0.00000000F, 0.00000000F, 0.00000000F));
        g14.addOrReplaceChild("cube_14", CubeListBuilder.create().texOffs(75, 143).addBox(8.606247F, -12.938600F, -4.471714F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.000000F, 5.000000F, -4.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g14.addOrReplaceChild("cube_15", CubeListBuilder.create().texOffs(108, 110).addBox(-8.415045F, -8.000000F, -3.500000F, 17.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(0.221291F, 0.041400F, -0.011479F, 0.00000000F, 0.00000000F, 0.00000000F));
        g14.addOrReplaceChild("cube_16", CubeListBuilder.create().texOffs(0, 141).addBox(8.671038F, -12.938600F, -2.448756F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.000000F, 5.000000F, 3.977042F, 0.00000000F, 0.00000000F, 0.00000000F));
        g25.addOrReplaceChild("cube_17", CubeListBuilder.create().texOffs(0, 0).addBox(-3.000000F, -2.420000F, -4.000000F, 6.000000F, 0.020000F, 6.000000F), PartPose.offsetAndRotation(0.000000F, 2.050000F, 1.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g31.addOrReplaceChild("cube_18", CubeListBuilder.create().texOffs(0, 180).addBox(-4.000000F, -3.900000F, -4.000000F, 8.000000F, 8.000000F, 8.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        return LayerDefinition.create(mesh, 256, 256).bakeRoot();
    }

    private static ModelPart bakeLightGreen() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition minecraftRoot = mesh.getRoot();
        PartDefinition g0 = minecraftRoot.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.000000F, 0.000000F, 0.000000F));
        PartDefinition g1 = g0.addOrReplaceChild("big_ring2", CubeListBuilder.create(), PartPose.offset(0.200001F, -5.102700F, -0.128193F));
        PartDefinition g2 = g1.addOrReplaceChild("light", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.017300F, -0.000000F));
        PartDefinition g3 = g2.addOrReplaceChild("light_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g4 = g2.addOrReplaceChild("light_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g5 = g2.addOrReplaceChild("light_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g6 = g2.addOrReplaceChild("light_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g7 = g0.addOrReplaceChild("big_ring", CubeListBuilder.create(), PartPose.offset(0.035534F, -1.550000F, 0.000000F));
        PartDefinition g8 = g7.addOrReplaceChild("light2", CubeListBuilder.create(), PartPose.offset(0.029310F, -6.065000F, 0.285973F));
        PartDefinition g9 = g8.addOrReplaceChild("light2_red", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g10 = g8.addOrReplaceChild("light2_green", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g11 = g8.addOrReplaceChild("light2_blue", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g12 = g8.addOrReplaceChild("light2_purple", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g13 = g7.addOrReplaceChild("light3", CubeListBuilder.create(), PartPose.offset(0.046009F, -13.546667F, 0.460078F));
        PartDefinition g14 = g13.addOrReplaceChild("light3_red", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g15 = g13.addOrReplaceChild("light3_green", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g16 = g13.addOrReplaceChild("light3_blue", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g17 = g13.addOrReplaceChild("light3_purple", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g18 = g7.addOrReplaceChild("hitbox", CubeListBuilder.create(), PartPose.offset(-0.081145F, -23.531800F, -0.248101F));
        PartDefinition g19 = g7.addOrReplaceChild("pool", CubeListBuilder.create(), PartPose.offset(0.000000F, -21.000000F, 0.000000F));
        PartDefinition g20 = g19.addOrReplaceChild("pool_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g21 = g19.addOrReplaceChild("pool_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g22 = g19.addOrReplaceChild("pool_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g23 = g19.addOrReplaceChild("pool_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g24 = g19.addOrReplaceChild("innerpool", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.050000F, 0.000000F));
        PartDefinition g25 = g24.addOrReplaceChild("innerpool_red", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g26 = g24.addOrReplaceChild("innerpool_green", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g27 = g24.addOrReplaceChild("innerpool_blue", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g28 = g24.addOrReplaceChild("innerpool_purple", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g29 = g7.addOrReplaceChild("ball", CubeListBuilder.create(), PartPose.offset(-0.102201F, -33.616667F, 0.066667F));
        PartDefinition g30 = g29.addOrReplaceChild("orb", CubeListBuilder.create(), PartPose.offset(0.066667F, -0.333333F, -0.066667F));
        PartDefinition g31 = g30.addOrReplaceChild("orb_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g32 = g30.addOrReplaceChild("orb_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g33 = g30.addOrReplaceChild("orb_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g34 = g30.addOrReplaceChild("orb_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g35 = g29.addOrReplaceChild("particles", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        g4.addOrReplaceChild("cube_0", CubeListBuilder.create().texOffs(37, 72).addBox(-3.213000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-10.276808F, 0.968600F, -10.723425F, 0.00000000F, -0.78539816F, 0.00000000F));
        g4.addOrReplaceChild("cube_1", CubeListBuilder.create().texOffs(49, 72).addBox(-3.213000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-10.785925F, 0.968600F, 10.339308F, 0.00000000F, 0.78539816F, 0.00000000F));
        g4.addOrReplaceChild("cube_2", CubeListBuilder.create().texOffs(94, 86).addBox(-3.213000F, -1.190000F, -7.140000F, 15.000000F, 0.020000F, 6.000000F), PartPose.offsetAndRotation(-4.287000F, 1.040000F, -10.904102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g4.addOrReplaceChild("cube_3", CubeListBuilder.create().texOffs(109, 61).addBox(-4.213000F, -1.190000F, -7.712000F, 15.000000F, 0.020000F, 7.000000F), PartPose.offsetAndRotation(-3.287000F, 1.040000F, 18.881102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g4.addOrReplaceChild("cube_4", CubeListBuilder.create().texOffs(19, 94).addBox(-2.785000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(10.275394F, 0.968600F, -10.722011F, 0.00000000F, 0.78539816F, 0.00000000F));
        g4.addOrReplaceChild("cube_5", CubeListBuilder.create().texOffs(31, 94).addBox(-0.286000F, -2.261000F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(12.392601F, 2.111000F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g4.addOrReplaceChild("cube_6", CubeListBuilder.create().texOffs(25, 70).addBox(-5.814000F, -2.261000F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-12.292601F, 2.111000F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g4.addOrReplaceChild("cube_7", CubeListBuilder.create().texOffs(43, 96).addBox(-2.785000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(10.784511F, 0.968600F, 10.337894F, 0.00000000F, -0.78539816F, 0.00000000F));
        g10.addOrReplaceChild("cube_8", CubeListBuilder.create().texOffs(0, 32).addBox(-6.634926F, -1.968600F, -1.532735F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(4.134918F, 0.463600F, 10.116735F, 0.00000000F, 0.00000000F, 0.00000000F));
        g10.addOrReplaceChild("cube_9", CubeListBuilder.create().texOffs(0, 6).addBox(24.068383F, -2.968600F, -0.538773F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.563183F, 1.463600F, -1.954333F, 0.00000000F, 0.00000000F, 0.00000000F));
        g10.addOrReplaceChild("cube_10", CubeListBuilder.create().texOffs(0, 24).addBox(-2.500000F, -1.520000F, -2.550000F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-0.000008F, 0.015000F, -11.086000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g10.addOrReplaceChild("cube_11", CubeListBuilder.create().texOffs(0, 6).addBox(2.048383F, -2.968600F, -0.530667F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.563183F, 1.463600F, -1.954333F, 0.00000000F, 0.00000000F, 0.00000000F));
        g15.addOrReplaceChild("cube_12", CubeListBuilder.create().texOffs(75, 143).addBox(8.606247F, -12.938600F, -4.471714F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.000000F, 5.000000F, -4.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g15.addOrReplaceChild("cube_13", CubeListBuilder.create().texOffs(108, 110).addBox(-8.415045F, -8.000000F, -3.500000F, 17.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(0.221291F, 0.041400F, -0.011479F, 0.00000000F, 0.00000000F, 0.00000000F));
        g15.addOrReplaceChild("cube_14", CubeListBuilder.create().texOffs(0, 141).addBox(8.671038F, -12.938600F, -2.448756F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.000000F, 5.000000F, 3.977042F, 0.00000000F, 0.00000000F, 0.00000000F));
        g26.addOrReplaceChild("cube_15", CubeListBuilder.create().texOffs(0, 0).addBox(-3.000000F, -2.420000F, -4.000000F, 6.000000F, 0.020000F, 6.000000F), PartPose.offsetAndRotation(0.000000F, 2.050000F, 1.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g21.addOrReplaceChild("cube_16", CubeListBuilder.create().texOffs(106, 0).addBox(-7.000000F, -1.000000F, -7.000000F, 14.000000F, 2.000000F, 14.000000F), PartPose.offsetAndRotation(-0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g32.addOrReplaceChild("cube_17", CubeListBuilder.create().texOffs(131, 152).addBox(-4.000000F, -3.900000F, -4.000000F, 8.000000F, 8.000000F, 8.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g32.addOrReplaceChild("cube_18", CubeListBuilder.create().texOffs(0, 180).addBox(-4.000000F, -3.900000F, -4.000000F, 8.000000F, 8.000000F, 8.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        return LayerDefinition.create(mesh, 256, 256).bakeRoot();
    }

    private static ModelPart bakeLightBlue() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition minecraftRoot = mesh.getRoot();
        PartDefinition g0 = minecraftRoot.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.000000F, 0.000000F, 0.000000F));
        PartDefinition g1 = g0.addOrReplaceChild("big_ring2", CubeListBuilder.create(), PartPose.offset(0.200001F, -5.102700F, -0.128193F));
        PartDefinition g2 = g1.addOrReplaceChild("light", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.017300F, -0.000000F));
        PartDefinition g3 = g2.addOrReplaceChild("light_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g4 = g2.addOrReplaceChild("light_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g5 = g2.addOrReplaceChild("light_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g6 = g2.addOrReplaceChild("light_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g7 = g0.addOrReplaceChild("big_ring", CubeListBuilder.create(), PartPose.offset(0.035534F, -1.550000F, 0.000000F));
        PartDefinition g8 = g7.addOrReplaceChild("light2", CubeListBuilder.create(), PartPose.offset(0.029310F, -6.065000F, 0.285973F));
        PartDefinition g9 = g8.addOrReplaceChild("light2_red", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g10 = g8.addOrReplaceChild("light2_green", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g11 = g8.addOrReplaceChild("light2_blue", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g12 = g8.addOrReplaceChild("light2_purple", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g13 = g7.addOrReplaceChild("light3", CubeListBuilder.create(), PartPose.offset(0.046009F, -13.546667F, 0.460078F));
        PartDefinition g14 = g13.addOrReplaceChild("light3_red", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g15 = g13.addOrReplaceChild("light3_green", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g16 = g13.addOrReplaceChild("light3_blue", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g17 = g13.addOrReplaceChild("light3_purple", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g18 = g7.addOrReplaceChild("hitbox", CubeListBuilder.create(), PartPose.offset(-0.081145F, -23.531800F, -0.248101F));
        PartDefinition g19 = g7.addOrReplaceChild("pool", CubeListBuilder.create(), PartPose.offset(0.000000F, -21.000000F, 0.000000F));
        PartDefinition g20 = g19.addOrReplaceChild("pool_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g21 = g19.addOrReplaceChild("pool_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g22 = g19.addOrReplaceChild("pool_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g23 = g19.addOrReplaceChild("pool_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g24 = g19.addOrReplaceChild("innerpool", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.050000F, 0.000000F));
        PartDefinition g25 = g24.addOrReplaceChild("innerpool_red", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g26 = g24.addOrReplaceChild("innerpool_green", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g27 = g24.addOrReplaceChild("innerpool_blue", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g28 = g24.addOrReplaceChild("innerpool_purple", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g29 = g7.addOrReplaceChild("ball", CubeListBuilder.create(), PartPose.offset(-0.102201F, -33.616667F, 0.066667F));
        PartDefinition g30 = g29.addOrReplaceChild("orb", CubeListBuilder.create(), PartPose.offset(0.066667F, -0.333333F, -0.066667F));
        PartDefinition g31 = g30.addOrReplaceChild("orb_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g32 = g30.addOrReplaceChild("orb_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g33 = g30.addOrReplaceChild("orb_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g34 = g30.addOrReplaceChild("orb_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g35 = g29.addOrReplaceChild("particles", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        g5.addOrReplaceChild("cube_0", CubeListBuilder.create().texOffs(37, 72).addBox(-3.213000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-10.276808F, 0.968600F, -10.723425F, 0.00000000F, -0.78539816F, 0.00000000F));
        g5.addOrReplaceChild("cube_1", CubeListBuilder.create().texOffs(49, 72).addBox(-3.213000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-10.785925F, 0.968600F, 10.339308F, 0.00000000F, 0.78539816F, 0.00000000F));
        g5.addOrReplaceChild("cube_2", CubeListBuilder.create().texOffs(94, 86).addBox(-3.213000F, -1.190000F, -7.140000F, 15.000000F, 0.020000F, 6.000000F), PartPose.offsetAndRotation(-4.287000F, 1.040000F, -10.904102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g5.addOrReplaceChild("cube_3", CubeListBuilder.create().texOffs(109, 61).addBox(-4.213000F, -1.190000F, -7.712000F, 15.000000F, 0.020000F, 7.000000F), PartPose.offsetAndRotation(-3.287000F, 1.040000F, 18.881102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g5.addOrReplaceChild("cube_4", CubeListBuilder.create().texOffs(19, 94).addBox(-2.785000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(10.275394F, 0.968600F, -10.722011F, 0.00000000F, 0.78539816F, 0.00000000F));
        g5.addOrReplaceChild("cube_5", CubeListBuilder.create().texOffs(31, 94).addBox(-0.286000F, -2.261000F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(12.392601F, 2.111000F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g5.addOrReplaceChild("cube_6", CubeListBuilder.create().texOffs(25, 70).addBox(-5.814000F, -2.261000F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-12.292601F, 2.111000F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g5.addOrReplaceChild("cube_7", CubeListBuilder.create().texOffs(43, 96).addBox(-2.785000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(10.784511F, 0.968600F, 10.337894F, 0.00000000F, -0.78539816F, 0.00000000F));
        g11.addOrReplaceChild("cube_8", CubeListBuilder.create().texOffs(0, 6).addBox(24.068383F, -2.968600F, -0.538773F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.563183F, 1.463600F, -1.954333F, 0.00000000F, 0.00000000F, 0.00000000F));
        g11.addOrReplaceChild("cube_9", CubeListBuilder.create().texOffs(0, 24).addBox(-2.500000F, -1.520000F, -2.550000F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-0.000008F, 0.015000F, -11.086000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g11.addOrReplaceChild("cube_10", CubeListBuilder.create().texOffs(0, 6).addBox(2.048383F, -2.968600F, -0.530667F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.563183F, 1.463600F, -1.954333F, 0.00000000F, 0.00000000F, 0.00000000F));
        g11.addOrReplaceChild("cube_11", CubeListBuilder.create().texOffs(0, 32).addBox(-6.634926F, -1.968600F, -1.532735F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(4.134918F, 0.463600F, 10.116735F, 0.00000000F, 0.00000000F, 0.00000000F));
        g16.addOrReplaceChild("cube_12", CubeListBuilder.create().texOffs(75, 143).addBox(8.606247F, -12.938600F, -4.471714F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.000000F, 5.000000F, -4.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g16.addOrReplaceChild("cube_13", CubeListBuilder.create().texOffs(108, 110).addBox(-8.415045F, -8.000000F, -3.500000F, 17.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(0.221291F, 0.041400F, -0.011479F, 0.00000000F, 0.00000000F, 0.00000000F));
        g16.addOrReplaceChild("cube_14", CubeListBuilder.create().texOffs(0, 141).addBox(8.671038F, -12.938600F, -2.448756F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.000000F, 5.000000F, 3.977042F, 0.00000000F, 0.00000000F, 0.00000000F));
        g27.addOrReplaceChild("cube_15", CubeListBuilder.create().texOffs(0, 0).addBox(-3.000000F, -2.420000F, -4.000000F, 6.000000F, 0.020000F, 6.000000F), PartPose.offsetAndRotation(0.000000F, 2.050000F, 1.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g22.addOrReplaceChild("cube_16", CubeListBuilder.create().texOffs(106, 0).addBox(-7.000000F, -1.000000F, -7.000000F, 14.000000F, 2.000000F, 14.000000F), PartPose.offsetAndRotation(-0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g33.addOrReplaceChild("cube_17", CubeListBuilder.create().texOffs(131, 152).addBox(-4.000000F, -3.900000F, -4.000000F, 8.000000F, 8.000000F, 8.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g33.addOrReplaceChild("cube_18", CubeListBuilder.create().texOffs(0, 180).addBox(-4.000000F, -3.900000F, -4.000000F, 8.000000F, 8.000000F, 8.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        return LayerDefinition.create(mesh, 256, 256).bakeRoot();
    }

    private static ModelPart bakeLightPurple() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition minecraftRoot = mesh.getRoot();
        PartDefinition g0 = minecraftRoot.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.000000F, 0.000000F, 0.000000F));
        PartDefinition g1 = g0.addOrReplaceChild("big_ring2", CubeListBuilder.create(), PartPose.offset(0.200001F, -5.102700F, -0.128193F));
        PartDefinition g2 = g1.addOrReplaceChild("light", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.017300F, -0.000000F));
        PartDefinition g3 = g2.addOrReplaceChild("light_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g4 = g2.addOrReplaceChild("light_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g5 = g2.addOrReplaceChild("light_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g6 = g2.addOrReplaceChild("light_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g7 = g0.addOrReplaceChild("big_ring", CubeListBuilder.create(), PartPose.offset(0.035534F, -1.550000F, 0.000000F));
        PartDefinition g8 = g7.addOrReplaceChild("light2", CubeListBuilder.create(), PartPose.offset(0.029310F, -6.065000F, 0.285973F));
        PartDefinition g9 = g8.addOrReplaceChild("light2_red", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g10 = g8.addOrReplaceChild("light2_green", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g11 = g8.addOrReplaceChild("light2_blue", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g12 = g8.addOrReplaceChild("light2_purple", CubeListBuilder.create(), PartPose.offset(-0.000044F, -0.000000F, 0.000027F));
        PartDefinition g13 = g7.addOrReplaceChild("light3", CubeListBuilder.create(), PartPose.offset(0.046009F, -13.546667F, 0.460078F));
        PartDefinition g14 = g13.addOrReplaceChild("light3_red", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g15 = g13.addOrReplaceChild("light3_green", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g16 = g13.addOrReplaceChild("light3_blue", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g17 = g13.addOrReplaceChild("light3_purple", CubeListBuilder.create(), PartPose.offset(-0.187789F, -0.054733F, 0.011635F));
        PartDefinition g18 = g7.addOrReplaceChild("hitbox", CubeListBuilder.create(), PartPose.offset(-0.081145F, -23.531800F, -0.248101F));
        PartDefinition g19 = g7.addOrReplaceChild("pool", CubeListBuilder.create(), PartPose.offset(0.000000F, -21.000000F, 0.000000F));
        PartDefinition g20 = g19.addOrReplaceChild("pool_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g21 = g19.addOrReplaceChild("pool_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g22 = g19.addOrReplaceChild("pool_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g23 = g19.addOrReplaceChild("pool_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g24 = g19.addOrReplaceChild("innerpool", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.050000F, 0.000000F));
        PartDefinition g25 = g24.addOrReplaceChild("innerpool_red", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g26 = g24.addOrReplaceChild("innerpool_green", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g27 = g24.addOrReplaceChild("innerpool_blue", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g28 = g24.addOrReplaceChild("innerpool_purple", CubeListBuilder.create(), PartPose.offset(-0.000000F, -0.000000F, 0.000000F));
        PartDefinition g29 = g7.addOrReplaceChild("ball", CubeListBuilder.create(), PartPose.offset(-0.102201F, -33.616667F, 0.066667F));
        PartDefinition g30 = g29.addOrReplaceChild("orb", CubeListBuilder.create(), PartPose.offset(0.066667F, -0.333333F, -0.066667F));
        PartDefinition g31 = g30.addOrReplaceChild("orb_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g32 = g30.addOrReplaceChild("orb_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g33 = g30.addOrReplaceChild("orb_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g34 = g30.addOrReplaceChild("orb_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g35 = g29.addOrReplaceChild("particles", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        g6.addOrReplaceChild("cube_0", CubeListBuilder.create().texOffs(37, 72).addBox(-3.213000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-10.276808F, 0.968600F, -10.723425F, 0.00000000F, -0.78539816F, 0.00000000F));
        g6.addOrReplaceChild("cube_1", CubeListBuilder.create().texOffs(49, 72).addBox(-3.213000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-10.785925F, 0.968600F, 10.339308F, 0.00000000F, 0.78539816F, 0.00000000F));
        g6.addOrReplaceChild("cube_2", CubeListBuilder.create().texOffs(94, 86).addBox(-3.213000F, -1.190000F, -7.140000F, 15.000000F, 0.020000F, 6.000000F), PartPose.offsetAndRotation(-4.287000F, 1.040000F, -10.904102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g6.addOrReplaceChild("cube_3", CubeListBuilder.create().texOffs(109, 61).addBox(-4.213000F, -1.190000F, -7.712000F, 15.000000F, 0.020000F, 7.000000F), PartPose.offsetAndRotation(-3.287000F, 1.040000F, 18.881102F, 0.00000000F, 0.00000000F, 0.00000000F));
        g6.addOrReplaceChild("cube_4", CubeListBuilder.create().texOffs(19, 94).addBox(-2.785000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(10.275394F, 0.968600F, -10.722011F, 0.00000000F, 0.78539816F, 0.00000000F));
        g6.addOrReplaceChild("cube_5", CubeListBuilder.create().texOffs(31, 94).addBox(-0.286000F, -2.261000F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(12.392601F, 2.111000F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g6.addOrReplaceChild("cube_6", CubeListBuilder.create().texOffs(25, 70).addBox(-5.814000F, -2.261000F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(-12.292601F, 2.111000F, -0.297500F, 0.00000000F, 0.00000000F, 0.00000000F));
        g6.addOrReplaceChild("cube_7", CubeListBuilder.create().texOffs(43, 96).addBox(-2.785000F, -1.118600F, -7.140000F, 6.000000F, 0.020000F, 15.000000F), PartPose.offsetAndRotation(10.784511F, 0.968600F, 10.337894F, 0.00000000F, -0.78539816F, 0.00000000F));
        g12.addOrReplaceChild("cube_8", CubeListBuilder.create().texOffs(0, 6).addBox(24.068383F, -3.048600F, -0.538773F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.563183F, 1.543600F, -1.954333F, 0.00000000F, 0.00000000F, 0.00000000F));
        g12.addOrReplaceChild("cube_9", CubeListBuilder.create().texOffs(0, 24).addBox(-2.500000F, -1.600000F, -2.550000F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-0.000008F, 0.095000F, -11.086000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g12.addOrReplaceChild("cube_10", CubeListBuilder.create().texOffs(0, 6).addBox(2.048383F, -3.048600F, -0.530667F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(-15.563183F, 1.543600F, -1.954333F, 0.00000000F, 0.00000000F, 0.00000000F));
        g12.addOrReplaceChild("cube_11", CubeListBuilder.create().texOffs(0, 32).addBox(-6.634926F, -2.048600F, -1.532735F, 5.000000F, 3.000000F, 5.000000F), PartPose.offsetAndRotation(4.134918F, 0.543600F, 10.116735F, 0.00000000F, 0.00000000F, 0.00000000F));
        g17.addOrReplaceChild("cube_12", CubeListBuilder.create().texOffs(75, 143).addBox(8.606247F, -12.938600F, -4.471714F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.000000F, 5.000000F, -4.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g17.addOrReplaceChild("cube_13", CubeListBuilder.create().texOffs(108, 110).addBox(-8.415045F, -8.000000F, -3.500000F, 17.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(0.221291F, 0.041400F, -0.011479F, 0.00000000F, 0.00000000F, 0.00000000F));
        g17.addOrReplaceChild("cube_14", CubeListBuilder.create().texOffs(0, 141).addBox(8.671038F, -12.938600F, -2.448756F, 7.000000F, 16.000000F, 7.000000F), PartPose.offsetAndRotation(-12.000000F, 5.000000F, 3.977042F, 0.00000000F, 0.00000000F, 0.00000000F));
        g28.addOrReplaceChild("cube_15", CubeListBuilder.create().texOffs(0, 0).addBox(-3.000000F, -2.420000F, -4.000000F, 6.000000F, 0.020000F, 6.000000F), PartPose.offsetAndRotation(0.000000F, 2.050000F, 1.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g23.addOrReplaceChild("cube_16", CubeListBuilder.create().texOffs(106, 0).addBox(-7.000000F, -1.000000F, -7.000000F, 14.000000F, 2.000000F, 14.000000F), PartPose.offsetAndRotation(-0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g34.addOrReplaceChild("cube_17", CubeListBuilder.create().texOffs(131, 152).addBox(-4.000000F, -3.900000F, -4.000000F, 8.000000F, 8.000000F, 8.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        g34.addOrReplaceChild("cube_18", CubeListBuilder.create().texOffs(0, 180).addBox(-4.000000F, -3.900000F, -4.000000F, 8.000000F, 8.000000F, 8.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        return LayerDefinition.create(mesh, 256, 256).bakeRoot();
    }

    private static ModelPart bakeAuraRed() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition minecraftRoot = mesh.getRoot();
        PartDefinition g0 = minecraftRoot.addOrReplaceChild("area_effect", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.500000F, 0.000000F));
        PartDefinition g1 = g0.addOrReplaceChild("area_effect_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g2 = g0.addOrReplaceChild("area_effect_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g3 = g0.addOrReplaceChild("area_effect_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g4 = g0.addOrReplaceChild("area_effect_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        g1.addOrReplaceChild("cube_0", CubeListBuilder.create().texOffs(-256, 0).addBox(-128.000000F, -0.025000F, -128.000000F, 256.000000F, 0.050000F, 256.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        return LayerDefinition.create(mesh, 512, 512).bakeRoot();
    }

    private static ModelPart bakeAuraGreen() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition minecraftRoot = mesh.getRoot();
        PartDefinition g0 = minecraftRoot.addOrReplaceChild("area_effect", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.500000F, 0.000000F));
        PartDefinition g1 = g0.addOrReplaceChild("area_effect_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g2 = g0.addOrReplaceChild("area_effect_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g3 = g0.addOrReplaceChild("area_effect_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g4 = g0.addOrReplaceChild("area_effect_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        g2.addOrReplaceChild("cube_0", CubeListBuilder.create().texOffs(-256, 0).addBox(-128.000000F, -0.025000F, -128.000000F, 256.000000F, 0.050000F, 256.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        return LayerDefinition.create(mesh, 512, 512).bakeRoot();
    }

    private static ModelPart bakeAuraBlue() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition minecraftRoot = mesh.getRoot();
        PartDefinition g0 = minecraftRoot.addOrReplaceChild("area_effect", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.500000F, 0.000000F));
        PartDefinition g1 = g0.addOrReplaceChild("area_effect_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g2 = g0.addOrReplaceChild("area_effect_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g3 = g0.addOrReplaceChild("area_effect_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g4 = g0.addOrReplaceChild("area_effect_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        g3.addOrReplaceChild("cube_0", CubeListBuilder.create().texOffs(-256, 0).addBox(-128.000000F, -0.025000F, -128.000000F, 256.000000F, 0.050000F, 256.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        return LayerDefinition.create(mesh, 512, 512).bakeRoot();
    }

    private static ModelPart bakeAuraPurple() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition minecraftRoot = mesh.getRoot();
        PartDefinition g0 = minecraftRoot.addOrReplaceChild("area_effect", CubeListBuilder.create(), PartPose.offset(0.000000F, -1.500000F, 0.000000F));
        PartDefinition g1 = g0.addOrReplaceChild("area_effect_red", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g2 = g0.addOrReplaceChild("area_effect_green", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g3 = g0.addOrReplaceChild("area_effect_blue", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        PartDefinition g4 = g0.addOrReplaceChild("area_effect_purple", CubeListBuilder.create(), PartPose.offset(0.000000F, -0.000000F, 0.000000F));
        g4.addOrReplaceChild("cube_0", CubeListBuilder.create().texOffs(-256, 0).addBox(-128.000000F, -0.025000F, -128.000000F, 256.000000F, 0.050000F, 256.000000F), PartPose.offsetAndRotation(0.000000F, -0.000000F, 0.000000F, 0.00000000F, 0.00000000F, 0.00000000F));
        return LayerDefinition.create(mesh, 512, 512).bakeRoot();
    }
}

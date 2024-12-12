package com.tcn.cosmoslibrary.client.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class CosmosShieldModel extends Model {
	private static final String PLATE = "plate";
	private static final String HANDLE = "handle";
	private final ModelPart root;
	private final ModelPart plate;
	private final ModelPart handle;

	public CosmosShieldModel() {
		super(RenderType::entitySolid);
		this.root = createLayer().bakeRoot();
		this.plate = root.getChild(PLATE);
		this.handle = root.getChild(HANDLE);
	}

	public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild(PLATE, CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -11.0F, -2.0F, 12.0F, 22.0F, 1.0F), PartPose.ZERO);
		partdefinition.addOrReplaceChild(HANDLE, CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F), PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public ModelPart plate() {
		return this.plate;
	}

	public ModelPart handle() {
		return this.handle;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, int color) {
		this.root.render(poseStack, vertexBuilder, packedLightIn, packedOverlayIn, color);
	}
}

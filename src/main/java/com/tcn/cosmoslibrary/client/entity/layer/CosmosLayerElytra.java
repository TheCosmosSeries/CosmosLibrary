package com.tcn.cosmoslibrary.client.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tcn.cosmoslibrary.common.item.CosmosArmourItemElytra;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;

import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CosmosLayerElytra<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

	private final ElytraModel<T> elytraModel;
	private ResourceLocation TEXTURE_ELYTRA;

	public CosmosLayerElytra(RenderLayerParent<T, M> rendererIn, EntityModelSet modelSet, ResourceLocation location) {
		super(rendererIn);

		this.elytraModel = new ElytraModel<>(modelSet.bakeLayer(ModelLayers.ELYTRA));
		this.TEXTURE_ELYTRA = location;
	}

	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack stackIn = entityLivingBaseIn.getItemBySlot(EquipmentSlot.CHEST);
		
		if (shouldRender(stackIn, entityLivingBaseIn)) {
			ResourceLocation resourcelocation;
			
			if (entityLivingBaseIn instanceof AbstractClientPlayer abstractclientplayer) {
				PlayerSkin playerskin = abstractclientplayer.getSkin();
				
				if (playerskin.elytraTexture() != null) {
					resourcelocation = playerskin.elytraTexture();
				} else if (playerskin.capeTexture() != null && abstractclientplayer.isModelPartShown(PlayerModelPart.CAPE)) {
					resourcelocation = playerskin.capeTexture();
				} else {
					resourcelocation = getElytraTexture(stackIn, entityLivingBaseIn);
				}
			} else {
				resourcelocation = getElytraTexture(stackIn, entityLivingBaseIn);
			}
			
			int colour = ComponentColour.LIGHT_GRAY.decOpaque();
			
			if (stackIn.has(DataComponents.CUSTOM_DATA)) {
				CompoundTag stackTag = stackIn.get(DataComponents.CUSTOM_DATA).copyTag();
				
				if (stackTag.contains("nbt_data")) {
					CompoundTag nbtData = stackTag.getCompound("nbt_data");
					
					if (nbtData.contains("wing_colour")) {
						int wingColour = nbtData.getInt("wing_colour");
						
						colour = FastColor.ARGB32.opaque(wingColour);
					}
				}
			}

			matrixStackIn.pushPose();
			matrixStackIn.translate(0.0D, 0.0D, 0.125D);
			this.getParentModel().copyPropertiesTo(this.elytraModel);
			this.elytraModel.setupAnim(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, RenderType.armorCutoutNoCull(resourcelocation), stackIn.hasFoil());
			this.elytraModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, colour);
			
			matrixStackIn.popPose();
		}
	}

	public boolean shouldRender(ItemStack stack, T entity) {
		return stack.getItem() instanceof CosmosArmourItemElytra;
	}
	
	public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
		return TEXTURE_ELYTRA;
	}
}
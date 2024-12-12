package com.tcn.cosmoslibrary.client.renderer.item;

import java.util.Objects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tcn.cosmoslibrary.client.renderer.model.CosmosShieldModel;
import com.tcn.cosmoslibrary.energy.item.CosmosEnergyShieldItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CosmosShieldBEWLR extends BlockEntityWithoutLevelRenderer {
	
	private final CosmosShieldModel shieldModel = new CosmosShieldModel();
	
	public CosmosShieldBEWLR(ResourceLocation normalIn, ResourceLocation noPatternIn) {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void renderByItem(ItemStack stack, ItemDisplayContext transformIn, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
		Item item = stack.getItem();
		
		if (item instanceof CosmosEnergyShieldItem shieldItem) {
			BannerPatternLayers bannerpatternlayers = stack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
            DyeColor dyecolor = stack.get(DataComponents.BASE_COLOR);
            boolean flag = !bannerpatternlayers.layers().isEmpty() || dyecolor != null;
            
            poseStack.pushPose();
            poseStack.scale(1.0F, -1.0F, -1.0F);
            
            Material material = flag ? new Material(Sheets.SHIELD_SHEET, shieldItem.getResource(true)) : new Material(Sheets.SHIELD_SHEET, shieldItem.getResource(false));
            VertexConsumer vertexconsumer = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(buffer, this.shieldModel.renderType(material.atlasLocation()), true, stack.hasFoil()));
            this.shieldModel.handle().render(poseStack, vertexconsumer, packedLight, packedOverlay);
            
            if (flag) {
                BannerRenderer.renderPatterns(poseStack, buffer, packedLight, packedOverlay, this.shieldModel.plate(), material, false, Objects.requireNonNullElse(dyecolor, DyeColor.WHITE), bannerpatternlayers, stack.hasFoil() );
            } else {
                this.shieldModel.plate().render(poseStack, vertexconsumer, packedLight, packedOverlay);
            }

            poseStack.popPose();
		}
	}
}
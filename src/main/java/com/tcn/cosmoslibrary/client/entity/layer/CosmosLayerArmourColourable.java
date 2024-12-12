package com.tcn.cosmoslibrary.client.entity.layer;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tcn.cosmoslibrary.common.item.CosmosArmourItemColourable;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings({ "deprecation" })
public class CosmosLayerArmourColourable<E extends LivingEntity, M extends HumanoidModel<E>, A extends HumanoidModel<E>> extends RenderLayer<E, M> {
	
	private enum TYPE {
		BASE,
		OVERLAY,
		ALPHA;
	}
	
	private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
	
	private final A innerModel;
	private final A outerModel;
    private final TextureAtlas armorTrimAtlas;

	public CosmosLayerArmourColourable(RenderLayerParent<E, M> entityRenderer, A innerModelIn, A outerModelIn, ModelManager modelManager) {
		super(entityRenderer);
		this.innerModel = innerModelIn;
		this.outerModel = outerModelIn;
        this.armorTrimAtlas = modelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
	}

	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, E entityLivingBaseIn, float limbSwingIn, float limbSwingAmountIn, float partialTicks, float ageInTicks, float netHeadYawIn, float headPitchIn) {
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlot.CHEST, packedLightIn, headPitchIn, headPitchIn, headPitchIn, headPitchIn, headPitchIn, packedLightIn, this.getArmorModel(EquipmentSlot.CHEST));
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlot.LEGS,  packedLightIn, headPitchIn, headPitchIn, headPitchIn, headPitchIn, headPitchIn, packedLightIn, this.getArmorModel(EquipmentSlot.LEGS));
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlot.FEET,  packedLightIn, headPitchIn, headPitchIn, headPitchIn, headPitchIn, headPitchIn, packedLightIn, this.getArmorModel(EquipmentSlot.FEET));
		this.renderArmorPiece(matrixStackIn, bufferIn, entityLivingBaseIn, EquipmentSlot.HEAD,  packedLightIn, headPitchIn, headPitchIn, headPitchIn, headPitchIn, headPitchIn, packedLightIn, this.getArmorModel(EquipmentSlot.HEAD));
	}

	private void renderArmorPiece(PoseStack matrixStackIn, MultiBufferSource bufferIn, E livingEntityIn, EquipmentSlot slotTypeIn, float limbSwingIn, float limbSwingAmountIn, float partialTicksIn, float ageInTicksIn, float netHeadYawIn, float headPitchIn, int packedLightIn, A modelIn) {
		ItemStack stackIn = livingEntityIn.getItemBySlot(slotTypeIn);
		
		if (stackIn.getItem() instanceof CosmosArmourItemColourable armourItem) {
			if (armourItem.getEquipmentSlot() == slotTypeIn) {
				this.getParentModel().copyPropertiesTo(modelIn);
				Model model = this.getArmorModelHook(livingEntityIn, stackIn, slotTypeIn, modelIn);
				this.setPartVisibility(modelIn, slotTypeIn);
				
				boolean flag = this.usesInnerModel(slotTypeIn);
				boolean flag1 = stackIn.hasFoil();
				ArmorMaterial armormaterial = armourItem.getMaterial().value();

                IClientItemExtensions extensions = IClientItemExtensions.of(stackIn);
                extensions.setupModelAnimations(livingEntityIn, stackIn, slotTypeIn, model, limbSwingIn, limbSwingAmountIn, partialTicksIn, ageInTicksIn, netHeadYawIn, headPitchIn);
				
				if (stackIn.has(DataComponents.CUSTOM_DATA)) {
					CompoundTag stackTag = stackIn.get(DataComponents.CUSTOM_DATA).copyTag();
					
					if (stackTag.contains("nbt_data")) {
						CompoundTag nbtData = stackTag.getCompound("nbt_data");
						
						if (nbtData.contains("colour")) {
							int colour = FastColor.ARGB32.opaque(nbtData.getInt("colour"));
							
							this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, model, colour, false, this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, TYPE.BASE, null));
						}
					} else {
						this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, model, ComponentColour.POCKET_PURPLE_LIGHT.decOpaque(), false, this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, TYPE.BASE, null));
					}
				} else {
					this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, model, ComponentColour.POCKET_PURPLE_LIGHT.decOpaque(), false, this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, TYPE.BASE, null));
				}
				
				this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, model, ComponentColour.WHITE.decOpaque(), false, this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, TYPE.OVERLAY, null));
				this.renderModel(matrixStackIn, bufferIn, packedLightIn, flag1, model, ComponentColour.WHITE.decOpaque(), true, this.getArmorResource(livingEntityIn, stackIn, slotTypeIn, TYPE.ALPHA, null));
			}
		}
	}

	@SuppressWarnings("incomplete-switch")
	protected void setPartVisibility(A modelIn, EquipmentSlot slotIn) {
		modelIn.setAllVisible(false);
		switch (slotIn) {
		case HEAD:
			modelIn.head.visible = true;
			modelIn.hat.visible = true;
			break;
		case CHEST:
			modelIn.body.visible = true;
			modelIn.rightArm.visible = true;
			modelIn.leftArm.visible = true;
			break;
		case LEGS:
			modelIn.body.visible = true;
			modelIn.rightLeg.visible = true;
			modelIn.leftLeg.visible = true;
			break;
		case FEET:
			modelIn.rightLeg.visible = true;
			modelIn.leftLeg.visible = true;
		}

	}

	private void renderModel(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, boolean foilIn, Model modelIn, int colour, boolean alphaLayer, ResourceLocation armorResource) {
		if (alphaLayer) {
			RenderType type = RenderType.entityTranslucent(armorResource);
			
			VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, type, foilIn);
			modelIn.renderToBuffer(poseStack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, colour);
		} else {
			RenderType type = RenderType.armorCutoutNoCull(armorResource);
			
			VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, type, false);
			modelIn.renderToBuffer(poseStack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, colour);
		}
	}

	private A getArmorModel(EquipmentSlot slotIn) {
		return (A) (this.usesInnerModel(slotIn) ? this.innerModel : this.outerModel);
	}

	private boolean usesInnerModel(EquipmentSlot slotIn) {
		return slotIn == EquipmentSlot.LEGS;
	}
	
	protected Model getArmorModelHook(E entity, ItemStack itemStack, EquipmentSlot slot, A model) {
		return ClientHooks.getArmorModel(entity, itemStack, slot, model);
	}
	
	public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot, TYPE typeIn, @Nullable String type) {
		Item item = stack.getItem();
		
		if (item instanceof CosmosArmourItemColourable armour) {
			List<ArmorMaterial.Layer> layers = armour.getMaterial().value().layers();
			
			if (layers.size() > 2) {				
				if (typeIn == TYPE.BASE) {
					String s1 = ClientHooks.getArmorTexture(entity, stack, layers.get(0), this.usesInnerModel(slot), slot).toString();

					ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);
					
					if (resourcelocation == null) {
						resourcelocation = ResourceLocation.parse(s1);
						ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
					}
			
					return resourcelocation;
				} else if (typeIn == TYPE.OVERLAY) {
					String s1 = ClientHooks.getArmorTexture(entity, stack, layers.get(1), this.usesInnerModel(slot), slot).toString();

					ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);
					
					if (resourcelocation == null) {
						resourcelocation = ResourceLocation.parse(s1);
						ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
					}
			
					return resourcelocation;
				} else if (typeIn == TYPE.ALPHA) {
					String s1 = ClientHooks.getArmorTexture(entity, stack, layers.get(2), this.usesInnerModel(slot), slot).toString();

					ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);
					
					if (resourcelocation == null) {
						resourcelocation = ResourceLocation.parse(s1);
						ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
					}
			
					return resourcelocation;
				} else {
					String s1 = ClientHooks.getArmorTexture(entity, stack, layers.get(0), this.usesInnerModel(slot), slot).toString();
					ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);
					
					if (resourcelocation == null) {
						resourcelocation = ResourceLocation.parse(s1);
						ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
					}
			
					return resourcelocation;
				}
			} else {
				String s1 = ClientHooks.getArmorTexture(entity, stack, layers.get(0), this.usesInnerModel(slot), slot).toString();
				ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);
				
				if (resourcelocation == null) {
					resourcelocation = ResourceLocation.parse(s1);
					ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
				}
		
				return resourcelocation;
			}
		} else {
			return ResourceLocation.fromNamespaceAndPath("", "");
		}
	}
}
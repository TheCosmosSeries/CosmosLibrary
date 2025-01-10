package com.tcn.cosmoslibrary.runtime.common;

import com.tcn.cosmoslibrary.common.capability.IEnergyCapBE;
import com.tcn.cosmoslibrary.common.capability.IEnergyCapItem;
import com.tcn.cosmoslibrary.common.capability.IFluidCapBE;
import com.tcn.cosmoslibrary.common.capability.IFluidCapItem;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;

public class CosmosRuntime {

	@OnlyIn(Dist.CLIENT)
	public class Client {
		@Deprecated
		@OnlyIn(Dist.CLIENT)
		public static void setRenderLayers(RenderType renderType, Block... blocks) {
			for (Block block : blocks) {
				ItemBlockRenderTypes.setRenderLayer(block, renderType);
			}
		}

		@OnlyIn(Dist.CLIENT)
		public static void setFluidRenderLayers(RenderType type, Fluid... fluids) {
			for (Fluid fluid: fluids) {
				ItemBlockRenderTypes.setRenderLayer(fluid, type);
			}
		}

		@OnlyIn(Dist.CLIENT)
		public static void registerItemColours(RegisterColorHandlersEvent.Item event, ItemColor colour, ItemLike... items) {
			for (ItemLike item : items) {
				registerItemColour(event, colour, item);
			}
		}

		@OnlyIn(Dist.CLIENT)
		public static void registerItemColour(RegisterColorHandlersEvent.Item event, ItemColor colour, ItemLike item) {
			event.register(colour, item);
		}

		@OnlyIn(Dist.CLIENT)
		public static void registerBlockColours(RegisterColorHandlersEvent.Block event, BlockColor colour, Block... blocks) {
			for (Block block : blocks) {
				registerBlockColour(event, colour, block);
			}
		}

		@OnlyIn(Dist.CLIENT)
		public static void registerBlockColour(RegisterColorHandlersEvent.Block event, BlockColor colour, Block block) {
			event.register(colour, block);
		}

		@OnlyIn(Dist.CLIENT)
		public static void registerStandaloneItemModels(ModelEvent.RegisterAdditional eventIn, String modId, String... modelPaths) {
			for (String modelPath : modelPaths) {
				registerStandalone(eventIn, modId, "item/" + modelPath);
			}
		}

		@OnlyIn(Dist.CLIENT)
		public static void registerStandaloneModels(ModelEvent.RegisterAdditional eventIn, String modId, String... modelPaths) {
			for (String modelPath : modelPaths) {
				registerStandalone(eventIn, modId, modelPath);
			}
		}

		@OnlyIn(Dist.CLIENT)
		public static void registerStandalone(ModelEvent.RegisterAdditional eventIn, String modId, String modelPath) {
			eventIn.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(modId, modelPath)));
		}

		@OnlyIn(Dist.CLIENT)
		public static IClientItemExtensions getItemRendererExtension(BlockEntityWithoutLevelRenderer renderer) {
			return new IClientItemExtensions() {
				@Override
				public BlockEntityWithoutLevelRenderer getCustomRenderer() {
					return renderer;
				}
			};
		}

		@OnlyIn(Dist.CLIENT)
		public static void registerBEWLRToItems(RegisterClientExtensionsEvent event, BlockEntityWithoutLevelRenderer renderer, Item... items) {
			for (Item item : items) {
				registerBEWLRToItem(event, renderer, item);
			}
		}

		@OnlyIn(Dist.CLIENT)
		public static void registerBEWLRToItem(RegisterClientExtensionsEvent event, BlockEntityWithoutLevelRenderer renderer, Item item) {
			event.registerItem(getItemRendererExtension(renderer), item);
		}
		

		@SafeVarargs
		@OnlyIn(Dist.CLIENT)
		public static <T extends BlockEntity> void registerBERenderers(EntityRenderersEvent.RegisterRenderers event, BlockEntityRendererProvider<T> provider, BlockEntityType<? extends T>... types) {
			for (BlockEntityType<? extends T> type : types) {
				registerBERenderer(event, provider, type);
			}
		}

		@OnlyIn(Dist.CLIENT)
		public static <T extends BlockEntity> void registerBERenderer(EntityRenderersEvent.RegisterRenderers event, BlockEntityRendererProvider<T> provider, BlockEntityType<? extends T> type) {
			event.registerBlockEntityRenderer(type, provider);
		}

		@OnlyIn(Dist.CLIENT)
		public static void regiserCameraOverlay(RegisterGuiLayersEvent event, String id, LayeredDraw.Layer layer) {
			registerOverlay(event, VanillaGuiLayers.CAMERA_OVERLAYS, id, layer);
		}

		@OnlyIn(Dist.CLIENT)
		public static void registerOverlay(RegisterGuiLayersEvent event, ResourceLocation guiLayer, String id, LayeredDraw.Layer layer) {
			event.registerAbove(guiLayer, ResourceLocation.parse(id), layer);
		}

		@OnlyIn(Dist.CLIENT)
		public static void regiserConfigScreen(ModContainer container, IConfigScreenFactory extension) {
			container.registerExtensionPoint(IConfigScreenFactory.class, extension);
		}
	}

	public class Server {
		public static void registerBucketItemCapabilities(RegisterCapabilitiesEvent event, Item... items) {
			for (Item item : items) {
				event.registerItem(Capabilities.FluidHandler.ITEM, (stack, voidA) -> new FluidBucketWrapper(stack), item);
			}
		}
	
		@SafeVarargs
		public static void registerBlockEnergyCapabilities(RegisterCapabilitiesEvent event, BlockEntityType<? extends IEnergyCapBE>... caps) {
			for (BlockEntityType<? extends IEnergyCapBE> cap : caps) {
				event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, cap, (myBlockEntity, side) -> myBlockEntity.getEnergyCapability(side));
			}
		}
		
		public static void registerItemEnergyCapabilities(RegisterCapabilitiesEvent event, IEnergyCapItem... caps) {
			for (IEnergyCapItem item : caps) {
				event.registerItem(Capabilities.EnergyStorage.ITEM, (stackIn, voidA) -> item.getEnergyCapability(stackIn), item);
			}
		}
	
		@SafeVarargs
		public static void registerBlockFluidCapabilities(RegisterCapabilitiesEvent event, BlockEntityType<? extends IFluidCapBE>... caps) {
			for (BlockEntityType<? extends IFluidCapBE> cap : caps) {
				event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, cap, (myBlockEntity, side) -> myBlockEntity.getFluidCapability(side));
			}
		}
		
		public static void registerItemFluidCapabilities(RegisterCapabilitiesEvent event, IFluidCapItem... caps) {
			for (IFluidCapItem item : caps) {
				event.registerItem(Capabilities.FluidHandler.ITEM, (stackIn, voidA) -> item.getFluidCapability(stackIn), item);
			}
		}
	}
}
package com.tcn.cosmoslibrary.common.runtime;

import com.tcn.cosmoslibrary.common.capability.IEnergyCapBE;
import com.tcn.cosmoslibrary.common.capability.IEnergyCapItem;
import com.tcn.cosmoslibrary.common.capability.IFluidCapBE;
import com.tcn.cosmoslibrary.common.capability.IFluidCapItem;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;

public class CosmosRuntimeHelper {
	
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
			event.register(colour, item);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerBlockColours(RegisterColorHandlersEvent.Block event, BlockColor colour, Block... blocks) {
		for (Block block : blocks) {
			event.register(colour, block);
		}
	}
	
	
	@OnlyIn(Dist.CLIENT)
	public static void registerStandaloneModels(ModelEvent.RegisterAdditional eventIn, String modId, String... modelPaths) {
		for (String modelPath : modelPaths) {
			eventIn.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(modId, "item/" + modelPath)));
		}
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

	@OnlyIn(Dist.CLIENT)
	public static void regiserConfigScreen(ModContainer container, IConfigScreenFactory extension) {
		container.registerExtensionPoint(IConfigScreenFactory.class, extension);
	}
	
	public static void regiserBucketItemCapabilities(RegisterCapabilitiesEvent event, Item... items) {
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
package com.tcn.cosmoslibrary.common.fluid;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;

public class CosmosFluidType extends FluidType {
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final ResourceLocation overlayTexture;
    private final ResourceLocation overlayTextureFull;
    
    private final int tintColor;
    private final Vector3f fogColor;
    private final float fogStart;
    private final float fogEnd;

    public CosmosFluidType(
    	ResourceLocation stillTexture, ResourceLocation flowingTexture, 
    	ResourceLocation overlayTexture, ResourceLocation overlayTextureFull, 
    	int tintColor, Vector3f fogColor,
    	float fogStart, float fogEnd, FluidType.Properties properties
    ) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.overlayTextureFull = overlayTextureFull;        
        this.tintColor = tintColor;
        this.fogColor = fogColor;
        this.fogStart = fogStart;
        this.fogEnd = fogEnd;
    }

    public ResourceLocation getStillTexture() {
        return this.stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return this.flowingTexture;
    }

    public int getTintColor() {
        return this.tintColor;
    }

    public ResourceLocation getOverlayTexture() {
        return this.overlayTexture;
    }

    public ResourceLocation getOverlayTextureFull() {
        return this.overlayTextureFull;
    }

    public Vector3f getFogColor() {
        return this.fogColor;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return CosmosFluidType.this.getStillTexture();
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return CosmosFluidType.this.getFlowingTexture();
            }

            @Override
            public @Nullable ResourceLocation getOverlayTexture() {
                return CosmosFluidType.this.getOverlayTexture();
            }

            @Override
            public int getTintColor() {
                return CosmosFluidType.this.getTintColor();
            }
            
            @Override
            public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                return CosmosFluidType.this.getOverlayTextureFull();
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level,  int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return CosmosFluidType.this.getFogColor();
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(CosmosFluidType.this.fogStart);
                RenderSystem.setShaderFogEnd(CosmosFluidType.this.fogEnd);
            }
        });
    }
}
package com.tcn.cosmoslibrary.client.renderer.model;

import java.util.List;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

//@SuppressWarnings("deprecation")
@Deprecated
public class CosmosBakedModel implements BakedModel {
	
	private final BakedModel internal;

	public CosmosBakedModel(BakedModel internal) {
		this.internal = internal;
	}
	
	@Override
	public boolean useAmbientOcclusion() {
		return this.internal.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return this.internal.isGui3d();
	}

	public BakedModel getInternal() {
		return this.internal;
	}

	@Override
	public boolean isCustomRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return this.internal.getParticleIcon();
	}

	@Override
	public ItemOverrides getOverrides() {
		return this.internal.getOverrides();
	}

	@Override
	public ItemTransforms getTransforms() {
		return this.internal.getTransforms();
	}

	@Override
	public boolean usesBlockLight() {
		return false;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand) {
		return this.internal.getQuads(state, side, rand);
	}
}
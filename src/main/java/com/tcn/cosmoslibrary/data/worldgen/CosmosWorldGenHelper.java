package com.tcn.cosmoslibrary.data.worldgen;

import java.util.List;

import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class CosmosWorldGenHelper {
	public static List<PlacementModifier> orePlacement(PlacementModifier countModifierIn, PlacementModifier modifierIn) {
		return List.of(countModifierIn, InSquarePlacement.spread(), modifierIn, BiomeFilter.biome());
	}

	public static List<PlacementModifier> commonOrePlacement(int countIn, PlacementModifier modifierIn) {
		return orePlacement(CountPlacement.of(countIn), modifierIn);
	}

	public static List<PlacementModifier> rareOrePlacement(int countIn, PlacementModifier modifierIn) {
		return orePlacement(RarityFilter.onAverageOnceEvery(countIn), modifierIn);
	}
}
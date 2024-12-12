package com.tcn.cosmoslibrary.core.teleport;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public enum EnumSafeTeleport {
	ZERO(0, 0, 0),
	UP(0, 1, 0),
	NORTH(0, 0, -1), NORTH_MINUS_ONE(0, -1, -1),
	SOUTH(0, 0, 1), SOUTH_MINUS_ONE(0, -1, 1),
	WEST(-1, 0, 0), WEST_MINUS_ONE(-1, -1, 0),
	EAST(1, 0, 0), EAST_MINUS_ONE(1, -1, 0),
	NORTHWEST(-1, 0, -1), NORTHWEST_MINUS_ONE(-1, -1, -1),
	NORTHEAST(1, 0, -1), NORTHEAST_MINUS_ONE(1, -1, -1),
	SOUTHWEST(-1, 0, 1), SOUTHWEST_MINUS_ONE(-1, -1, 1),
	SOUTHEAST(1, 0, 1), SOUTHEAST_MINUS_ONE(1, -1, 1),
	
	UP_TWO(0, 2, 0),
	NORTH_TWO(0, 0, -2), NORTH_MINUS_ONE_TWO(0, -2, -2),
	SOUTH_TWO(0, 0, 2), SOUTH_MINUS_ONE_TWO(0, -2, 2),
	WEST_TWO(-2, 0, 0), WEST_MINUS_ONE_TWO(-2, -2, 0),
	EAST_TWO(2, 0, 0), EAST_MINUS_ONE_TWO(2, -2, 0),
	NORTHWEST_TWO(-2, 0, -2), NORTHWEST_MINUS_ONE_TWO(-2, -2, -2),
	NORTHEAST_TWO(2, 0, -2), NORTHEAST_MINUS_ONE_TWO(2, -2, -2),
	SOUTHWEST_TWO(-2, 0, 2), SOUTHWEST_MINUS_ONE_TWO(-2, -2, 2),
	SOUTHEAST_TWO(2, 0, 2), SOUTHEAST_MINUS_ONE_TWO(2, -2, 2),
	
	UP_THREE(0, 3, 0),
	NORTH_THREE(0, 0, -3), NORTH_MINUS_ONE_THREE(0, -3, -3),
	SOUTH_THREE(0, 0, 3), SOUTH_MINUS_ONE_THREE(0, -3, 3),
	WEST_THREE(-3, 0, 0), WEST_MINUS_ONE_THREE(-3, -3, 0),
	EAST_THREE(3, 0, 0), EAST_MINUS_ONE_THREE(3, -3, 0),
	NORTHWEST_THREE(-3, 0, -3), NORTHWEST_MINUS_ONE_THREE(-3, -3, -3),
	NORTHEAST_THREE(3, 0, -3), NORTHEAST_MINUS_ONE_THREE(3, -3, -3),
	SOUTHWEST_THREE(-3, 0, 3), SOUTHWEST_MINUS_ONE_THREE(-3, -3, 3),
	SOUTHEAST_THREE(3, 0, 3), SOUTHEAST_MINUS_ONE_THREE(3, -3, 3),
	
	UNKNOWN(0, 0, 0),
	UNSAFE(0, 0, 0);

	public static final EnumSafeTeleport[] VALID_DIRECTIONS = {
			ZERO, UP, NORTH, SOUTH, WEST, EAST,
			NORTH_MINUS_ONE, SOUTH_MINUS_ONE, WEST_MINUS_ONE, EAST_MINUS_ONE,
			NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST,
			NORTHWEST_MINUS_ONE, NORTHEAST_MINUS_ONE, SOUTHWEST_MINUS_ONE, SOUTHEAST_MINUS_ONE,
			
			UP_TWO, NORTH_TWO, SOUTH_TWO, WEST_TWO, EAST_TWO,
			NORTH_MINUS_ONE_TWO, SOUTH_MINUS_ONE_TWO, WEST_MINUS_ONE_TWO, EAST_MINUS_ONE_TWO,
			NORTHWEST_TWO, NORTHEAST_TWO, SOUTHWEST_TWO, SOUTHEAST_TWO,
			NORTHWEST_MINUS_ONE_TWO, NORTHEAST_MINUS_ONE_TWO, SOUTHWEST_MINUS_ONE_TWO, SOUTHEAST_MINUS_ONE_TWO,
			
			UP_THREE, NORTH_THREE, SOUTH_THREE, WEST_THREE, EAST_THREE,
			NORTH_MINUS_ONE_THREE, SOUTH_MINUS_ONE_THREE, WEST_MINUS_ONE_THREE, EAST_MINUS_ONE_THREE,
			NORTHWEST_THREE, NORTHEAST_THREE, SOUTHWEST_THREE, SOUTHEAST_THREE,
			NORTHWEST_MINUS_ONE_THREE, NORTHEAST_MINUS_ONE_THREE, SOUTHWEST_MINUS_ONE_THREE, SOUTHEAST_MINUS_ONE_THREE
	};

	public final int offsetX;
	public final int offsetY;
	public final int offsetZ;

	private EnumSafeTeleport(int x, int y, int z) {
		offsetX = x;
		offsetY = y;
		offsetZ = z;
	}
	
	private static boolean isAir(Level levelIn, BlockPos posIn) {
		BlockState state = levelIn.getBlockState(posIn);
		
		return state.isAir();
	}
	
	private static boolean isLiquid(Level levelIn, BlockPos posIn) {
		BlockState state = levelIn.getBlockState(posIn);
		
		return state.liquid();
	}

	private static boolean isAirOrLiquid(Level levelIn, BlockPos posIn) {
		BlockState state = levelIn.getBlockState(posIn);
		
		return state.isAir() || state.liquid();
	}
	
	public static EnumSafeTeleport getValidTeleportLocation(Level levelIn, BlockPos posIn) {
		for (EnumSafeTeleport direction : VALID_DIRECTIONS) {
			BlockPos testPos = new BlockPos(posIn.getX() + direction.offsetX, posIn.getY() + direction.offsetY, posIn.getZ() + direction.offsetZ);
			
			if (isAirOrLiquid(levelIn, testPos)) {
				if (isAirOrLiquid(levelIn, testPos) && isAirOrLiquid(levelIn, testPos.offset(Direction.UP.getNormal()))) {
					return direction;
				}
			}
		}
		return UNKNOWN;
	}

	public static boolean isSafeTeleportLocation(Level levelIn, BlockPos posIn) {
		for (EnumSafeTeleport direction : VALID_DIRECTIONS) {
			BlockPos testPos = new BlockPos(posIn.getX() + direction.offsetX, posIn.getY() + direction.offsetY, posIn.getZ() + direction.offsetZ);
			
			if (isAirOrLiquid(levelIn, testPos)) {
				if (isAirOrLiquid(levelIn, testPos) && isAirOrLiquid(levelIn, testPos.offset(Direction.UP.getNormal()))) {
					if ((isLiquid(levelIn, testPos) && !isAir(levelIn, testPos)) || (isLiquid(levelIn, testPos.offset(Direction.UP.getNormal())) && !isAir(levelIn, testPos.offset(Direction.UP.getNormal())))) {
						return false;
					} else {
						return true;
					}
				}
			}
		}
		return true;
	}

	public BlockPos toBlockPos() {
		return new BlockPos(offsetX, offsetY, offsetZ);
	}
}
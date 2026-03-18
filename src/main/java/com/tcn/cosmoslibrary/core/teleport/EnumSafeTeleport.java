package com.tcn.cosmoslibrary.core.teleport;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

@SuppressWarnings("deprecation")
public enum EnumSafeTeleport {
	ZERO(0, 0, 0),
	
	// ONE
	NORTH(0, 0, -1), NORTH_MINUS_ONE(0, 0, -1),
	SOUTH(0, 0,  1), SOUTH_MINUS_ONE(0, 0,  1),
	WEST(-1, 0,  0), WEST_MINUS_ONE(-1, 0,  0),
	EAST( 1, 0,  0), EAST_MINUS_ONE(1,  0,  0),
	
	NORTHWEST(-1, 0, -1), NORTHWEST_MINUS_ONE(-1, 0, -1),
	NORTHEAST( 1, 0, -1), NORTHEAST_MINUS_ONE( 1, 0, -1),
	SOUTHWEST(-1, 0,  1), SOUTHWEST_MINUS_ONE(-1, 0,  1),
	SOUTHEAST( 1, 0,  1), SOUTHEAST_MINUS_ONE( 1, 0,  1),
	
	// UP ONE
	UP(0, 1, 0),
	
	UP_NORTH(0, 1, -1), UP_NORTH_MINUS_ONE(0, 1, -1),
	UP_SOUTH(0, 1,  1), UP_SOUTH_MINUS_ONE(1, 1,  1),
	UP_WEST(-1, 1,  0), UP_WEST_MINUS_ONE(-1, 1,  0),
	UP_EAST( 1, 1,  0), UP_EAST_MINUS_ONE( 1, 1,  0),
	
	UP_NORTHWEST(-1, 1, -1), UP_NORTHWEST_MINUS_ONE(-1, 1, -1),
	UP_NORTHEAST(1,  1, -1), UP_NORTHEAST_MINUS_ONE(1,  1, -1),
	UP_SOUTHWEST(-1, 1,  1), UP_SOUTHWEST_MINUS_ONE(-1, 1,  1),
	UP_SOUTHEAST(1,  1,  1), UP_SOUTHEAST_MINUS_ONE(1,  1,  1),
	
	// TWO
	NORTH_TWO(0, 0, -2), NORTH_MINUS_ONE_TWO(0, 0, -2),
	SOUTH_TWO(0, 0,  2), SOUTH_MINUS_ONE_TWO(0, 0,  2),
	WEST_TWO(-2, 0,  0), WEST_MINUS_ONE_TWO(-2, 0,  0),
	EAST_TWO( 2, 0,  0), EAST_MINUS_ONE_TWO( 2, 0,  0),
	
	NORTHWEST_TWO(-2, 0, -2), NORTHWEST_MINUS_ONE_TWO(-2, 0, -2),
	NORTHEAST_TWO( 2, 0, -2), NORTHEAST_MINUS_ONE_TWO( 2, 0, -2),
	SOUTHWEST_TWO(-2, 0,  2), SOUTHWEST_MINUS_ONE_TWO(-2, 0,  2),
	SOUTHEAST_TWO( 2, 0,  2), SOUTHEAST_MINUS_ONE_TWO( 2, 0,  2),

	// UP TWO
	UP_TWO(0, 2, 0),
	
	UP_NORTH_TWO(0, 2, -2), UP_NORTH_MINUS_ONE_TWO(0, 2, -2),
	UP_SOUTH_TWO(0, 2,  2), UP_SOUTH_MINUS_ONE_TWO(0, 2,  2),
	UP_WEST_TWO(-2, 2,  0), UP_WEST_MINUS_ONE_TWO(-2, 2,  0),
	UP_EAST_TWO( 2, 2,  0), UP_EAST_MINUS_ONE_TWO( 2, 2,  0),
	
	UP_NORTHWEST_TWO(-2, 2, -2), UP_NORTHWEST_MINUS_ONE_TWO(-2, 2, -2),
	UP_NORTHEAST_TWO( 2, 2, -2), UP_NORTHEAST_MINUS_ONE_TWO( 2, 2, -2),
	UP_SOUTHWEST_TWO(-2, 2,  2), UP_SOUTHWEST_MINUS_ONE_TWO(-2, 2,  2),
	UP_SOUTHEAST_TWO( 2, 2,  2), UP_SOUTHEAST_MINUS_ONE_TWO( 2, 2,  2),
	
	// THREE
	NORTH_THREE(0, 0, -3), NORTH_MINUS_ONE_THREE(0, 0, -3),
	SOUTH_THREE(0, 0,  3), SOUTH_MINUS_ONE_THREE(0, 0,  3),
	WEST_THREE(-3, 0,  0), WEST_MINUS_ONE_THREE(-3, 0,  0),
	EAST_THREE( 3, 0,  0), EAST_MINUS_ONE_THREE( 3, 0,  0),
	
	NORTHWEST_THREE(-3, 0, -3), NORTHWEST_MINUS_ONE_THREE(-3, 0, -3),
	NORTHEAST_THREE( 3, 0, -3), NORTHEAST_MINUS_ONE_THREE( 3, 0, -3),
	SOUTHWEST_THREE(-3, 0,  3), SOUTHWEST_MINUS_ONE_THREE(-3, 0,  3),
	SOUTHEAST_THREE( 3, 0,  3), SOUTHEAST_MINUS_ONE_THREE( 3, 0,  3),

	// UP THREE
	UP_THREE(0, 3, 0),
	
	UP_NORTH_THREE(0, 3, -3), UP_NORTH_MINUS_ONE_THREE(0, 3, -3),
	UP_SOUTH_THREE(0, 3,  3), UP_SOUTH_MINUS_ONE_THREE(0, 3,  3),
	UP_WEST_THREE(-3, 3,  0), UP_WEST_MINUS_ONE_THREE(-3, 3,  0),
	UP_EAST_THREE( 3, 3,  0), UP_EAST_MINUS_ONE_THREE( 3, 3,  0),
	
	UP_NORTHWEST_THREE(-3, 3, -3), UP_NORTHWEST_MINUS_ONE_THREE(-3, 3, -3),
	UP_NORTHEAST_THREE( 3, 3, -3), UP_NORTHEAST_MINUS_ONE_THREE( 3, 3, -3),
	UP_SOUTHWEST_THREE(-3, 3,  3), UP_SOUTHWEST_MINUS_ONE_THREE(-3, 3,  3),
	UP_SOUTHEAST_THREE( 3, 3,  3), UP_SOUTHEAST_MINUS_ONE_THREE( 3, 3,  3),
	
	// FOUR
	NORTH_FOUR(0, 0, -4), NORTH_MINUS_ONE_FOUR(0, 0, -4),
	SOUTH_FOUR(0, 0,  4), SOUTH_MINUS_ONE_FOUR(0, 0,  4),
	WEST_FOUR(-4, 0,  0), WEST_MINUS_ONE_FOUR(-4, 0,  0),
	EAST_FOUR( 4, 0,  0), EAST_MINUS_ONE_FOUR( 4, 0,  0),
	
	NORTHWEST_FOUR(-4, 0, -4), NORTHWEST_MINUS_ONE_FOUR(-4, 0, -4),
	NORTHEAST_FOUR( 4, 0, -4), NORTHEAST_MINUS_ONE_FOUR( 4, 0, -4),
	SOUTHWEST_FOUR(-4, 0,  4), SOUTHWEST_MINUS_ONE_FOUR(-4, 0,  4),
	SOUTHEAST_FOUR( 4, 0,  4), SOUTHEAST_MINUS_ONE_FOUR( 4, 0,  4),

	// UP FOUR
	UP_FOUR(0, 4, 0),
	
	UP_NORTH_FOUR(0, 4, -4), UP_NORTH_MINUS_ONE_FOUR(0, 4, -4),
	UP_SOUTH_FOUR(0, 4,  4), UP_SOUTH_MINUS_ONE_FOUR(0, 4,  4),
	UP_WEST_FOUR(-4, 4,  0), UP_WEST_MINUS_ONE_FOUR(-4, 4,  0),
	UP_EAST_FOUR( 4, 4,  0), UP_EAST_MINUS_ONE_FOUR( 4, 4,  0),
	
	UP_NORTHWEST_FOUR(-4, 4, -4), UP_NORTHWEST_MINUS_ONE_FOUR(-4, 4, -4),
	UP_NORTHEAST_FOUR( 4, 4, -4), UP_NORTHEAST_MINUS_ONE_FOUR( 4, 4, -4),
	UP_SOUTHWEST_FOUR(-4, 4,  4), UP_SOUTHWEST_MINUS_ONE_FOUR(-4, 4,  4),
	UP_SOUTHEAST_FOUR( 4, 4,  4), UP_SOUTHEAST_MINUS_ONE_FOUR( 4, 4,  4),
	
	UNKNOWN(0, 0, 0),
	UNSAFE(0, 0, 0);

	public static final EnumSafeTeleport[] VALID_DIRECTIONS = {
			ZERO,
			
			// ONE
			NORTH, NORTH_MINUS_ONE, SOUTH, SOUTH_MINUS_ONE, WEST, WEST_MINUS_ONE, EAST, EAST_MINUS_ONE,
			NORTHWEST, NORTHWEST_MINUS_ONE, NORTHEAST, NORTHEAST_MINUS_ONE, SOUTHWEST, SOUTHWEST_MINUS_ONE, SOUTHEAST, SOUTHEAST_MINUS_ONE,
			
			// UP ONE
			UP,
			UP_NORTH, UP_NORTH_MINUS_ONE, UP_SOUTH, UP_SOUTH_MINUS_ONE, UP_WEST, UP_WEST_MINUS_ONE, UP_EAST, UP_EAST_MINUS_ONE,
			UP_NORTHWEST, UP_NORTHWEST_MINUS_ONE, UP_NORTHEAST, UP_NORTHEAST_MINUS_ONE, UP_SOUTHWEST, UP_SOUTHWEST_MINUS_ONE, UP_SOUTHEAST, UP_SOUTHEAST_MINUS_ONE,
			
			// TWO
			NORTH_TWO, NORTH_MINUS_ONE_TWO, SOUTH_TWO, SOUTH_MINUS_ONE_TWO, WEST_TWO, WEST_MINUS_ONE_TWO, EAST_TWO, EAST_MINUS_ONE_TWO,
			NORTHWEST_TWO, NORTHWEST_MINUS_ONE_TWO, NORTHEAST_TWO, NORTHEAST_MINUS_ONE_TWO, SOUTHWEST_TWO, SOUTHWEST_MINUS_ONE_TWO, SOUTHEAST_TWO, SOUTHEAST_MINUS_ONE_TWO,

			// UP TWO
			UP_TWO,
			UP_NORTH_TWO, UP_NORTH_MINUS_ONE_TWO, UP_SOUTH_TWO, UP_SOUTH_MINUS_ONE_TWO, UP_WEST_TWO, UP_WEST_MINUS_ONE_TWO, UP_EAST_TWO, UP_EAST_MINUS_ONE_TWO,
			UP_NORTHWEST_TWO, UP_NORTHWEST_MINUS_ONE_TWO, UP_NORTHEAST_TWO, UP_NORTHEAST_MINUS_ONE_TWO, UP_SOUTHWEST_TWO, UP_SOUTHWEST_MINUS_ONE_TWO, UP_SOUTHEAST_TWO, UP_SOUTHEAST_MINUS_ONE_TWO,
			
			// THREE
			NORTH_THREE, NORTH_MINUS_ONE_THREE, SOUTH_THREE, SOUTH_MINUS_ONE_THREE, WEST_THREE, WEST_MINUS_ONE_THREE, EAST_THREE, EAST_MINUS_ONE_THREE,
			NORTHWEST_THREE, NORTHWEST_MINUS_ONE_THREE, NORTHEAST_THREE, NORTHEAST_MINUS_ONE_THREE, SOUTHWEST_THREE, SOUTHWEST_MINUS_ONE_THREE, SOUTHEAST_THREE, SOUTHEAST_MINUS_ONE_THREE,

			// UP THREE
			UP_THREE,
			UP_NORTH_THREE, UP_NORTH_MINUS_ONE_THREE, UP_SOUTH_THREE, UP_SOUTH_MINUS_ONE_THREE, UP_WEST_THREE, UP_WEST_MINUS_ONE_THREE, UP_EAST_THREE, UP_EAST_MINUS_ONE_THREE,
			UP_NORTHWEST_THREE, UP_NORTHWEST_MINUS_ONE_THREE, UP_NORTHEAST_THREE, UP_NORTHEAST_MINUS_ONE_THREE, UP_SOUTHWEST_THREE, UP_SOUTHWEST_MINUS_ONE_THREE, UP_SOUTHEAST_THREE, UP_SOUTHEAST_MINUS_ONE_THREE,
			
			// FOUR
			NORTH_FOUR, NORTH_MINUS_ONE_FOUR, SOUTH_FOUR, SOUTH_MINUS_ONE_FOUR, WEST_FOUR, WEST_MINUS_ONE_FOUR, EAST_FOUR, EAST_MINUS_ONE_FOUR,
			NORTHWEST_FOUR, NORTHWEST_MINUS_ONE_FOUR, NORTHEAST_FOUR, NORTHEAST_MINUS_ONE_FOUR, SOUTHWEST_FOUR, SOUTHWEST_MINUS_ONE_FOUR, SOUTHEAST_FOUR, SOUTHEAST_MINUS_ONE_FOUR,

			// UP FOUR
			UP_FOUR,
			UP_NORTH_FOUR, UP_NORTH_MINUS_ONE_FOUR,	UP_SOUTH_FOUR, UP_SOUTH_MINUS_ONE_FOUR,	UP_WEST_FOUR, UP_WEST_MINUS_ONE_FOUR, UP_EAST_FOUR, UP_EAST_MINUS_ONE_FOUR,
			UP_NORTHWEST_FOUR, UP_NORTHWEST_MINUS_ONE_FOUR, UP_NORTHEAST_FOUR, UP_NORTHEAST_MINUS_ONE_FOUR,	UP_SOUTHWEST_FOUR, UP_SOUTHWEST_MINUS_ONE_FOUR,	UP_SOUTHEAST_FOUR, UP_SOUTHEAST_MINUS_ONE_FOUR
	};

	public final int offsetX;
	public final int offsetY;
	public final int offsetZ;

	private EnumSafeTeleport(int x, int y, int z) {
		this.offsetX = x;
		this.offsetY = y;
		this.offsetZ = z;
	}

	private static boolean isAirOrLiquid(Level levelIn, BlockPos posIn) {
		return isAir(levelIn, posIn) || isLiquid(levelIn, posIn) || isNotFullCollision(levelIn, posIn);
	}
	
	private static boolean isAir(Level levelIn, BlockPos posIn) {
		return levelIn.getBlockState(posIn).isAir();
	}
	
	private static boolean isLiquid(Level levelIn, BlockPos posIn) {
		return levelIn.getBlockState(posIn).liquid();
	}
	
	private static boolean isNotFullCollision(Level levelIn, BlockPos posIn) {
		return !levelIn.getBlockState(posIn).isCollisionShapeFullBlock(levelIn, posIn);
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
		return new BlockPos(this.offsetX, this.offsetY, this.offsetZ);
	}
}
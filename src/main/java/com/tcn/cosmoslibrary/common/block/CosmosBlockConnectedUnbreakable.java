package com.tcn.cosmoslibrary.common.block;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class CosmosBlockConnectedUnbreakable extends CosmosBlockUnbreakable {
	
	public static final BooleanProperty DOWN = BooleanProperty.create("down");
	public static final BooleanProperty UP = BooleanProperty.create("up");
	public static final BooleanProperty NORTH = BooleanProperty.create("north");
	public static final BooleanProperty SOUTH = BooleanProperty.create("south");
	public static final BooleanProperty WEST = BooleanProperty.create("west");
	public static final BooleanProperty EAST = BooleanProperty.create("east");
	
	public CosmosBlockConnectedUnbreakable(Block.Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
				 .setValue(DOWN, Boolean.FALSE)
                 .setValue(EAST, Boolean.FALSE)
                 .setValue(NORTH, Boolean.FALSE)
                 .setValue(SOUTH, Boolean.FALSE)
                 .setValue(UP, Boolean.FALSE)
                 .setValue(WEST, Boolean.FALSE));
		
	}
	
	private boolean canSideConnect(LevelAccessor world, BlockPos pos, Direction facing) {
		final BlockState orig = world.getBlockState(pos);
		final BlockState conn = world.getBlockState(pos.offset(facing.getNormal()));
		
		return orig != null && conn != null && this.canConnect(orig, conn);
	}
	
	protected boolean canConnect(@Nonnull BlockState orig, @Nonnull BlockState conn) {
		return orig.getBlock() == conn.getBlock();
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos pos, BlockPos facingPos) {
		super.updateShape(stateIn, facing, facingState, worldIn, pos, facingPos);
		
		return stateIn.setValue(DOWN,  this.canSideConnect(worldIn, pos, Direction.DOWN))
						.setValue(EAST, this.canSideConnect(worldIn, pos, Direction.EAST))
						.setValue(NORTH, this.canSideConnect(worldIn, pos, Direction.NORTH))
						.setValue(SOUTH, this.canSideConnect(worldIn, pos, Direction.SOUTH))
						.setValue(UP, this.canSideConnect(worldIn, pos, Direction.UP))
						.setValue(WEST, this.canSideConnect(worldIn, pos, Direction.WEST));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(DOWN, EAST, NORTH, SOUTH, UP, WEST);
	}
}
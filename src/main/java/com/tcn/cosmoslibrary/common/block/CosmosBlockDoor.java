package com.tcn.cosmoslibrary.common.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class CosmosBlockDoor extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
	public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	
	protected static final float AABB_DOOR_THICKNESS = 3.0F;
	protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
	protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
	private final BlockSetType type;

	public CosmosBlockDoor(BlockBehaviour.Properties propertiesIn, BlockSetType typeIn) {
		super(propertiesIn);
		this.type = typeIn;
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
				.setValue(OPEN, Boolean.valueOf(false)).setValue(HINGE, DoorHingeSide.LEFT)
				.setValue(POWERED, Boolean.valueOf(false)).setValue(HALF, DoubleBlockHalf.LOWER));
	}

	public BlockSetType type() {
		return this.type;
	}

	@Override
	public VoxelShape getShape(BlockState stateIn, BlockGetter levelIn, BlockPos posIn, CollisionContext contextIn) {
		Direction direction = stateIn.getValue(FACING);
		boolean flag = !stateIn.getValue(OPEN);
		boolean flag1 = stateIn.getValue(HINGE) == DoorHingeSide.RIGHT;
		switch (direction) {
		case EAST:
		default:
			return flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
		case SOUTH:
			return flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
		case WEST:
			return flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
		case NORTH:
			return flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
		}
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction directionIn, BlockState newStateIn, LevelAccessor levelIn, BlockPos posIn, BlockPos secPosIn) {
		DoubleBlockHalf doubleblockhalf = stateIn.getValue(HALF);
		
		if (directionIn.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (directionIn == Direction.UP)) {
			return newStateIn.is(this) && newStateIn.getValue(HALF) != doubleblockhalf ? stateIn.setValue(FACING, newStateIn.getValue(FACING)).setValue(OPEN, newStateIn.getValue(OPEN))
					.setValue(HINGE, newStateIn.getValue(HINGE)).setValue(POWERED, newStateIn.getValue(POWERED)) : Blocks.AIR.defaultBlockState();
		} else {
			return doubleblockhalf == DoubleBlockHalf.LOWER && directionIn == Direction.DOWN && !stateIn.canSurvive(levelIn, posIn) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, directionIn, newStateIn, levelIn, posIn, secPosIn);
		}
	}

	@Override
	public BlockState playerWillDestroy(Level levelIn, BlockPos posIn, BlockState stateIn, Player playerIn) {
		if (!levelIn.isClientSide && playerIn.isCreative()) {
			//DoublePlantBlock.preventCreativeDropFromBottomPart(levelIn, posIn, stateIn, playerIn);
		}
		return super.playerWillDestroy(levelIn, posIn, stateIn, playerIn);
	}

	@Override
	public boolean isPathfindable(BlockState stateIn, PathComputationType pathCompIn) {
		switch (pathCompIn) {
		case LAND:
			return stateIn.getValue(OPEN);
		case WATER:
			return false;
		case AIR:
			return stateIn.getValue(OPEN);
		default:
			return false;
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext contextIn) {
		BlockPos blockpos = contextIn.getClickedPos();
		Level level = contextIn.getLevel();
		if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(contextIn)) {
			boolean flag = level.hasNeighborSignal(blockpos) || level.hasNeighborSignal(blockpos.above());
			return this.defaultBlockState().setValue(FACING, contextIn.getHorizontalDirection()).setValue(HINGE, this.getHinge(contextIn)).setValue(POWERED, Boolean.valueOf(flag)).setValue(OPEN, Boolean.valueOf(flag)).setValue(HALF, DoubleBlockHalf.LOWER);
		} else {
			return null;
		}
	}

	@Override
	public void setPlacedBy(Level levelIn, BlockPos posIn, BlockState stateIn, LivingEntity entityIn, ItemStack stackIn) {
		levelIn.setBlock(posIn.above(), stateIn.setValue(HALF, DoubleBlockHalf.UPPER), 3);
	}

	private DoorHingeSide getHinge(BlockPlaceContext contextIn) {
		BlockGetter blockgetter = contextIn.getLevel();
		BlockPos blockpos = contextIn.getClickedPos();
		Direction direction = contextIn.getHorizontalDirection();
		
		BlockPos blockpos1 = blockpos.above();
		Direction direction1 = direction.getCounterClockWise();
		BlockPos blockpos2 = blockpos.relative(direction1);
		BlockState blockstate = blockgetter.getBlockState(blockpos2);
		BlockPos blockpos3 = blockpos1.relative(direction1);
		BlockState blockstate1 = blockgetter.getBlockState(blockpos3);
		Direction direction2 = direction.getClockWise();
		BlockPos blockpos4 = blockpos.relative(direction2);
		BlockState blockstate2 = blockgetter.getBlockState(blockpos4);
		BlockPos blockpos5 = blockpos1.relative(direction2);
		BlockState blockstate3 = blockgetter.getBlockState(blockpos5);
		
		int i = (blockstate.isCollisionShapeFullBlock(blockgetter, blockpos2) ? -1 : 0) + (blockstate1.isCollisionShapeFullBlock(blockgetter, blockpos3) ? -1 : 0)
				+ (blockstate2.isCollisionShapeFullBlock(blockgetter, blockpos4) ? 1 : 0) + (blockstate3.isCollisionShapeFullBlock(blockgetter, blockpos5) ? 1 : 0);
		
		boolean flag = blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
		boolean flag1 = blockstate2.is(this) && blockstate2.getValue(HALF) == DoubleBlockHalf.LOWER;
		if ((!flag || flag1) && i <= 0) {
			if ((!flag1 || flag) && i >= 0) {
				int j = direction.getStepX();
				int k = direction.getStepZ();
				Vec3 vec3 = contextIn.getClickLocation();
				double d0 = vec3.x - (double) blockpos.getX();
				double d1 = vec3.z - (double) blockpos.getZ();
				return (j >= 0 || !(d1 < 0.5D)) && (j <= 0 || !(d1 > 0.5D)) && (k >= 0 || !(d0 > 0.5D)) && (k <= 0 || !(d0 < 0.5D)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
			} else {
				return DoorHingeSide.LEFT;
			}
		} else {
			return DoorHingeSide.RIGHT;
		}
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stackIn, BlockState stateIn, Level levelIn, BlockPos posIn, Player playerIn, InteractionHand handIn, BlockHitResult resultIn) {
		 if (!this.type.canOpenByHand()) {
	         return ItemInteractionResult.SUCCESS;
	      } else {
	         stateIn = stateIn.cycle(OPEN);
	         levelIn.setBlock(posIn, stateIn, 10);
	         this.playSound(playerIn, levelIn, posIn, stateIn.getValue(OPEN));
	         levelIn.gameEvent(playerIn, this.isOpen(stateIn) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, posIn);
	         
	         return ItemInteractionResult.sidedSuccess(levelIn.isClientSide);
	      }
	}

	public boolean isOpen(BlockState stateIn) {
		return stateIn.getValue(OPEN);
	}

	public void setOpen(@Nullable Entity entityIn, Level levelIn, BlockState stateIn, BlockPos posIn, boolean valueIn) {
		if (stateIn.is(this) && stateIn.getValue(OPEN) != valueIn) {
			levelIn.setBlock(posIn, stateIn.setValue(OPEN, Boolean.valueOf(valueIn)), 10);
			this.playSound(entityIn, levelIn, posIn, valueIn);
			levelIn.gameEvent(entityIn, valueIn ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, posIn);
		}
	}

	@Override
	public void neighborChanged(BlockState stateIn, Level levelIn, BlockPos posIn, Block blockIn, BlockPos otherPosIn, boolean valueIn) {
		boolean flag = levelIn.hasNeighborSignal(posIn) || levelIn.hasNeighborSignal(posIn.relative(stateIn.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
		
		if (!this.defaultBlockState().is(blockIn) && flag != stateIn.getValue(POWERED)) {
			if (flag != stateIn.getValue(OPEN)) {
				this.playSound((Entity)null, levelIn, posIn, flag);
				levelIn.gameEvent((Entity)null, flag ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, posIn);
			}

			levelIn.setBlock(posIn,stateIn.setValue(POWERED, Boolean.valueOf(flag)).setValue(OPEN, Boolean.valueOf(flag)), 2);
		}
	}

	@Override
	public boolean canSurvive(BlockState stateIn, LevelReader levelIn, BlockPos posIn) {
		BlockPos blockpos = posIn.below();
		BlockState blockstate = levelIn.getBlockState(blockpos);
		return stateIn.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(levelIn, blockpos, Direction.UP) : blockstate.is(this);
	}

	private void playSound(@Nullable Entity entityIn, Level levelIn, BlockPos posIn, boolean open) {
		levelIn.playSound(entityIn, posIn, open ? this.type.doorOpen() : this.type.doorClose(),SoundSource.BLOCKS, 1.0F, levelIn.getRandom().nextFloat() * 0.1F + 0.9F);
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState stateIn) {
		return PushReaction.IGNORE;
	}

	@Override
	public BlockState rotate(BlockState stateIn, Rotation rotationIn) {
		return stateIn.setValue(FACING, rotationIn.rotate(stateIn.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState stateIn, Mirror mirrorIn) {
		return mirrorIn == Mirror.NONE ? stateIn : stateIn.rotate(mirrorIn.getRotation(stateIn.getValue(FACING))).cycle(HINGE);
	}

	@Override
	public long getSeed(BlockState stateIn, BlockPos posIn) {
		return Mth.getSeed(posIn.getX(), posIn.below(stateIn.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), posIn.getZ());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilderIn) {
		stateBuilderIn.add(HALF, FACING, OPEN, HINGE, POWERED);
	}

	public static boolean isWoodenDoor(Level p_52746_, BlockPos p_52747_) {
		return isWoodenDoor(p_52746_.getBlockState(p_52747_));
	}

	public static boolean isWoodenDoor(BlockState p_52818_) {
		Block block = p_52818_.getBlock();
		if (block instanceof DoorBlock doorblock) {
			if (doorblock.type().canOpenByHand()) {
				return true;
			}
		}

		return false;
	}
}
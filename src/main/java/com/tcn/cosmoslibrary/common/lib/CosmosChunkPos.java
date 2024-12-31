package com.tcn.cosmoslibrary.common.lib;

import java.util.Random;

import javax.annotation.concurrent.Immutable;

import com.google.common.collect.AbstractIterator;
import com.tcn.cosmoslibrary.common.nbt.CosmosNBTHelper.Const;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Rotation;

@Immutable
public class CosmosChunkPos extends CosmosVec2 {
	
	public static final StreamCodec<ByteBuf, CosmosChunkPos> STREAM_CODEC = new StreamCodec<ByteBuf, CosmosChunkPos>() {
        public CosmosChunkPos decode(ByteBuf buf) {
            return CosmosChunkPos.convertTo(FriendlyByteBuf.readBlockPos(buf));
        }

        public void encode(ByteBuf buf, CosmosChunkPos posIn) {
            FriendlyByteBuf.writeBlockPos(buf, CosmosChunkPos.convertFrom(posIn));
        }
    };
    
	public static final CosmosChunkPos ZERO = new CosmosChunkPos(0, 0);
	
	public CosmosChunkPos(int x, int y) {
		super(x, y);
	}

	public CosmosChunkPos(double x, double y) {
		super(x, y);
	}
	
	public CosmosChunkPos(long longIn) {
		super((int)longIn, (int)(longIn >> 32));
	}

	public static CosmosChunkPos convertTo(BlockPos posIn) {
		if (posIn != null) {
			return new CosmosChunkPos(posIn.getX(), posIn.getZ());
		} else {
			return ZERO;
		}
	}

	public static BlockPos convertFrom(CosmosChunkPos posIn) {
		if (posIn != null) {
			return new BlockPos(posIn.getX(), 0, posIn.getZ());
		} else {
			return BlockPos.ZERO;
		}
	}

	public static CosmosChunkPos scaleToChunkPos(BlockPos posIn) {
		if (posIn != null) {
			return new CosmosChunkPos(posIn.getX() >> 4, posIn.getZ() >> 4);
		} else {
			return ZERO;
		}
	}

	public static BlockPos scaleFromChunkPos(CosmosChunkPos posIn) {
		if (posIn != null) {
			return new BlockPos(posIn.getX() * 16, 0, posIn.getZ() * 16);
		} else {
			return BlockPos.ZERO;
		}
	}
	
	public CosmosChunkPos(Position position) {
		this(position.x(), position.z());
	}

	public CosmosChunkPos(CosmosVec2 source) {
		this(source.getX(), source.getZ());
	}

	public long toLong() {
		return asLong(this.x, this.z);
	}

	public static long asLong(int p_45590_, int p_45591_) {
		return (long) p_45590_ & 4294967295L | ((long) p_45591_ & 4294967295L) << 32;
	}

	public static long asLong(BlockPos p_151389_) {
		return asLong(SectionPos.blockToSectionCoord(p_151389_.getX()),
				SectionPos.blockToSectionCoord(p_151389_.getZ()));
	}

	public static long atSectionBottomY(long packedPos) {
		return packedPos & -16L;
	}
	
	public CosmosChunkPos add(double x, double z) {
		return x == 0.0D && z == 0.0D ? this : new CosmosChunkPos((double) this.getX() + x, (double) this.getZ() + z);
	}
	
	public CosmosChunkPos add(int x, int y, int z) {
		return x == 0 && y == 0 && z == 0 ? this : new CosmosChunkPos(this.getX() + x, this.getZ() + z);
	}
	
	public CosmosChunkPos add(CosmosVec2 vec) {
		return this.add(vec.getX(), vec.getZ());
	}
	
	public CosmosChunkPos subtract(CosmosVec2 vec) {
		return this.add(-vec.getX(), -vec.getZ());
	}
	
	@Override
	public CosmosChunkPos up() {
		return this.offset(Direction.UP);
	}
	
	@Override
	public CosmosChunkPos up(int n) {
		return this.offset(Direction.UP, n);
	}
	
	@Override
	public CosmosChunkPos down() {
		return this.offset(Direction.DOWN);
	}
	
	@Override
	public CosmosChunkPos down(int n) {
		return this.offset(Direction.DOWN, n);
	}
	
	public CosmosChunkPos north() {
		return this.offset(Direction.NORTH);
	}
	
	public CosmosChunkPos north(int n) {
		return this.offset(Direction.NORTH, n);
	}
	
	public CosmosChunkPos south() {
		return this.offset(Direction.SOUTH);
	}
	
	public CosmosChunkPos south(int n) {
		return this.offset(Direction.SOUTH, n);
	}
	
	public CosmosChunkPos west() {
		return this.offset(Direction.WEST);
	}
	
	public CosmosChunkPos west(int n) {
		return this.offset(Direction.WEST, n);
	}
	
	public CosmosChunkPos east() {
		return this.offset(Direction.EAST);
	}
	
	public CosmosChunkPos east(int n) {
		return this.offset(Direction.EAST, n);
	}
	
	public CosmosChunkPos offset(Direction facing) {
		return new CosmosChunkPos(this.getX() + facing.getStepX(), this.getZ() + facing.getStepZ());
	}
	
	@Override
	public CosmosChunkPos offset(Direction facing, int n) {
		return n == 0 ? this
				: new CosmosChunkPos(this.getX() + facing.getStepX() * n, this.getZ() + facing.getStepZ() * n);
	}

	public CosmosChunkPos rotate(Rotation rotationIn) {
		switch (rotationIn) {
		case NONE:
		default:
			return this;
		case CLOCKWISE_90:
			return new CosmosChunkPos(-this.getZ(), this.getX());
		case CLOCKWISE_180:
			return new CosmosChunkPos(-this.getX(), -this.getZ());
		case COUNTERCLOCKWISE_90:
			return new CosmosChunkPos(this.getZ(), -this.getX());
		}
	}
	
	public CosmosChunkPos toImmutable() {
		return this;
	}

	public CosmosChunkPos.Mutable toMutable() {
		return new CosmosChunkPos.Mutable(this.getX(), this.getZ());
	}

	public static Iterable<CosmosChunkPos> getRandomPositions(Random rand, int amount, int minX, int minZ, int maxX,
			int maxZ) {
		int i = maxX - minX + 1;
		int k = maxZ - minZ + 1;
		return () -> {
			return new AbstractIterator<CosmosChunkPos>() {
				final CosmosChunkPos.Mutable pos = new CosmosChunkPos.Mutable();
				int remainingAmount = amount;

				@Override
				protected CosmosChunkPos computeNext() {
					if (this.remainingAmount <= 0) {
						return this.endOfData();
					} else {
						CosmosChunkPos blockpos = this.pos.setPos(minX + rand.nextInt(i), minZ + rand.nextInt(k));
						--this.remainingAmount;
						return blockpos;
					}
				}
			};
		};
	}

	public static class Mutable extends CosmosChunkPos {
		public Mutable() {
			this(0, 0);
		}

		public Mutable(int x_, int z_) {
			super(x_, z_);
		}

		public Mutable(double x, double z) {
			this(Mth.floor(x), Mth.floor(z));
		}
		
		@Override
		public CosmosChunkPos add(double x, double z) {
			return super.add(x, z).toImmutable();
		}
		
		public CosmosChunkPos add(int x, int z) {
			return super.add(x, z).toImmutable();
		}
		
		@Override
		public CosmosChunkPos offset(Direction facing, int n) {
			return super.offset(facing, n).toImmutable();
		}
		
		@Override
		public CosmosChunkPos rotate(Rotation rotationIn) {
			return super.rotate(rotationIn).toImmutable();
		}
		
		public CosmosChunkPos.Mutable setPos(int xIn, int zIn) {
			this.setX(xIn);
			this.setZ(zIn);
			return this;
		}
		
		public CosmosChunkPos.Mutable setPos(double xIn, double zIn) {
			return this.setPos(Mth.floor(xIn), Mth.floor(zIn));
		}

		public CosmosChunkPos.Mutable setPos(CosmosVec2 vec) {
			return this.setPos(vec.getX(), vec.getZ());
		}
		
		public CosmosChunkPos.Mutable setAndMove(CosmosVec2 pos, Direction direction) {
			return this.setPos(pos.getX() + direction.getStepX(), pos.getZ() + direction.getStepZ());
		}

		public CosmosChunkPos.Mutable setAndOffset(CosmosVec2 pos, int offsetX, int offsetY, int offsetZ) {
			return this.setPos(pos.getX() + offsetX, pos.getZ() + offsetZ);
		}

		public CosmosChunkPos.Mutable move(Direction facing) {
			return this.move(facing, 1);
		}

		public CosmosChunkPos.Mutable move(Direction facing, int n) {
			return this.setPos(this.getX() + facing.getStepX() * n, this.getZ() + facing.getStepZ() * n);
		}

		public CosmosChunkPos.Mutable move(int xIn, int yIn, int zIn) {
			return this.setPos(this.getX() + xIn, this.getZ() + zIn);
		}

		public CosmosChunkPos.Mutable func_243531_h(CosmosVec2 p_243531_1_) {
			return this.setPos(this.getX() + p_243531_1_.getX(), this.getZ() + p_243531_1_.getZ());
		}

		public CosmosChunkPos.Mutable clampAxisCoordinate(Direction.Axis axis, int min, int max) {
			switch (axis) {
			case X:
				return this.setPos(Mth.clamp(this.getX(), min, max), this.getZ());
			case Z:
				return this.setPos(this.getX(), Mth.clamp(this.getZ(), min, max));
			default:
				throw new IllegalStateException("Unable to clamp axis " + axis);
			}
		}

		@Override
		public void setX(int xIn) {
			super.setX(xIn);
		}

		@Override
		public void setZ(int zIn) {
			super.setZ(zIn);
		}

		@Override
		public CosmosChunkPos toImmutable() {
			return new CosmosChunkPos(this);
		}
	}
	
	public void saveRaw(CompoundTag tag) {
		tag.putInt(Const.NBT_POS_X_KEY, this.getX());
		tag.putInt(Const.NBT_POS_Z_KEY, this.getZ());
	}

	public static CosmosChunkPos loadRaw(CompoundTag tag) {
		int x = tag.getInt(Const.NBT_POS_X_KEY);
		int z = tag.getInt(Const.NBT_POS_Z_KEY);
		
		return new CosmosChunkPos(x, z);
	}
	public void save(CompoundTag tag) {
		CompoundTag info = new CompoundTag();
		
		info.putInt(Const.NBT_POS_X_KEY, this.getX());
		info.putInt(Const.NBT_POS_Z_KEY, this.getZ());
		
		tag.put("chunk_pos", info);
	}
	
	public static CosmosChunkPos load(CompoundTag tag) {
		if (tag.contains("chunk_pos")) {
			CompoundTag loaded = tag.getCompound("chunk_pos");
			
			int x = loaded.getInt(Const.NBT_POS_X_KEY);
			int z = loaded.getInt(Const.NBT_POS_Z_KEY);
			
			return new CosmosChunkPos(x, z);
		}
		return null;
	}
	
	public CosmosChunkPos copy() {
		return new CosmosChunkPos(this.getX(), this.getZ());
	}
}
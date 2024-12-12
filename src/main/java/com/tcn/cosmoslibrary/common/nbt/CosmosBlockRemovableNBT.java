package com.tcn.cosmoslibrary.common.nbt;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.common.block.CosmosBlock;
import com.tcn.cosmoslibrary.common.lib.CompatHelper;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Class used to implement shift-right click removal with a wrench. 
 * NBT supported for {@link IInventory} and {@link IEnergyHandler}
 * @author TheRealZeher
 *
 */
public class CosmosBlockRemovableNBT extends CosmosBlock {
	
	public CosmosBlockRemovableNBT(Block.Properties builder) {
		super(builder);		
	}
	
	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level levelIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		levelIn.sendBlockUpdated(pos, state, state, 3);
		
		if (CosmosUtil.holdingWrench(player) && player.isShiftKeyDown()) {
			if (!levelIn.isClientSide) {
				CompatHelper.generateStack(levelIn, pos, levelIn.registryAccess());
			}
			
			return ItemInteractionResult.SUCCESS;
		}	
		return ItemInteractionResult.FAIL;
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> typeA, BlockEntityType<E> typeE, BlockEntityTicker<? super E> ticker) {
		return typeE == typeA ? (BlockEntityTicker<A>) ticker : null;
	}
}
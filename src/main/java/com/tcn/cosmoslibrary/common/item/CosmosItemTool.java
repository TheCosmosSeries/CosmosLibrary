package com.tcn.cosmoslibrary.common.item;

import com.tcn.cosmoslibrary.common.interfaces.item.ICosmosToolAdvanced;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class CosmosItemTool extends CosmosItem implements ICosmosToolAdvanced {
	
	public CosmosItemTool(Item.Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public boolean isActive(ItemStack paramstack) {
		return true;
	}
	
	@Override
	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
		return false;
	}
	
	@Override
	public boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player) {
        return true;
    }
	
	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		return InteractionResult.PASS;
	}

}
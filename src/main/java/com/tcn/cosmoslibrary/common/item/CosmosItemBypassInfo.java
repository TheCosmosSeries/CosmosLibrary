package com.tcn.cosmoslibrary.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class CosmosItemBypassInfo extends CosmosItemInfo {
	
	public CosmosItemBypassInfo(Item.Properties properties, String info, String shift_desc_one, String shift_desc_two) {
		super(properties, info, shift_desc_one, shift_desc_two);
	}
	
	public CosmosItemBypassInfo(Item.Properties properties, String info, String shift_desc_one, String shift_desc_two, int max_stack) {
		super(properties.stacksTo(max_stack), info, shift_desc_one, shift_desc_two);
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
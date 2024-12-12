package com.tcn.cosmoslibrary.common.block;

import java.util.List;

import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Basic ItemBlock for giving a Block a description.
 */
public class CosmosItemBlock extends BlockItem {
	
	public String info;
	public String shift_desc_one;
	public String shift_desc_two;

	public CosmosItemBlock(Block block, Item.Properties properties, String info, String shift_desc_one, String shift_desc_two) {
		super(block, properties);
		
		this.info = info;
		this.shift_desc_one = shift_desc_one;
		this.shift_desc_two = shift_desc_two;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, context, tooltip, flagIn);
		
		if (!shift_desc_one.isEmpty() && !shift_desc_two.isEmpty()) {
			if (!ComponentHelper.isShiftKeyDown(Minecraft.getInstance())) {
				tooltip.add(ComponentHelper.getTooltipInfo(this.info));
				
				if (ComponentHelper.displayShiftForDetail) {
					tooltip.add(ComponentHelper.shiftForMoreDetails());
				}
			} else {
				tooltip.add(ComponentHelper.getTooltipOne(shift_desc_one));
				tooltip.add(ComponentHelper.getTooltipTwo(shift_desc_two));
				
				tooltip.add(ComponentHelper.shiftForLessDetails());
			}
		} else {
			tooltip.add(ComponentHelper.getTooltipInfo(this.info));
		}
	}
}
package com.tcn.cosmoslibrary.common.nbt;

import java.util.List;

import com.tcn.cosmoslibrary.common.enums.EnumSideState;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * ItemBlock class used to apply information to a block.
 * Can display NBT information when a block has such information.
 * Otherwise it just displays the given [info], [shift_desc_one] & [shift_desc_two] information on the tool-tip.
 */
public class CosmosBlockItemNBT extends BlockItem {
	
	public String info;
	public String shift_desc_one;
	public String shift_desc_two;
	public String shift_desc_three;
	public String limitation;

	public CosmosBlockItemNBT(Block block, Item.Properties builder, String info, String shift_desc_one) {
		super(block, builder);
		
		this.info = info;
		this.shift_desc_one = shift_desc_one;
	}
	
	public CosmosBlockItemNBT(Block block, Item.Properties builder, String info, String shift_desc_one, String shift_desc_two) {
		super(block, builder);
		
		this.info = info;
		this.shift_desc_one = shift_desc_one;
		this.shift_desc_two = shift_desc_two;
	}
	
	public CosmosBlockItemNBT(Block block, Item.Properties builder, String info, String shift_desc_one, String shift_desc_two, String limitation) {
		super(block, builder);
		
		this.info = info;
		this.shift_desc_one = shift_desc_one;
		this.shift_desc_two = shift_desc_two;
		this.limitation = limitation;
	}
	
	public CosmosBlockItemNBT(Block block, Item.Properties builder, String info, String shift_desc_one, String shift_desc_two, String shift_desc_three, String limitation) {
		super(block, builder);
		
		this.info = info;
		this.shift_desc_one = shift_desc_one;
		this.shift_desc_two = shift_desc_two;
		this.shift_desc_three = shift_desc_three;
		this.limitation = limitation;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, context, tooltip, flagIn);
		
		if (!ComponentHelper.isShiftKeyDown(Minecraft.getInstance())) {
			tooltip.add(ComponentHelper.getTooltipInfo(this.info));
			
			if (ComponentHelper.displayShiftForDetail) {
				tooltip.add(ComponentHelper.shiftForMoreDetails());
			}
		} else {
			tooltip.add(ComponentHelper.getTooltipOne(shift_desc_one));
			
			if (this.shift_desc_two != null) {
				tooltip.add(ComponentHelper.getTooltipTwo(shift_desc_two));
			}
			
			if (this.shift_desc_three != null) {
				tooltip.add(ComponentHelper.getTooltipThree(shift_desc_three));
			}
			
			if (this.limitation != null) {
				tooltip.add(ComponentHelper.getTooltipLimit(limitation));
			}
			tooltip.add(ComponentHelper.shiftForLessDetails());
		}
		
		if (stack.has(DataComponents.CUSTOM_DATA)) {
			if (!ComponentHelper.isControlKeyDown(Minecraft.getInstance())) {
				tooltip.add(ComponentHelper.ctrlForMoreDetails());
			} else {
				if (stack.has(DataComponents.CUSTOM_DATA)) {
					CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).getUnsafe();
					if (tag.contains("nbt_data")) {
						
						CompoundTag compound_tag = tag.getCompound("nbt_data");
						
						//tooltip.add(new TextComponent(TextHelper.GRAY + "~ [ Data Tag: { nbt_data } ]:"));
						
						//if(compound_tag.hasKey("energy")) {
							int energy = compound_tag.getInt("energy");
							
							tooltip.add(Component.literal(Value.GRAY + "Energy Stored: " + Value.LIGHT_GRAY + "["+ energy + "] " + Value.PURPLE + "RF"));
							
						//}
						
						if (compound_tag.contains("Items")) {
							int size = compound_tag.getInt("size");
							
							NonNullList<ItemStack> list_ = NonNullList.<ItemStack>withSize(size, ItemStack.EMPTY);
							ContainerHelper.loadAllItems(tag.getCompound("nbt_data"), list_, context.registries());
							
							tooltip.add(Component.literal(Value.GRAY + "   > [ Items: { Items } ]: "));
							
							if (list_.size() > 6) {
								for (int j = 0; j < 6; j++) {
									if (list_.get(j)!= null){
										if (list_.get(j).getItem() != Item.byBlock(Blocks.AIR)) {
											tooltip.add(Component.literal(Value.GRAY + "     - ( " + "Slot " + j + ": " + list_.get(j).getCount() + "x " + I18n.get(list_.get(j).getItem().getName(list_.get(j)).toString()) + " )"));
										}
									}
								}
								tooltip.add(Component.literal(Value.GRAY + "     - ( & " + (list_.size() - 5) + " stack(s) more... )"));
							} else {
								for (int j = 0; j < list_.size(); j++) {
									if (list_.get(j)!= null){
										if (list_.get(j).getItem() != Item.byBlock(Blocks.AIR)) {
											tooltip.add(Component.literal(Value.GRAY + "     - ( " + "Slot " + j + ": " + list_.get(j).getCount() + "x " + I18n.get(list_.get(j).getItem().getName(list_.get(j)).toString()) + " )"));
										}
									}
								}
							}
						}
						
						if (compound_tag.contains("sides")) {
							CompoundTag compound_tag_sides = compound_tag.getCompound("sides");
							
							String[] strings = new String[] {"", "", "", "", "", ""};
							int[] text_colours = new int[] {0, 0, 0, 0, 0, 0};
							
							for (Direction c : Direction.values()) {
								int index = compound_tag_sides.getInt("index_" + c.get3DDataValue());
								strings[c.get3DDataValue()] = EnumSideState.getStateFromIndex(index).getName();
								text_colours[c.get3DDataValue()] = EnumSideState.getStateFromIndex(index).getTextColour().dec();
							}
							
							tooltip.add(Component.literal(Value.LIGHT_BLUE + "   > " + Value.LIGHT_GRAY + "[ " + Value.CYAN + "ISidedTile " + Value.GRAY + "(sides)" + Value.LIGHT_GRAY + " ]"));
							
							tooltip.add(Component.literal(Value.RED + "     - " + Value.LIGHT_GRAY + "[U] " + Value.GRAY + "= " + text_colours[1] + strings[1]));
							tooltip.add(Component.literal(Value.RED + "     - " + Value.LIGHT_GRAY + "[D] " + Value.GRAY + "= " + text_colours[0] + strings[0]));
							tooltip.add(Component.literal(Value.RED + "     - " + Value.LIGHT_GRAY + "[N] " + Value.GRAY + "= " + text_colours[2] + strings[2]));
							tooltip.add(Component.literal(Value.RED + "     - " + Value.LIGHT_GRAY + "[S] " + Value.GRAY + "= " + text_colours[3] + strings[3]));
							tooltip.add(Component.literal(Value.RED + "     - " + Value.LIGHT_GRAY + "[E] " + Value.GRAY + "= " + text_colours[5] + strings[5]));
							tooltip.add(Component.literal(Value.RED + "     - " + Value.LIGHT_GRAY + "[W] " + Value.GRAY + "= " + text_colours[4] + strings[4]));
							
						}
					}
					tooltip.add(ComponentHelper.ctrlForLessDetails());
				}
			}
		}
	}
}
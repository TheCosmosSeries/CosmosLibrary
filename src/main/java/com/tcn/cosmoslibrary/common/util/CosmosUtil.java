package com.tcn.cosmoslibrary.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Nullable;

import com.ibm.icu.text.DecimalFormat;
import com.tcn.cosmoslibrary.common.interfaces.item.ICosmosTool;
import com.tcn.cosmoslibrary.common.item.CosmosArmourItemColourable;
import com.tcn.cosmoslibrary.common.item.CosmosArmourItemElytra;
import com.tcn.cosmoslibrary.common.item.CosmosItemTool;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CosmosUtil {
	
	public static final void syncBlockAndRerender(Level world, BlockPos pos) {
		if (world == null || pos == null)
			return;

		BlockState state = world.getBlockState(pos);

		world.markAndNotifyBlock(pos, null, state, state, 2, 0);
	}
	
	public static boolean holdingWrench(Player playerIn) {
		if (playerIn.getInventory().getSelected().isEmpty()) {
			return false;
		}
		
		Item currentItem = playerIn.getInventory().getSelected().getItem();
		
		if (currentItem instanceof CosmosItemTool) {
			return ((CosmosItemTool) currentItem).isActive(playerIn.getInventory().getSelected());
		} else if (currentItem instanceof ICosmosTool) {
			return true;
		}

		return false;
	}
	
	public static void setToAir(Level worldIn, BlockPos pos) {
		worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	}
	
	public static boolean handEmpty(Player playerIn) {
		return playerIn.getInventory().getSelected().isEmpty();
	}

	public static boolean handItem(Player playerIn, Item item) {
		if (playerIn.getInventory().getSelected().isEmpty()) {
			return false;
		}
		
		Item currentItem = playerIn.getInventory().getSelected().getItem();
		if (currentItem.equals(item)) {
			return true;
		}
		return false;
	}
	
	public static ItemStack getStack(Player playerIn) {
		return playerIn.getInventory().getSelected();
	}
	
	public static ItemStack getStack(Player playerIn, InteractionHand hand) {
		return playerIn.getItemInHand(hand);
	}
	
	public static Item getStackItem(Player playerIn) {
		return playerIn.getInventory().getSelected().getItem();
	}
	
	public static void addItem(Level worldIn, Player playerIn, Item item, int count) {
		if(!playerIn.getInventory().add(new ItemStack(item, count))) {
			BlockPos pos = playerIn.blockPosition();
			worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(item, count)));
		}
	}
	
	public static void addStack(Level worldIn, Player playerIn, ItemStack stackIn) {
		if (!playerIn.getInventory().add(stackIn)) {
			BlockPos pos = playerIn.blockPosition();
			worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stackIn));
		}
	}
	
	public static ItemStack setArmourColourInformation(ItemStack stack, @Nullable ComponentColour mainColour, @Nullable ComponentColour wingColour) {
		Item stackItem = stack.getItem();
		
		if (stack.has(DataComponents.CUSTOM_DATA)) {
			CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
			CompoundTag stackTag = data.copyTag();
			
			if (stackTag.contains("nbt_data")) {
				CompoundTag nbtData = stackTag.getCompound("nbt_data");
				
				if (stackItem instanceof CosmosArmourItemColourable) {
					if (mainColour != null) {
						int colour = mainColour.dec();
						
						if (colour > 0) {
							nbtData.putInt("colour", colour);
							stackTag.put("nbt_data", nbtData);
							stack.set(DataComponents.CUSTOM_DATA, CustomData.of(stackTag));
						}
					}
				}
				
				if (stackItem instanceof CosmosArmourItemElytra) {
					if (wingColour != null) {
						int wing_colour = wingColour.dec();
						
						if (wing_colour >= 0) {
							nbtData.putInt("wing_colour", wing_colour);
							stackTag.put("nbt_data", nbtData);
							stack.set(DataComponents.CUSTOM_DATA, CustomData.of(stackTag));
						}
					}
				}
			} else {
				CompoundTag nbtData = new CompoundTag();

				if (stackItem instanceof CosmosArmourItemColourable) {
					if (mainColour != null) {
						int colour = mainColour.dec();
						
						if (colour > 0) {
							nbtData.putInt("colour", colour);
							stackTag.put("nbt_data", nbtData);
							stack.set(DataComponents.CUSTOM_DATA, CustomData.of(stackTag));
						}
					}
				}
				
				if (stackItem instanceof CosmosArmourItemElytra) {
					if (wingColour != null) {
						int wing_colour = wingColour.dec();
						
						if (wing_colour >= 0) {
							nbtData.putInt("wing_colour", wing_colour);
							stackTag.put("nbt_data", nbtData);
							stack.set(DataComponents.CUSTOM_DATA, CustomData.of(stackTag));
						}
					}
				}
				
				stackTag.put("nbt_data", nbtData);
				stack.set(DataComponents.CUSTOM_DATA, CustomData.of(stackTag));
			}
		} else {
			CompoundTag stackTag = new CompoundTag();
			CompoundTag nbtData = new CompoundTag();

			if (stackItem instanceof CosmosArmourItemColourable) {
				if (mainColour != null) {
					int colour = mainColour.dec();
					
					if (colour > 0) {
						nbtData.putInt("colour", colour);
						stackTag.put("nbt_data", nbtData);
						stack.set(DataComponents.CUSTOM_DATA, CustomData.of(stackTag));
					}
				}
			}
			
			if (stackItem instanceof CosmosArmourItemElytra) {
				if (wingColour != null) {
					int wing_colour = wingColour.getIndex();
					
					if (wing_colour > 0) {
						nbtData.putInt("wing_colour", wing_colour);
						stackTag.put("nbt_data", nbtData);
						stack.set(DataComponents.CUSTOM_DATA, CustomData.of(stackTag));
					}
				}
			}
			
			stackTag.put("nbt_data", nbtData);
			stack.set(DataComponents.CUSTOM_DATA, CustomData.of(stackTag));
		}
		
		return stack;
	}
	
	public static ComponentColour getColourFromStack(ItemStack stackIn, @Nullable ComponentColour defaultColour) {
		DyeColor dyeColour = DyeColor.getColor(stackIn);
		
		if (defaultColour != null) {
			return dyeColour != null ? ComponentColour.fromIndex(dyeColour.getId()) : defaultColour;
		}
		
		return dyeColour != null ? ComponentColour.fromIndex(dyeColour.getId()) : ComponentColour.WHITE;
	}
	
	public static String formatIntegerBillion(int toFormat) {
		DecimalFormat formatter = new DecimalFormat("#,###,###,###");
		
		return formatter.format(toFormat);
	}
	
	public static String formatIntegerMillion(int toFormat) {
		DecimalFormat formatter = new DecimalFormat("#,###,###");
		
		return formatter.format(toFormat);
	}

	public static String getTimeHMS() {
		return getTimeHMS(":");
	}

	public static String getTimeHMS(String separator) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH" + separator + "mm" + separator + "ss");
		LocalDateTime now = LocalDateTime.now();
		
		return dtf.format(now).replace("/", "-").replace(" ", " | ");
	}

	public static String getDateYMD(boolean withTime, String separator) {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy" + separator + "MM" + separator + "dd");
		
		LocalDateTime now = LocalDateTime.now();
		
		String return_ = date.format(now).replace("/", "-").replace(" ", " | ");
		
		if (withTime) {
			return_ += "_" + getTimeHMS("-");
		}
		
		return return_;
	}
	
    public static boolean isInBounds(VoxelShape shape, BlockPos pos, Vec3 hit) {
        AABB aabb = shape.bounds().move(pos);

        return hit.x >= aabb.minX
            && hit.x <= aabb.maxX
            && hit.y >= aabb.minY
            && hit.y <= aabb.maxY
            && hit.z >= aabb.minZ
            && hit.z <= aabb.maxZ;
    }

    public static AABB getBounds(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
        return new AABB((float) fromX / 16F, (float) fromY / 16F, (float) fromZ / 16F, (float) toX / 16F, (float) toY / 16F, (float) toZ / 16F);
    }

	public static int getSides(boolean down, boolean up, boolean north, boolean south, boolean west, boolean east) {
		int side_connect = 0;
		
		if(down){
			side_connect = 1;
		}
		
		if(up){
			side_connect = 2;
		}
		if(up && down){
			side_connect = 3;
		}
		
		if(north){
			side_connect = 4;
		}
		if(north && down){
			side_connect = 5;
		}
		if(north && up){
			side_connect = 6;
		}
		if(north && up && down){
			side_connect = 7;
		}
		
		if(south){
			side_connect = 8;
		}
		if(south && down){
			side_connect = 9;
		}
		if(south && up){
			side_connect = 10;
		}
		if(south && up && down){
			side_connect = 11;
		}
		
		if(north && south){
			side_connect = 12;
		}
		if(north && south && down){
			side_connect = 13;
		}
		if(north && south && up){
			side_connect = 14;
		}
		if(north && south && up && down){
			side_connect = 15;
		}
		
		if(west){
			side_connect = 16;
		}
		if(west && down){
			side_connect = 17;
		}
		if(west && up){
			side_connect = 18;
		}
		if(west && up && down){
			side_connect = 19;
		}
		
		if(north && west){
			side_connect = 20;
		}
		if(north && west && down){
			side_connect = 21;
		}
		if(north && west && up){
			side_connect = 22;
		}
		if(north && west && up && down){
			side_connect = 23;
		}
		
		if(south && west){
			side_connect = 24;
		}
		if(south && west && down){
			side_connect = 25;
		}
		if(south && west && up){
			side_connect = 26;
		}
		if(south && west && up && down){
			side_connect = 27;
		}
		
		
		if(north && south && west){
			side_connect = 28;
		}
		if(north && south && west && down){
			side_connect = 29;
		}
		if(north && south && west && up){
			side_connect = 30;
		}
		if(north && south && west && up && down){
			side_connect = 31;
		}
		
		
		if(east){
			side_connect = 32;
		}
		if(east && down){
			side_connect = 33;
		}
		if(east && up){
			side_connect = 34;
		}
		if(east && up && down){
			side_connect = 35;
		}
		
		if(north && east){
			side_connect = 36;
		}
		if(north && east && down){
			side_connect = 37;
		}
		if(north && east && up){
			side_connect = 38;
		}
		if(north && east && up && down){
			side_connect = 39;
		}
		
		
		if(south && east){
			side_connect = 40;
		}
		if(south && east && down){
			side_connect = 41;
		}
		if(south && east && up){
			side_connect = 42;
		}
		if(south && east && up && down){
			side_connect = 43;
		}
		
		
		if(north && south && east){
			side_connect = 44;
		}
		if(north && south && east && down){
			side_connect = 45;
		}
		if(north && south && east && up){
			side_connect = 46;
		}
		if(north && south &&  east && up && down){
			side_connect = 47;
		}
		
		if(east && west){
			side_connect = 48;
		}
		if(east && west && down){
			side_connect = 49;
		}
		if(east && west && up){
			side_connect = 50;
		}
		if(east && west && up && down){
			side_connect = 51;
		}
		
		
		if(north && west && east){
			side_connect = 52;
		}
		if(north && west && east && down){
			side_connect = 53;
		}
		if(north && west && east && up){
			side_connect = 54;
		}
		if(north && west &&  east && up && down){
			side_connect = 55;
		}
		
		if(south && west && east){
			side_connect = 56;
		}
		if(south && west && east && down){
			side_connect = 57;
		}
		if(south && west && east && up){
			side_connect = 58;
		}
		if(south && west &&  east && up && down){
			side_connect = 59;
		}
		
		
		if(north && south && west &&  east){
			side_connect = 60;
		}
		if(north && south && west &&  east && down){
			side_connect = 61;
		}
		if(north && south && west &&  east && up){
			side_connect = 62;
		}
		if(north && south && west &&  east && up && down){
			side_connect = 63;
		}
		
		return side_connect;
	}
}
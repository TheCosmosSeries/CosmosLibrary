package com.tcn.cosmoslibrary.energy.item;

import java.util.List;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;

public class CosmosEnergyShovelItem extends ShovelItem implements ICosmosEnergyItem {

	private int maxEnergyStored;
	private int maxExtract;
	private int maxReceive;
	private int maxUse;
	private boolean doesExtract;
	private boolean doesCharge;
	private boolean doesDisplayEnergyInTooltip;
	private ComponentColour barColour;
	
	public CosmosEnergyShovelItem(Tier itemTier, Properties builderIn, CosmosEnergyItem.Properties energyProperties) {
		super(itemTier, builderIn);
		
		this.maxEnergyStored = energyProperties.maxEnergyStored;
		this.maxExtract = energyProperties.maxExtract;
		this.maxReceive = energyProperties.maxReceive;
		this.maxUse = energyProperties.maxUse;
		this.doesExtract = energyProperties.doesExtract;
		this.doesCharge = energyProperties.doesCharge;
		this.doesDisplayEnergyInTooltip = energyProperties.doesDisplayEnergyInTooltip;
		this.barColour = energyProperties.barColour;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, context, tooltip, flagIn);
		
		if (stack.has(DataComponents.CUSTOM_DATA)) {
			CompoundTag stackTag = stack.get(DataComponents.CUSTOM_DATA).copyTag();
			tooltip.add(ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.tooltip.energy_item.stored").append(ComponentHelper.comp(Value.LIGHT_GRAY + "[ " + Value.RED + CosmosUtil.formatIntegerMillion(stackTag.getInt("energy")) + Value.LIGHT_GRAY + " / " + Value.RED + CosmosUtil.formatIntegerMillion(this.getMaxEnergyStored(stack)) + Value.LIGHT_GRAY + " ]")));
		}
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = level.getBlockState(blockpos);
		
		if (context.getClickedFace() == Direction.DOWN) {
			return InteractionResult.PASS;
		} else {
			Player player = context.getPlayer();
            BlockState blockstate1 = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false);
            BlockState blockstate2 = null;
			ItemStack selected = context.getItemInHand();

			if (this.hasEnergy(selected)) {
				if (blockstate1 != null && level.getBlockState(blockpos.above()).isAir()) {
	                level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
	                blockstate2 = blockstate1;
	            } else if ((blockstate2 = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_DOUSE, false)) != null) {
	                if (!level.isClientSide()) {
	                    level.levelEvent(null, 1009, blockpos, 0);
	                }
	            }
	
				if (blockstate2 != null) {
					if (!level.isClientSide) {
						level.setBlock(blockpos, blockstate2, 11);
						level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, blockstate2));
						
						if (player != null) {
							this.extractEnergy(selected, this.getMaxUse(selected), false);
							context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
						}
					}
	
					return InteractionResult.sidedSuccess(level.isClientSide);
				} else {
					return InteractionResult.PASS;
				}
			}
		}

		return InteractionResult.FAIL;
	}

	@Override
	public boolean canAttackBlock(BlockState stateIn, Level worldIn, BlockPos posIn, Player playerEntity) {
		ItemStack heldStack = playerEntity.getInventory().getSelected();
		
		if (heldStack.getItem() instanceof CosmosEnergyShovelItem) {
			if (this.hasEnergy(heldStack)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean hurtEnemy(ItemStack stackIn, LivingEntity target, LivingEntity attacker) {
		if (this.hasEnergy(stackIn)) {
			this.extractEnergy(stackIn, (this.getMaxUse(stackIn) / 2), false);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stackIn, Player player, Entity entity) {
		if (this.hasEnergy(stackIn)) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean onEntitySwing(ItemStack stackIn, LivingEntity entity) {
		if (this.hasEnergy(stackIn)) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stackIn, Level worldIn, BlockState blockStateIn, BlockPos posIn, LivingEntity entityLiving) {
		if (this.hasEnergy(stackIn)) {
			this.extractEnergy(stackIn, this.getMaxUse(stackIn), false);
			return true;
		}
		
		return false;
	}

	@Override
	public int getMaxEnergyStored(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShovelItem) ? 0 : ((CosmosEnergyShovelItem)item).maxEnergyStored;
	}

	@Override
	public int getMaxExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShovelItem) ? 0 : ((CosmosEnergyShovelItem)item).maxExtract;
	}

	@Override
	public int getMaxReceive(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShovelItem) ? 0 : ((CosmosEnergyShovelItem)item).maxReceive;
	}

	@Override
	public int getMaxUse(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShovelItem) ? 0 : ((CosmosEnergyShovelItem)item).maxUse;
	}

	@Override
	public boolean doesExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShovelItem) ? false : ((CosmosEnergyShovelItem)item).doesExtract;
	}

	@Override
	public boolean doesCharge(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShovelItem) ? false : ((CosmosEnergyShovelItem)item).doesCharge;
	}

	@Override
	public boolean doesDisplayEnergyInTooltip(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyShovelItem) ? false : ((CosmosEnergyShovelItem)item).doesDisplayEnergyInTooltip;
	}
	
	@Override
	public boolean canReceiveEnergy(ItemStack stackIn) {
		return this.getEnergy(stackIn) < this.getMaxEnergyStored(stackIn);
	}

	@Override
	public double getScaledEnergy(ItemStack stackIn, int scaleIn) {
		Item item = stackIn.getItem();
		
		if (item instanceof ICosmosEnergyItem) {
			return (double) this.getEnergy(stackIn) * scaleIn / (double) this.getMaxEnergyStored(stackIn);
		}
		
		return 0;
	}

	@Override
	public double getScaledEnergy(ItemStack stackIn, float scaleIn) {
		Item item = stackIn.getItem();
		
		if (item instanceof ICosmosEnergyItem) {
			return (double) this.getEnergy(stackIn) * scaleIn / (double) this.getMaxEnergyStored(stackIn);
		}
		
		return 0;
	}

	@Override
	public int receiveEnergy(ItemStack stackIn, int energy, boolean simulate) {
		if (this.canReceiveEnergy(stackIn)) {
			if(this.doesCharge(stackIn)) {
				int storedReceived = Math.min(this.getMaxEnergyStored(stackIn) - this.getEnergy(stackIn), Math.min(this.getMaxReceive(stackIn), energy));
				
				if (!simulate) {
					this.setEnergy(stackIn, this.getEnergy(stackIn) + storedReceived);
				}
				
				return storedReceived;
			} 
		}
		
		return 0;
	}

	@Override
	public int extractEnergy(ItemStack stackIn, int energy, boolean simulate) {
		if (this.canExtractEnergy(stackIn)) {
			if (this.doesExtract(stackIn)) {
				int storedExtracted = Math.min(this.getEnergy(stackIn), Math.min(this.getMaxExtract(stackIn), energy));
				
				if (!simulate) {
					this.setEnergy(stackIn, this.getEnergy(stackIn) - storedExtracted);
				}
				
				return storedExtracted;
			}
		}
		
		return 0;
	}

	@Override
	public boolean isBarVisible(ItemStack stackIn) {
		return true;
	}
	
	@Override
	public int getBarColor(ItemStack stackIn) {
		return this.barColour.dec();
	}
	
	@Override
	public int getBarWidth(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof ICosmosEnergyItem) ? 0 : Mth.clamp(Math.round((float) ((ICosmosEnergyItem) item).getScaledEnergy(stackIn, 13)), 0, 13);
	}
}
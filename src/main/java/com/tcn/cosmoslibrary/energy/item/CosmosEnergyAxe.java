package com.tcn.cosmoslibrary.energy.item;

import java.util.List;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.client.renderer.item.CosmosEnergyItemItemStackRenderer;
import com.tcn.cosmoslibrary.common.comp.CosmosColour;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper;
import com.tcn.cosmoslibrary.common.comp.CosmosCompHelper.Value;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class CosmosEnergyAxe extends AxeItem implements ICosmosEnergyItem {

	private int maxEnergyStored;
	private int maxExtract;
	private int maxReceive;
	private int maxUse;
	private boolean doesExtract;
	private boolean doesCharge;
	private boolean doesDisplayEnergyInTooltip;
	private boolean doesDisplayEnergyBar;

	public CosmosEnergyAxe(IItemTier itemTier, int attackDamageIn, float attackSpeedIn, Properties builderIn, CosmosEnergyItem.Properties energyProperties) {
		super(itemTier, attackDamageIn, attackSpeedIn, builderIn.setISTER(() -> CosmosEnergyItemItemStackRenderer::new));
		
		this.maxEnergyStored = energyProperties.maxEnergyStored;
		this.maxExtract = energyProperties.maxExtract;
		this.maxReceive = energyProperties.maxReceive;
		this.maxUse = energyProperties.maxUse;
		this.doesExtract = energyProperties.doesExtract;
		this.doesCharge = energyProperties.doesCharge;
		this.doesDisplayEnergyInTooltip = energyProperties.doesDisplayEnergyInTooltip;
		this.doesDisplayEnergyBar = energyProperties.doesDisplayEnergyBar;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTag()) {
			CompoundNBT stackTag = stack.getTag();
			tooltip.add(CosmosCompHelper.locComp(CosmosColour.GRAY, false, "cosmoslibrary.tooltip.energy_item.stored").append(CosmosCompHelper.locComp(Value.LIGHT_GRAY + "[ " + Value.RED + CosmosUtil.formatIntegerMillion(stackTag.getInt("energy")) + Value.LIGHT_GRAY + " / " + Value.RED + CosmosUtil.formatIntegerMillion(this.getMaxEnergyStored(stack)) + Value.LIGHT_GRAY + " ]")));
		}
	}

	@Override
	public boolean canAttackBlock(BlockState stateIn, World worldIn, BlockPos posIn, PlayerEntity playerEntity) {
		ItemStack heldStack = playerEntity.inventory.getSelected();
		
		if (heldStack.getItem() instanceof CosmosEnergyAxe) {
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
	public boolean onLeftClickEntity(ItemStack stackIn, PlayerEntity player, Entity entity) {
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
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public boolean mineBlock(ItemStack stackIn, World worldIn, BlockState blockStateIn, BlockPos posIn, LivingEntity entityLiving) {
		if (this.hasEnergy(stackIn)) {
			this.extractEnergy(stackIn, this.getMaxUse(stackIn), false);
			return true;
		}
		
		return false;
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		World world = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = world.getBlockState(blockpos);
		BlockState block = blockstate.getToolModifiedState(world, blockpos, context.getPlayer(), context.getItemInHand(), ToolType.AXE);
		
		if (block != null) {
			PlayerEntity playerentity = context.getPlayer();
			ItemStack selected = playerentity.inventory.getSelected();
			
			if (this.hasEnergy(selected)) {
				if (!world.isClientSide) {
					if (playerentity != null) {
						world.setBlock(blockpos, block, 11);
						world.playSound(playerentity, blockpos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
						this.extractEnergy(selected, this.getMaxUse(selected), false);
					}
				}
			
				return ActionResultType.sidedSuccess(world.isClientSide);
			} else {
				return ActionResultType.FAIL;
			}
		} else {
			return ActionResultType.PASS;
		}
	}

	@Override
	public int getMaxEnergyStored(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? 0 : ((CosmosEnergyAxe)item).maxEnergyStored;
	}

	@Override
	public int getMaxExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? 0 : ((CosmosEnergyAxe)item).maxExtract;
	}

	@Override
	public int getMaxReceive(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? 0 : ((CosmosEnergyAxe)item).maxReceive;
	}

	@Override
	public int getMaxUse(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? 0 : ((CosmosEnergyAxe)item).maxUse;
	}

	@Override
	public boolean doesExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? false : ((CosmosEnergyAxe)item).doesExtract;
	}

	@Override
	public boolean doesCharge(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? false : ((CosmosEnergyAxe)item).doesCharge;
	}

	@Override
	public boolean doesDisplayEnergyInTooltip(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? false : ((CosmosEnergyAxe)item).doesDisplayEnergyInTooltip;
	}

	@Override
	public boolean doesDisplayEnergyBar(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyAxe) ? false : ((CosmosEnergyAxe)item).doesDisplayEnergyBar;
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
	
}
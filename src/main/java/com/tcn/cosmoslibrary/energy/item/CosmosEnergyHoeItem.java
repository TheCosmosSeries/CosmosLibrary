package com.tcn.cosmoslibrary.energy.item;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;
import com.tcn.cosmoslibrary.energy.CosmosEnergyUtil;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class CosmosEnergyHoeItem extends HoeItem implements ICosmosEnergyItem {

	private int maxEnergyStored;
	private int maxExtract;
	private int maxReceive;
	private int maxUse;
	private boolean doesExtract;
	private boolean doesCharge;
	private boolean doesDisplayEnergyInTooltip;
	private ComponentColour barColour;
	
	public CosmosEnergyHoeItem(Tier itemTier, Properties builderIn, CosmosEnergyItem.Properties energyProperties) {
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
		BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(context, ItemAbilities.HOE_TILL, false);
		Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of(ctx -> true, changeIntoState(toolModifiedState));
		
		if (pair == null) {
			return InteractionResult.PASS;
		} else {
			Predicate<UseOnContext> predicate = pair.getFirst();
			Consumer<UseOnContext> consumer = pair.getSecond();
			
			if (predicate.test(context)) {
				Player player = context.getPlayer();
				ItemStack selected = context.getItemInHand();

				if (this.hasEnergy(selected)) {
					level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
					if (!level.isClientSide) {
						consumer.accept(context);
						if (player != null) {
							this.extractEnergy(selected, this.getMaxUse(selected), false);
							context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
						}
					}
				}

				return InteractionResult.sidedSuccess(level.isClientSide);
			} else {
				return InteractionResult.PASS;
			}
		}
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stackIn, int amount, @Nullable T entity, Consumer<Item> onBroken) {
		if (this.hasEnergy(stackIn)) {
			this.extractEnergy(stackIn, this.getMaxUse(stackIn), false);
		}
		
		return 0;
	}
	
	@Override
	public boolean canAttackBlock(BlockState stateIn, Level worldIn, BlockPos posIn, Player playerEntity) {
		ItemStack heldStack = playerEntity.getInventory().getSelected();
		
		if (this.hasEnergy(heldStack)) {
			return true;
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
		return !(item instanceof CosmosEnergyHoeItem) ? 0 : ((CosmosEnergyHoeItem)item).maxEnergyStored;
	}

	@Override
	public int getMaxExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyHoeItem) ? 0 : ((CosmosEnergyHoeItem)item).maxExtract;
	}

	@Override
	public int getMaxReceive(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyHoeItem) ? 0 : ((CosmosEnergyHoeItem)item).maxReceive;
	}

	@Override
	public int getMaxUse(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyHoeItem) ? 0 : ((CosmosEnergyHoeItem)item).maxUse;
	}

	@Override
	public boolean doesExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyHoeItem) ? false : ((CosmosEnergyHoeItem)item).doesExtract;
	}

	@Override
	public boolean doesCharge(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyHoeItem) ? false : ((CosmosEnergyHoeItem)item).doesCharge;
	}

	@Override
	public boolean doesDisplayEnergyInTooltip(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyHoeItem) ? false : ((CosmosEnergyHoeItem)item).doesDisplayEnergyInTooltip;
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
	public IEnergyStorage getEnergyCapability(ItemStack stackIn) {
		return CosmosEnergyUtil.getDefault(stackIn, this);
	}
	
	@Override
	public boolean isBarVisible(ItemStack stackIn) {
		return stackIn.has(DataComponents.CUSTOM_DATA) ? stackIn.get(DataComponents.CUSTOM_DATA).copyTag().contains("energy") : false;
	}
	
	@Override
	public int getBarColor(ItemStack stackIn) {
		return this.barColour.dec();
	}
	
	@Override
	public int getBarWidth(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof ICosmosEnergyItem energyItem) ? 0 : Mth.clamp(Math.round((float) energyItem.getScaledEnergy(stackIn, 13)), 0, 13);
	}
}
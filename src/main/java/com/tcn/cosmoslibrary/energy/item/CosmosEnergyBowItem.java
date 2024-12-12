package com.tcn.cosmoslibrary.energy.item;

import java.util.List;
import java.util.function.Predicate;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;
import com.tcn.cosmoslibrary.energy.interfaces.ICosmosEnergyItem;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

public class CosmosEnergyBowItem extends BowItem implements ICosmosEnergyItem {
	private int maxEnergyStored;
	private int maxExtract;
	private int maxReceive;
	private int maxUse;
	private boolean doesExtract;
	private boolean doesCharge;
	private boolean doesDisplayEnergyInTooltip;
	private ComponentColour barColour;
	
	public CosmosEnergyBowItem(Properties builder, CosmosEnergyItem.Properties energyProperties, int rangeIn, int knockbackModifierIn, float powerModifierIn, float damageModifierIn) {
		super(builder.stacksTo(1));

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
	public void appendHoverText(ItemStack stackIn, Item.TooltipContext context, List<Component> toolTipIn, TooltipFlag flagIn) {
		super.appendHoverText(stackIn, context, toolTipIn, flagIn);
		
		if (stackIn.has(DataComponents.CUSTOM_DATA)) {
			CompoundTag stackTag = stackIn.get(DataComponents.CUSTOM_DATA).copyTag();
			toolTipIn.add(ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.tooltip.energy_item.stored").append(ComponentHelper.comp(Value.LIGHT_GRAY + "[ " + Value.RED + CosmosUtil.formatIntegerMillion(stackTag.getInt("energy")) + Value.LIGHT_GRAY + " / " + Value.RED + CosmosUtil.formatIntegerMillion(this.getMaxEnergyStored(stackIn)) + Value.LIGHT_GRAY + " ]")));
		}
	}

	@Override
	public int getUseDuration(ItemStack stackIn, LivingEntity entityIn) {
		return 72000;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		boolean flag = !playerIn.getProjectile(itemstack).isEmpty();

		InteractionResultHolder<ItemStack> ret = EventHooks.onArrowNock(itemstack, levelIn, playerIn, handIn, flag);
		if (ret != null) {
			return ret;
		}
		
		if (this.hasEnergy(itemstack)) {
			if (!playerIn.getAbilities().instabuild && !flag) {
				return InteractionResultHolder.fail(itemstack);
			} else {
				playerIn.startUsingItem(handIn);
				return InteractionResultHolder.consume(itemstack);
			}
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
	}
	
	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
		if (livingEntity instanceof Player) {
			Player playerentity = (Player) livingEntity;
			ItemStack itemstack = playerentity.getProjectile(stack);

			int i = this.getUseDuration(stack, livingEntity) - timeLeft;
			i = EventHooks.onArrowLoose(stack, level, playerentity, i, !itemstack.isEmpty());
			
			if (i < 0) {
				return;
			} else if (!itemstack.isEmpty()) {
				if (itemstack.isEmpty()) {
					itemstack = new ItemStack(Items.ARROW);
				}

				float f = CosmosEnergyBowItem.getPowerForTime(i);
				if (!((double) f < 0.1D)) {
					List<ItemStack> list = draw(stack, itemstack, playerentity);
                    if (level instanceof ServerLevel serverlevel && !list.isEmpty()) {
                        this.shoot(serverlevel, playerentity, playerentity.getUsedItemHand(), stack, list, f * 3.0F, 1.0F, f == 1.0F, null);
						this.extractEnergy(stack, this.getMaxUse(stack) / 2, false);
                    }

                    level.playSound(
                        null,
                        playerentity.getX(),
                        playerentity.getY(),
                        playerentity.getZ(),
                        SoundEvents.ARROW_SHOOT,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F
                    );
                    playerentity.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
    
	@Override
	public int getMaxEnergyStored(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyBowItem) ? 0 : ((CosmosEnergyBowItem)item).maxEnergyStored;
	}

	@Override
	public int getMaxExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyBowItem) ? 0 : ((CosmosEnergyBowItem)item).maxExtract;
	}

	@Override
	public int getMaxReceive(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyBowItem) ? 0 : ((CosmosEnergyBowItem)item).maxReceive;
	}

	@Override
	public int getMaxUse(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyBowItem) ? 0 : ((CosmosEnergyBowItem)item).maxUse;
	}

	@Override
	public boolean doesExtract(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyBowItem) ? false : ((CosmosEnergyBowItem)item).doesExtract;
	}

	@Override
	public boolean doesCharge(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyBowItem) ? false : ((CosmosEnergyBowItem)item).doesCharge;
	}

	@Override
	public boolean doesDisplayEnergyInTooltip(ItemStack stackIn) {
		Item item = stackIn.getItem();
		return !(item instanceof CosmosEnergyBowItem) ? false : ((CosmosEnergyBowItem)item).doesDisplayEnergyInTooltip;
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
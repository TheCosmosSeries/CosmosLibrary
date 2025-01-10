package com.tcn.cosmoslibrary.common.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class CosmosFluidBlock extends LiquidBlock {

	private boolean damageInside;
	private ResourceKey<DamageType> damageSource;
	private float damageAmount;
	
	public CosmosFluidBlock(FlowingFluid fluid, Properties properties) {
		this(fluid, properties, false, null, 0.0F);
	}
	
	public CosmosFluidBlock(FlowingFluid fluid, Properties properties, boolean damageInside, @Nullable ResourceKey<DamageType> damageSource, float damageAmount) {
		super(fluid, properties);
		
		this.damageInside = damageInside;
		this.damageAmount = damageAmount;
		
		if (damageInside) {
			if (damageSource != null) {
				this.damageSource = damageSource;
			} else throw(new NullPointerException("DamageSource cannot be null! Here be dragons!"));
		}
	}
	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (this.damageInside) {
			if (entity instanceof LivingEntity livingEntity) {
				if (this.damageSource.equals(DamageTypes.FREEZE)) {
					livingEntity.setIsInPowderSnow(true);
					livingEntity.hurt(level.damageSources().source(this.damageSource), this.damageAmount);
				} else if (this.damageSource.equals(DamageTypes.LAVA)) {
					livingEntity.setRemainingFireTicks(100);
					livingEntity.hurt(level.damageSources().source(this.damageSource), this.damageAmount);
				}
			} else {
				entity.hurt(level.damageSources().source(this.damageSource), this.damageAmount);
			}
		}
	}
}
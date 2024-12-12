package com.tcn.cosmoslibrary.common.event;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class PortalEvent extends Event implements ICancellableEvent {
	
	private final Entity entity;
	private final BlockPos entityPos;
	private final BlockPos destPos;
	private final ResourceLocation destDimension;
	
	public PortalEvent(Entity entityIn, BlockPos entityPosIn, BlockPos destPosIn,  ResourceLocation destDimensionIn, boolean defaultResultIn) {
		this.entity = entityIn;
		this.entityPos = entityPosIn;
		this.destPos = destPosIn;
		this.destDimension = destDimensionIn;
	}

	public Entity getEntity() {
		return entity;
	}

	public BlockPos getEntityPos() {
		return entityPos;
	}

	public BlockPos getDestPos() {
		return destPos;
	}

	public ResourceLocation getDestDimension() {
		return destDimension;
	}

	public static class PortalTravel extends PortalEvent {
	    private final boolean defaultResult;
	    private Result result = Result.DEFAULT;
	    
		public PortalTravel(Entity entityIn, BlockPos entityPosIn, BlockPos destPosIn, ResourceLocation destDimensionIn, boolean defaultResultIn) {
			super(entityIn, entityPosIn, destPosIn, destDimensionIn, defaultResultIn);
			this.defaultResult = defaultResultIn;
		}

	    public boolean getDefaultResult() {
	        return this.defaultResult;
	    }
	    
	    public void setResult(Result result) {
	        this.result = result;
	    }
	    
	    public Result getResult() {
	        return this.result;
	    }
	    
	    public boolean getPortalTravelResult() {
	        if (this.result == Result.SUCCEED) {
	            return true;
	        }
	        return this.result == Result.DEFAULT && this.getDefaultResult();
	    }

	    public static enum Result {
	        SUCCEED,
	        DEFAULT,
	        FAIL;
	    }
	}
	
	public static class LinkContainer extends PortalEvent {		
	    private final boolean defaultResult;
	    private Result result = Result.DEFAULT;
	    
		public LinkContainer(Entity entityIn, BlockPos entityPosIn, BlockPos destPosIn, ResourceLocation destDimensionIn, boolean defaultResultIn) {
			super(entityIn, entityPosIn, destPosIn, destDimensionIn, defaultResultIn);
			this.defaultResult = defaultResultIn;
		}

	    public boolean getDefaultResult() {
	        return this.defaultResult;
	    }
	    
	    public void setResult(Result result) {
	        this.result = result;
	    }
	    
	    public Result getResult() {
	        return this.result;
	    }
	    
	    public boolean getLinkContainerResult() {
	        if (this.result == Result.SUCCEED) {
	            return true;
	        }
	        return this.result == Result.DEFAULT && this.getDefaultResult();
	    }

	    public static enum Result {
	        SUCCEED,
	        DEFAULT,
	        FAIL;
	    }
	}

}
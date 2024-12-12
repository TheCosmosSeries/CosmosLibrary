package com.tcn.cosmoslibrary.runtime.network;

import com.tcn.cosmoslibrary.CosmosLibrary;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public record PacketUIMode(BlockPos pos, ResourceKey<Level> dimension) implements CustomPacketPayload, ICosmosPacket {

	public static final CustomPacketPayload.Type<PacketUIMode> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CosmosLibrary.MOD_ID, "ui_mode"));

	public static final StreamCodec<ByteBuf, PacketUIMode> STREAM_CODEC = StreamCodec.composite(
		BlockPos.STREAM_CODEC,
		PacketUIMode::pos, 
		ResourceKey.streamCodec(Registries.DIMENSION),
		PacketUIMode::dimension,
		PacketUIMode::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
	
	/*
	private BlockPos pos;
	private ResourceKey<Level> dimension;
	
	public PacketUIMode(FriendlyByteBuf buf) {
		this.pos = buf.readBlockPos();
		ResourceLocation location = buf.readResourceLocation();
		this.dimension = ResourceKey.create(Registries.DIMENSION, location);
	}
	
	public PacketUIMode(CosmosContainerMenuBlockEntity containerIn) {
		this.pos = containerIn.getBlockPos();
		this.dimension = containerIn.getLevel().dimension();
	}
	
	public PacketUIMode(CosmosContainerRecipeBookBlockEntity<? extends Container> containerIn) {
		this.pos = containerIn.getBlockPos();
		this.dimension = containerIn.getLevel().dimension();
	}
	
	public static void encode(PacketUIMode packet, FriendlyByteBuf buf) {
		buf.writeBlockPos(packet.pos);
		buf.writeResourceLocation(packet.dimension.location());
	}
	
	public static void handle(final PacketUIMode packet, Supplier<NetworkEvent.Context> context) {
		NetworkEvent.Context ctx = context.get();
		
		ctx.enqueueWork(() -> {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			ServerLevel world = server.getLevel(packet.dimension);
			BlockEntity entity = world.getBlockEntity(packet.pos);
			
			if (entity instanceof IBlockEntityUIMode) {
				IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
			
				blockEntity.cycleUIMode();
			} else {
				CosmosLibrary.CONSOLE.debugWarn("[Packet Delivery Failure] <uimode> Block Entity not equal to expected.");
			}
			
		});
		
		ctx.setPacketHandled(true);
	}*/
}

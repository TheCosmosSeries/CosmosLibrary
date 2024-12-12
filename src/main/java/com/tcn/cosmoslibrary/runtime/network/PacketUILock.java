package com.tcn.cosmoslibrary.runtime.network;

import java.util.UUID;

import com.tcn.cosmoslibrary.CosmosLibrary;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public record PacketUILock(BlockPos pos, ResourceKey<Level> dimension, UUID uuid) implements CustomPacketPayload, ICosmosPacket {

	public static final CustomPacketPayload.Type<PacketUILock> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CosmosLibrary.MOD_ID, "ui_lock"));

	public static final StreamCodec<ByteBuf, PacketUILock> STREAM_CODEC = StreamCodec.composite(
		BlockPos.STREAM_CODEC,
		PacketUILock::pos, 
		ResourceKey.streamCodec(Registries.DIMENSION),
		PacketUILock::dimension,
		UUIDUtil.STREAM_CODEC,
		PacketUILock::uuid,
		PacketUILock::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
	/*

	public PacketUILock(FriendlyByteBuf buf) {
		this.pos = buf.readBlockPos();
		ResourceLocation location = buf.readResourceLocation();
		this.dimension = ResourceKey.create(Registries.DIMENSION, location);
		this.uuid = buf.readUUID();
	}
	
	public PacketUILock(CosmosContainerMenuBlockEntity containerIn) {
		this.pos = containerIn.getBlockPos();
		this.dimension = containerIn.getLevel().dimension();
		this.uuid = containerIn.getPlayer().getUUID();
	}
	
	public PacketUILock(CosmosContainerRecipeBookBlockEntity<? extends Container> containerIn) {
		this.pos = containerIn.getBlockPos();
		this.dimension = containerIn.getLevel().dimension();
		this.uuid = containerIn.getPlayer().getUUID();
	}
	
	public static void encode(PacketUILock packet, FriendlyByteBuf buf) {
		buf.writeBlockPos(packet.pos);
		buf.writeResourceLocation(packet.dimension.location());
		buf.writeUUID(packet.uuid);
	}
	
	public static void handle(final PacketUILock packet, Supplier<NetworkEvent.Context> context) {
		NetworkEvent.Context ctx = context.get();
		
		ctx.enqueueWork(() -> {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			ServerLevel world = server.getLevel(packet.dimension);
			BlockEntity entity = world.getBlockEntity(packet.pos);
			
			Player player = world.getPlayerByUUID(packet.uuid);
			
			if (entity instanceof IBlockEntityUIMode) {
				IBlockEntityUIMode blockEntity = (IBlockEntityUIMode) entity;
				
				if (blockEntity.checkIfOwner(player)) {
					blockEntity.cycleUILock();
				}
			} else {
				CosmosLibrary.CONSOLE.debugWarn("[Packet Delivery Failure] <uilock> Block Entity not equal to expected.");
			}
			
		});
		
		ctx.setPacketHandled(true);
	}*/
}

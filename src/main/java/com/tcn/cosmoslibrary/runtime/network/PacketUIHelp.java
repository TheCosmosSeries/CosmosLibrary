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

public record PacketUIHelp(BlockPos pos, ResourceKey<Level> dimension) implements CustomPacketPayload, ICosmosPacket {
	
	public static final CustomPacketPayload.Type<PacketUIHelp> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CosmosLibrary.MOD_ID, "ui_help"));

	public static final StreamCodec<ByteBuf, PacketUIHelp> STREAM_CODEC = StreamCodec.composite(
		BlockPos.STREAM_CODEC,
		PacketUIHelp::pos, 
		ResourceKey.streamCodec(Registries.DIMENSION),
		PacketUIHelp::dimension,
		PacketUIHelp::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}

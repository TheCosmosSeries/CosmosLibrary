package com.tcn.cosmoslibrary.runtime;

import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.runtime.network.ServerPayloadHandler;
import com.tcn.cosmoslibrary.runtime.network.packet.PacketUIHelp;
import com.tcn.cosmoslibrary.runtime.network.packet.PacketUILock;
import com.tcn.cosmoslibrary.runtime.network.packet.PacketUIMode;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CosmosLibrary.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkManagerCosmos {
	
	@SubscribeEvent
	public static void register(final RegisterPayloadHandlersEvent event) {
	    final PayloadRegistrar registrar = event.registrar("1");
	    
	    registrar.playToServer(PacketUIHelp.TYPE, PacketUIHelp.STREAM_CODEC, ServerPayloadHandler::handleDataOnNetwork);
	    registrar.playToServer(PacketUILock.TYPE, PacketUILock.STREAM_CODEC, ServerPayloadHandler::handleDataOnNetwork);
	    registrar.playToServer(PacketUIMode.TYPE, PacketUIMode.STREAM_CODEC, ServerPayloadHandler::handleDataOnNetwork);
	}
}
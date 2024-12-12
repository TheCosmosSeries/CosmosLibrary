package com.tcn.cosmoslibrary.runtime;

import com.tcn.cosmoslibrary.CosmosLibrary;
import com.tcn.cosmoslibrary.runtime.network.PacketUIHelp;
import com.tcn.cosmoslibrary.runtime.network.PacketUILock;
import com.tcn.cosmoslibrary.runtime.network.PacketUIMode;
import com.tcn.cosmoslibrary.runtime.network.ServerPayloadHandler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CosmosLibrary.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkManagerCosmos {
	
	/*
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(CosmosLibrary.MOD_ID, "network"), 
		() -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
	);*/
	
	@SubscribeEvent
	public static void register(final RegisterPayloadHandlersEvent event) {
	    // Sets the current network version
	    final PayloadRegistrar registrar = event.registrar("1");
	    registrar.playToServer(PacketUIHelp.TYPE, PacketUIHelp.STREAM_CODEC, ServerPayloadHandler::handleDataOnNetwork);
	    registrar.playToServer(PacketUILock.TYPE, PacketUILock.STREAM_CODEC, ServerPayloadHandler::handleDataOnNetwork);
	    registrar.playToServer(PacketUIMode.TYPE, PacketUIMode.STREAM_CODEC, ServerPayloadHandler::handleDataOnNetwork);
	}
	/*
	public static void registerPackets() {
		INSTANCE.registerMessage(0, PacketUIMode.class, PacketUIMode::encode, PacketUIMode::new, PacketUIMode::handle);
		INSTANCE.registerMessage(1, PacketUIHelp.class, PacketUIHelp::encode, PacketUIHelp::new, PacketUIHelp::handle);
		INSTANCE.registerMessage(2, PacketUILock.class, PacketUILock::encode, PacketUILock::new, PacketUILock::handle);
		
		CosmosLibrary.CONSOLE.startup("Cosmos Network Setup complete.");
	}
	
	public static void sendToServer(ICosmosPacket message) {
        INSTANCE.sendToServer(message);
    }*/
}
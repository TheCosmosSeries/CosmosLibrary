package com.tcn.cosmoslibrary;

import com.tcn.cosmoslibrary.runtime.ModBusSubscriberCosmos;
import com.tcn.cosmoslibrary.runtime.common.CosmosConsoleManager;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod("cosmoslibrary")
public final class CosmosLibrary {

	public static final String MOD_ID = "cosmoslibrary";
	public static final String MOD_ID_WITH = "cosmoslibrary:";
	public static final CosmosConsoleManager CONSOLE = new CosmosConsoleManager(CosmosLibrary.MOD_ID, false, true);

    public CosmosLibrary(IEventBus bus) {
    	ModBusSubscriberCosmos.register(bus);
    	
    	bus.addListener(this::commonSetup);
    }
	
	public void commonSetup(FMLCommonSetupEvent event){
		CONSOLE.startup("CosmosLibrary Common Setup complete.");
	}
}
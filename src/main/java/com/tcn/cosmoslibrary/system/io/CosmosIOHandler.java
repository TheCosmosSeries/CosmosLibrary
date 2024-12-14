package com.tcn.cosmoslibrary.system.io;

import java.io.File;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class CosmosIOHandler {
	
	public static File getFile(String pathIn, boolean createFile) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		StringBuilder filePath = new StringBuilder(pathIn);
		
		File returnFile = server.getFile(filePath.toString()).toFile();
		
		if (!returnFile.exists()) {
			if (createFile) {
				createFile(returnFile);
			}
		}
		
		return returnFile;
	}

	public static void createFile(File fileIn) {
		try {
			fileIn.getParentFile().mkdirs();
			fileIn.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getServerLevelId(MinecraftServer serverIn) { //TODO: needs to be changed to f_129744_ when building.
		final Object levelId = ObfuscationReflectionHelper.getPrivateValue(MinecraftServer.class, serverIn, "storageSource");
		
		if (levelId instanceof LevelStorageSource.LevelStorageAccess access) {
			return access.getLevelId();
		}
		
		return "";
	}
}
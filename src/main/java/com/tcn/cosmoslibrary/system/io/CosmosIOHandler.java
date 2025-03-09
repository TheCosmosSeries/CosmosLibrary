package com.tcn.cosmoslibrary.system.io;

import java.io.File;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class CosmosIOHandler {
	
	public static File getFile(String pathIn, boolean createFile) {
		File returnFile = ServerLifecycleHooks.getCurrentServer().getFile(new StringBuilder(pathIn).toString()).toFile();
		
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
	
	public static String getServerLevelId(MinecraftServer serverIn) { //TODO: needs to be changed to f_129744_ when building (FORGE only).
		if (ObfuscationReflectionHelper.getPrivateValue(MinecraftServer.class, serverIn, "storageSource") instanceof LevelStorageSource.LevelStorageAccess access) {
			return access.getLevelId();
		}
		return "";
	}
}
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
	
/*
	private static File getFile(boolean includeSaveFolder, String folderName, String fileName, RegistryFileType fileTypeIn, boolean backup) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		StringBuilder filePath = new StringBuilder();
		
			if (server != null) {
				if (includeSaveFolder) {
					if (server.isSingleplayer()) {
						filePath.append("saves/");
					}
					
					final Object save = ObfuscationReflectionHelper.getPrivateValue(MinecraftServer.class, server, "f_129744_");
					try {
						if (save instanceof LevelStorageSource.LevelStorageAccess) {
							filePath.append(((LevelStorageAccess) save).getLevelId());
						}
					} catch (Exception e) {
						DimensionalPockets.CONSOLE.fatal("[File System Error] <getfile> Unable to get LevelId. See stacktrace for more info:", e);
					}
				}
				
				filePath.append("/" + folderName + "/" + fileName + fileTypeIn.getExtension());
				
				File save_file = server.getFile(filePath.toString());
				
				// - Backwards way of transferring JSON to DAT - /
				if (!save_file.exists()) {
					if (fileTypeIn.equals(RegistryFileType.DAT) && fileName.equals(POCKET_REGISTRY_FILE) && !backup) {
						File jsonFile = getFile(true, folderName, fileName, RegistryFileType.JSON, false);
						
						if (jsonFile.exists()) {
							save_file.getParentFile().mkdirs();
							save_file.createNewFile();
							
							Map<PocketChunkInfo, Pocket> newPocketRegistry = new LinkedHashMap<>();
							
							// - LOAD - /
							try {
								Pocket[] pocket_array = null;
								
								try (FileReader reader = new FileReader(jsonFile)) {
									pocket_array = GSON.fromJson(reader, Pocket[].class);
									reader.close();
								} catch (Exception e) {
									DimensionalPockets.CONSOLE.fatal("[File System Error] <loadPocketRegistry> Could not load PocketRegistry. See stacktrace for more info:", e);
								}
								
								if (pocket_array != null) {
									for (Pocket link : pocket_array) {
										if (link.energy_capacity != DimReference.CONSTANT.POCKET_FE_CAP) {
											link.energy_capacity = DimReference.CONSTANT.POCKET_FE_CAP;
										}
										
										if (link.energy_max_receive != DimReference.CONSTANT.POCKET_FE_REC) {
											link.energy_max_receive = DimReference.CONSTANT.POCKET_FE_REC;
										}
										
										if (link.energy_max_extract != DimReference.CONSTANT.POCKET_FE_EXT) {
											link.energy_max_extract = DimReference.CONSTANT.POCKET_FE_EXT;
										}
					
										if (link.fluid_tank.getFluidTank().getCapacity() != (DimReference.CONSTANT.POCKET_FLUID_CAP)) {
											link.fluid_tank.getFluidTank().setCapacity((DimReference.CONSTANT.POCKET_FLUID_CAP));
										}
					
										if (link.getChunkInfo() == null && link.chunk_pos != null) {
											link.chunk_info = new PocketChunkInfo(link.chunk_pos, true);
										}
										
										newPocketRegistry.put(link.getChunkInfo(), link);
									}
								}
								
								DimensionalPockets.CONSOLE.debug("[File System Load] <transferTOJSON> Finished Transferring.");
							} catch (Exception e) {
								DimensionalPockets.CONSOLE.fatal("[File System Error] <transferFromJSON> Could not load PocketRegistry. See stacktrace for more info:", e);
							}
							
							createBackupZipped(newPocketRegistry, BackupType.CONVERSION);
							
							//- SAVE - /
							try {
								Collection<Pocket> values = newPocketRegistry.values();
								ArrayList<Pocket> array = new ArrayList<>(values);

								CompoundTag tag = new CompoundTag();
								
								tag.putInt("size", array.size());
								
								for (int i = 0; i < array.size(); i++) {
									Pocket pocket = array.get(i);
									
									CompoundTag pocketTag = new CompoundTag();
									pocket.writeToNBT(pocketTag);
									
									tag.put(Integer.toString(i), pocketTag);
								}
								
								CosmosNBTIOHandler.write(tag, save_file);

								jsonFile.delete();
								
								return save_file;
							} catch (Exception e) {
								DimensionalPockets.CONSOLE.fatal("[File System Error] <writePocketRegistryNBTToFile> Could not save PocketRegistry. See stacktrace for more info:", e);
							}
						} else {
							save_file.getParentFile().mkdirs();
							save_file.createNewFile();
							
							if (!backup) {
								CosmosNBTIOHandler.write(new CompoundTag(), save_file);
							}
							
							return save_file;
						}
					} else if (backup) {
						save_file.getParentFile().mkdirs();
						save_file.createNewFile();
					}
				}
				
				return save_file;
			}
		
		return new File(".");
	}*/
}

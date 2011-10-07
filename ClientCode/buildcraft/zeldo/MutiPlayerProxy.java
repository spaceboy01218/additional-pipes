package net.minecraft.src.buildcraft.zeldo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.minecraft.src.IInventory;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_AdditionalPipes;
import net.minecraft.src.mod_AdditionalPipes.chunkXZ;
import net.minecraft.src.buildcraft.api.APIProxy;
import net.minecraft.src.buildcraft.transport.TileGenericPipe;
import net.minecraft.src.buildcraft.zeldo.gui.GuiAdvancedWoodPipe;
import net.minecraft.src.buildcraft.zeldo.gui.GuiItemTeleportPipe;
import net.minecraft.src.buildcraft.zeldo.gui.GuiLiquidTeleportPipe;
import net.minecraft.src.buildcraft.zeldo.gui.GuiPowerTeleportPipe;

public class MutiPlayerProxy {
	public static boolean NeedsLoad = true;
	public static File WorldDir;
	public static void displayGUIItemTeleport(TileGenericPipe tilePipe) {
		if (!APIProxy.isClient(APIProxy.getWorld())) {
			ModLoader.getMinecraftInstance().displayGuiScreen(new GuiItemTeleportPipe(tilePipe));
		}
	}
	public static void displayGUILiquidTeleport(TileGenericPipe tilePipe) {
		if (!APIProxy.isClient(APIProxy.getWorld())) {
			ModLoader.getMinecraftInstance().displayGuiScreen(new GuiLiquidTeleportPipe(tilePipe));
		}
	}
	public static void displayGUIPowerTeleport(TileGenericPipe tilePipe) {
		if (!APIProxy.isClient(APIProxy.getWorld())) {
			ModLoader.getMinecraftInstance().displayGuiScreen(new GuiPowerTeleportPipe(tilePipe));
		}
	}
	public static void displayGUIAdvancedWood(IInventory PlayerInv, TileGenericPipe container) {
		if (!APIProxy.isClient(APIProxy.getWorld())) {
			ModLoader.getMinecraftInstance().displayGuiScreen(new GuiAdvancedWoodPipe(PlayerInv, container, container));
		}
	}
	public static boolean isOnServer()
	{
		return false;
	}
	public static void AddChunkToList(int x, int z) {
		if (isOnServer())
			return;
		MutiPlayerProxy.LoadChunkData();
		x = x >> 4;
		z = z >> 4;
		Iterator<chunkXZ> chunks = mod_AdditionalPipes.keepLoadedChunks.iterator();
		while (chunks.hasNext()) {
			chunkXZ curChunk = (chunkXZ)chunks.next();
			if (curChunk.x == x && curChunk.z == z) {
				//System.out.println("Didn't need to add PermChunk @ " + x + "," + z);
				return;
			}
				
		}
		mod_AdditionalPipes.keepLoadedChunks.add(new mod_AdditionalPipes.chunkXZ(x, z));
		 //System.out.println("Added PermChunk @ " + x + "," + z);
		 SaveChunkData();
	}
	public static void SaveChunkData() {
		try {
			
			//System.out.println("Saving ChunkLoader data...");
	        FileOutputStream fos = new FileOutputStream(getChunkSaveFile().getAbsolutePath());
	        GZIPOutputStream gzos = new GZIPOutputStream(fos);
	        ObjectOutputStream out = new ObjectOutputStream(gzos);
	        out.writeObject(mod_AdditionalPipes.keepLoadedChunks);
	        out.flush();
	        out.close();
	        //System.out.println("Saved ChunkLoader data...");
	     }
	     catch (IOException e) {
	    	 e.printStackTrace(); 
	     }
	}
	@SuppressWarnings("unchecked")
	public static void LoadChunkData() {
		if (!NeedsLoad)
			return;
		NeedsLoad = false;
		 try {
		        FileInputStream fis = new FileInputStream(getChunkSaveFile().getAbsolutePath());
		        GZIPInputStream gzis = new GZIPInputStream(fis);
		        ObjectInputStream in = new ObjectInputStream(gzis);
		        List<mod_AdditionalPipes.chunkXZ> loaded = (List<mod_AdditionalPipes.chunkXZ>)in.readObject();
		        in.close();
		        mod_AdditionalPipes.keepLoadedChunks = loaded;
		        System.out.println("Loaded " + loaded.size() + " Forced Chunks");
		      }
		      catch (Exception e) {
		          e.printStackTrace();
		      }
	}
	public static void DeleteChunkFromList(int x, int z) {
		if (isOnServer())
			return;
		MutiPlayerProxy.LoadChunkData();
		x = x >> 4;
		z = z >> 4;
		Iterator<chunkXZ> chunks = mod_AdditionalPipes.keepLoadedChunks.iterator();
		while (chunks.hasNext()) {
			chunkXZ curChunk = (chunkXZ)chunks.next();
			if (curChunk.x == x && curChunk.z == z) {
				mod_AdditionalPipes.keepLoadedChunks.remove(curChunk);
				//System.out.println("Removed PermChunk @ " + x + "," + z);
				SaveChunkData();
				return;
			}
				
		}
	}
	public static File getChunkSaveFile() {
		//With MC being dumb we have to load the world location ourselfs
		if (WorldDir == null)
			WorldDir = mod_AdditionalPipes.getSaveDirectory();
		return new File(WorldDir, "ChunkLoader.doNotTouch");
	}
	
}

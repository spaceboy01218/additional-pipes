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

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.mod_zAdditionalPipes;
import net.minecraft.src.mod_zAdditionalPipes.chunkXZ;
import net.minecraft.src.buildcraft.transport.TileGenericPipe;
import net.minecraft.src.buildcraft.zeldo.gui.ContainerTeleportPipe;
import net.minecraft.src.buildcraft.zeldo.gui.CraftingAdvancedWoodPipe;
import net.minecraft.src.buildcraft.zeldo.pipes.PipeItemTeleport;
import net.minecraft.src.buildcraft.zeldo.pipes.PipeItemsAdvancedWood;
import net.minecraft.src.buildcraft.zeldo.pipes.PipeLiquidsTeleport;
import net.minecraft.src.buildcraft.zeldo.pipes.PipePowerTeleport;

public class MutiPlayerProxy {
	public static boolean NeedsLoad = true;
	public static File WorldDir;
	public static boolean isServer = true;
	public static void displayGUIItemTeleport(EntityPlayer entityplayer, TileGenericPipe tilePipe) {
		ModLoaderMp.SendPacketTo(mod_zAdditionalPipes.instance, (EntityPlayerMP)entityplayer, ((PipeItemTeleport)tilePipe.pipe).getDescPipe());
		ModLoaderMp.SendPacketTo(mod_zAdditionalPipes.instance, (EntityPlayerMP)entityplayer, mod_zAdditionalPipes.getCountPacket(((PipeItemTeleport)tilePipe.pipe).getConnectedPipes(true).size()));
		ModLoader.OpenGUI(entityplayer, mod_zAdditionalPipes.GUI_ITEM_SEND, entityplayer.inventory, new ContainerTeleportPipe());
	}
	public static void displayGUILiquidTeleport(EntityPlayer entityplayer,TileGenericPipe tilePipe) {
		ModLoaderMp.SendPacketTo(mod_zAdditionalPipes.instance, (EntityPlayerMP)entityplayer, ((PipeLiquidsTeleport)tilePipe.pipe).getDescPipe());
		ModLoaderMp.SendPacketTo(mod_zAdditionalPipes.instance, (EntityPlayerMP)entityplayer, mod_zAdditionalPipes.getCountPacket(((PipeLiquidsTeleport)tilePipe.pipe).getConnectedPipes(true).size()));
		ModLoader.OpenGUI(entityplayer, mod_zAdditionalPipes.GUI_LIQUID_SEND, entityplayer.inventory, new ContainerTeleportPipe());
	}
	public static void displayGUIPowerTeleport(EntityPlayer entityplayer, TileGenericPipe tilePipe) {
		ModLoaderMp.SendPacketTo(mod_zAdditionalPipes.instance, (EntityPlayerMP)entityplayer, ((PipePowerTeleport)tilePipe.pipe).getDescPipe());
		ModLoaderMp.SendPacketTo(mod_zAdditionalPipes.instance, (EntityPlayerMP)entityplayer, mod_zAdditionalPipes.getCountPacket(((PipePowerTeleport)tilePipe.pipe).getConnectedPipes(true).size()));
		ModLoader.OpenGUI(entityplayer, mod_zAdditionalPipes.GUI_ENERGY_SEND, entityplayer.inventory, new ContainerTeleportPipe());
	}
	public static void displayGUIAdvancedWood(EntityPlayer entityplayer, TileGenericPipe tilePipe) {
		ModLoaderMp.SendPacketTo(mod_zAdditionalPipes.instance, (EntityPlayerMP)entityplayer, ((PipeItemsAdvancedWood)tilePipe.pipe).getDescPacket());
		ModLoader.OpenGUI(entityplayer, mod_zAdditionalPipes.GUI_ADVANCEDWOOD_SEND, tilePipe, new CraftingAdvancedWoodPipe(entityplayer.inventory, tilePipe));
	}
	public static void requestItemTeleport(int x, int y, int z) {}
	public static boolean isOnServer()
	{
		return true;
	}
	public static void AddChunkToList(int x, int z) {
		MutiPlayerProxy.LoadChunkData();
		x = x >> 4;
		z = z >> 4;
		Iterator<chunkXZ> chunks = mod_zAdditionalPipes.keepLoadedChunks.iterator();
		while (chunks.hasNext()) {
			chunkXZ curChunk = (chunkXZ)chunks.next();
			if (curChunk.x == x && curChunk.z == z) {
				//System.out.println("Didn't need to add PermChunk @ " + x + "," + z);
				return;
			}
				
		}
		mod_zAdditionalPipes.keepLoadedChunks.add(new mod_zAdditionalPipes.chunkXZ(x, z));
		 //System.out.println("Added PermChunk @ " + x + "," + z);
		 SaveChunkData();
	}
	public static void SaveChunkData() {
		try {
			
			//System.out.println("Saving ChunkLoader data...");
	        FileOutputStream fos = new FileOutputStream(getChunkSaveFile().getAbsolutePath());
	        GZIPOutputStream gzos = new GZIPOutputStream(fos);
	        ObjectOutputStream out = new ObjectOutputStream(gzos);
	        out.writeObject(mod_zAdditionalPipes.keepLoadedChunks);
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
		        List<mod_zAdditionalPipes.chunkXZ> loaded = (List<mod_zAdditionalPipes.chunkXZ>)in.readObject();
		        in.close();
		        mod_zAdditionalPipes.keepLoadedChunks = loaded;
		        System.out.println("Loaded " + loaded.size() + " Forced Chunks");
		      }
		      catch (Exception e) {
		          e.printStackTrace();
		      }
	}
	public static void DeleteChunkFromList(int x, int z) {
		MutiPlayerProxy.LoadChunkData();
		x = x >> 4;
		z = z >> 4;
		Iterator<chunkXZ> chunks = mod_zAdditionalPipes.keepLoadedChunks.iterator();
		while (chunks.hasNext()) {
			chunkXZ curChunk = (chunkXZ)chunks.next();
			if (curChunk.x == x && curChunk.z == z) {
				mod_zAdditionalPipes.keepLoadedChunks.remove(curChunk);
				//System.out.println("Removed PermChunk @ " + x + "," + z);
				SaveChunkData();
				return;
			}
				
		}
	}
	public static File getChunkSaveFile() {
		//With MC being dumb we have to load the world location ourselfs
		if (WorldDir == null)
			WorldDir = mod_zAdditionalPipes.getSaveDirectory();
		return new File(WorldDir, "ChunkLoader.doNotTouch");
	}
	public static boolean isOp(String entityplayermp) {
		if(ModLoader.getMinecraftServerInstance().configManager.isOp(entityplayermp))
		{
			return true;
		}
		return false;
	}
	public static void SendPacketToAll(Packet230ModLoader packet)
	{
		ModLoaderMp.SendPacketToAll(mod_zAdditionalPipes.instance, packet);
	}
	public static void SendPacket(Packet230ModLoader packet, EntityPlayer entityplayermp)
	{
		ModLoaderMp.SendPacketTo(mod_zAdditionalPipes.instance, (EntityPlayerMP)entityplayermp, packet);
	}
	public static void bindTex()
	{
	}
}

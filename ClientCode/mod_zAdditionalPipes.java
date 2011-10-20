package net.minecraft.src;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.buildcraft.api.APIProxy;
import net.minecraft.src.buildcraft.api.LaserKind;
import net.minecraft.src.buildcraft.core.Box;
import net.minecraft.src.buildcraft.core.CoreProxy;
import net.minecraft.src.buildcraft.transport.BlockGenericPipe;
import net.minecraft.src.buildcraft.transport.Pipe;
import net.minecraft.src.buildcraft.transport.TileGenericPipe;
import net.minecraft.src.buildcraft.zeldo.MutiPlayerProxy;
import net.minecraft.src.buildcraft.zeldo.gui.GuiAdvancedWoodPipe;
import net.minecraft.src.buildcraft.zeldo.gui.GuiDistributionPipe;
import net.minecraft.src.buildcraft.zeldo.gui.GuiItemTeleportPipe;
import net.minecraft.src.buildcraft.zeldo.gui.GuiLiquidTeleportPipe;
import net.minecraft.src.buildcraft.zeldo.gui.GuiPowerTeleportPipe;
import net.minecraft.src.buildcraft.zeldo.logic.PipeLogicAdvancedWood;
import net.minecraft.src.buildcraft.zeldo.pipes.PipeItemTeleport;
import net.minecraft.src.buildcraft.zeldo.pipes.PipeItemsAdvancedInsertion;
import net.minecraft.src.buildcraft.zeldo.pipes.PipeItemsAdvancedWood;
import net.minecraft.src.buildcraft.zeldo.pipes.PipeItemsDistributor;
import net.minecraft.src.buildcraft.zeldo.pipes.PipeItemsRedstone;
import net.minecraft.src.buildcraft.zeldo.pipes.PipeLiquidsTeleport;
import net.minecraft.src.buildcraft.zeldo.pipes.PipePowerTeleport;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.MinecraftForgeClient;
import net.minecraft.src.forge.Property;



public class mod_zAdditionalPipes extends BaseModMp {
	@SuppressWarnings("serial")
	public static class chunkXZ implements Serializable
	{
		public int x;
		public int z;

		public chunkXZ(int ax, int az)
		{
			x = ax;
			z = az;
		}
	}


	public static int MASTER_TEXTURE_OFFSET = 8 * 16;
	public static String MASTER_TEXTURE_FILE = "/net/minecraft/src/buildcraft/zeldo/gui/block_textures.png";
	public static String MASTER_OVERRIDE_FILE = "/net/minecraft/src/buildcraft/zeldo/gui/block_textures_override.png";

	//Item Teleport
	public static Item pipeItemTeleport;
	public static int DEFUALT_ITEM_TELEPORT_ID = 4047;
	public static int DEFUALT_ITEM_TELEPORT_TEXTURE = 8 * 16 + 0;
	public static String DEFUALT_ITEM_TELEPORT_TEXTURE_FILE = "/net/minecraft/src/buildcraft/zeldo/gui/BlueItem.png";

	//Liquid Teleport
	public static Item pipeLiquidTeleport;
	public static int DEFUALT_LIQUID_TELEPORT_ID = 4048;
	public static int DEFUALT_LIQUID_TELEPORT_TEXTURE = 8 * 16 + 2;
	public static String DEFUALT_LIQUID_TELEPORT_TEXTURE_FILE = "/net/minecraft/src/buildcraft/zeldo/gui/BlueLiquid.png";

	//Power Teleport
	public static Item pipePowerTeleport;
	public static int DEFUALT_POWER_TELEPORT_ID = 4049;
	public static int DEFUALT_POWER_TELEPORT_TEXTURE = 8 * 16 + 3;
	public static String DEFUALT_POWER_TELEPORT_TEXTURE_FILE = "/net/minecraft/src/buildcraft/zeldo/gui/BluePower.png";

	//Distributor
	public static Item pipeDistributor;
	public static int DEFUALT_DISTRIBUTOR_TELEPORT_ID = 4046;
	public static int DEFUALT_DISTRIBUTOR_TEXTURE_0 = 8*16+9;
	public static String DEFUALT_DISTRIBUTOR_TEXTURE_FILE_BASE = "/net/minecraft/src/buildcraft/zeldo/gui/Dist";
	public static String DEFUALT_DISTRIBUTOR_TEXTURE_FILE = "/net/minecraft/src/buildcraft/zeldo/gui/DistributionOpen.png";
	public static String DEFUALT_DISTRIBUTOR_TEXTURE_FILE_CLOSED = "/net/minecraft/src/buildcraft/zeldo/gui/DistributionClosed.png";

	//Advanced Wood
	public static Item pipeAdvancedWood;
	public static int DEFUALT_ADVANCEDWOOD_ID = 4045;
	public static int DEFUALT_ADVANCEDWOOD_TEXTURE = 8*16+6;
	public static int DEFUALT_ADVANCEDWOOD_TEXTURE_CLOSED = 8*16+7;
	public static String DEFUALT_ADVANCEDWOOD_FILE = "/net/minecraft/src/buildcraft/zeldo/gui/AdvancedWood.png";
	public static String DEFUALT_ADVANCEDWOOD_FILE_CLOSED = "/net/minecraft/src/buildcraft/zeldo/gui/AdvancedWoodClosed.png";

	//Advanced Insertion
	public static Item pipeAdvancedInsertion;
	public static int DEFUALT_Insertion_ID = 4044;
	public static int DEFUALT_Insertion_TEXTURE = 8*16+8;
	public static String DEFUALT_Insertion_FILE = "/net/minecraft/src/buildcraft/zeldo/gui/AdvInsert.png";

	//Redstone
	public static Item pipeRedStone;
	public static int DEFUALT_RedStone_ID = 4043;
	public static int DEFUALT_RedStone_TEXTURE = 8*16+4;
	public static int DEFUALT_RedStone_TEXTURE_POWERED = 8*16+5;
	public static String DEFUALT_RedStone_FILE = "/net/minecraft/src/buildcraft/zeldo/gui/RS.png";
	public static String DEFUALT_RedStone_FILE_POWERED = "/net/minecraft/src/buildcraft/zeldo/gui/RSP.png";

	//Redstone ticker

	//GUI Packet Ids
	public static int GUI_ITEM_SEND = 255;
	public static int GUI_LIQUID_SEND = 254;
	public static int GUI_ENERGY_SEND = 253;
	public static int GUI_ADVANCEDWOOD_SEND = 252;
	public static int GUI_ITEM_REC = -1;
	public static int GUI_LIQUID_REC = -2;
	public static int GUI_ENERGY_REC = -3;
	public static int GUI_ADVANCEDWOOD_REC = -4;

	//Main Packet ID's
	public static int PACKET_SET_AW = 1;
	public static int PACKET_SET_ITEM = 2;
	public static int PACKET_SET_LIQUID = 3;
	public static int PACKET_SET_POWER = 4;
	public static int PACKET_REQ_ITEM = 5;
	public static int PACKET_REQ_LIQUID = 6;
	public static int PACKET_REQ_POWER = 7;
	public static int PACKET_GUI_COUNT = 8;
	public static int PACKET_OPEN_GUI = 9;
	public static int PACKET_SET_DIST = 10;

	public static int CurrentGUICount = 0;




	private static Configuration config;
	public static mod_zAdditionalPipes instance;
	public static boolean isInGame = false;
	public static boolean lagFix = false;
	public static boolean wrenchOpensGui = false;
	public static boolean allowWPRemove = false; //Remove waterproofing/redstone


	//ChunkLoader Variables
	public int chunkTestTime = 500;
	public long lastCheckTime = 0;
	public static List<chunkXZ> keepLoadedChunks = new ArrayList<chunkXZ>();

	public static List<Integer> pipeIds = new LinkedList<Integer>();

	public KeyBinding key_lasers = new KeyBinding("key_lasers", 67); //F9
	public static boolean lasers_showing = false;
	public static List<Box> laser_box = new LinkedList<Box>();

	public void KeyboardEvent(KeyBinding keybinding)
	{
		System.out.println("Keyboard Event");
		Minecraft mc = ModLoader.getMinecraftInstance();

		if(keybinding == this.key_lasers)
		{
			if (!lasers_showing)
			{
				System.out.println("Lasers on...");
				lasers_showing = true;
				for (int i=0; i<keepLoadedChunks.size(); i++)
				{
					Box temp[] = new Box[5];

					int y = (int) mc.thePlayer.posY;
					temp[0] = new Box();
					temp[1] = new Box();
					temp[2] = new Box();
					temp[3] = new Box();
					temp[4] = new Box();

					temp[0].initialize(keepLoadedChunks.get(i).x * 16, y, keepLoadedChunks.get(i).z * 16, keepLoadedChunks.get(i).x * 16 + 16, y, keepLoadedChunks.get(i).z * 16 + 16);
					temp[1].initialize((keepLoadedChunks.get(i).x - 1) * 16, y, keepLoadedChunks.get(i).z * 16, (keepLoadedChunks.get(i).x - 1) * 16 + 16, y, keepLoadedChunks.get(i).z * 16 + 16);
					temp[2].initialize((keepLoadedChunks.get(i).x + 1) * 16, y, keepLoadedChunks.get(i).z * 16, (keepLoadedChunks.get(i).x + 1) * 16 + 16, y, keepLoadedChunks.get(i).z * 16 + 16);
					temp[3].initialize(keepLoadedChunks.get(i).x * 16, y, (keepLoadedChunks.get(i).z + 1) * 16, keepLoadedChunks.get(i).x * 16 + 16, y, (keepLoadedChunks.get(i).z + 1) * 16 + 16);
					temp[4].initialize(keepLoadedChunks.get(i).x * 16, y, (keepLoadedChunks.get(i).z - 1) * 16, keepLoadedChunks.get(i).x * 16 + 16, y, (keepLoadedChunks.get(i).z - 1) * 16 + 16);

					for (int a=0; a< temp.length; a++)
					{
						if (!laser_box.contains(temp[a])) { //Dont want to display the same one 6 times now do we? :p
							temp[a].createLasers(mc.theWorld, LaserKind.Blue);
							laser_box.add(temp[a]);
						}
					}


				}
			} else
			{
				System.out.println("Lasers off...");
				lasers_showing = false;
				List<Box> delete = new LinkedList<Box>();
				for (int i=0; i<laser_box.size(); i++)
				{
					Box temp = laser_box.get(i);
					temp.deleteLasers();
					delete.add(temp);
				}
				laser_box.removeAll(delete);
			}
		}
	}

	public mod_zAdditionalPipes() {
		ModLoader.SetInGameHook(this, true, true);
		ModLoader.SetInGUIHook(this, true, true);
		ModLoader.RegisterKey(this, this.key_lasers, false);
		ModLoader.AddLocalization("key_lasers", "Turn on/off chunk loader boundries");
	}
	@Override
	public boolean OnTickInGame(Minecraft minecraft)
	{
		if (MutiPlayerProxy.isOnServer())
			return true;
		if (System.currentTimeMillis() - chunkTestTime >= lastCheckTime) {
			lastCheckTime = System.currentTimeMillis();
			if (lagFix) {
				minecraft.theWorld.autosavePeriod = 6000;
			}
			Iterator<chunkXZ> chunks = keepLoadedChunks.iterator();
			while (chunks.hasNext()) {
				chunkXZ curChunk = chunks.next();
				if (minecraft.theWorld.chunkProvider.chunkExists(curChunk.x,curChunk.z)) {
					//System.out.print("A: " + minecraft.theWorld.chunkProvider.provideChunk(curChunk.x,curChunk.z).isChunkLoaded + "\n");
				} else {
					//System.out.print("Does not exist...\n");
					minecraft.theWorld.chunkProvider.provideChunk(curChunk.x,curChunk.z);
					minecraft.theWorld.chunkProvider.provideChunk(curChunk.x-1,curChunk.z);
					minecraft.theWorld.chunkProvider.provideChunk(curChunk.x+1,curChunk.z);
					minecraft.theWorld.chunkProvider.provideChunk(curChunk.x,curChunk.z-1);
					minecraft.theWorld.chunkProvider.provideChunk(curChunk.x,curChunk.z+1);
					//System.out.println("Loaded Chunk @ " + curChunk.x + "," + curChunk.z);
				}
			}

		}
		return true;
	}
	@Override
	public boolean OnTickInGUI(Minecraft minecraft, GuiScreen guiscreen)
	{

		//System.out.print("World: " + (minecraft.theWorld == null) + "\n");
		if (minecraft.theWorld == null)
		{

			if (isInGame)
			{
				//System.out.print("Cleared TeleportPipes...\n");
				PipeItemTeleport.ItemTeleportPipes.clear();
				PipeLiquidsTeleport.LiquidTeleportPipes.clear();
				PipePowerTeleport.PowerTeleportPipes.clear();
				keepLoadedChunks.clear();
				MutiPlayerProxy.NeedsLoad = true;
				isInGame = false;
			}
		} else {
			isInGame = true;
		}
		return true;
	}
	public static File getSaveDirectory() { return ((SaveHandler)ModLoader.getMinecraftInstance().theWorld.saveHandler).getSaveDirectory(); }
	@Override
	public void ModsLoaded () {
		ModLoaderMp.RegisterGUI(this, GUI_ADVANCEDWOOD_REC);
		ModLoaderMp.RegisterGUI(this, GUI_ENERGY_REC);
		ModLoaderMp.RegisterGUI(this, GUI_ITEM_REC);
		ModLoaderMp.RegisterGUI(this, GUI_LIQUID_REC);
		instance = this;

		config = new Configuration(new File(CoreProxy.getBuildCraftBase(), "config/AdditionalPipes.cfg"));
		config.load();

		lagFix = Boolean.parseBoolean(config.getOrCreateBooleanProperty("saveLagFix", Configuration.GENERAL_PROPERTY, false).value);
		wrenchOpensGui = Boolean.parseBoolean(config.getOrCreateBooleanProperty("wrenchOpensGui", Configuration.GENERAL_PROPERTY, false).value);
		allowWPRemove = Boolean.parseBoolean(config.getOrCreateBooleanProperty("EnableWaterProofRemoval", Configuration.GENERAL_PROPERTY, false).value);

		config.save();

		AddImageOverride();
		MinecraftForgeClient.preloadTexture(mod_zAdditionalPipes.MASTER_TEXTURE_FILE);
		MinecraftForgeClient.preloadTexture(mod_zAdditionalPipes.MASTER_OVERRIDE_FILE);

		pipeItemTeleport = createPipe (mod_zAdditionalPipes.DEFUALT_ITEM_TELEPORT_ID, PipeItemTeleport.class, "Item Teleport Pipe", BuildCraftCore.diamondGearItem, Block.glass, BuildCraftCore.diamondGearItem, null);
		pipeLiquidTeleport = createPipe (mod_zAdditionalPipes.DEFUALT_LIQUID_TELEPORT_ID, PipeLiquidsTeleport.class, "Waterproof Teleport Pipe", BuildCraftTransport.pipeWaterproof, pipeItemTeleport, null, null);
		pipePowerTeleport = createPipe (mod_zAdditionalPipes.DEFUALT_POWER_TELEPORT_ID, PipePowerTeleport.class, "Power Teleport Pipe", Item.redstone, pipeItemTeleport, null, null);
		pipeDistributor = createPipe(mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TELEPORT_ID, PipeItemsDistributor.class, "Distribution Transport Pipe", Item.redstone, Item.ingotIron, Block.glass, Item.ingotIron);
		pipeAdvancedWood = createPipe(mod_zAdditionalPipes.DEFUALT_ADVANCEDWOOD_ID, PipeItemsAdvancedWood.class, "Advanced Wooden Transport Pipe", Item.redstone, Block.planks, Block.glass, Block.planks);
		pipeAdvancedInsertion = createPipe(mod_zAdditionalPipes.DEFUALT_Insertion_ID, PipeItemsAdvancedInsertion.class, "Advanced Insertion Transport Pipe", Item.redstone, Block.stone, Block.glass, Block.stone);
		pipeRedStone = createPipe(mod_zAdditionalPipes.DEFUALT_RedStone_ID, PipeItemsRedstone.class, "Redstone Transport Pipe", Item.redstone, Block.glass, Item.redstone, null);

		MinecraftForgeClient.registerCustomItemRenderer(pipeItemTeleport.shiftedIndex, mod_BuildCraftTransport.instance);
		MinecraftForgeClient.registerCustomItemRenderer(pipeLiquidTeleport.shiftedIndex, mod_BuildCraftTransport.instance);
		MinecraftForgeClient.registerCustomItemRenderer(pipePowerTeleport.shiftedIndex, mod_BuildCraftTransport.instance);
		MinecraftForgeClient.registerCustomItemRenderer(pipeDistributor.shiftedIndex, mod_BuildCraftTransport.instance);
		MinecraftForgeClient.registerCustomItemRenderer(pipeAdvancedWood.shiftedIndex, mod_BuildCraftTransport.instance);
		MinecraftForgeClient.registerCustomItemRenderer(pipeAdvancedInsertion.shiftedIndex, mod_BuildCraftTransport.instance);
		MinecraftForgeClient.registerCustomItemRenderer(pipeRedStone.shiftedIndex, mod_BuildCraftTransport.instance);

		if (allowWPRemove)
		{
			CraftingManager craftingmanager = CraftingManager.getInstance();

			//Mine
			craftingmanager.addRecipe(new ItemStack(pipeItemTeleport, 1), new Object[] {"A", Character.valueOf('A'), pipeLiquidTeleport});
			craftingmanager.addRecipe(new ItemStack(pipeItemTeleport, 1), new Object[] {"A", Character.valueOf('A'), pipePowerTeleport});

			//BC Liquid
			craftingmanager.addRecipe(new ItemStack(BuildCraftTransport.pipeItemsCobblestone, 1), new Object[] {"A", Character.valueOf('A'), BuildCraftTransport.pipeLiquidsCobblestone});
			craftingmanager.addRecipe(new ItemStack(BuildCraftTransport.pipeItemsGold, 1), new Object[] {"A", Character.valueOf('A'), BuildCraftTransport.pipeLiquidsGold});
			craftingmanager.addRecipe(new ItemStack(BuildCraftTransport.pipeItemsIron, 1), new Object[] {"A", Character.valueOf('A'), BuildCraftTransport.pipeLiquidsIron});
			craftingmanager.addRecipe(new ItemStack(BuildCraftTransport.pipeItemsStone, 1), new Object[] {"A", Character.valueOf('A'), BuildCraftTransport.pipeLiquidsStone});
			craftingmanager.addRecipe(new ItemStack(BuildCraftTransport.pipeItemsWood, 1), new Object[] {"A", Character.valueOf('A'), BuildCraftTransport.pipeLiquidsWood});

			//BC Power
			craftingmanager.addRecipe(new ItemStack(BuildCraftTransport.pipeItemsGold, 1), new Object[] {"A", Character.valueOf('A'), BuildCraftTransport.pipePowerGold});
			craftingmanager.addRecipe(new ItemStack(BuildCraftTransport.pipeItemsStone, 1), new Object[] {"A", Character.valueOf('A'), BuildCraftTransport.pipePowerStone});
			craftingmanager.addRecipe(new ItemStack(BuildCraftTransport.pipeItemsWood, 1), new Object[] {"A", Character.valueOf('A'), BuildCraftTransport.pipePowerWood});
		}

		RegisterPipeIds();

	}
	@SuppressWarnings("rawtypes")
	public static void AddImageOverride()
	{
		try {
			HashMap textures = new HashMap();
			textures = (HashMap) ModLoader.getPrivateValue(RenderEngine.class, ModLoader.getMinecraftInstance().renderEngine, 1);
			int i = (Integer) textures.get(BuildCraftCore.customBuildCraftTexture);
			ModTextureStatic modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_ITEM_TELEPORT_TEXTURE, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_ITEM_TELEPORT_TEXTURE_FILE));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_LIQUID_TELEPORT_TEXTURE, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_LIQUID_TELEPORT_TEXTURE_FILE));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_POWER_TELEPORT_TEXTURE, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_POWER_TELEPORT_TEXTURE_FILE));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_RedStone_TEXTURE, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_RedStone_FILE));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_RedStone_TEXTURE_POWERED, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_RedStone_FILE_POWERED));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_ADVANCEDWOOD_TEXTURE_CLOSED, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_ADVANCEDWOOD_FILE_CLOSED));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_ADVANCEDWOOD_TEXTURE, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_ADVANCEDWOOD_FILE));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_Insertion_TEXTURE, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_Insertion_FILE));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_0, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_FILE_BASE + "0.png"));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_0 + 1, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_FILE_BASE + "1.png"));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_0 + 2, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_FILE_BASE + "2.png"));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_0 + 3, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_FILE_BASE + "3.png"));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_0 + 4, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_FILE_BASE + "4.png"));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
			modtexturestatic = new ModTextureStatic(mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_0 + 5, i, ModLoader.loadImage(ModLoader.getMinecraftInstance().renderEngine, mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_FILE_BASE + "5.png"));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(modtexturestatic);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public GuiScreen HandleGUI(int inventoryType)
	{
		//System.out.println("InvType: " + inventoryType);
		if(inventoryType == GUI_LIQUID_REC)
			return new GuiLiquidTeleportPipe((TileGenericPipe)ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(ModLoader.getMinecraftInstance().objectMouseOver.blockX, ModLoader.getMinecraftInstance().objectMouseOver.blockY, ModLoader.getMinecraftInstance().objectMouseOver.blockZ));
		else if (inventoryType == GUI_ITEM_REC)
			return new GuiItemTeleportPipe((TileGenericPipe)ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(ModLoader.getMinecraftInstance().objectMouseOver.blockX, ModLoader.getMinecraftInstance().objectMouseOver.blockY, ModLoader.getMinecraftInstance().objectMouseOver.blockZ));
		else if (inventoryType == GUI_ENERGY_REC)
			return new GuiPowerTeleportPipe((TileGenericPipe)ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(ModLoader.getMinecraftInstance().objectMouseOver.blockX, ModLoader.getMinecraftInstance().objectMouseOver.blockY, ModLoader.getMinecraftInstance().objectMouseOver.blockZ));
		else if (inventoryType == GUI_ADVANCEDWOOD_REC)
		{
			TileGenericPipe tmp = new TileGenericPipe();
			tmp.pipe = new PipeItemsAdvancedWood(pipeAdvancedWood.shiftedIndex);
			return new GuiAdvancedWoodPipe(ModLoader.getMinecraftInstance().thePlayer.inventory, tmp, (TileGenericPipe)ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(ModLoader.getMinecraftInstance().objectMouseOver.blockX, ModLoader.getMinecraftInstance().objectMouseOver.blockY, ModLoader.getMinecraftInstance().objectMouseOver.blockZ));

		}


		return null;
	}

	public byte [] ReDim(byte [] array, int newSize)
	{
		byte [] abytNew = new byte[newSize];
		System.arraycopy(array, 0, abytNew, 0, array.length);
		return abytNew;
	}
	public int getTileSize() {
		try {
			return Class.forName("com.pclewis.mcpatcher.mod.TileSize").getField("int_numBytes").getInt(Class.forName("com.pclewis.mcpatcher.mod.TileSize").getClass());
		} catch( ClassNotFoundException e ) {
			return 1024;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1024;
	}

	@Override
	public String Version() {
		return "2.0Dev2";
	}
	@Override
	public void HandlePacket(Packet230ModLoader packet) {
		System.out.println("Packet: " + packet.packetType);
		if (packet.packetType == PACKET_SET_AW) {
			int x = packet.dataInt [0];
			int y = packet.dataInt [1];
			int z = packet.dataInt [2];
			if (APIProxy.getWorld().blockExists(x, y, z)) {
				TileGenericPipe tile = (TileGenericPipe) APIProxy.getWorld().getBlockTileEntity(x, y, z);
				boolean Exclude = intToBool(packet.dataInt[3]);
				((PipeLogicAdvancedWood)tile.pipe.logic).exclude = Exclude;
			}
		}
		if (packet.packetType == PACKET_SET_ITEM) {
			int x = packet.dataInt [0];
			int y = packet.dataInt [1];
			int z = packet.dataInt [2];
			if (APIProxy.getWorld().blockExists(x, y, z)) {
				TileGenericPipe tile = (TileGenericPipe) APIProxy.getWorld().getBlockTileEntity(x, y, z);
				int freq = packet.dataInt[3];
				boolean canRec = intToBool(packet.dataInt[4]);
				String own = packet.dataString[0];
				((PipeItemTeleport)tile.pipe).canReceive = canRec;
				((PipeItemTeleport)tile.pipe).myFreq = freq;
				((PipeItemTeleport)tile.pipe).Owner = own;
			}
		}
		if (packet.packetType == PACKET_SET_LIQUID) {
			int x = packet.dataInt [0];
			int y = packet.dataInt [1];
			int z = packet.dataInt [2];
			if (APIProxy.getWorld().blockExists(x, y, z)) {
				TileGenericPipe tile = (TileGenericPipe) APIProxy.getWorld().getBlockTileEntity(x, y, z);
				int freq = packet.dataInt[3];
				boolean canRec = intToBool(packet.dataInt[4]);
				String own = packet.dataString[0];
				((PipeLiquidsTeleport)tile.pipe).canReceive = canRec;
				((PipeLiquidsTeleport)tile.pipe).myFreq = freq;
				((PipeLiquidsTeleport)tile.pipe).Owner = own;
			}
		}
		if (packet.packetType == PACKET_SET_POWER) {
			int x = packet.dataInt [0];
			int y = packet.dataInt [1];
			int z = packet.dataInt [2];
			if (APIProxy.getWorld().blockExists(x, y, z)) {
				TileGenericPipe tile = (TileGenericPipe) APIProxy.getWorld().getBlockTileEntity(x, y, z);
				int freq = packet.dataInt[3];
				boolean canRec = intToBool(packet.dataInt[4]);
				String own = packet.dataString[0];
				((PipePowerTeleport)tile.pipe).canReceive = canRec;
				((PipePowerTeleport)tile.pipe).myFreq = freq;
				((PipePowerTeleport)tile.pipe).Owner = own;
			}
		}
		if (packet.packetType == PACKET_GUI_COUNT) {
			CurrentGUICount = packet.dataInt[0];
		}
		if (packet.packetType == PACKET_OPEN_GUI) {
			int x = packet.dataInt [0];
			int y = packet.dataInt [1];
			int z = packet.dataInt [2];
			TileGenericPipe tilePipe = (TileGenericPipe) APIProxy.getWorld().getBlockTileEntity(x, y, z);
			switch (packet.dataInt[3])
			{
			case 0:
				ModLoader.getMinecraftInstance().displayGuiScreen(new GuiItemTeleportPipe(tilePipe));
				break;
			case 1:
				ModLoader.getMinecraftInstance().displayGuiScreen(new GuiLiquidTeleportPipe(tilePipe));
				break;
			case 2:
				ModLoader.getMinecraftInstance().displayGuiScreen(new GuiPowerTeleportPipe(tilePipe));
				break;
			case 3:
				ModLoader.getMinecraftInstance().displayGuiScreen(new GuiDistributionPipe(tilePipe));
				break;
			}
		}
		if (packet.packetType == PACKET_SET_DIST) {
			int x = packet.dataInt [0];
			int y = packet.dataInt [1];
			int z = packet.dataInt [2];
			if (APIProxy.getWorld().blockExists(x, y, z)) {
				TileGenericPipe tile = (TileGenericPipe) APIProxy.getWorld().getBlockTileEntity(x, y, z);
				PipeItemsDistributor a = (PipeItemsDistributor) tile.pipe;
				for (int i=0; i<a.distData.length; i++)
				{
					a.distData[i] = packet.dataInt[3+i];
				}
			}
		}
	}
	public static boolean intToBool(int a) {
		return (a == 1);
	}

	public static int boolToInt(boolean a) {
		if (a)
			return 1;
		return 0;
	}
	private static Item createPipe (int defaultID, Class <? extends Pipe> clas, String descr, Object r1, Object r2, Object r3, Object r4) {
		String name = Character.toLowerCase(clas.getSimpleName().charAt(0))
				+ clas.getSimpleName().substring(1);

		Property prop = config
				.getOrCreateIntProperty(name + ".id",
						Configuration.ITEM_PROPERTY, defaultID);
		Property propLoad = config
				.getOrCreateBooleanProperty(name + ".Enabled",
						Configuration.ITEM_PROPERTY, true);
		config.save();
		int id = Integer.parseInt(prop.value);
		Item res =  BlockGenericPipe.registerPipe (id, clas);
		res.setItemName(clas.getSimpleName());
		CoreProxy.addName(res, descr);

		if (!Boolean.parseBoolean(propLoad.value))
			return res;

		CraftingManager craftingmanager = CraftingManager.getInstance();
		if (r1 != null && r2 != null && r3 != null && r4 != null) {
			craftingmanager.addRecipe(new ItemStack(res, 8), new Object[] {
				" D ", "ABC", "   ",
				Character.valueOf('D'), r1,
				Character.valueOf('A'), r2,
				Character.valueOf('B'), r3,
				Character.valueOf('C'), r4});
		} else if (r1 != null && r2 != null && r3 != null) {
			craftingmanager.addRecipe(new ItemStack(res, 8), new Object[] {
				"   ", "ABC", "   ",
				Character.valueOf('A'), r1,
				Character.valueOf('B'), r2,
				Character.valueOf('C'), r3});
		} else if (r1 != null && r2 != null) {
			craftingmanager.addRecipe(new ItemStack(res, 1), new Object[] {
				"A ", "B ",
				Character.valueOf('A'), r1,
				Character.valueOf('B'), r2});
		}

		return res;
	}
	public static void RegisterPipeIds()
	{
		pipeIds.add(BuildCraftTransport.pipeItemsCobblestone.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipeItemsDiamond.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipeItemsGold.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipeItemsIron.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipeItemsObsidian.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipeItemsStone.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipeItemsWood.shiftedIndex);

		pipeIds.add(BuildCraftTransport.pipeLiquidsCobblestone.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipeLiquidsGold.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipeLiquidsIron.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipeLiquidsStone.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipeLiquidsWood.shiftedIndex);

		pipeIds.add(BuildCraftTransport.pipePowerGold.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipePowerStone.shiftedIndex);
		pipeIds.add(BuildCraftTransport.pipePowerWood.shiftedIndex);

		pipeIds.add(mod_zAdditionalPipes.pipeAdvancedInsertion.shiftedIndex);
		pipeIds.add(mod_zAdditionalPipes.pipeAdvancedWood.shiftedIndex);
		pipeIds.add(mod_zAdditionalPipes.pipeDistributor.shiftedIndex);
		pipeIds.add(mod_zAdditionalPipes.pipeItemTeleport.shiftedIndex);
		pipeIds.add(mod_zAdditionalPipes.pipeLiquidTeleport.shiftedIndex);
		pipeIds.add(mod_zAdditionalPipes.pipePowerTeleport.shiftedIndex);
	}
	public static boolean ItemIsPipe(int ItemID)
	{
		if (pipeIds.contains(ItemID))
			return true;
		return false;
	}
}

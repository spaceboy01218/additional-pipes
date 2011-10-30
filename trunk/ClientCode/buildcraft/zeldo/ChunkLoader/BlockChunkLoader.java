package net.minecraft.src.buildcraft.zeldo.ChunkLoader;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.buildcraft.zeldo.MutiPlayerProxy;


// Referenced classes of package net.minecraft.src:
//            Block, Material

public class BlockChunkLoader extends Block
{

	public BlockChunkLoader()
	{
		super(254, 12 * 16 + 1, Material.cloth);
	}

	public void onBlockAdded(World world, int i, int j, int k)
	{
		MutiPlayerProxy.AddChunkToList(i, k);
	}

	public void onBlockRemoval(World world, int i, int j, int k)
	{
		MutiPlayerProxy.DeleteChunkFromList(i, k);
	}

}
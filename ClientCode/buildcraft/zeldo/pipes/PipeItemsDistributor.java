/** 
 * BuildCraft is open-source. It is distributed under the terms of the 
 * BuildCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 */

package net.minecraft.src.buildcraft.zeldo.pipes;

import java.util.LinkedList;

import net.minecraft.src.mod_zAdditionalPipes;
import net.minecraft.src.buildcraft.api.EntityPassiveItem;
import net.minecraft.src.buildcraft.api.Orientations;
import net.minecraft.src.buildcraft.api.Position;
import net.minecraft.src.buildcraft.core.TileNetworkData;
import net.minecraft.src.buildcraft.transport.IPipeTransportItemsHook;
import net.minecraft.src.buildcraft.transport.Pipe;
import net.minecraft.src.buildcraft.transport.PipeTransportItems;
import net.minecraft.src.buildcraft.zeldo.logic.PipeLogicDistributor;

public class PipeItemsDistributor extends Pipe implements IPipeTransportItemsHook {
	
	private int baseTexture = mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE;
	private int plainTexture = mod_zAdditionalPipes.DEFUALT_DISTRIBUTOR_TEXTURE_CLOSED;
	private @TileNetworkData int nextTexture = baseTexture;

	public PipeItemsDistributor(int itemID) {
		super(new PipeTransportItems(), new PipeLogicDistributor(), itemID);
	}
	
	@Override
	public void prepareTextureFor(Orientations connection) {
		if (connection == Orientations.Unknown) {
			nextTexture = baseTexture;
		} else {
			int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

			if (metadata == connection.ordinal()) {
				nextTexture = baseTexture;
			} else {
				nextTexture = plainTexture;
			}
		}
	}
	
	@Override
	public int getBlockTexture() {
		return nextTexture;
	}

	@Override
	public LinkedList<Orientations> filterPossibleMovements(
			LinkedList<Orientations> possibleOrientations, Position pos,
			EntityPassiveItem item) {
		//System.out.println("Filter: " + item.entityId);
		((PipeLogicDistributor)this.logic).switchPosition();
		worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		return possibleOrientations;
	}

	@Override
	public void entityEntered(EntityPassiveItem item, Orientations orientation) {	
		
	}

	@Override
	public void readjustSpeed(EntityPassiveItem item) {
	}

}

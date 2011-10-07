package net.minecraft.src.buildcraft.zeldo.gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.buildcraft.transport.TileGenericPipe;
import net.minecraft.src.buildcraft.zeldo.pipes.PipeLiquidsTeleport;

import org.lwjgl.opengl.GL11;

public class GuiLiquidTeleportPipe extends GuiContainer {
	
	protected int xSize;
	protected int ySize;
	private PipeLiquidsTeleport actualPipe;
	private GuiButton[] buttons = new GuiButton[7];
	public int guiX = 0;
	public int guiY = 0;
	
	public GuiLiquidTeleportPipe(TileGenericPipe thisPipe) {
		super(new ContainerTeleportPipe());
		actualPipe = (PipeLiquidsTeleport)thisPipe.pipe;
		xSize = 228;
		ySize = 117;
	}
    @SuppressWarnings("unchecked")
	public void initGui()
    {
    	int bw = this.xSize - 20;
        int guiX = (width - this.xSize) / 2;
        int guiY = (height - this.ySize) / 2;

        controlList.add(this.buttons[0] =  new GuiButton(1, guiX + 10, guiY + 20, bw / 6, 20, "-100"));
        controlList.add(this.buttons[1] =  new GuiButton(2, guiX + 12 + bw / 6, guiY + 20, bw / 6, 20, "-10"));
        controlList.add(this.buttons[2] =  new GuiButton(3, guiX + 12 + bw * 2 / 6, guiY + 20, bw / 6, 20, "-1"));
        controlList.add(this.buttons[3] =  new GuiButton(4, guiX + 12 + bw * 3 / 6, guiY + 20, bw / 6, 20, "+1"));
        controlList.add(this.buttons[4] =  new GuiButton(5, guiX + 12 + bw * 4 / 6, guiY + 20, bw / 6, 20, "+10"));
        controlList.add(this.buttons[5] =  new GuiButton(6, guiX + 16 + bw * 5 / 6, guiY + 20, bw / 6, 20, "+100"));
        controlList.add(this.buttons[6] =  new GuiButton(7, guiX + 16, guiY + 52, bw / 6, 20, "Switch"));
    }
    protected void drawGuiContainerForegroundLayer()
    {
    	int i = mc.renderEngine.getTexture("/net/minecraft/src/buildcraft/zeldo/gui/gui.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(i);
		int j = guiX-25;
		int k = guiY+25;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		
		fontRenderer.drawString("Frequency: " + actualPipe.myFreq, j + 8, k + 6, 0x404040);
		fontRenderer.drawString("Connected Pipes: " + actualPipe.getConnectedPipes(true).size(), j + 100, k + 6, 0x404040);
		fontRenderer.drawString("Can Receive: " + actualPipe.canReceive, j + 8, k + 42, 0x404040);
		//fontRenderer.drawString("Owner: " + actualPipe.Owner, j + 8, k + 75, 0x404040);
		
		super.drawGuiContainerForegroundLayer();
        //fontRenderer.drawString(filterInventory.getInvName(), 8, 6, 0x404040);
        //fontRenderer.drawString(playerInventory.getInvName(), 8, ySize - 97, 0x404040);        
    }
    protected void actionPerformed(GuiButton guibutton) {
    	switch(guibutton.id) {
    	case 1:
    		actualPipe.myFreq -= 100;
    		break;
    	case 2:
    		actualPipe.myFreq -= 10;
    		break;
    	case 3:
    		actualPipe.myFreq -= 1;
    		break;
    	case 4:
    		actualPipe.myFreq += 1;
    		break;
    	case 5:
    		actualPipe.myFreq += 10;
    		break;
    	case 6:
    		actualPipe.myFreq += 100;
    		break;
    	case 7:
    		actualPipe.canReceive = !actualPipe.canReceive;
    		break;
    	}
    	if (actualPipe.myFreq < 0)
    		actualPipe.myFreq = 0;
    	//ModLoaderMp.SendPacket(mod_AdditionalPipes.instance, actualPipe.getFreqPipe());
    }
	@Override
	protected void drawGuiContainerBackgroundLayer(float arg0) {}

}

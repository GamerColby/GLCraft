package net.codepixl.GLCraft.GUI;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

public class GUIScrollBox extends GUIScreen{
	public int spacing;
	private ArrayList<GUIScreen> items = new ArrayList<GUIScreen>();
	
	public GUIScrollBox(int spacing){
		this.spacing = spacing;
	}
	
	public void addItem(GUIScreen i){
		items.add(i);
	}
	
	public boolean removeItem(GUIScreen i){
		return items.remove(i);
	}
	
	public boolean removeItem(int i){
		return items.remove(i) != null;
	}
	
	@Override
	public void render(){
		glDisable(GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glColorMask(false, false, false, false);
		GL11.glDepthMask(false);
		GL11.glClearStencil(0);
		GL11.glStencilFunc(GL11.GL_NEVER, 1, 0xFF);  //Always fail the stencil test
		GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_KEEP, GL11.GL_KEEP);   //Set the pixels which failed to 1
		GL11.glStencilMask(0xFF);
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        
        GL11.glBegin(GL11.GL_QUADS);
        glVertex2f(0,0);
		glVertex2f(0,height);
		glVertex2f(width,height);
		glVertex2f(width,0);
        GL11.glEnd();
        
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthMask(false);
        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
        
        Iterator<GUIScreen> i = items.iterator();
        while(i.hasNext()){
        	i.next().renderMain();
        }
        
        GL11.glDisable(GL11.GL_STENCIL_TEST);
	}
}

package net.codepixl.GLCraft.world.item;

import net.codepixl.GLCraft.GLCraft;
import net.codepixl.GLCraft.plugin.Plugin;
import net.codepixl.GLCraft.render.TextureManager;
import net.codepixl.GLCraft.world.tile.PluginTile;

public class PluginItem extends Item{
	
	public Plugin plugin;
	public static byte assignedID = -1;
	@Override
	public byte getId(){
		return assignedID;
	}
	
	
	@Override
	public void registerItem(){
		//System.out.println("Attempted to register a plugin item the wrong way!");
	}
	
	public PluginItem(Plugin p){
		this.plugin = p;
		if(this.getClass() != PluginItem.class){
			TextureManager.addExternalTexture("items."+getTextureName(), GLCraft.getGLCraft().getPluginManager().getResourceLocation()+"textures\\items\\"+getTextureName()+".png");
		}
	}
}
package net.codepixl.GLCraft.world.tile;

import com.nishu.utils.Color4f;
import net.codepixl.GLCraft.render.RenderType;
import net.codepixl.GLCraft.render.Shape;
import net.codepixl.GLCraft.render.TextureManager;
import net.codepixl.GLCraft.world.Chunk;
import net.codepixl.GLCraft.world.WorldManager;
import net.codepixl.GLCraft.world.tile.material.Material;
import org.lwjgl.opengl.GL11;

public class TileGlass extends Tile{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Glass";
	}
	
	@Override
	public Material getMaterial(){
		return Material.GLASS;
	}
	
	@Override
	public String getTextureName(){
		return "glass";
	}

	@Override
	public Color4f getColor() {
		// TODO Auto-generated method stub
		return Color4f.WHITE;
	}
	
	@Override
	public float getHardness(){
		return 1f;
	}

	@Override
	public boolean isTransparent() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isTranslucent() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canPassThrough() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public RenderType getRenderType(){
		return RenderType.CUSTOM;
	}
	
	@Override
	public RenderType getCustomRenderType(){
		return RenderType.CUBE;
	}
	
	@Override
	public void customRender(float x, float y, float z, Color4f[] col, WorldManager w, Chunk c){
		float size;
		byte meta = w.getMetaAtPos((int)x, (int)y, (int)z);
		if(meta == 0){
			size = 1f;
		}else if(meta == 8){
			size = 1f;
		}else{
			size = (7f-((float)meta-1f))/7f;
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glScalef(1, size, 1);
		GL11.glBegin(GL11.GL_QUADS);
		/*
		 * bottom - 0,1 (0)
		 * top - 2,3 (1)
		 * front - 4,5 (2)
		 * back - 6,7 (3)
		 * left - 8,9 (4)
		 * right - 10,11 (5)
		 */
		float tempTexCoords[] = new float[24];
		for(int i = 0; i < 6; i++){
			if(sideShouldRender(i,x,y,z,w)){
				tempTexCoords[i*4] = getTexCoords()[0];
				tempTexCoords[i*4+1] = getTexCoords()[1];
				tempTexCoords[i*4+2] = getTexCoords()[2];
				tempTexCoords[i*4+3] = getTexCoords()[3];
			}else{
				tempTexCoords[i*4] = TextureManager.texture("misc.nothing")[0];
				tempTexCoords[i*4+1] = TextureManager.texture("misc.nothing")[1];
				tempTexCoords[i*4+2] = TextureManager.texture("misc.nothing")[2];
				tempTexCoords[i*4+3] = TextureManager.texture("misc.nothing")[3];
			}
		}
		Shape.createCube(0, 0, 0, col, tempTexCoords, 1f);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	private boolean sideShouldRender(int f, float x, float y, float z, WorldManager w){
		if(w.getMetaAtPos(x, y, z) == 8)
			return true;
		switch(f){
			case 0: //BOTTOM
				return (w.getTileAtPos(x,y-1,z) != getId());
			case 1: //TOP
				return (w.getTileAtPos(x,y+1,z) != getId());
			case 2: //FRONT
				return (w.getTileAtPos(x,y,z-1) != getId());
			case 3: //BACK
				return (w.getTileAtPos(x,y,z+1) != getId());
			case 4: //LEFT
				return (w.getTileAtPos(x+1,y,z) != getId());
			case 5: //RIGHT
				return (w.getTileAtPos(x-1,y,z) != getId());
			default:
				return false;
		}
	}

}

package net.codepixl.GLCraft.world.tile;

import com.nishu.utils.Color4f;

import net.codepixl.GLCraft.render.Spritesheet;

public class TileStone extends Tile{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Stone";
	}

	@Override
	public byte getId() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Color4f getColor() {
		// TODO Auto-generated method stub
		return Color4f.WHITE;
	}
	
	@Override
	public float getHardness(){
		return 4f;
	}

	@Override
	public float[] getTexCoords() {
		// TODO Auto-generated method stub
		return new float[]{Spritesheet.tiles.uniformSize()*4,0};
	}

	@Override
	public boolean isTransparent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPassThrough() {
		// TODO Auto-generated method stub
		return false;
	}

}

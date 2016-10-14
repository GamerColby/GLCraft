package net.codepixl.GLCraft.GUI;

import java.util.concurrent.Callable;

import net.codepixl.GLCraft.GLCraft;
import net.codepixl.GLCraft.GUI.Elements.GUIButton;
import net.codepixl.GLCraft.GUI.Elements.GUISlider;
import net.codepixl.GLCraft.GUI.Elements.GUITextBox;
import net.codepixl.GLCraft.render.texturepack.TexturePackManagerWindow;
import net.codepixl.GLCraft.render.util.SettingsManager;
import net.codepixl.GLCraft.util.Constants;

public class GUISettings extends GUIScreen{
	
	private static int CENTERX = Constants.WIDTH/2;
	
	public GUIButton texturepackButton;
	public GUISlider fpsSlider;
	public GUITextBox nameBox;
	
	public GUISettings(){
		this.setDrawStoneBackground(true);
		int ySoFar = 100;
		
		texturepackButton = new GUIButton("Texture Packs", CENTERX, ySoFar, new Callable<Void>(){
			@Override
			public Void call() throws Exception {
				new TexturePackManagerWindow();
				return null;
			}
		});
		
		ySoFar+=GUIButton.BTNHEIGHT/2+20;
		
		fpsSlider = new GUISlider("Max FPS",CENTERX-(300/2), ySoFar, 300, 10, 121, new Callable<Void>(){
			public Void call(){
				int rate = fpsSlider.getVal();
				if(rate > 120){
					Constants.maxFPS = -1;
				}else{
					Constants.maxFPS = rate;
				}
				SettingsManager.setSetting("max_fps", ((Integer)rate).toString());
				return null;
			}
		});
		fpsSlider.setVal(Constants.maxFPS);
		fpsSlider.maxlbl = "No limit";
		
		ySoFar+=GUISlider.HEIGHT+20;
		
		nameBox = new GUITextBox(CENTERX-(Constants.WIDTH/8)-10, ySoFar, Constants.WIDTH/4, "Name");
		nameBox.setFilter("[^a-zA-Z0-9_\\-]");
		nameBox.setText(SettingsManager.getSetting("name"));
		
		nameBox.setCallback(new Callable<Void>(){
			@Override
			public Void call() throws Exception {
				SettingsManager.setSetting("name", nameBox.getText());
				GLCraft.getGLCraft().getWorldManager(false).getPlayer().setName(nameBox.getText());
				return null;
			}
		});
		
		ySoFar+=nameBox.height+20;
		
		addElements(texturepackButton, fpsSlider, nameBox);
	}
	
	@Override
	public void onOpen(){
		super.onOpen();
		nameBox.visible = Constants.GAME_STATE != Constants.GAME;
	}
}
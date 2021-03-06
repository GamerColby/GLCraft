package net.codepixl.GLCraft.GUI.Elements;

import com.nishu.utils.Color4f;
import com.nishu.utils.Time;
import net.codepixl.GLCraft.GUI.GUIManager;
import net.codepixl.GLCraft.render.Shape;
import net.codepixl.GLCraft.render.util.Tesselator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

import java.util.concurrent.Callable;

public class GUITextBox extends GUIElement{
	
	private String placeholder;
	private String text;
	private int length;
	private boolean focused = false;
	private int index = 0;
	private int trans = 0;
	private float cblink = 0;
	private boolean blinkdir = true;
	private Callable<Void> callable;
	private String filter = "";
	public boolean drawOutline = true;
	public Color4f bgColor = Color4f.BLACK;
	public Color4f outlineColor = Color4f.WHITE;
	public Color4f textColor = Color4f.WHITE;
	public Color4f placeholderTextColor = Color4f.GRAY;
	public boolean closeOnUnfocus = false;
	private Callable<Void> enterCallback;
	public int characterLimit = -1;
	
	/**
	 * Something to note is that the length is just the length in pixels of the INSIDE of the text box, the padding is 10px on either side.
	 */
	public GUITextBox(int x, int y, int length, String placeholder){
		this.x = x;
		this.y = y;
		this.placeholder = placeholder;
		this.length = length;
		this.width = length + 20;
		this.height = Tesselator.getFontHeight() + 10;
		this.text = "";
	}
	
	@Override
	public void render(){
		GL11.glPushMatrix();
		Tesselator.stencilArea(0, 0, width, height);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glBegin(GL11.GL_QUADS);
		Shape.createTexturelessRect2D(0, 0, width, height, bgColor);
		GL11.glEnd();
		
		if(drawOutline){
			GL11.glBegin(GL11.GL_LINE_LOOP);
			Shape.createTexturelessRect2D(0, 0, width, height, outlineColor);
			GL11.glEnd();
		}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		if(!focused && text.equals("")){
			Tesselator.drawString(10, 5, placeholder, new Color(placeholderTextColor.r, placeholderTextColor.g, placeholderTextColor.b));
		}else{
			GL11.glTranslatef(-trans, 0, 0);
			Tesselator.drawTextWithShadow(10, 5, text, new Color(textColor.r, textColor.g, textColor.b), Color.darkGray);
			if(cblink > 0.5f && focused)
				Tesselator.drawTextWithShadow(10+Tesselator.getFontWidth(text.substring(0, index)), 7, "_");
		}
		TextureImpl.unbind();
		
		GL11.glColor3f(1f, 1f, 1f);
		
		Tesselator.stencilFinish();
		GL11.glPopMatrix();
	}
	
	@Override public void update(){
		if(blinkdir)
			cblink+=Time.getDelta()*2;
		else
			cblink-=Time.getDelta()*2;
		if(cblink > 1){
			cblink = 1f;
			blinkdir = false;
		}else if(cblink < 0){
			cblink = 0;
			blinkdir = true;
		}
	}
	
	@Override
	public void input(int xof, int yof){
		if(testClick(xof, yof, 0)){
			GUIManager.getMainManager().setFocusedTextBox(this);
		}if(Mouse.isButtonDown(0) && !testMouse(xof, yof)){
			GUIManager.getMainManager().unfocusTextBox();
		}
	}
	
	public void setCallback(Callable<Void> callable){
		this.callable = callable;
	}
	
	public void setPlaceholder(String placeholder){
		this.placeholder = placeholder;
	}
	
	public String getPlaceholder(){
		return this.placeholder;
	}
	
	public void setFilter(String regex){
		this.filter  = regex;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.index = text.length();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
		this.width = length + 20;
	}

	public boolean isFocused() {
		return focused;
	}

	/**
	 * ONLY to be used by GUIManager.
	 */
	public void setFocused(boolean focused) {
		this.focused = focused;
	}
	
	public void textInput(int k, char c){
		if(c == '\b'){
			if(index > 1){
				text = text.substring(0, index-1) + text.substring(index, text.length());
				index--;
			}else{
				text = "";
				index = 0;
			}
		}else if(k == Keyboard.KEY_LEFT){
			if(index > 0)
				index--;
		}else if(k == Keyboard.KEY_RIGHT){
			if(index < text.length())
				index++;
		}else if(k == Keyboard.KEY_RETURN){
			if(this.enterCallback != null)
				try {
					this.enterCallback.call();
				} catch (Exception e) {
					e.printStackTrace();
				}
			return;
		}else if(c >= 32 && (characterLimit == -1 || text.length() <= characterLimit)){
			if(Character.toString(c).matches(filter))
				return;
			text = text.substring(0, index) + c + text.substring(index, text.length());
			index++;
		}
		
		String tindex = text.substring(0,index);
		int tindexWidth = Tesselator.getFontWidth(tindex);
		if(tindexWidth > width-20){
			trans = tindexWidth-width+20;
		}else{
			trans = 0;
		}
		
		if(this.callable != null)
			try {
				this.callable.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void setOnEnterCallback(Callable<Void> callback) {
		this.enterCallback = callback;
	}
}

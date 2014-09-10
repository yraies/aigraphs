package de.pxbox.aingraphs;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class ColorComponent extends Component{

	Color c;
	
	public ColorComponent(){
		c = new Color(0xffffffff);
	}
	
	public Color getColor(){
		return c;
	}
	
	public void setColor(Color color) {
		c.set(color);
	}

	public void setColor(int rgba) {
		c.set(rgba);
	}
	
	public void setColor(float r, float g, float b, float a){
		c.set(r, g, b, a);
	}
	
	public void setRandomColor(){
		c.set((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f);
	}
}

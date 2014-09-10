package de.pxbox.aingraphs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;

public class NodeEntity extends Entity {
	
	public NodeEntity(float x, float y){
		this.add(new PositionComponent(x,y));
		this.add(new ColorComponent());
	}
	
	public void changeColor(Color color){
		ComponentMapper<ColorComponent> cm = ComponentMapper
				.getFor(ColorComponent.class);
		cm.get(this).setColor(color);
	}
	
	public void changeColor(int rgba){
		ComponentMapper<ColorComponent> cm = ComponentMapper
				.getFor(ColorComponent.class);
		cm.get(this).setColor(rgba);
	}
	
	public void changeColor(float r, float g, float b, float a){
		ComponentMapper<ColorComponent> cm = ComponentMapper
				.getFor(ColorComponent.class);
		cm.get(this).setColor(r,g,b,a);
	}
		
}

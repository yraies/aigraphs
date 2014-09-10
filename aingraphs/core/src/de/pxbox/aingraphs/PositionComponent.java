package de.pxbox.aingraphs;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent extends Component {
	public PositionComponent(float x, float y) {
		this.xy = new Vector2(x,y);
	}
	public Vector2 xy;
}

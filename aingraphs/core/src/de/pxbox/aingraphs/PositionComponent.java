package de.pxbox.aingraphs;

import com.badlogic.ashley.core.Component;

public class PositionComponent extends Component {
	public PositionComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public float x = 0.0f;
	public float y = 0.0f;
}

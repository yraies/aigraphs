package de.pxbox.aingraphs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class NodeRendererSystem extends EntitySystem {

	ShapeRenderer sr;
	Engine engine;
	
	ImmutableArray<Entity> entities;
	ComponentMapper<PositionComponent> pm;
	ComponentMapper<EdgeComponent> em;
	ComponentMapper<ColorComponent> cm;
	
	public final float SCALE = 150;

	public NodeRendererSystem(Engine engine, int priority) {
		this.priority = priority;
		this.sr = new ShapeRenderer();
		this.engine = engine;
	}
	
	/** 
	 * Standard priority of 100
	 * */
	public NodeRendererSystem(Engine engine) {
		this.priority = 100;
		this.sr = new ShapeRenderer();
		this.engine = engine;
	}

	public void update(float deltaTime) {
		
		sr.setProjectionMatrix(AIGraphs.camera.combined);
		
		updateMaps();

		sr.begin(ShapeType.Filled);
		sr.setColor(1f, 1f, 1f, 1f);
		for (int i = 0; i < entities.size(); i++) {
			Entity e1 = entities.get(i);
			if (e1 != null) {
				PositionComponent p1 = pm.get(e1);
				float x1 = p1.xy.x*SCALE;
				float y1 = p1.xy.y*SCALE;
				//draw the Node
				sr.setColor(cm.get(e1).getColor());
				sr.circle(x1, y1, 0.1f*SCALE);
	
				EdgeComponent edgeComp = em.get(e1);
				int numOfEdges = edgeComp.getEdges().size();
				
				for (int j = 0; j < numOfEdges; j++) {
					PositionComponent p2 = pm.get(edgeComp.getEntity(j));
					float x2 = p2.xy.x*SCALE;
					float y2 = p2.xy.y*SCALE;
					Vector2 path = p1.xy.cpy().sub(p2.xy);
					float angle = path.angle() / 90;
					float xoffset = 0;
					float yoffset = 0;
					
					// calculate the offset for the connection lines
					if (angle < 1) {
						xoffset = 2;
						yoffset = 1;
					} else if (angle < 2) {
						xoffset = 2;
						yoffset = -1;
					} else if (angle < 3) {
						xoffset = -2;
						yoffset = -1;
					} else {
						xoffset = -2;
						yoffset = 1;
					}
					
					//draw the connection line
					sr.line(x1+xoffset, y1+yoffset, x2+xoffset, y2+yoffset);
					path.limit(path.len()*0.9f);
					//draw the connection indicator
					sr.circle(x1-path.x*SCALE, y1-path.y*SCALE,  0.05f*SCALE);
				}
			}
		}
		sr.end();

	}
	
	@SuppressWarnings("unchecked")
	private void updateMaps() {
		entities = engine.getEntitiesFor(Family.getFor(EdgeComponent.class));
		pm = ComponentMapper.getFor(PositionComponent.class);
		em = ComponentMapper.getFor(EdgeComponent.class);
		cm = ComponentMapper.getFor(ColorComponent.class);
	}

}

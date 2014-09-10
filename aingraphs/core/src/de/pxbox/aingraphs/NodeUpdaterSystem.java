package de.pxbox.aingraphs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class NodeUpdaterSystem extends EntitySystem {

	private Engine engine;
	private Family family;

	@SuppressWarnings("unchecked")
	public NodeUpdaterSystem(Engine engine) {
		this.engine = engine;
		this.family = Family.getFor(EdgeComponent.class);
	}
	/**
	 * standart priority of 10
	 * */
	@SuppressWarnings("unchecked")
	public NodeUpdaterSystem(Engine engine, int priority) {
		this.engine = engine;
		this.family = Family.getFor(EdgeComponent.class);
		this.priority = 10;
	}

	@SuppressWarnings("unused")
	public void update(float deltaTime) {
		ImmutableArray<Entity> entities = engine.getEntitiesFor(family);
		ComponentMapper<PositionComponent> pm = ComponentMapper
				.getFor(PositionComponent.class);
		ComponentMapper<EdgeComponent> em = ComponentMapper
				.getFor(EdgeComponent.class);
		
		((NodeEntity) entities.get(4)).changeColor(0xffff00ff);
	}

}

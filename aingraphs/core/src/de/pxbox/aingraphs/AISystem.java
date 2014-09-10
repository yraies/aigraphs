package de.pxbox.aingraphs;

import java.util.ArrayList;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class AISystem extends EntitySystem {

	Engine engine;
	int activeSystem;
	final int ASTAR = 0;
	final int DEPTH = 1;
	final int BROADTH = 2;

	ImmutableArray<Entity> entities;
	ComponentMapper<PositionComponent> pm;
	ComponentMapper<EdgeComponent> em;
	ComponentMapper<ColorComponent> cm;

	public AISystem(Engine engine, int priority) {
		this.priority = priority;
		this.engine = engine;
	}

	/**
	 * standart priority of 20
	 * */
	public AISystem(Engine engine) {
		this.priority = 20;
		this.engine = engine;
	}

	float timer = 0;
	
	@Override
	public void update(float deltaTime){
		this.timer += 1;
		
		if(this.timer > 10){
			updateMaps();
			int r1 = (int) Math.floor(Math.random()*entities.size());
			int r2 = (int) Math.floor(Math.random()*entities.size());
			NodeEntity n1 = (NodeEntity) entities.get(r1);
			NodeEntity n2 = (NodeEntity) entities.get(r2);
			System.out.println(r1 + " " + r2 + "     -  " + Runtime.getRuntime().freeMemory());
			findPath(n1,n2);
			timer = 0;
		}
	}
	
	public ArrayList<NodeEntity> findPath(NodeEntity start, NodeEntity target) {
		updateMaps();
		clearBests();

		ArrayList<NodeEntity> open = new ArrayList<NodeEntity>(0);
		ArrayList<NodeEntity> closed = new ArrayList<NodeEntity>(0);
		boolean atTarget = false;

		ArrayList<NodeEntity> path = new ArrayList<NodeEntity>(0);
		
		open.add(start);
		while (!atTarget) {

			NodeEntity x = null; 

			for(NodeEntity openEnt : open)
				System.out.print(pm.get(openEnt).xy + " - " );
			System.out.println("");
			
			// getting the best ( = lowest distance to target + lowest distance
			// traveled ) open node
			for (NodeEntity ent : open) {
				float entHeuristic = getDistance(ent, target)+calculateBestLen(ent);
				if (x != null) {
					float xDist = getDistance(x, target) + calculateBestLen(x);
					if (entHeuristic < xDist) {
						x = ent;
					}
				} else {
					x = ent;
				}
				
			}

			// checking if x is allready the target
			atTarget = target.equals(x);

			if (!atTarget) {
				//expanding preperations
				open.remove(x);
				closed.add(x);
				
				//expand x
				EdgeComponent e = em.get(x);
				ArrayList<NodeEntity> successors = e.getEdges();
				for (NodeEntity y : successors) {
					// check with other paths to y which is the best and set
					// the best or if there is no path add it to open
					if (open.contains(y)) {
						
						float currLen = calculateBestLen(y);
						float newLen = calculateBestLen(x) + getDistance(x, y);
						
						if(newLen < currLen){
							em.get(y).setBestEdge(x);
						}
						
					} else if (closed.contains(y)) {
						
						float currLen = calculateBestLen(y);
						float newLen = calculateBestLen(x) + getDistance(x, y);
						
						if(newLen < currLen){
							em.get(y).setBestEdge(x);
						}
						
					} else {
						em.get(y).setBestEdge(x);
						open.add(y);
					}
					
				}
				
			} else {
				path = fillPath(path,x);
				continue;
			}

		}

		float c1 = (float) Math.random();
		float c2 = (float) Math.random();
		float c3 = (float) Math.random();
		
		for(NodeEntity node : path)
			cm.get(node).setColor(c1, c2, c3, 1f);
		
		System.out.println("Path length: " + path.size());
		
		return path;
	}

	private void clearBests() {

		for(int i = 0; i < entities.size()-1 ; i++)
			em.get(entities.get(i)).setBestEdge(null);
		
	}

	private ArrayList<NodeEntity> fillPath(ArrayList<NodeEntity> path,
			NodeEntity endNode) {
		
		boolean isAtEnd = (em.get(endNode).getBestEdge() == null);
		
		NodeEntity currNode = endNode;
		
		while(!isAtEnd){
			path.add(currNode);
			currNode = em.get(currNode).getBestEdge();
			isAtEnd = (em.get(currNode).getBestEdge() == null);
		}
		path.add(currNode);
		
		System.out.print("fillPath ");
		for(NodeEntity node : path){
			System.out.print(pm.get(node).xy + " ");
		}
		System.out.print("\n");
			
		return path;
	}

	@SuppressWarnings("unchecked")
	private void updateMaps() {
		entities = engine.getEntitiesFor(Family.getFor(EdgeComponent.class));
		pm = ComponentMapper.getFor(PositionComponent.class);
		em = ComponentMapper.getFor(EdgeComponent.class);
		cm = ComponentMapper.getFor(ColorComponent.class);
	}

	@SuppressWarnings("unused")
	private float calculateLength(NodeEntity[] nodes) {
		float len = 0;
		
		for(int i = 1; i < nodes.length; i++)
			len += getDistance(nodes[i-1], nodes[i]);
			
		return len;
	}
	
	public float calculateBestLen(NodeEntity endNode){
		
		float len = 0;
		boolean isAtEnd = (em.get(endNode).getBestEdge() == null);
		
		NodeEntity currNode = endNode;
		
		while(!isAtEnd){
			System.out.println("bestLen  --  BROKEN!  --  " + entities.indexOf(currNode, true));
			len += getDistance(currNode, em.get(currNode).getBestEdge());
			currNode = em.get(currNode).getBestEdge();
			isAtEnd = (em.get(currNode).getBestEdge() == null);
		}
		
		return len;
	}
	
	
	public float getDistance(NodeEntity aNode, NodeEntity bNode) {
		PositionComponent a = pm.get(aNode);
		PositionComponent b = pm.get(bNode);

//		System.out.println( " getDistance " + a.xy + " " + b.xy + " - dst " + b.xy.dst(a.xy));
		
		return b.xy.dst(a.xy);
	}
}

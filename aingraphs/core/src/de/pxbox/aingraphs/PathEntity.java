package de.pxbox.aingraphs;

import java.util.ArrayList;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class PathEntity extends Entity {
	
	ComponentMapper<EdgeComponent> em;
	ComponentMapper<PositionComponent> pm;

	NodeEntity start;
	NodeEntity end;
	ArrayList<NodeEntity> path;
	float totalLength;
	float lastLength;
	
	public PathEntity(NodeEntity start){
		this.start = start;
		path = new ArrayList<NodeEntity>();
		this.end = start;
	}
	
	public void move(NodeEntity nextNode){
		path.add(nextNode);
		lastLength = pm.get(nextNode).xy.dst(pm.get(end).xy);
		totalLength =+ lastLength;
		end = nextNode;
	}
	
	public ArrayList<PathEntity> createSuccessors(){
		ArrayList<PathEntity> successors = new ArrayList<PathEntity>();
		
		for(NodeEntity edge : em.get(end).getEdges()){
			PathEntity clone = null;
			try {
				clone = (PathEntity) this.clone();
				successors.add(clone);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			clone.move(edge);
		}
		
		return successors;
	}
	
}

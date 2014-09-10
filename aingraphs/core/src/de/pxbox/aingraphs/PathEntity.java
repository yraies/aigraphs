package de.pxbox.aingraphs;

import java.util.ArrayList;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import de.pxbox.aigraphs.EdgeComponent.Edge;

public class PathEntity extends Entity {
	
	ComponentMapper<EdgeComponent> em;

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
		EdgeComponent endEdgeComp = em.get(end);
		lastLength = endEdgeComp.getLength(nextNode);
		totalLength =+ lastLength;
		end = nextNode;
	}
	
	public ArrayList<PathEntity> createSuccessors(){
		ArrayList<PathEntity> successors = new ArrayList<PathEntity>();
		
		for(Edge edge : em.get(end).getEdges()){
			PathEntity clone = null;
			try {
				clone = (PathEntity) this.clone();
				successors.add(clone);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			clone.move(edge.e);
		}
		
		return successors;
	}
	
}

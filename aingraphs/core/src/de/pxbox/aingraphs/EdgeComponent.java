package de.pxbox.aingraphs;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;

public class EdgeComponent extends Component {
	private ArrayList<NodeEntity> followingEdges;
	private NodeEntity bestEdge;
	
	public EdgeComponent(NodeEntity... e) {
		this.followingEdges = new ArrayList<NodeEntity>(0);
		this.bestEdge = null;
		
		for(NodeEntity entity : e)
			this.followingEdges.add(entity);
	}
	
	public NodeEntity getEntity(int index){
		return followingEdges.get(index);
	}

	public ArrayList<NodeEntity> getEdges(){
		return this.followingEdges;
	}

	
	public void setBestEdge(NodeEntity e, float dst){
		bestEdge = e;
	}
	
	public NodeEntity getBestEdge(){
		return bestEdge;
	}

	
}

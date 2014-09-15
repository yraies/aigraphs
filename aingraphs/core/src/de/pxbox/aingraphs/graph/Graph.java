package de.pxbox.aingraphs.graph;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class Graph {

	private HashMap<Integer,Node> nodes;
	private HashMap<Integer,ArrayList<Edge>> edges;
	
	public Graph(){
		nodes = new HashMap<Integer, Node>(0);
		edges = new HashMap<Integer, ArrayList<Edge>>(0);
	}
	
	public void addNode(Node newNode){
		nodes.put(newNode.getID(), newNode);
		edges.put(newNode.getID(), new ArrayList<Edge>(0));
	}
	
	public Edge addConnection(int i, int j){
		Edge e = new Edge(getNode(i), getNode(j));
		edges.get(i).add(e);
		return e;
	}
	
	public float getDistance(Integer node1, Integer node2){
		return getPosition(node1).dst(getPosition(node2));
	}
	
	public Vector3 getPosition(Integer node){
		return nodes.get(node).getPos();
	}
	
	public Node getNode(int node){
		return nodes.get(node);
	}
	
	public ArrayList<Edge> getEdges(int node){
		return edges.get(node);
	}
	
	public int getNumOfEdges(int node){
		return getEdges(node).size();
	}

	public ArrayList<Integer> getExpansion(Integer expandedNode) {
		ArrayList<Integer> expandedNodes = new ArrayList<Integer>(0);
		
		for(Edge edge : edges.get(expandedNode)){
			expandedNodes.add(edge.node2.getID());
		}
		
		return expandedNodes;
	}
	
	public Integer getSize(){
		return nodes.size();
	}
	
	public ArrayList<Integer> getAllNodes(){
		ArrayList<Integer> nodeList = new ArrayList<Integer>(0);
		nodeList.addAll(nodes.keySet());
		return nodeList;
	}
	
	public void setNodeColor(Integer node,Color color){
		nodes.get(node).setCol(color);
	}
	
	public void clearColors(){
		for(Node node : nodes.values())
			node.setCol(Color.WHITE);
		for(ArrayList<Edge> edgeList : edges.values())
			for(Edge edge : edgeList)
				edge.setColor(Color.WHITE);
	}
}

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
	
	public Edge addConnection(Node node1, Node node2){
		return addConnection(node1.getID(), node2.getID());
	}
	
	public Edge addConnection(int nodeID1, int nodeID2){

		boolean alreadyConnected = false;
		ArrayList<Edge> currentEdges = getEdges(nodeID1);
		for(Edge edge : currentEdges){
			alreadyConnected = edge.node2.getID() == nodeID2;
			if(alreadyConnected)
				return edge;
		}

		Edge e = new Edge(getNode(nodeID1), getNode(nodeID2));
		edges.get(nodeID1).add(e);
		return e;
	}
	
	public void deleteNode(Node node){
		deleteNode(node.getID());
	}
	
	public void deleteNode(int node){
		nodes.remove(node);
		edges.remove(node);

		ArrayList<Integer> edgeKeys = new ArrayList<Integer>(edges.keySet());
		
		for(int i = 0; i < edges.size(); i++){
			for(int j = 0; j < edges.get(edgeKeys.get(i)).size(); j++){
				if(edges.get(edgeKeys.get(i)).get(j).node2.getID() == node)
					edges.get(edgeKeys.get(i)).remove(j);
			}
		}
		
	}
	
	public void deleteConnection(Node node1, Node node2){
		deleteConnection(node1.getID(), node2.getID());
	}
	
	public void deleteConnection(Edge edge){
		edges.get(edge.node1.getID()).remove(edge);
	}
	
	public void deleteConnection(int nodeID1, int nodeID2){
		for(int i = 0; i < edges.get(nodeID1).size(); i++){
			if(edges.get(nodeID1).get(i).node2.getID() == nodeID2){
				edges.get(nodeID1).remove(i);
				break;
			}
		}
	}
	
	public void setNodeColor(Node node,Color color){
		setNodeColor(node.getID(), color);
	}
	
	public void setNodeColor(int nodeID,Color color){
		nodes.get(nodeID).setCol(color);
	}
	
	public void setEdgeColor(Node from, Node to, Color color) {
		setEdgeColor(from.getID(),to.getID(),color);
	}
	
	public void setEdgeColor(int fromID, int toID, Color color) {
		Edge[] edgeArray = new Edge[edges.size()];
		edges.get(fromID).toArray(edgeArray);
		for(Edge edge : edgeArray){
			if(edge.node2.ID == toID){
				edge.setColor(color);
				break;
			}
		}
	}

	public float getLength(Edge e){
		return getDistance(e.node1.getID(),e.node2.getID());
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
	
	public void clearColors(){
		for(Node node : nodes.values())
			node.setCol(Color.WHITE);
		for(ArrayList<Edge> edgeList : edges.values())
			for(Edge edge : edgeList)
				edge.setColor(Color.WHITE);
	}
}

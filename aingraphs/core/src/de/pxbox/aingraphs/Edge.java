package de.pxbox.aingraphs;

import com.badlogic.gdx.graphics.Color;

public class Edge {

	Node node1;
	Node node2;
	float modifier;
	Color color;
	
	// constructors
	
	public Edge(){
		node1 = null;
		node2 = null;
		modifier = 1f;
	}
	
	public Edge(float mod){
		node1 = null;
		node2 = null;
		modifier = mod;
	}
	
	public Edge(Node from, Node to){
		node1 = from;
		node2 = to;
		modifier = 1f;
	}
	
	public Edge(Node from, Node to, float mod){
		node1 = from;
		node2 = to;
		modifier = mod;
	}
	
	
	// getters and setters
	
	public Node getNode1() {
		return node1;
	}
	public Node getNode2() {
		return node2;
	}
	public float getModifier() {
		return modifier;
	}
	public Color getColor() {
		return color;
	}
	public void setNode1(Node node1) {
		this.node1 = node1;
	}
	public void setNode2(Node node2) {
		this.node2 = node2;
	}
	public void setModifier(float modifier) {
		this.modifier = modifier;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public float getLength(){
		return node2.pos.dst(node1.pos) * modifier;
	}
	
}

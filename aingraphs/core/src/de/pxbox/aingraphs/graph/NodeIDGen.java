package de.pxbox.aingraphs.graph;

public class NodeIDGen {

	static NodeIDGen idGen;
	static int maxID;
	
	public NodeIDGen(){
		maxID = 0;
	}
	
	public static void init(){
		NodeIDGen.idGen = new NodeIDGen();
		System.out.println("Initializing at - " + maxID);
	}
	
	public static int get(){
		return maxID++;
	}
}

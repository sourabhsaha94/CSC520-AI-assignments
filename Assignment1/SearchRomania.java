/*
Unity id: sssaha2
Name: Sourabh Saha

usage: java SearchRomania [bfs|dfs] [srccityname] [destcityname]
*/

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;


//implementation of Stack for DFS
class Stack {
	
    int size;
    int top=-1;
	
    String[] elements;
	
    Stack(int size){	//stack initialization by making all vacant spots as null
	this.size=size;
	this.top =-1;
		
	elements = new String[size];
	for(int i=0;i<elements.length;i++){
	    elements[i]=null;
	}
    }
	
    public boolean isEmpty(){	//check if stack is empty
	if(top==-1)
	    return true;
	else
	    return false;
		
    }	
	
    public String pop(){	//remove top of stack
		
	if(!isEmpty()){
	    return elements[top--];
	}
	else
	    return null;
		
    }
	
    public boolean isFull(){	//check if stack is full
		
	if(top==size-1)
	    return true;
	else
	    return false;
		
    }
	
    public void push(String data){	//add data to top of stack
	if(!isFull()){
	    top++;
	    elements[top] = data;
	}
    }
	
    public void displayStack(){	//display stack contents in LIFO order
		
	if(!isEmpty())
	    for(int i=top;i>=0;i--){
		System.out.println(elements[i]);
	    }
	else
	    System.out.println("Empty");
		
	System.out.println("--------------");
    }
}

//implementation of Queue for BFS
class Queue {
	
    LinkedList <String> elements;
    int size;
	
    Queue(int size){  //init queue of size specified
	this.size = size;
	elements = new LinkedList<String>();
    }
	
    public void enqueue(String v){  //add elements to the queue
	if(!isFull()){
	    elements.addLast(v);
	}
    }
	
    public String dequeue(){  //remove elements from the queue
		
	return elements.poll();
		
    }

    private boolean isFull() { //check if queue is full
	if(elements.size()==size){
	    return true;
	}
	else
	    return false;
    }
	
    public String displayQueue(){ //display the contents of the queue
		
	Iterator<String> i = elements.iterator();
	String op = "";
	while(i.hasNext()){
			
	    op = op + i.next();
	}
	return op;
    }
	
}

//implementation of graph for storing the city nodes
class Graph {

    ArrayList <Vertex> Vlist;	//list of all the vertices
    ArrayList<Edge> Elist;	//list of all the edges
	
    public Graph(ArrayList<Vertex> v){	//initialize the graph with the list of vertices given by the user
	this.Vlist = new ArrayList<Vertex>();
	this.Elist = new ArrayList<Edge>();
		
	for(Vertex vert:v){
	    this.Vlist.add(vert);
	}
    }
	
    public void addEdge(Vertex one, Vertex two){	//insert a particular edge into the edge list with vertex references
	Edge e = new Edge(one,two);
	this.Elist.add(e);
	one.addEdge(e);
	two.addEdge(e);
    }
	
    public void addEdge(String s1, String s2){	//insert call to the above method by passing only the labels as input
	addEdge(getVertexFromLabel(s1),getVertexFromLabel(s2));
    }
	
    public Vertex getVertexFromLabel(String label){	//get vertex from the list of vertices matching input label
		
	for(Vertex v:this.Vlist){
	    if(v.label.equalsIgnoreCase(label))
		return v;
	}
	return null;
    }
}


//vertex class for storing each node, the edges of the node and the path to previous nodes

class Vertex {
    String label;	//the data of the vertex is stored as label
    ArrayList <Edge> Elist;	//this stores number of outgoing and incoming edges
    ArrayList<Vertex> Path; //this stores the vertices in previous iterations
	
	
    public Vertex(String l){	//initialize vertex with own label and list of edges
	this.label = l;
	this.Elist = new ArrayList<Edge>();		
	this.Path = new ArrayList<Vertex>();
	this.Path.add(this);	//add the current node to its own path
    }
	
    public void addEdge(Edge e){	//add an edge to the list of the current vertex
	if(this.Elist.contains(e))
	    return;
	else
	    this.Elist.add(e);
    }
	
    public boolean containsEdge(Edge e){	//check if the list contains a particular edge
	return this.Elist.contains(e);
    }
	
    public ArrayList<Edge> getElist(){	//return all the edges associated with the vertex
	return Elist;
    }
	
    public int getEdgeCount(){	//return the total count of the edges
	return Elist.size();
    }
	
    public ArrayList<Vertex> getAdjacentVertexList(){	//get the list of vertices on the other end of all the connected edges
		
	ArrayList<Vertex> v = new ArrayList<Vertex>();
		
	for(Edge e:Elist){
	    v.add(e.getAdjacentVertex(this));
	}
		
	return v;
    }
	
}

//edge class for storing the path between two cities

class Edge {

    Vertex one,two;	//store the ends of the edge
	
    public Edge(Vertex one,Vertex two){	//initialize the edge with the corresponding vertices
	this.one=one;
	this.two=two;
    }
	
    public Vertex getAdjacentVertex(Vertex v){	//return the other end of the edge for the input vertex
	return (v.equals(one))?two:one;
    }
	
}

//pseudo-code understood from en.wikipedia.org/wiki/Breadth-first_search


class BreadthFirstSearch {


    ArrayList<Vertex> Vlist = new ArrayList<Vertex>();	//create new list of vertex
    ArrayList<Vertex> Temp = new ArrayList<Vertex>();
    String[] splitString;
    
    
    String goal="",start_node="";

    BreadthFirstSearch(String cities,String start,String goal){
	this.splitString = cities.split("#");
	this.start_node = start;
	this.goal = goal;
    }
    
    public void runAlgo() {

	
	String[] cityString;
	
	boolean flag=true;	//to mark duplicate values
	
	for(int i=0;i<splitString.length;i++){	//to fill temp array with all city names including duplicates
	    cityString = splitString[i].split(",");
	    Temp.add(new Vertex(cityString[0]));
	    Temp.add(new Vertex(cityString[1]));
	}
			
	Vlist.add(Temp.get(0));
		
	for(Vertex v : Temp){	//to remove duplicate entries from temp into vlist
	    flag=true;
	    for(Vertex v1:Vlist){
		if(v.label.equalsIgnoreCase(v1.label)){
		    flag=true;
		    break;
		}else
		    {
			flag=false;
		    }
	    }
	    if(!flag){
		Vlist.add(v);
	    }
	}
		
		
	Graph g = new Graph(Vlist);
		
		
	for(int j=0;j<splitString.length;j++){	//to fill temp array with all city names including duplicates
	    cityString = splitString[j].split(",");
	    g.addEdge(cityString[0], cityString[1]);
	}
		
		
	String current = "";
	
	String[] visited = new String[Vlist.size()];
	int i,counter=0;
		
	Queue q = new Queue(Vlist.size());	//new initialization of queue
	ArrayList<Vertex> succ = new ArrayList<Vertex>();	//list of successor nodes
		
		
	q.enqueue(start_node);	//Enqueue a vertex
	visited[counter++]=start_node;
		
		
	while(q.size!=0){	//main algorithm loop of BFS
			
	    current = q.dequeue();
						
	    if(current.equalsIgnoreCase(goal)){
				
		System.out.println("Goal reached(BFS)");
		break;
	    }
	    else{
		succ = g.getVertexFromLabel(current).getAdjacentVertexList();

		Collections.sort(succ,new Comparator<Vertex>(){ //sort node list in ascending order, for BFS nodes will be considered in alphabetical order
			@Override public int compare(Vertex v1,Vertex v2){
			    return v1.label.compareTo(v2.label);
			}
		    });
		
		for(i=0;i<succ.size();i++){
		    if(!checkIfVisited(visited,succ.get(i).label))
			{
			    q.enqueue(succ.get(i).label);
			    visited[counter++] = succ.get(i).label;
			    succ.get(i).Path.addAll(g.getVertexFromLabel(current).Path);
			}
		}
	    }
	}

	//print in a formatted manner; the nodes visited
	System.out.println("Visited nodes");
	for(i=0;i<visited.length;i++){
	    if(visited[i]!=null)
		System.out.print(visited[i]+" ");
	}
	System.out.println("\n");
	
	
	//print in a formatted manner; the entire route
		
	System.out.println("Route take from "+start_node+" to "+goal+"(BFS):");
				
	for(i=g.getVertexFromLabel(goal).Path.size()-1;i>0;i--){
	    System.out.print(g.getVertexFromLabel(goal).Path.get(i).label+" > ");
	}
	System.out.print(g.getVertexFromLabel(goal).Path.get(0).label+"\n");
    }

    public boolean checkIfVisited(String[] visited, String label) {
		
	for(int i=0;i<visited.length;i++){
	    if(label.equalsIgnoreCase(visited[i]))
		return true;
	}
		
	return false;
    }

}
//pseudo-code understood from en.wikipedia.org/wiki/Depth-first_search
class DepthFirstSearch {

    ArrayList<Vertex> Vlist = new ArrayList<Vertex>();	//create new list of vertex
    ArrayList<Vertex> Temp = new ArrayList<Vertex>();
		
    String[] splitString;
    boolean flag=true;	//to mark duplicate values

    String goal="",start_node="";	//enter the goal and start node
    
    DepthFirstSearch(String cities,String start, String goal){
	this.splitString = cities.split("#");
	this.start_node = start;
	this.goal = goal;
    }

    public void runAlgo() {

	String[] cityString;
	for(int i=0;i<splitString.length;i++){	//to fill temp array with all city names including duplicates
	    cityString = splitString[i].split(",");
	    Temp.add(new Vertex(cityString[0]));
	    Temp.add(new Vertex(cityString[1]));
	}
		
	     
	Vlist.add(Temp.get(0));
		
	for(Vertex v : Temp){	//to remove duplicate entries from temp into vlist
	    flag=true;
	    for(Vertex v1:Vlist){
		if(v.label.equalsIgnoreCase(v1.label)){
		    flag=true;
		    break;
		}else
		    {
			flag=false;
		    }
	    }
	    if(!flag){
		Vlist.add(v);
	    }
	}
		
		
	Graph g = new Graph(Vlist);
		
	for(int i=0;i<splitString.length;i++){	//to fill temp array with all city names including duplicates
	    cityString = splitString[i].split(",");
	    g.addEdge(cityString[0], cityString[1]);
	}
			
		
	Stack stack = new Stack(g.Vlist.size());	//initialize the stack of size equals number of vertices
	String label="";
		
	
		
	String[] visited = new String[g.Vlist.size()];	//store all the nodes already taken into consideration
	ArrayList<Vertex> succList = new ArrayList<Vertex>();	//store all the successive vertices of a given vertex
		
	int counter = 0 ;	//to iterate through the visited array
				
	stack.push(start_node);	//push first node on stack
		
		
	visited[counter++]=start_node;
		
	while(!stack.isEmpty()){	//main loop of the DFS algorithm
					
	    label = stack.pop();
			
	    if(label.equalsIgnoreCase(goal))
		{
		    System.out.println("Goal reached(DFS)");
		    break;
		}
	    else
		{
				
		    succList = g.getVertexFromLabel(label).getAdjacentVertexList();
		    Collections.sort(succList,new Comparator<Vertex>(){ //sort node list in ascending order, for DFS nodes will be considered in reverse alphabetical order
			    @Override public int compare(Vertex v1,Vertex v2){
				return v1.label.compareTo(v2.label);
			    }
			});
		    for(Vertex v:succList)
			{
			    if(!checkIfVisited(visited,v.label)){
				visited[counter++]=v.label;
				stack.push(v.label);
				v.Path.addAll(g.getVertexFromLabel(label).Path);
			    }
							
			}
		}
	}

	//print in a formatted manner; the nodes visited
	System.out.println("Visited nodes");
	for(int i=0;i<visited.length;i++){
	    if(visited[i]!=null)
		System.out.print(visited[i]+" ");
	}
	System.out.println("\n");
	
	//print in a formatted manner; the actual route taken
		
	System.out.println("Route take from "+start_node+" to "+goal+"(DFS):");
		
	for(int i=g.getVertexFromLabel(goal).Path.size()-1;i>0;i--){
	    System.out.print(g.getVertexFromLabel(goal).Path.get(i).label+" > ");
	}
	System.out.print(g.getVertexFromLabel(goal).Path.get(0).label+"\n");
		
    }

    public boolean checkIfVisited(String[] visited, String label) {
	
	for(int i=0;i<visited.length;i++){
	    if(label.equalsIgnoreCase(visited[i]))
		return true;
	}
	return false;
    }
		
}

//main class

class SearchRomania
{

    static String cities = "oradea,zerind,71#zerind,arad,75#arad,timisoara,118#timisoara,lugoj,111#lugoj,mehadia,70#dobreta,mehadia,75#oradea,sibiu,151#arad,sibiu,140#dobreta,craiova,120#sibiu,rimnicu_vilcea,80#sibiu,fagaras,99#rimnicu_vilcea,craiova,146#pitesti,craiova,138#rimnicu_vilcea,pitesti,97#bucharest,pitesti,101#bucharest,fagaras,211#bucharest,giurgiu,90#bucharest,urziceni,85#vaslui,urziceni,142#hirsova,urziceni,98#hirsova,eforie,86#vaslui,iasi,92#neamt,iasi,87#";
     
    public static void main(String[] args) throws IOException {

	if(args.length == 3){
	    if(args[0].equalsIgnoreCase("bfs")){

		BreadthFirstSearch bfs = new BreadthFirstSearch(cities,args[1],args[2]);
		bfs.runAlgo();
	    }
	    else if(args[0].equalsIgnoreCase("dfs")){
		
		DepthFirstSearch dfs = new DepthFirstSearch(cities,args[1],args[2]);
		dfs.runAlgo();
	
	    }
	    else{
		System.out.println("usage: java SearchRomania [bfs|dfs] [srccityname] [destcityname]");
	    }
	}
	else{
	    System.out.println("usage: java SearchRomania [bfs|dfs] [srccityname] [destcityname]");
	}
	
    }
}

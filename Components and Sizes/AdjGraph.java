// C343 Fall 2020
// Bryan Hatami: bhatami
// TASK-D PS10
// a simple implementation for graphs with adjacency lists

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class AdjGraph implements Graph {

    // is it a directed graph (true or false) :
    private boolean digraph;

    private int totalNodes;
    // all the nodes in the graph:
    private Vector<String> nodeList;

    private int totalEdges;
    // all the adjacency lists, one for each node in the graph:
    private Vector<LinkedList<Integer>>  adjList;

    // every visited node:
    private Vector<Boolean> visited;

    // list of nodes pre-visit:
    private Vector<Integer> nodeEnum;

    public AdjGraph() {
        init();
    }

    public AdjGraph(boolean ifdigraph) {
        init();
        digraph = ifdigraph;
    }

    public void init() {
        nodeList = new Vector<String>();
        adjList = new Vector<LinkedList<Integer>>();
        visited = new Vector<Boolean>();
        nodeEnum = new Vector<Integer>();
        totalNodes = totalEdges = 0;
        digraph = false;
    }

    // set vertices:
    public void setVertices(String[] nodes) {
        for (int i = 0; i < nodes.length; i ++) {
            nodeList.add(nodes[i]);
            adjList.add(new LinkedList<Integer>());
            visited.add(false);
            totalNodes ++;
        }
    }

    // add a vertex:
    public void addVertex(String label) {
        nodeList.add(label);
        visited.add(false);
        adjList.add(new LinkedList<Integer>());
        totalNodes ++;
    }

    public int getNode(String node) {
        for (int i = 0; i < nodeList.size(); i ++) {
            if (nodeList.elementAt(i).equals(node)) return i;
        }
        return -1;
    }

    // return the number of vertices:
    public int length() {
        return nodeList.size();
    }

    // add edge from v1 to v2:
    public void setEdge(int v1, int v2, int weight) {
        LinkedList<Integer> tmp = adjList.elementAt(v1);
        if (adjList.elementAt(v1).contains(v2) == false) {
            tmp.add(v2);
            adjList.set(v1,  tmp);
            totalEdges ++;
        }
    }

    public void setEdge(String v1, String v2, int weight) {
        if ((getNode(v1) != -1) && (getNode(v2) != -1)) {
            // add edge from v1 to v2:
            setEdge(getNode(v1), getNode(v2), weight);
            // for undirected graphs, add edge from v2 to v1 as well:
            if (digraph == false) {
                setEdge(getNode(v2), getNode(v1), weight);
            }
        }
    }

    // keep track whether a vertex has been visited or not,
    //    for graph traversal purposes:
    public void setVisited(int v) {
        visited.set(v, true);
        nodeEnum.add(v);
    }

    public boolean ifVisited(int v) {
        return visited.get(v);
    }


    // clean up before traversing the graph:
    public void clearWalk() {
        nodeEnum.clear();
        for (int i = 0; i < nodeList.size(); i ++)
            visited.set(i, false);
    }

    public void walk(String method) {
        clearWalk();
        // traverse the graph:
        for (int i = 0; i < nodeList.size(); i ++) {
            if (ifVisited(i) == false) {
                if (method.equals("BFS")) {
                    BFS(i);      // i is the start node
                } else if (method.equals("DFS")) {
                    DFS(i); // i is the start node
                } else {
                    System.out.println("unrecognized traversal order: " + method);
                    System.exit(0);
                }
            }
        }
        System.out.println(method + ":");
        displayEnum();
    }

    // Problem Set 10 TODO:
    //
    // write your componentsAndSizes() method here.
    //

    public void componentsAndSizes(){
        //walk("DFS");
        int e = 0;
        int allComponents = 0;
        ArrayList<Integer> n = new ArrayList<>();
        //////////
        for(int a = 0;
            a < nodeList.size();
            a++){
            //////////
            if(ifVisited(a)==false){
                allComponents++;
                DFS(a);
                n.add(nodeEnum.size()-e);
                e += nodeEnum.size()-e; } }
        //////////
        System.out.println("total components: " + allComponents);
        //////////
        for(int a = 0;
            a<n.size();
            a++){
            //////////
            System.out.println("component " + a +  " contains " + n.get(a)+ " nodes"); } }



    public void DFS(int v) {
        setVisited(v);
        LinkedList<Integer> neighbors = adjList.elementAt(v);
        for (int i = 0; i < neighbors.size(); i ++) {
            int v1 = neighbors.get(i);
            if (ifVisited(v1) == false) {
                DFS(v1);
            }
        }
    }

    public void BFS(int s) {
        ArrayList<Integer> toVisit = new ArrayList<Integer>();
        toVisit.add(s);
        while (toVisit.size() > 0) {
            int v = toVisit.remove(0);   // first-in, first-visit
            setVisited(v);
            LinkedList<Integer> neighbors = adjList.elementAt(v);
            for (int i = 0; i < neighbors.size(); i ++) {
                int v1 = neighbors.get(i);
                if ( (ifVisited(v1) == false) && (toVisit.contains(v1) == false) ) {
                    toVisit.add(v1);
                }
            }
        }
    }

    public void display() {
        System.out.println("total nodes: " + totalNodes);
        System.out.println("total edges: " + totalEdges);
    }

    public void displayEnum() {
        for (int i = 0; i < nodeEnum.size(); i ++) {
            System.out.print(nodeList.elementAt(nodeEnum.elementAt(i)) + " ");
        }
        System.out.println();
    }

    // Problem Set 10 TODO:

    // --- write a main() method here ---

    // Provide 3 different graph examples,
    //   with at least 10 nodes for each different graph,
    //   following these steps 1) ... 4) for each graph:

    // 1) instantiate a new graph,
    // 2) assign2 vertices and edges to the graph,
    // 3) then display2 the graph's content (use display() )
    // 4) finally call your componentsAndSizes() method to provide
    //    output results as from Problem Set 10 instructions


    public static void main(String args[]){
        String[] str1 = {"A","B","C","D","E","F","G","H","I","J"};
        AdjGraph firstTest = new AdjGraph();
        System.out.println("//////////////////////////////////////");
        System.out.println();

        firstTest.setVertices(str1);
        firstTest.setEdge(1,1,1);
        firstTest.setEdge(2,2,1);
        firstTest.setEdge(3,3,1);
        firstTest.setEdge(4,4,1);
        firstTest.setEdge(5,5,1);
        firstTest.setEdge(6,6,1);
        firstTest.setEdge(7,7,1);
        firstTest.setEdge(8,8,1);
        firstTest.display();
        firstTest.componentsAndSizes();
        System.out.println();
        System.out.println("//////////////////////////////////////");
        System.out.println();


        String[] str2 = {"A","B","C","D","E","F","G","H","I","J"};
        AdjGraph secondTest = new AdjGraph();

        secondTest.setVertices(str2);
        secondTest.setEdge(2,1,4);
        secondTest.setEdge(5,3,4);
        secondTest.setEdge(6,4,4);
        secondTest.setEdge(7,2,4);
        secondTest.setEdge(3,1,4);
        secondTest.setEdge(4,7,4);
        secondTest.display();
        secondTest.componentsAndSizes();
        System.out.println();
        System.out.println("//////////////////////////////////////");
        System.out.println();

        String[] str3 = {"A","B","C","D","E","F","G","H","I","J"};
        AdjGraph thirdTest = new AdjGraph();

        thirdTest.setVertices(str3);
        thirdTest.setEdge(1,8,2);
        thirdTest.setEdge(2,7,2);
        thirdTest.setEdge(3,6,2);
        thirdTest.setEdge(4,5,2);
        thirdTest.setEdge(5,4,2);
        thirdTest.setEdge(6,3,2);
        thirdTest.setEdge(7,2,2);
        thirdTest.setEdge(8,1,2);
        thirdTest.display();
        thirdTest.componentsAndSizes();
        System.out.println();
    }


} // end of class AdjGraph
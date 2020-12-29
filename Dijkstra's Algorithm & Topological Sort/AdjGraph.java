// C343 Fall 2020
//
// a simple implementation for graphs with adjacency lists

// Problem Set 11 starter file

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
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

    // all the weights of the edges, one for each node in the graph:
    private Vector<LinkedList<Integer>> adjWeight;

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
        adjWeight = new Vector<LinkedList<Integer>>();
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
            adjWeight.add(new LinkedList<Integer>());
            visited.add(false);
            totalNodes ++;
        }
    }

    // add a vertex:
    public void addVertex(String label) {
        nodeList.add(label);
        visited.add(false);
        adjList.add(new LinkedList<Integer>());
        adjWeight.add(new LinkedList<Integer>());
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
            LinkedList<Integer> tmp2 = adjWeight.elementAt(v1);
            tmp2.add(weight);
            adjWeight.set(v1,  tmp2);
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


    // new for Problem Set 11:
    public LinkedList<Integer> getNeighbors(int v) {
        return adjList.get(v);
    }

    public int getWeight(int v, int u) {
        LinkedList<Integer> tmp = getNeighbors(v);
        LinkedList<Integer> weight = adjWeight.get(v);
        if (tmp.contains(u)) {
            return weight.get(tmp.indexOf(u));
        } else {
            return Integer.MAX_VALUE;
        }
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

    // Problem Set 11 TODO:
    //
    // write your methods here.
    //

    public void relax(int i, int v, int toV){
        toV = adjList.get(v).get(i);
        int dist[] = new int[totalNodes];
        if(dist[v]>dist[adjList.get(v).get(i)]+toV){
            dist[v]=dist[adjList.get(v).get(i)]+toV;
        }
    }

    public void dijkstra1(int s){
        int dist[] = new int[totalNodes];
        boolean[] beenTo = new boolean[totalNodes];
        /////////
        for(int a = 0;
            a<totalNodes;
            a++){
            ///////////
            dist[a]=Integer.MAX_VALUE;
        }
        ///////////
        dist[s] = 0;

        while(true){
            int v = -1;
            int distVec = Integer.MAX_VALUE;
            ///////////
            for(int i = 0;
                i<totalNodes;
                i++){
                ///////////
                if(beenTo[i]==false && dist[i]<distVec){ v=i; distVec = dist[i]; } }
            ///////////
            if(distVec==Integer.MAX_VALUE){
                ///////////
                for(int e: dist){ System.out.println(e +" "); }
                ///////////
                break;
            }

            beenTo[v] = true;

            for(int i = 0;
                i< adjList.get(v).size();
                i++){
                ///////////
                if(!beenTo[adjList.get(v).get(i)]){
                    int attachV = adjList.get(v).get(i);
                    relax(i,v,attachV);
                    ///////////
                    if(dist[adjList.get(v).get(i)]>dist[v]+attachV){
                        dist[adjList.get(v).get(i)]=dist[v]+attachV;
                    }
                }
            }
        }

    }

    public void topologicalSortWithQueue(){
        ArrayList<Integer> organized = new ArrayList<>();
        int[] degOfQ = new int[totalNodes];
        Queue<Integer> vPoints = new LinkedList<>();
        ///////////
        for(int a = 0;
            a<totalNodes;
            a++){
            //////////
            for(int j = 0;
                j<adjList.get(a).size();
                j++){
                //////////
                degOfQ[adjList.get(a).get(j)]++;
            }
        }
        //////////
        for(int a = 0;
            a<totalNodes;
            a++){
            //////////
            if(degOfQ[a]==0){
                vPoints.add(a);
            }
        }
        //////////
        while (!vPoints.isEmpty()){
            int curr = vPoints.poll();
            organized.add(curr);
            //////////
            for(int a = 0;
                a< adjList.get(curr).size();
                a++){
                //////////
                degOfQ[adjList.get(curr).get(a)]--;
                //////////
                if(degOfQ[adjList.get(curr).get(a)]==0){ vPoints.add(adjList.get(curr).get(a)); }
            }
        }
        //////////
        if(organized.size()!=totalNodes){ System.out.println("contains circle"); }
        //////////
        else{ for(int e: organized){ System.out.print(e+" "); }
            //////////
            System.out.println();
            System.out.println();
        }
    }



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

    // Problem Set 11 TODO:

    // write your new main() method here:

    // for Problem Set 11 Task B:
    //   provide 3 different examples, using the two different versions of Dijkstra's algorithm
    //   with at least 10 nodes for each different graph

    // for Problem Set 11 Task C:
    //   provide 3 different examples, using the two different versions of Dijkstra's algorithm
    //   with at least 10 nodes for each different graph

    public static void main(String argv[]) {

        //second example: g2
        AdjGraph g2 = new AdjGraph(true);
        String[] nodes2 = {"a", "b", "c", "d", "e", "f", "g","h", "i", "j"};
        g2.setVertices(nodes2);
        g2.setEdge("a", "b", 1);
        g2.setEdge("c", "d", 2);
        g2.setEdge("e", "f", 3);
        g2.setEdge("f", "g", 4);
        g2.setEdge("h", "i", 5);
        g2.setEdge("j", "a", 6);
        g2.setEdge("b", "c", 7);
        g2.setEdge("d", "e", 8);
        g2.setEdge("f", "g", 9);
        g2.setEdge("h", "i", 1);
        g2.topologicalSortWithQueue();
        g2.dijkstra1(3);
        System.out.println();
        System.out.println("---------------------------");
        System.out.println();

        AdjGraph g3 = new AdjGraph(true);
        String[] nodes3 = {"a", "b", "c", "d", "e", "f", "g","h", "i", "j"};
        g3.setVertices(nodes3);
        g3.setEdge("h", "b", 2);
        g3.setEdge("a", "f", 4);
        g3.setEdge("a", "i", 6);
        g3.setEdge("g", "c", 8);
        g3.setEdge("b", "f", 1);
        g3.setEdge("c", "j", 3);
        g3.setEdge("i", "f", 5);
        g3.setEdge("d", "f", 7);
        g3.setEdge("d", "h", 9);
        g3.setEdge("e", "f", 1);
        g3.topologicalSortWithQueue();
        g3.dijkstra1(4);
        System.out.println();
        System.out.println("---------------------------");
        System.out.println();

        AdjGraph g4 = new AdjGraph(true);
        String[] nodes4 = {"a", "b", "c", "d", "e", "f", "g","h", "i", "j"};
        g4.setVertices(nodes4);
        g4.setEdge("a", "g", 3);
        g4.setEdge("a", "f", 9);
        g4.setEdge("h", "e", 2);
        g4.setEdge("b", "i", 4);
        g4.setEdge("j", "f", 6);
        g4.setEdge("c", "g", 8);
        g4.setEdge("h", "f", 1);
        g4.setEdge("d", "i", 2);
        g4.setEdge("d", "e", 7);
        g4.setEdge("j", "f", 5);
        g4.topologicalSortWithQueue();
        g4.dijkstra1(5);
        System.out.println();
        System.out.println("---------------------------");
        System.out.println();

    }

} // end of class AdjGraph
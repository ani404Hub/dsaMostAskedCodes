package org.example;

import java.util.*;

public class weightedUnDirectedGraph {
    public static void main(String[] args) {
        int vertex = 5;
        ArrayList<ArrayList<ArrayList<Integer>>> adjList = new ArrayList<>();
        for(int i = 0; i < vertex; i++)                                                                               //Initialise the ArrayList inside another ArrayList
            adjList.add(new ArrayList<>());

        addEdge(adjList, 0, 1, 2);
        addEdge(adjList, 0, 4, 6);
        addEdge(adjList, 0, 3, 7);
        addEdge(adjList, 1, 2, 1);
        addEdge(adjList, 1, 4, 4);
        addEdge(adjList, 4, 3, 5);
        addEdge(adjList, 2, 4, 2);
        addEdge(adjList, 2, 3, 3);
        System.out.println("Least distance to traverse whole graph : " + minSpanningTree(vertex,adjList));
        System.out.println("Least distance using dis-joint set = " + kruskalMinSpanningTree(vertex,adjList));
    }

    static void addEdge(ArrayList<ArrayList<ArrayList<Integer>>> adjList, int src, int dest, int weight) {          //create to avoid repeated entry of same source & destination
        ArrayList<ArrayList<Integer>> neighbor = adjList.get(src);
        ArrayList<Integer> neighborDetails = new ArrayList<>();                                                     //Initialise the ArrayList inside ArrayList of ArrayList
        neighborDetails.add(0, dest);                                                                         //Add destination node in index 0 of Arraylist
        neighborDetails.add(1, weight);                                                                       //Add weight between two nodes in index 1 of Arraylist
        neighbor.add(neighborDetails);
    }

    static class Pair implements Comparable<Pair>{                                                                  //Create a class that accept a node & edge distance
        int vertex;
        int weight;
        public Pair(int vertex, int weight){
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Pair that) {                                                                           //Compare between edge distances and sort accordingly
            return this.weight - that.weight;
        }
    }
    static int minSpanningTree(int vertex, ArrayList<ArrayList<ArrayList<Integer>>> adjList) {
        boolean[] visited = new boolean[vertex];                                                                    //create boolean array with same size as no of vertex
        Arrays.fill(visited, false);                                                                           //Fill all arrays with false
        PriorityQueue<Pair> pq = new PriorityQueue<>();                                                            //Create Priority Queue as it implement minHeap by default in java
        pq.add(new Pair(0, 0));                                                                      //Add Source Pair in PriorityQ from where traversal will start
        int res = 0;

        while (!pq.isEmpty()) {
            Pair cur = pq.remove();                                                                                //Fetch the current pair having min weight from source node
            int curVertex = cur.vertex;                                                                            //fetch the vertex of current pair
            if (visited[curVertex])                                                                                //If current vertex is already visited, skip the while loop
                continue;
            res = res + cur.weight;                                                                                //If current Node is not visited, then add it's weight in final result & mark the node visited
            visited[curVertex] = true;
            ArrayList<ArrayList<Integer>> neighbor = adjList.get(curVertex);                                       //Create ArrayList of ArrayList to store the neighbour node details
            for (ArrayList<Integer> neighborlist : neighbor) {
                int neighborVertexList = neighborlist.get(0);
                int neighborWeightList = neighborlist.get(1);

                if (!visited[neighborVertexList])                                                                  //Check if neighbor node not visited then add that pair in priority queue
                    pq.add(new Pair(neighborVertexList, neighborWeightList));
            }
        }
        return res;
    }

    static class Edge implements Comparable<Edge>{                                                                //Create a new class that accept two nodes & edge distances
        int src;
        int dest;
        int weight;
        public Edge(int src, int dest, int weight){
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
        public int compareTo(Edge that){                                                                         //Compare between edge distances & sort them in ascending order
            return this.weight - that.weight;
        }
    }

    static int[] parent;
    static int findRoot(int x){                                                                                 //Method to find Parent of the given node using path compression
        if(parent[x] != x)                                                                                      //If parent of a node is not the node itself then again call recursive to find parent
            parent[x] = findRoot(parent[x]);
        return parent[x];
    }
    static void union(int x, int y){                                                                            //Method to unite two different nodes having different parent node
        int xRoot = findRoot(x);
        int yRoot = findRoot(y);
        if(xRoot == yRoot)                                                                                      //If parent of both nodes are same then do nothing & return
            return;
        parent[yRoot] = xRoot;                                                                                  //If parent of both nodes are different, then map one node as parent of other
    }
    static int kruskalMinSpanningTree(int vertex, ArrayList<ArrayList<ArrayList<Integer>>> adjList){
        boolean[][] visited = new boolean[vertex][vertex];                                                      //Initialise a boolean 2D array with size equal to no. of vertex
        ArrayList<Edge> edges = new ArrayList<>();                                                              //Initialise ArrayList of type Edge
        for(int i = 0; i < adjList.size(); i++){
            for(int j =0; j < adjList.get(i).size(); j++){
                ArrayList<Integer> curNode = adjList.get(i).get(j);                                             //Fetch current node from input
                if(!visited[i][curNode.get(0)]){                                                                //If current node is not visited yet then visit it
                    visited[i][curNode.get(0)] = true;                                                          //Mark destination node as visited
                    visited[curNode.get(0)][i] = true;                                                          //Mark source node as visited
                    edges.add(new Edge(i, curNode.get(0), curNode.get(1)));                                     //Store the path distance of the visited nodes in arrayList of Edge type
                }
            }
        }
        parent = new int[vertex];                                                                               //Initialise the parent array
        for(int i = 0; i < vertex; i++)                                                                         //Map each node as parent of itself
            parent[i] = i;
        Collections.sort(edges);                                                                                //Sort the arrayList that store edge distances in ascending order
        int count = 1;
        int res = 0;
        for(int i = 0; count < vertex; i++){                                                                    //Create a loop that will iterate at max of vertex-1 (Total no of edges) times to visit all nodes
            Edge edge = edges.get(i);                                                                           //Fetch each edge based on lowest distance as it is already sorted
            int xRoot = findRoot(edge.src);                                                                     //Fetch parent node of both the nodes whose weight is considered
            int yRoot = findRoot(edge.dest);

            if(xRoot != yRoot){                                                                                 //If parent of both nodes are not same then unite both nodes & create a single parent node
                union(xRoot, yRoot);
                count++;
                res = res + edge.weight;                                                                        //Store the edge distance of the united nodes
            }
        }
        return res;
    }
}

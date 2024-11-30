package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class weightedUnDirectedGraph {
    public static void main(String[] args) {
        int vertex = 5;
        ArrayList<ArrayList<ArrayList<Integer>>> adjList = new ArrayList<>();
        for(int i = 0; i < vertex; i++)                                                                             //Initialise the ArrayList inside another ArrayList
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
    }

    static void addEdge(ArrayList<ArrayList<ArrayList<Integer>>> adjList, int src, int dest, int weight) {          //create to avoid repeated entry of same source & destination
        ArrayList<ArrayList<Integer>> neighbor = adjList.get(src);
        ArrayList<Integer> neighborDetails = new ArrayList<>();                                                     //Initialise the ArrayList inside ArrayList of ArrayList
        neighborDetails.add(0, dest);                                                                         //Add destination node in index 0 of Arraylist
        neighborDetails.add(1, weight);                                                                       //Add weight between two nodes in index 1 of Arraylist
        neighbor.add(neighborDetails);
    }

    static class Pair implements Comparable<Pair>{
        int vertex;
        int weight;
        public Pair(int vertex, int weight){
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Pair that) {
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
}

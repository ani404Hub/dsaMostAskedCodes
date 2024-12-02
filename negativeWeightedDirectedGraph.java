package org.example;

import java.util.Arrays;

public class negativeWeightedDirectedGraph {
    public static void main(String[] args) {
        int vertex = 4;
        int[][] edges = new int[][]{
                {0,1,3},
                {0,2,1},
                {1,2,-8},
                {2,3,2},
                {3,1,4}
        };
        int sourceNode = 0;
        System.out.println("Is negative cycle present in graph = " + negativeCyclePresent(vertex, sourceNode, edges));
    }

    static boolean negativeCyclePresent(int vertex,int startNode, int[][] edges){
        int[] dist = new int[vertex];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[startNode] = 0;

        for(int i = 0; i < vertex; i++){
            for(int j = 0; j < edges.length; j++ ){
                int src = edges[j][0];
                int dest = edges[j][1];
                int weight = edges[j][2];

                if(dist[src] != Integer.MAX_VALUE && dist[src] + weight < dist[dest])
                    dist[dest] = dist[src] + weight;
            }
        }

        for (int[] edge : edges) {
            int src = edge[0];
            int dest = edge[1];
            int weight = edge[2];

            if (dist[src] != Integer.MAX_VALUE && dist[src] + weight < dist[dest])
                return true;
        }
        return false;
    }
}

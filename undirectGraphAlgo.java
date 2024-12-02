package org.example;

import java.util.*;

public class undirectGraphAlgo {
    static boolean otherComponent;

    public static void main(String[] args) {
        int vertex = 13;
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        int componentCount = 0;
        int[] predecessor = new int[vertex + 1];
        int[] distance = new int[vertex + 1];
        boolean[] visited = new boolean[vertex + 1];                                             //As vertex numbering starts from 1
        for (int i = 0; i <= vertex; i++) {                                                      //Oth index has no use
            adjList.add(new ArrayList<>());                                                      //Initialise & create new arraylist for each Array
            predecessor[i] = -1;
            visited[i] = false;
            distance[i] = Integer.MAX_VALUE;
        }

        addEdge(adjList, 1, 2);
        addEdge(adjList, 1, 5);
        addEdge(adjList, 1, 3);
        addEdge(adjList, 2, 5);
        addEdge(adjList, 4, 5);
        addEdge(adjList, 4, 6);
        addEdge(adjList, 5, 6);
        addEdge(adjList, 7, 8);
        addEdge(adjList, 7, 9);
        addEdge(adjList, 8, 9);
        addEdge(adjList, 10, 11);
        addEdge(adjList, 11, 12);
        addEdge(adjList, 12, 13);

        for (int i = 1; i <= vertex; i++) {
            if (!visited[i] && !otherComponent) {
                componentCount++;
                bfs(adjList, 1, 6, predecessor, distance, visited);
            }
            if (!visited[i]) {
                componentCount++;
                bfsDisconnected(adjList, i, visited);
            }
        }

        System.out.println("Total Component count : " + componentCount);

        var res = new ArrayList<Integer>();
        Arrays.fill(visited, false);
        System.out.println("DFS traversal List = " + dfs(1, adjList, visited, res));

        Arrays.fill(visited,false);
        System.out.println("Is cycle Present = " + isCyclic(vertex, adjList, visited));
    }

    static void addEdge(ArrayList<ArrayList<Integer>> adjList, int src, int dest) {               //create to avoid repeated entry of same source & destination
        adjList.get(src).add(dest);
        adjList.get(dest).add(src);
    }

    static void bfs(ArrayList<ArrayList<Integer>> adjList, int src, int dest, int[] parent, int[] dist, boolean[] visited) {
        ArrayList<Integer> bfsTraverseList = new ArrayList<>();
        LinkedList<Integer> que = new LinkedList<>();
        visited[src] = true;
        dist[src] = 0;
        que.add(src);
        while (!que.isEmpty()) {
            int currentNode = que.remove();
            bfsTraverseList.add(currentNode);
            for (Integer neighbour : adjList.get(currentNode)) {
                if (!visited[neighbour]) {
                    visited[neighbour] = true;
                    dist[neighbour] = dist[currentNode] + 1;
                    parent[neighbour] = currentNode;
                    que.add(neighbour);
                    if (neighbour == dest) {
                        System.out.println("BFS traversal Path when source and destination found = " + bfsTraverseList);
                        otherComponent = true;
                    }
                }
            }
        }
        System.out.println("Each graph traversal path = " + bfsTraverseList);
    }

    static void bfsDisconnected(ArrayList<ArrayList<Integer>> adjList, int src, boolean[] visited) {
        ArrayList<Integer> bfsTraverseList = new ArrayList<>();
        Queue<Integer> que = new LinkedList<>();
        visited[src] = true;
        que.add(src);
        while (!que.isEmpty()) {
            int cur = que.poll();
            bfsTraverseList.add(cur);
            for (Integer neighbour : adjList.get(cur)) {
                if (!visited[neighbour]) {
                    visited[neighbour] = true;
                    que.add(neighbour);
                }
            }
        }
        System.out.println("Each graph traversal path = " + bfsTraverseList);
    }

    static ArrayList<Integer> dfs(int src, ArrayList<ArrayList<Integer>> adjList, boolean[] visited, ArrayList<Integer> ans) {
        visited[src] = true;
        ans.add(src);
        for (Integer neighbor : adjList.get(src)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adjList, visited, ans);
            }
        }
        return ans;
    }

    static boolean isCyclic(int vertex, ArrayList<ArrayList<Integer>> adjList, boolean[] visited){
        for(int i=0; i <= vertex; i++) {                                                        //Check all vertex if any cycle present in other disconnected graph
            if (!visited[i])
                if (cycleDFS(i, adjList, visited, -1))                                   //Check if any connected graph has cycle then mark true
                    return true;
        }
        return false;
    }

    static boolean cycleDFS(int src, ArrayList<ArrayList<Integer>> adjList, boolean[] visited, int parent) {
        visited[src] = true;
        for (Integer neighbor : adjList.get(src)) {
            if (!visited[src]) {
                if (cycleDFS(neighbor, adjList, visited, src))                                //When "if" clause returns true, then return cycleDFS will return true
                    return true;
            } else if (parent != neighbor) {                                                  //If the condition return true, then above "If" condition will be true
                return true;
            }
        }
        return false;
    }
}

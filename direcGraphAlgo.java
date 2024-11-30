package org.example;

import java.util.*;

public class direcGraphAlgo {
    public static void main(String[] args) {
        int vertex = 6;
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        boolean[] visited = new boolean[vertex];
        int[] inDegree = new int[vertex];
        for(int i = 0; i < vertex; i++ ) {
            adjList.add(new ArrayList<>());
            visited[i] = false;
        }

        adjList.get(0).add(3);
        adjList.get(0).add(2);
        adjList.get(2).add(3);
        adjList.get(2).add(1);
        adjList.get(3).add(1);
        adjList.get(1).add(4);
        adjList.get(5).add(4);
        adjList.get(5).add(1);

        for(int i = 0; i < vertex; i++)
            if(!visited[i])
                topoSortDfs(i,adjList,visited,res);
        System.out.println("DFS Topological sorting output = " + res);

        Arrays.fill(visited, false);
        res.clear();

        for(ArrayList<Integer> list : adjList )
            for(Integer linkNode : list)
                inDegree[linkNode]++;

        topoSortBFS(vertex, adjList, res, inDegree);
        System.out.println("BFS Topological sorting output = " + res);

        Arrays.fill(visited,false);
        boolean[] recStack = new boolean[vertex];
        System.out.println("Is cycle Present = " + isCyclic(vertex, adjList, visited, recStack));

    }

    static void topoSortDfs(int src, ArrayList<ArrayList<Integer>> adjList, boolean[] visited, ArrayList<Integer> res){
        Stack<Integer> stack = new Stack<>();
        visited[src] = true;
        for(Integer neighbor : adjList.get(src)){
            if(!visited[neighbor])
                topoSortDfs(neighbor, adjList, visited, res);
        }
        stack.push(src);
        int i = 0;
        while(!stack.isEmpty())
            res.add(i++, stack.pop());
    }

    static void topoSortBFS(int vertex, ArrayList<ArrayList<Integer>> adjList, ArrayList<Integer> result, int[] inDegree){
        Queue<Integer> que = new LinkedList<>();
        for(int i = 0; i < vertex; i++)
            if(inDegree[i] == 0 )
                que.add(i);
        while(!que.isEmpty()){
            int cur = que.poll();
            result.add(cur);
            for (Integer linkNode : adjList.get(cur)){
                if(--inDegree[linkNode] == 0)
                    que.add(linkNode);
            }
        }
    }

    static boolean isCyclic(int vertex, ArrayList<ArrayList<Integer>> adjList, boolean[] visited, boolean[] recStack){
        for(int i=0; i <= vertex; i++) {                                                       //Check all vertex if any cycle present in other disconnected graph
            if (!visited[i])
                if (cycleDFS(i, adjList, visited, recStack))                                   //Check if any connected graph has cycle then mark true
                    return true;
        }
        return false;
    }

    static boolean cycleDFS(int src, ArrayList<ArrayList<Integer>> adjList, boolean[] visited, boolean[] recurStack) {
        visited[src] = true;
        for (Integer neighbor : adjList.get(src)) {
            if (!visited[src]) {
                if (cycleDFS(neighbor, adjList, visited, recurStack))                            //When "if" clause returns true, then return cycleDFS will return true
                    return true;
            } else if (!recurStack[neighbor]) {                                                  //If the condition return true, then above "If" condition will be true
                return true;
            }
        }
        recurStack[src] = false;
        return false;
    }
}

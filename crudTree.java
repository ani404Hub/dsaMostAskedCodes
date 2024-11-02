package org.example;

import org.jetbrains.annotations.Nullable;

import java.util.*;

public class crudTree {
    static Scanner sc = null;
    public static void main(String[] args) {
        Node root = createBTree();
        preOrder(root);                                        //preOrder traversal of tree
        levelOrder(root);                                      //levelOrder traversal of tree
        verticalOrder(root);                                   //verticalOrder traversal of tree
        leftView(root);                                        //leftView traversal of tree
        topView(root);                                         //topView traversal of tree
    }

    @Nullable
    static Node createBTree() {
        Node root = null;                                      //initialise root
        System.out.println("Enter root data : ");
        sc = new Scanner(System.in);
        int data = sc.nextInt();
        if (data == -1)                                        //-1 denote node is null
            return null;
        root = new Node(data);                                 //Enter root if data not empty
        System.out.println("Enter left child of " + data);
        root.left = createBTree();                             //create left child using recursion
        System.out.println("Enter right child of " + data);
        root.right = createBTree();                            //create right child using recursion
        return root;
    }

    static class Node {
        Node left, right;
        int data;
        public Node(int data) {                                //initialise constructor
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    static void preOrder(Node root) {
        if (root == null)                                     // preOrder - Root -> Left -> Right
            return;
        System.out.println("PreOrder traversal : " + root.data);
        preOrder(root.left);
        preOrder(root.right);
    }

    static void levelOrder(Node root){
        Queue<Node> q = new LinkedList<>();                  //create queue of node using linkedList
        q.add(root);
        q.add(null);                                         //add null value after root node to print level by level
        while(!q.isEmpty()){
            Node cur = q.poll();
            if(cur == null){
                if(q.isEmpty())                              //base case
                    return;
                q.add(null);                                 //add null value at end during null node iteration to print level by level
                System.out.println(" ");
                continue;                                    //skip current node after add null node
            }
            System.out.print(" Current level : " + cur.data);
            if(cur.left != null)
               q.add(cur.left);
            if(cur.right != null)
                q.add(cur.right);
        }
    }

    static void leftView(Node root){
        var list = new ArrayList<Node>();
        printLeftViewUtil(root, list, 0);
        System.out.println(" ");
        System.out.println("Left view : ");
        for(Node cur : list)
            System.out.print(" "+cur.data);
    }

    static void printLeftViewUtil(Node root, ArrayList<Node> list, int lvl){
        if(root == null)                                    //base case
            return;
        if(list.size() == lvl)                             //if list is empty then no element added in this level & add in list
            list.add(root);                                //if list has element present, no need to add as it is left child
        printLeftViewUtil(root.left, list, lvl+1);     //need to traverse left child first to print left view for next lvl
        printLeftViewUtil(root.right, list, lvl+1);
    }

    static class Pair{                                     //new class with horizontal distance(hd) as added attribute
        int hd;
        Node node;
        public Pair(int hd, Node node){                    //initialise constructor
            this.hd = hd;
            this.node = node;
        }
    }

    static void topView(Node root){
        Queue<Pair> q = new ArrayDeque<>();                //create Queue of pair using ArrayDeque, use level order traversal
        var map = new TreeMap<Integer, Integer>();         //Tree map taken for sorted order as arrayList cant have -ve index
        var ans = new ArrayList<Integer>();
        q.add(new Pair(0, root));                      //Add a new pair in queue
        while(!q.isEmpty()){
           Pair cur = q.poll();
           if(!map.containsKey(cur.hd))                   //If map contain no element in current horizontal distance,then add
               map.put(cur.hd, cur.node.data);            //For bottom view, no need for this condition as it will override exist node
           if(cur.node.left != null)
               q.add(new Pair(cur.hd-1, cur.node.left));//add left child pair in queue with decreased horizontal distance
           if(cur.node.right != null)
               q.add(new Pair(cur.hd+1, cur.node.right));//add right child pair in queue with increased horizontal distance
        }
        System.out.println(" ");
        System.out.println("Top level view : ");
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.print(" " +entry.getValue());
        }
    }

   static void verticalOrder(Node root){
        Queue<Pair> q = new ArrayDeque<>();
        var map = new TreeMap<Integer, ArrayList<Integer>>();//ArrayList is taken to store pair in each vertical level
        var list = new ArrayList<>();
        q.add(new Pair(0, root));
        while(!q.isEmpty()){
           Pair cur = q.poll();
           if(map.containsKey(cur.hd))                      //use lvl order traversal when 2 pair have same horizontal distances
               map.get(cur.hd).add(cur.node.data);          //Add pair in order which comes first in level order traversal
           else{
               var temp = new ArrayList<Integer>();
               temp.add(cur.node.data);                     //if no Pair in same hd, create arrayList & add pair then put in map
               map.put(cur.hd, temp);
           }
            if(cur.node.left != null)
                q.add(new Pair(cur.hd-1, cur.node.left));//add left child pair in queue with decreased horizontal distance
            if(cur.node.right != null)
                q.add(new Pair(cur.hd+1, cur.node.right));//add right child pair in queue with increased horizontal distance
        }
       System.out.println(" ");
       System.out.println("Vertical Order traversal : ");
       for(Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet())
           for(int key : entry.getValue())
               System.out.print(" " + key);
   }
}
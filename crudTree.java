package org.example;

import org.jetbrains.annotations.Nullable;

import java.util.*;

public class crudTree {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Node root = createBTree();                            //create binary tree
        preOrder(root);                                       //preOrder traversal of tree
        levelOrder(root);                                     //levelOrder traversal of tree
        verticalOrder(root);                                  //verticalOrder traversal of tree
        leftView(root);                                       //leftView traversal of tree
        topView(root);                                        //topView traversal of tree
        lca(root);                                            //lowest common ancestor of two nodes
        minTimeToBurn(root);                                  //Time to burn the whole tree from target node
    }

    @Nullable
    static Node createBTree() {
        Node root;                                            //initialise root
        System.out.println("Enter root data : ");
        int data = sc.nextInt();
        if (data == -1)                                       //-1 denote node is null
            return null;
        root = new Node(data);                                //Enter root if data not empty
        System.out.println("Enter left child of " + data);
        root.left = createBTree();                            //create left child using recursion
        System.out.println("Enter right child of " + data);
        root.right = createBTree();                           //create right child using recursion
        return root;
    }

    static class Node {
        Node left, right;
        int data;
        public Node(int data) {                               //initialise constructor
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
        if(root == null)                                     //base case
            return;
        if(list.size() == lvl)                               //if list is empty then no element added in this level & add in list
            list.add(root);                                  //if list has element present, no need to add as it is left child
        printLeftViewUtil(root.left, list, lvl+1);       //need to traverse left child first to print left view for next lvl
        printLeftViewUtil(root.right, list, lvl+1);
    }

    static class Pair{                                      //new class with horizontal distance(hd) as added attribute
        int hd;
        Node node;
        public Pair(int hd, Node node){                     //initialise constructor
            this.hd = hd;
            this.node = node;
        }
    }

    static void topView(Node root){
        Queue<Pair> q = new ArrayDeque<>();                 //create Queue of pair using ArrayDeque, use level order traversal
        var map = new TreeMap<Integer, Integer>();          //Tree map taken for sorted order as arrayList cant have -ve index
        q.add(new Pair(0, root));                       //Add a new pair in queue
        while(!q.isEmpty()){
           Pair cur = q.poll();
           if(!map.containsKey(cur.hd))                     //If map contain no element in current horizontal distance,then add
               map.put(cur.hd, cur.node.data);              //For bottom view, no need for this condition as it will override exist node
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
        q.add(new Pair(0, root));
        while(!q.isEmpty()){
           Pair cur = q.poll();
           if(map.containsKey(cur.hd))                      //use lvl order traversal when 2 pair have same horizontal distances
               map.get(cur.hd).add(cur.node.data);          //Add pair in order which comes first in level order traversal
           else{
               var temp = new ArrayList<Integer>();         //** Need to create ArrayList here, so that it initialises in every iteration
               temp.add(cur.node.data);                     //if no Pair in same horizontal distance, add pair in ArrayList then put in map
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
   static void lca(Node root){
       System.out.println(" ");
       System.out.println("Enter node 1 : " );
       int data1 = sc.nextInt();
       System.out.println("Enter node 2 : " );
       int data2 = sc.nextInt();
       Node lca = lowestCommonAncestor(root, data1, data2);   //Find the lowest common Ancestor between two nodes
       assert lca != null;
       System.out.println("Lowest common Ancestor of two nodes = " + lca.data);
   }

   static Node lowestCommonAncestor(Node root, int n1, int n2){
        if(root == null)
            return null;
        if((root.data == n1) || (root.data == n2))           //Between node n1 & n2, if anyone is root itself then return root
            return root;
        Node left = lowestCommonAncestor(root.left, n1, n2); //if node n1 or n2 present in left child, it will return the node
        Node right = lowestCommonAncestor(root.right, n1, n2);  //if node n1 or n2 present in right child, it will return the node
        if(left == null)                                     //if node n1 or n2 not present in left child, it will present in right so return right child
            return  right;
        if(right == null)                                   //if node n1 or n2 not present in right child, it will present in left so return left child
            return left;
        return root;                                        //if node n1 present in right & n2 present in left child, then return root
   }

    static void minTimeToBurn(Node root){
        System.out.println("Enter the Target node : ");
        int target = sc.nextInt();
        HashMap<Node, Node> parentChildMap = new HashMap<>();         //Create map to store Child and it's corresponding Parent node
        Node targetAddress = bfsToMapParent(root, parentChildMap, target);  //Map parent from child node, also return address of target node in BTree
        System.out.println("Total time need to burn the tree = " + findMaxTime(parentChildMap, targetAddress));
    }
    static int findMaxTime(HashMap<Node, Node> parentChildMap, Node targetAddress){
        Queue<Node> q = new LinkedList<>();                 //Create a Queue of node using LinkedList
        q.offer(targetAddress);                             //Push the Target node address in Queue got from bfsParentMap
        HashMap<Node, Integer> visit = new HashMap<>();     //Create map of node that is visited and set value as 1
        visit.put(targetAddress, 1);                        //Target node address has been visited so stored in map and marked as 1
        int maxTime = 0;                                    //initialise Time taken counter
        while(!q.isEmpty()){
            int length = q.size();                          //fetch the size of the queue
            int flag = 0;                                   //Initialise flag counter
            for(int i=0; i<length; i++ ){                   //Iterate no of node present in queue
                Node node = q.poll();
                assert node != null;
                if(node.left != null && visit.get(node.left) == null){              //Check left node if present & not visited yet, will burn
                    flag = 1;                                                       //Set the flag counter if current node will burn
                    visit.put(node.left, 1);                                        //Burn the left node by storing in visited map
                    q.offer(node.left);                                             //store that burned node in queue
                }
                if(node.right != null && visit.get(node.right) == null){            //Check right node if present & not visited yet, will burn
                    flag = 1;
                    visit.put(node.right, 1);                                       //Burn the left node by storing in visited map
                    q.offer(node.right);
                }
                if(parentChildMap.get(node) != null && visit.get(parentChildMap.get(node)) == null){   //Check Parent node from map if present & not visited yet, will burn
                    flag = 1;
                    visit.put(parentChildMap.get(node), 1);                         //Burn the top node by storing in visited map
                    q.offer(parentChildMap.get(node));
                }
            }
            if(flag == 1)                                   //If any of the left, right or Parent of current node is burned successfully, flag will be updated
                maxTime++;                                  //Then Time taken counter will be incremented
        }
        return maxTime;
    }
    static Node bfsToMapParent(Node root, HashMap<Node, Node> parentChildMap, int start){
        Queue<Node> q = new LinkedList<>();
        q.offer(root);                                      //Push the Root node in Queue
        Node result = new Node(-1);                    //Result node created to store address of Target node
        while(!q.isEmpty()){
            Node cur = q.poll();                            //Start level order traversal, pop the first node from queue
            if(cur.data == start)                           //if current node data is equal to target, store the address in result node
                result = cur;
            if(cur.left != null){
                parentChildMap.put(cur.left, cur);          //if current node has left child, store left child & its parent in map
                q.offer(cur.left);                          //Push the left child in queue
            }
            if(cur.right != null){
                parentChildMap.put(cur.right, cur);         //if current node has right child, store right child & its parent in map
                q.offer(cur.right);                         //Push the right child in queue
            }
        }
        return result;
    }
}
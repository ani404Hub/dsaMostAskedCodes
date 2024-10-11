package org.example;

class Node{
    int data;
    Node nextAddress;          // 'nextAddress' is reference to another obj. of same 'Node' class (allowing self referencing).
    public Node(int data){     //  useful in recursive data structures(like linkedlists,trees) where one object points to another object of same type, forming a chain
        this.data = data;
        this.nextAddress =  null;
    }
}

public class crudLinkedList {
    static int size =0;
    public static void main(String[] args) {

        Node n1 = new Node(30);
        Node n2 = new Node(20);
        Node n3 = new Node(50);
        Node n4 = new Node(10);

        Node head = n1;                                  //create pointer & assign to 1st node
        n1.nextAddress = n2;                             //Linking the list
        n2.nextAddress = n3;
        n3.nextAddress = n4;
        n4.nextAddress = null;

        //Node cur = n1;
        while(n1 != null){
            n1 = n1.nextAddress;                         //Traverse every element
            size++;                                      //to calculate size of list
        }
        int pos = 2;
        if(size >= pos){                                 //pos not more than Nodes size
            head = insertNode(60, head, pos);
            System.out.println("Inserted Sucessfully at middle");
            head = reverseNode(head);                    //reverse node and set to head
            System.out.println("Reversed Sucessfully");
            deleteNode(head, pos-2);
            System.out.println("Deleted Successfully from head");
        }
    }
    private static Node insertNode(int data, Node head, int pos){
        Node newNode = new Node(data);                  //create new node to insert
        if(pos == 0){                                   //to insert at start
            newNode.nextAddress = head;                 //set newNode next address to head (**** Evaluate toward left ****)
            head = newNode;                             //Shift head to new node
            traverseNode(head);
            return head;
        }
        Node prev = head;                               //Create a pointer & set to header
        for(int i =0; i<pos-1; i++){                    //Traverse & set prev to Position-1 element
            prev = prev.nextAddress;
        }
        newNode.nextAddress = prev.nextAddress;         // set newNode address to prev, (so that next element address not lost)
        prev.nextAddress = newNode;                     // set prev pointer to newNode
        traverseNode(head);
        return head;
    }
    private static Node deleteNode(Node head, int pos){
        if(pos == 0){
            head = head.nextAddress;
            traverseNode(head);
            return head;
        }
        Node prev = head;
        for(int i=0; i<pos-1; i++){
            prev = prev.nextAddress;                    //Traverse & set prev to Position-1 element
        }
        prev.nextAddress= prev.nextAddress.nextAddress;
        traverseNode(head);
        return head;
    }
    private static Node reverseNode(Node head){
        Node prev = null;
        Node cur = head;                               //Create pointer & assign to head
        while(cur != null) {
            Node temp = cur.nextAddress;               //Shift temp pointer to next of current
            cur.nextAddress = prev;                    //Set current pointer next address to prev pointer
            prev = cur;                                //Shift prev pointer to current
            cur = temp;                                //Shift current pointer to temp
        }
        traverseNode(prev);
        return prev;
    }
    static void traverseNode(Node head){
        Node cur = head;
        while(cur != null){
            System.out.println("Current element = " + cur.data);
            cur = cur.nextAddress;                      //Traverse every element
            size++;
        }
    }
}

package org.example;

import java.util.Arrays;
import java.util.List;

public class crudTrieTree {
    public static void main(String[] args) {
        TrieNode node = new TrieNode();
        List<String> list = Arrays.asList("this", "coding", "is", "in", "java");                         //Create list of words
        for(String newStr : list)
            insert(node, newStr);                                                                        //Insert the list of words
        System.out.println("Insertion Successful ");
        List<String> searchList = Arrays.asList("coding", "javac");
        for(String searchStr : searchList)
            System.out.println(searchStr + " present = " + search(node, searchStr));                     //Search the selected word from list
        int[] arr = new int[]{2,5,7,9,4,1};
        System.out.println("Max Sum XOR Complement = " + maxXOR(arr, arr.length));
    }
    public static class TrieNode{                                                                        //create new static class called prefix tree
        TrieNode[] child;                                                                                //Create array as it can hold many no of children
        boolean isEndOfWord ;                                                                            //Boolean flag to mark the end letter of a word
        public TrieNode(){                                                                               //Constructor to initialise all attributes
            child = new TrieNode[52];
            isEndOfWord = false;
        }
    }
    public static void insert(TrieNode root, String key){
        TrieNode curNode = root;                                                                        //Fetch the root node
        for(int i = 0; i < key.length(); i++){
            char cur = key.charAt(i);                                                                   //Fetch the current letter of the word
            if(curNode.child[cur - 'a'] == null){                                                       //check if current letter node is not present then create a new trie node
                TrieNode newNode = new TrieNode();
                curNode.child[cur - 'a'] = newNode;                                                     //Assign the new created node to store the reference of the current inserted node
            }
             curNode = curNode.child[cur - 'a'];                                                        //If current letter node already present, traverse to the child node for next letter of word
        }
        curNode.isEndOfWord = true;                                                                     //Once all the letter of the current word is inserted successfully, mark the flag as true
    }

    public static boolean search(TrieNode root, String key){
        TrieNode currNode = root;
        for(int i = 0; i < key.length(); i++){
            char curr = key.charAt(i);                                                                  //Fetch the current letter of the word
            if(currNode.child[curr - 'a'] ==  null)                                                     //If the starting letter is not present then stop iteration
                return false;
            currNode = currNode.child[curr - 'a'];                                                      //If the starting letter is present, check for the next letter
        }
        return currNode.isEndOfWord;
    }

    public static class TrieNodeBinary{                                                                 //Create new Trie data structure with 2 nodes
        TrieNodeBinary zero, one;
    }

    public static void insert32Bit(TrieNodeBinary root, int n){
        TrieNodeBinary cur = root;
        for(int i = 31; i > 0; i--){
            int bit = (n >> i) & 1;                                                                     //Create XOR operation and start fetching from MSB (from left)
            if(bit == 0){                                                                               //Checks the current bit if zero
                if(cur.zero == null)                                                                    //If current zero bit is empty create new Trie node to hold reference of current bit
                    cur.zero = new TrieNodeBinary();
                cur = cur.zero;                                                                         //Shift current pointer to the next bit
            }
            else {
                if(cur.one == null)                                                                     //If current one bit is empty create new Trie node to hold reference of current bit
                    cur.one = new TrieNodeBinary();
                cur = cur.one;                                                                          //Shift current pointer to the next bit
            }
        }
    }

    public static int maxXORComplement(TrieNodeBinary root, int n){
            TrieNodeBinary cur = root;
            int maxComplement = 0;
            for(int i = 31; i > 0; i-- ){
                int bit = (n >> i) & 1;                                                                //Create XOR operation and start fetching from MSB (from left)
                if(bit == 1){                                                                          //Checks the current bit of current element
                    if(cur.zero != null){                                                              //Try to search the complement bit from inserted element list
                        maxComplement = maxComplement + (1 << i);                                      //If complement element present then add in result
                        cur = cur.zero;                                                                //Shift current pointer to the next bit
                    }
                    else
                        cur = cur.one;                                                                 //If complement bit not present then shift to next bit
                }
                else{
                    if(cur.one != null){                                                               //Complement of above "if" operation is performed
                        maxComplement = maxComplement + (1 << i);
                        cur = cur.one;
                    }
                    else
                        cur = cur.zero;
                }
            }
            return maxComplement;                                                                      //Return the best complement integer from inserted list of the current element
    }

    public static int maxXOR(int[] arr, int n){
            TrieNodeBinary root = new TrieNodeBinary();
            int res = 0;
            for(int i = 0; i < n ; i++)
                insert32Bit(root, arr[i]);                                                            //Insertion of list of integers
            System.out.println("Integer Insertion Successful");
            for(int i = 1; i < n; i++)
                res = Math.max(res, maxXORComplement(root, arr[i]));                                  //Compare and select max sum result of an integer and its complement from the list
            return res;
    }
}

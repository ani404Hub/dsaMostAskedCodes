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
}

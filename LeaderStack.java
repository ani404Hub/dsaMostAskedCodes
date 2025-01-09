package org.example;

import java.util.*;

public class LeaderStack {
    public static void main(String[] args) {
        int[] numList = new int[]{4,10,5,8,20,15,3,12};
        printLeaderFromRight(numList);
    }
    public static void printLeaderFromRight(int[] num){
        Deque<Integer> dq = new ArrayDeque<>();                 //Deque has same stack methods, stack use vector(legacy)
        List<Integer> list = new ArrayList<>();
        for(int i = num.length-1; i>=0; i--){
            while(!dq.isEmpty() && dq.peek() <= num[i]){        // if cur elem > top stack elem, remove it
                dq.pop();
            }
            if(dq.isEmpty()){
                list.add(-1);                                   // if no highest elem before make stack empty & print -1,
            }
            else{
                list.add(dq.peek());                            // Add top element in the list
            }
            dq.push(num[i]);
        }
        System.out.println("Leader from Right = " + list);
    }
}

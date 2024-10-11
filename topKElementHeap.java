package org.example;

import java.util.PriorityQueue;

public class topKElementHeap {
    public static void main(String[] args) {
        int[] arr = new int[]{5,8,12,15,17,21,22,28};
        System.out.println("Top Kth Element = " + topKthLargest(arr, 4));
    }
    public static int topKthLargest(int[] arr, int k){          //works on sorted Array
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i = 0; i<k; i++){
            pq.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] > pq.peek()) {
                pq.poll();
                pq.add(arr[i]);
            }
        }
        return pq.peek();
    }
}

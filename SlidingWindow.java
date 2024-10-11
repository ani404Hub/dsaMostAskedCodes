package org.example;// Java Program to find the maximum for
// each and every contiguous subarray of size K.

import java.util.Deque;
import java.util.LinkedList;

public class SlidingWindow {
    static void printMax(int arr[], int N, int K) {
        Deque<Integer> Qi = new LinkedList<>();
        int i=0;
        for (; i < K; ++i) {
            while (!Qi.isEmpty()
                    && arr[i] >= arr[Qi.peekLast()])
                Qi.removeLast();
            Qi.addLast(i);
        }
        for (; i < N; ++i) {
            System.out.print(arr[Qi.peek()] + " ");
            while ((!Qi.isEmpty()) && Qi.peek() <= i - K)
                Qi.removeFirst();
            while ((!Qi.isEmpty())
                    && arr[i] >= arr[Qi.peekLast()])
                Qi.removeLast();
            Qi.addLast(i);
        }
        System.out.print(arr[Qi.peek()]);
    }

    public static void main(String[] args) {
        int arr[] = {4, 8, 5, 7, 6, 2, 9, 7, 3};
        int K = 3;
        printMax(arr, arr.length, K);
        printKMax(arr, arr.length, K);     //Brute force
    }

    private static void printKMax(int arr[], int n, int k){
        int max;
        for(int i=0; i<n-k; i++){
            max = arr[i];
            for(int j=1; j<k; j++){
                if(arr[i+j]>max)            //if (arr[1] or arr[2] > arr[0])
                    max = arr[i+j];
            }
            System.out.print(max+" ");
        }
    }
}

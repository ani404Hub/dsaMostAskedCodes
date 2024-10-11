package org.example;

public class searchInRotatedArray {
    public static void main(String[] args){

    System.out.println(search(new int[]{4,5,8,9,1,2,3}, 0, 6, 2));
    }
    private static int search(int arr[], int left, int right, int key){
        int pivot = getPivot(arr, left, right);      // pivot,l,r are indexes not value
        int e = bs(arr, left, pivot, key);           // search key in left
        if(e == -1){
           e = bs(arr,pivot+1, right, key);     // if key not found in left, search right
        }
        return e; //return the index of searched element
    }
    //binary search logic
    private static int bs(int[] a, int low, int high, int key) {
        while(low <= high){
            int mid = (low + high)/2;
            if(a[mid] == key){
                return mid;
            } else if (a[mid] < key) {              // a[mid] is value while mid is index
                low = mid+1;
            } else{
                high = mid-1;                       //if element not in slot, decrease pivot index to stop while loop
            }
        }
        return -1;                                  //If element not in list

    }
    //Find pivot
    private static int getPivot(int[] a, int l, int r) {
        while(l <= r){
            int mid = (l+r)/2;
            if(a[mid]> a[mid+1]){               //if mid > mid+1, then mid is pivot
                return mid;
            } else if (a[mid]< a[mid-1]) {      //if mid < mid-1, then mid-1 is pivot
                return mid-1;
            }
            else if (a[mid] > a[l]){            //if mid is greater than left then left sorted in ascend order, shift left to mid+1
                l = mid+1;
            }
            else{
                r = mid-1;                      //if left not sorted, then right sorted, shift right to mid-1
            }
        }
        return -1;
    }
}

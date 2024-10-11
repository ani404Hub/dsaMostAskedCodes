package org.example;

import java.util.*;

public class twoSum3SumArray {
    public static void main(String[] args) {
        int[] nums = {2, 7, 7, 11, 15};
        int target = 9;
        twoSum(nums, target);                                                        //using Hashing Space Complexity o(n)
        twosum2pointer(nums,target);                                                 //optimal approach space complexity O(1)
        int[] num = {2, 7, 7, 2, 15};
        int targets = 11;
        threesum3pointer(num, targets);                                              //three sum Optimal approach Space complexity O(1)
        int[] numList = {5,-5,20,25,-35};
        target = 10;
        targetSumSubArray(numList, target);

    }
    public static void twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int complement1 = 0, complement2 = 0, complement = 0;
        for (int i = 0; i < nums.length; i++) {
            complement1 = Math.abs(target - nums[i]);                                /// take absolute value for target-num
            complement2 = Math.abs(target + nums[i]);                                /// take absolute value for target+num
            if (map.containsKey(complement1)){                                       /// If target-num matches to HashMap key
                System.out.println(Arrays.toString(new int[]{map.get(complement1),i}));
            }
            if (map.containsKey(complement2)){              /// If target+num matches to HashMap key
                System.out.println(Arrays.toString(new int[]{map.get(complement2),i}));
            }
            /*if (map.containsKey(complement1) || map.containsKey(complement2)) {           /// If either of matches to HashMap key
                System.out.println(Arrays.toString(new int[]{map.get((complement1 != 0) ? complement1 : complement2), i}));
            }*/
            map.put(nums[i], i);                                                    // Add the current number and its index to the map
        }
    }
    public static void twosum2pointer(int[] num, int target){
        Arrays.sort(num);                                                          //sort the array
        int left = 0;                                                              //set 2 pointers
        int right = num.length-1;
        while(left < right){
            if( num[left] == num[left+1])
                continue;
            int sum = num[left] + num[right];
            if(sum == target){
                StringJoiner join = new StringJoiner(", ","[","]");     //use string Joiner to indent
                join.add(String.valueOf(left));
                join.add(String.valueOf(right));
                System.out.println("Index pairs = " + join);
                break;
            } else if (sum > target) {                                      //decrease right pointer when sum > target in sorted array
                right --;
            } else {
                left++;
            }
        }
    }
    public static void threesum3pointer(int[] num, int target){
        Arrays.sort(num);
        for(int i =0; i< num.length-2; i++){                                       //length-2 coz two other pointer will set in it
            if(i>0 && num[i] == num[i-1]) continue;                                //skip duplicate during iterate of i
            int left = i+1;                                                        //set 2 pointers
            int right = num.length-1;
            while(left < right){
                int sum = num[i]+ num[left]+num[right];
                if(sum > target){
                    right--;
                } else if (sum < target) {
                    left ++;
                }
                else {
                    System.out.println("Target List = " + Arrays.asList(num[i],num[left],num[right]));
                    left ++; right --;                                              //increment & decrement of next pointers
                    while(left<right && num[left] == num[left+1]) left ++;          //skip duplicates to the right while i is fixed
                    while(left<right && num[right] == num[right-1]) right --;       //skip duplicates to the left while i is fixed
                }
            }
        }
    }
    public static void targetSumSubArray(int[] num, int target){
        int start = 0 ; int i = 0;                                                 //Two pointers one at start, other at current
        int end = -1;
        int curSum = 0;
        var curSumIndex = new HashMap<Integer, Integer>();                         //Map with current Sum as key & Index as Value
        while(i < num.length){
            curSum = curSum+num[i];
            if(curSum == target){                                                  // If target sum at 1st index
                end = i;
                break;
            }
            if(curSumIndex.containsKey(curSum-target)){                            //if map has the stored key of (curSum - target)
                start = curSumIndex.get(curSum-target)+1;                          //set start index to target sum start index
                end = i;
            }
            curSumIndex.put(curSum, i);                                            //store Current sum and current Index
            i++;
        }
        if(end == -1){
            System.out.println("No element Found ");
        }
        else{
            System.out.println("Start Index = " + start + ", End Index = " + end);
        }
    }
}

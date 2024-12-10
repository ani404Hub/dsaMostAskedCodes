package org.example;

import java.util.Arrays;

public class dynamicProgramming {
    public static void main(String[] args) {
        int totalSum = 11;
        int[] coinDenomination = {1, 5, 6, 9};
        int[] dynamicMemory = new int[totalSum + 1];                                                //As coin sum count starts from 1
        Arrays.fill(dynamicMemory, -1);                                                        //Fill the array with - 1
        dynamicMemory[0] = 0;                                                                      //Fill the 0th position with 0 as it won't be filled later
        System.out.println("Minimum no of coins of 1, 5, 6, 9 required to make " + totalSum + " is : " + minNoCoinReqd(totalSum, coinDenomination, dynamicMemory));

        int[] price = {15, 14, 10, 45, 30};
        int[] wt = {2, 5, 1, 3, 4};
        System.out.println("Maximum Profit within limited capacity sack = " + maxKnapSack(5, 7, wt, price));
    }

    static int minNoCoinReqd(int totalCoinSum, int[] coinDenomination, int[] dynMemory){
        if(totalCoinSum == 0)                                                                      //Initialise base case
            return 0;
        int res = Integer.MAX_VALUE;                                                               //Initialise result with max value
        for(int i = 0; i < coinDenomination.length; i++){
            if(totalCoinSum - coinDenomination[i] >= 0){                                           //Only take the coin denomination so that after subtraction result should not be < 0
                int subRes;
                if(dynMemory[totalCoinSum - coinDenomination[i]] != - 1)                           //It checks if index value of (totalSum - current denomination) array is not -1 then result already calculated before
                    subRes = dynMemory[totalCoinSum - coinDenomination[i]];                        //Put result already calculated before in sub Answer
                else
                    subRes = minNoCoinReqd(totalCoinSum - coinDenomination[i], coinDenomination, dynMemory);     //Call recursion for (totalSum - current denomination)
            if(subRes != Integer.MAX_VALUE && subRes + 1 < res )                                   //If no of coins calculated + 1 ( add current coin count ) is minimum than previous count which can be MAX VALUE or less then replace
                res = subRes + 1;
            }
        }
        dynMemory[totalCoinSum] = res;                                                             //Fill the dynamic memory array for each index with end result
        return  res;
    }

    static int maxKnapSack(int noOfAttempt, int maxCap, int[] wt, int[] price){
        int[][] dp = new int[noOfAttempt + 1][maxCap + 1];                                        //Create a 2D memorisation array with + 1 as blank row & col to store the result
        int remCap;
        for(int i = 1; i< dp.length; i++ ){
            for(int j = 1; j < dp[0].length; j++ ){
                if(j >= wt[i - 1] ) {                                                             //If current weight is included & more than top element of 2D array
                    remCap = j - wt[i - 1];
                    dp[i][j] = Math.max(dp[i - 1][remCap] + price[i - 1], dp[i - 1][j]);          //Take max value of current item price + price of top remain capacity index element or top index value
                }
                else
                    dp[i][j] = dp[i - 1][j];                                                      //If current weight not included in bag, take value of the top index
            }
        }
        return dp[noOfAttempt][maxCap];                                                          //Return value of last 2D index which store the max value
    }
}

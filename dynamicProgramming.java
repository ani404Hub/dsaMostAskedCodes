package org.example;

import java.util.Arrays;

public class dynamicProgramming {
    public static void main(String[] args) {
        int totalSum = 11;
        int[] coinDenomination = {1, 5, 6, 9};
        int[] dynamicMemory = new int[totalSum + 1];                                                //As coin sum count starts from 1
        Arrays.fill(dynamicMemory, -1);                                                        //Fill the array with - 1
        dynamicMemory[0] = 0;                                                                      //Fill the 0th position as it won't be used
        System.out.println("Minimum no of coins of 1, 5, 6, 9 required to make " + totalSum + " is : " + minNoCoinReqd(totalSum, coinDenomination, dynamicMemory));
    }

    static int minNoCoinReqd(int totalCoinSum, int[] coinDenomination, int[] dynMemory){
        if(totalCoinSum == 0)                                                                      //Initialise base case
            return 0;
        int res = Integer.MAX_VALUE;                                                               //Initialise result with max value
        for(int i = 0; i < coinDenomination.length; i++){
            if(totalCoinSum - coinDenomination[i] >= 0){                                           //Only take the coin denomination so that after subtraction result should not be < 0
                int subRes = 0;
                if(dynMemory[totalCoinSum - coinDenomination[i]] != - 1)                           //It checks if index value of (totalSum - current denomination) array is not -1 then result already calculated before
                    subRes = dynMemory[totalCoinSum - coinDenomination[i]];                        //Put result already calculated before in sub Answer
                else
                    subRes = minNoCoinReqd(totalCoinSum - coinDenomination[i], coinDenomination, dynMemory);     //Call recursion for (totalSum - current denomination)
            if(subRes != Integer.MAX_VALUE && subRes + 1 < res )                                   //If no of coins calculated + 1 ( add current coin count ) is minimum than previous count which can be MAX VALUE or less then replace
                res = subRes + 1;
            }
        }
        dynMemory[totalCoinSum] = res;                                                             //Fill the dynamic memory array by taking the end result
        return  res;
    }
}

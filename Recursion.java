package org.example;
import java.util.*;

public class Recursion {

    public static void main(String[] args){
//        print(4);
//        factorial(5);
//        int n = 9;
//        System.out.println("Sum Total for "+ n + " terms : "  + sum(n));
//
//        //josephus problem
//        int n = 5, k = 3;
//        System.out.println(Josh(n, k));

        //powerset of string
        Powerset("ab",0,"");
    }

    private static int sum(int n){
        int x = 0;
        if(n == 1)
            return n;

        return n + sum(n - 1);
    }

    private static int factorial(int n){
        if(n == 1 || n == 0) {
            System.out.println("Returning 1 because n is " + n);
            return 1;
        }
        else {
            int x = n * factorial(n-1);
            System.out.println("Factorial of "+ n + " is : " + x);
            return x;
        }
    }

    private static void print(int n){
        if(n == 1){
            System.out.println(n);
            return;
        }
        System.out.println(n);
        print(n-1);
        return;
    }

    static int Josh (int k, int n)
    {
        // Base case , when only one person is left
        if (n == 1) {
            return 1;
        }
        // recursive call for n-1 persons
        return ((Josh(n-1, k)+ k-1)%n) +1;
    }

    static void Powerset (String s,int i, String cur)
    {
        // Base case
        if (i == s.length()) {
            System.out.printf(" "+cur);
            return;
        }
        // print left side that include letter
        Powerset(s,i+1, cur+s.charAt(i));
        // print rigth side that exclude letter
        Powerset(s,i+1, cur);
    }
}

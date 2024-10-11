package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Spiral2DTraverse {
    static int x = 3131;

    static class Y {
        static int y = x++;

        static class Z {
            static int z = y++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Define Matrix with no of rows and cols
        int rows = sc.nextInt();                // int row = matrix.length;
        int cols = sc.nextInt();                // int col = matrix[0].length;
        int[][] matrix = new int[rows][cols];
        List<Integer> list = new ArrayList<>();
        List<Integer> listRev = new ArrayList<>();

        int top = 0, bottom = rows-1;
        int left = 0, right = cols-1;

        // Filling matrix with data
        for(int i =0; i < rows; i++){
            for(int j=0; j<cols; j++){
                matrix[i][j] = sc.nextInt();
            }
        }
        //Print the Matrix
        for(int[] arr : matrix){
            System.out.println(Arrays.toString(arr));
        }

        while(top <= bottom && left <= right){

            for(int i = left; i<=right; i++){
                //System.out.print(matrix[top][i]);
                list.add(matrix[top][i]);
            }
            top ++;

            for(int i = top; i<=bottom; i++){
                //System.out.print(matrix[i][right]);
                list.add(matrix[i][right]);
            }
            right --;

            if(top <= bottom) {
                for (int i = right; i >= left; i--) {
                    //System.out.print(matrix[bottom][i]);
                    list.add(matrix[bottom][i]);
                }
                bottom--;
            }

            if(left <= right) {
                for (int i = bottom; i >= top; i--) {
                    //System.out.print(matrix[i][left]);
                    list.add(matrix[i][left]);
                }
                left++;
            }
        }
        System.out.println(list);

        //Anticlockwise traverse 2d spiral
        for(int i=top; i<=bottom; i++){
            listRev.add(matrix[top][i]);
        }
        left++;
        for(int i=left; i<=right; i++){
            listRev.add(matrix[i][left]);
        }
        bottom --;
        for(int i=bottom; i>= top; i-- ){
            listRev.add(matrix[bottom][i]);
        }

    }
}
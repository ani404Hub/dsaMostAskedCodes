package org.example;

class backTracking {
    final int N = 4;
    public static void main(String[] args) {
        String str = "abc";
        permute(str,0,str.length()-1);
        //used in tic-tac-toe game
        if(new backTracking().solveNQueen())
            System.out.println("NQueen placed successfully ");
    }

    boolean nQueen(int board[][], int col){
        if (col >= N)
            return true;
        for (int row = 0; row < N; row++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 1;
                if (nQueen(board, col + 1))
                    return true;
                board[row][col] = 0; // BACKTRACK
            }
        }
        return false;
    }

    boolean isSafe(int board[][], int row, int col)
    {
        int i, j;
     /*  Step-1. Check row on left
         Step-2. Check upper left diagonal
         Step-3. Check lower left diagonal */
        for (i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;
        for (i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i][j] == 1)
                return false;
        return true;
    }

    private static void permute(String perm, int l, int r){
        if(l == r){
            System.out.println("Permuted String = " + perm);
            return;
        }
        /* Step-1. value of l only change during recursion
            Step-2. swap 1st letter with 1st letter during first execution
            Step-3. to original string */
        for(int i= l; i<=r; i++){
            perm = swap(perm,l,i);
            permute(perm,l+1,r);
            perm = swap(perm,l,i);
        }
    }

    private static String swap(String str, int i, int j) {
        char[] arr = str.toCharArray();
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        return String.valueOf(arr);
    }

    boolean solveNQueen(){
        int board[][] = {   { 0, 0, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 0, 0, 0 }   };

        if (nQueen(board, 0) == false) {
            System.out.print("Solution does not exist");
            return false;
        }

        printSoln(board);
        return true;
    }

    void printSoln(int board[][]){
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if (board[i][j] == 1)
                    System.out.print("Q");
                else
                    System.out.print(".");
            }
            System.out.println();
        }
    }

}

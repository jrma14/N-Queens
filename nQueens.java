import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class nQueens {
    static HashMap<Integer,Integer> cols = new HashMap<>();
    static HashMap<Integer,Integer> rightDiag = new HashMap<>();
    static HashMap<Integer,Integer> leftDiag = new HashMap<>();


    public static void main(String[] args) {//On my pc this whole program took about 44 seconds to run, if it takes to long try changing the first two ints, countNumAnswers has a bigger effect on time
        int getFirstAnswers = 29;
        int countNumAnswers = 14;
        long b = System.currentTimeMillis();
        System.out.println("isLegalPosition(new int[]{1,6,8,3,7,4,2,5}, 8): " + printIsLegalPosition(new int[]{1,6,8,3,7,4,2,5},8));//here I named the function printIsLegalPosition because
        System.out.println("isLegalPosition(new int[]{1,6,8,3,7,0,0,0}, 8): " + printIsLegalPosition(new int[]{1,6,8,3,7,0,0,0},8));// I have two methods for isLegal in order to optimize it for
        System.out.println("isLegalPosition(new int[]{1,6,8,3,5,0,0,0}, 8): " + printIsLegalPosition(new int[]{1,6,8,3,5,0,0,0},8));//getFirstAnswer and countNumAnswers
        System.out.println("nextLegalPosition(new int[]{1,6,8,3,5,0,0,0}, 8): " + Arrays.toString(printNextLegalPosition(new int[]{1,6,8,3,5,0,0,0},8)));//Same reasoning here as printIsLegalPostion
        System.out.println("nextLegalPosition(new int[]{1,6,8,3,7,0,0,0}, 8): " + Arrays.toString(printNextLegalPosition(new int[]{1,6,8,3,7,0,0,0},8)));
        System.out.println("nextLegalPosition(new int[]{1,6,8,3,7,4,2,5}, 8): " + Arrays.toString(printNextLegalPosition(new int[]{1,6,8,3,7,4,2,5},8)));
        getFirstAnswers(getFirstAnswers);
        for(int i = 1; i <= countNumAnswers; i++){
            System.out.println("There are " + countNumAnswers(i) + " solutions to the " + i + "-queens problem");
        }
        long a = System.currentTimeMillis();
        System.out.println((a-b) + "ms");
    }

    private static int[] printNextLegalPosition(int[] board, int n) {
        return nextLegalPosition2(board,n);
    }
    private static boolean printIsLegalPosition(int[] board, int n) {
        return isLegalPosition2(board, n);
    }
    public static boolean isLegalPosition(int[] board, int n){
        int lastInd = board[0];
        return cols.get(board[lastInd] - 1) <= 1 && rightDiag.get(board[lastInd] - lastInd) <= 1 && leftDiag.get(-(board[lastInd] - 1) + (n - lastInd)) <= 1;
    }
    public static boolean isLegalPosition2(int[] board, int n){
        if(board.length > n){
            return false;
        }
        HashSet<Integer> cols = new HashSet<>();
        HashSet<Integer> rightDiag = new HashSet<>();
        HashSet<Integer> leftDiag = new HashSet<>();
        for(int i = 0; i < board.length; i++){
            if(board[i] != 0 && (cols.contains(board[i] - 1) || rightDiag.contains(board[i] - 1 - i) || leftDiag.contains(-(board[i] - 1) + (n - i - 1)))){
                return false;
            }
            cols.add(board[i] - 1);
            rightDiag.add(board[i] - 1 - i);
            leftDiag.add(-(board[i] - 1) + (n - i - 1));
        }
        return true;
    }
    public static int[] nextLegalPosition(int[] board, int n) {
        do{
            board = getSuccessor(board, n);
            if(board[0] > n){
                break;
            }
        }while(!isLegalPosition(board, n));
        return board;
    }
    public static int[] nextLegalPosition2(int[] board, int n) {
        do{
            board = getSuccessor2(board, n);
        }while(!isLegalPosition2(board, n));
        return board;
    }
    public static int[] getSuccessor(int[] board,int n){
        int curr_row = board[0];
        int i = board[curr_row] + 1;
        while (i > n) {
            cols.put(board[curr_row] - 1, cols.get(board[curr_row] - 1) - 1);
            rightDiag.put(board[curr_row] - curr_row, rightDiag.get(board[curr_row] - curr_row) - 1);
            leftDiag.put(-(board[curr_row] - 1) + (n - curr_row), leftDiag.get(-(board[curr_row] - 1) + (n - curr_row)) - 1);
            board[curr_row] = 0;
            curr_row--;
            board[0] = curr_row;
            i = board[curr_row] + 1;
        }
        if(curr_row == 0){
            int[] ret = new int[n + 1];
            ret[0] = n + 1;
            return ret;
        }
        if(board[curr_row] == 0){
            board[curr_row]++;
            cols.put(board[curr_row] - 1,cols.get(board[curr_row] - 1) + 1);
            rightDiag.put(board[curr_row] - curr_row, rightDiag.get(board[curr_row] - curr_row) + 1);
            leftDiag.put(-(board[curr_row] - 1) + (n - curr_row),leftDiag.get(-(board[curr_row] - 1) + (n - curr_row)) + 1);
            board[0] = curr_row;
            return board;
        }
        cols.put(board[curr_row] - 1, cols.get(board[curr_row] - 1) - 1);
        rightDiag.put(board[curr_row] - curr_row, rightDiag.get(board[curr_row] - curr_row) - 1);
        leftDiag.put(-(board[curr_row] - 1) + (n - curr_row), leftDiag.get(-(board[curr_row] - 1) + (n - curr_row)) - 1);
        board[curr_row]++;
        cols.put(board[curr_row] - 1,cols.get(board[curr_row] - 1) + 1);
        rightDiag.put(board[curr_row] - curr_row, rightDiag.get(board[curr_row] - curr_row) + 1);
        leftDiag.put(-(board[curr_row] - 1) + (n - curr_row),leftDiag.get(-(board[curr_row] - 1) + (n - curr_row)) + 1);
        return board;
    }
    public static int[] getSuccessor2(int[] board,int n){
        int curr_row = 0;
        while (curr_row < n - 1 && board[curr_row] != 0) {
            curr_row++;
        }
        int i = board[curr_row] + 1;
        while (i > n) {
            board[curr_row] = 0;
            curr_row--;
            if(curr_row == -1){
                int[] ret = new int[n];
                return ret;
            }
            i = board[curr_row] + 1;
        }
        board[curr_row]++;
        return board;
    }
    public static void getFirstAnswers(int n){
        for(int i = 1; i <= n; i++){
            System.out.println("The first answer to the " + i + "-queens problem is: " + Arrays.toString(getFirstAnswer(i)));
        }
    }
    public static int[] getFirstAnswer(int n){
        for(int i = 0; i < n; i++){
            cols.put(i, 0);
            rightDiag.put(i,0);
            rightDiag.put(-i,0);
            leftDiag.put(i,0);
            leftDiag.put(-i,0);
        }
        int[] board = new int[n + 1];
        board[0] = 1;
        while(board[0] <= n){
            board = nextLegalPosition(board, n);
            board[0]++;
        }
        return Arrays.copyOfRange(board,1,n + 1);
    }
    public static int countNumAnswers(int n){
        for(int i = 0; i < n; i++){
            cols.put(i, 0);
            rightDiag.put(i,0);
            rightDiag.put(-i,0);
            leftDiag.put(i,0);
            leftDiag.put(-i,0);
        }
        int[] board = new int[n + 1];
        board[0] = 1;
        boolean start = false;
        int count = 0;
        while(board[0] != n + 2) {
            if(start) {
                board[0] = n;
                count++;
            }
            while (board[0] <= n) {
                board = nextLegalPosition(board, n);
                board[0]++;
            }
            start = true;
        }
        return count;
    }
    public static int testCountNumAnswers(int n){
        for(int i = 0; i < n; i++){
            cols.put(i, 0);
            rightDiag.put(i,0);
            rightDiag.put(-i,0);
            leftDiag.put(i,0);
            leftDiag.put(-i,0);
        }
        int[] board = new int[n + 1];
        board[0] = 1;
        int count = 0;
        while(board[1] <= Math.ceil(n/2)) {
            while (board[0] <= n) {
                board = nextLegalPosition(board, n);
                board[0]++;
            }
            board[0] = n;
            count++;
        }
        return count * 2;
    }
}
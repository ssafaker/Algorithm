package taewon_ahn;

import java.io.*;

public class Main_BOJ_17136 {
    static int N;
    static int[] A;
    static int cnt = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        A = new int[N];
        backtracking(0);
        System.out.println(cnt);
    }

    private static void backtracking(int row) {
        if (row == N) {
            cnt++;
            return;
        }
        for (int c = 0; c < N; c++) {
            A[row] = c;
            if (check(row)) {
                backtracking(row + 1);
            }
        }
    }

    private static boolean check(int row) {
        for (int i = 0; i < row; i++) {
            if (A[i] == A[row]) // 일직선 공격
                return false;
            if (Math.abs(row - i) == Math.abs(A[row] - A[i])) // 대각선 공격
                return false;
        }
        return true;
    }

}

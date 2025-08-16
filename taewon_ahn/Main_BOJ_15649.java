package taewon_ahn;

import java.io.*;
import java.util.*;

public class Main_BOJ_15649 {
    static int N, M;
    static int[] S;
    static boolean[] V;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        S = new int[M]; // 현재 만들어진 배열
        V = new boolean[N + 1]; // 수 사용 여부 체크 배열
        backtracking(0);
    }

    private static void backtracking(int idx) {
        if (idx == M) {
            printArr();
            return;
        }
        for (int i = 1; i <= N; i++) {
            if (V[i])
                continue;
            V[i] = true;
            S[idx] = i;
            backtracking(idx + 1);
            V[i] = false;
        }
    }

    private static void printArr() {
        for (int i = 0; i < M; i++) {
            System.out.print(S[i] + " ");
        }
        System.out.println();
    }

}

package taewon_ahn;

import java.io.*;
import java.util.*;

public class Main_BOJ_11050 {
    static int N, K;
    static int D[][];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        D = new int[N + 1][N + 1];
        for (int i = 0; i <= N; i++) {
            D[i][i] = 1; // 모두 선택
            D[i][0] = 1; // 0개 선택
            D[i][1] = i; // i개중 1개 선택
        }

        // 점화식으로 배열 완성
        for (int i = 2; i <= N; i++) {
            for (int j = 1; j < i; j++) {
                D[i][j] = D[i - 1][j] + D[i - 1][j - 1];
            }
        }
        System.out.println(D[N][K]);
    }
}

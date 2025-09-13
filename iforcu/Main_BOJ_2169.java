package iforcu;

import java.io.*;
import java.util.*;
/*
 *  문제명 : [로봇 조종하기]
 *  링크   : [https://www.acmicpc.net/problem/2169]
 *  난이도 : [골드 2]
 *  설명   :
 *    - N×M 배열에서 로봇이 (1,1)에서 (N,M)으로 이동하며, 위로는 이동 불가, 한 칸은 한 번만 방문 가능.
 *    - 탐사 가치의 합이 최대가 되도록 경로를 선택해야 한다.
 *  풀이   :
 *    - DP를 이용해 각 칸까지의 최대 탐사 가치 합을 계산.
 *    - 각 행마다 좌→우, 우→좌로 두 번 탐색해 최적 경로를 갱신.
 */

public class Main_BOJ_2169 {
    static int N, M;
    static int[][] map;
    static int[][] dp;
    static int[] tempL, tempR;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        dp = new int[N][M];
        
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        dp[0][0] = map[0][0];
        // 첫 번째 행은 오른쪽으로만 이동 가능하므로 누적합 계산
        for (int j = 1; j < M; j++) {
            dp[0][j] = dp[0][j - 1] + map[0][j];
        }

        for (int i = 1; i < N; i++) {
            // 각 행마다 좌→우(tempL), 우→좌(tempR)로 두 번 탐색
            tempL = new int[M];
            tempR = new int[M];

            tempL[0] = dp[i - 1][0] + map[i][0];
            for (int j = 1; j < M; j++) {
                tempL[j] = Math.max(dp[i - 1][j], tempL[j - 1]) + map[i][j];
            }

            tempR[M - 1] = dp[i - 1][M - 1] + map[i][M - 1];
            for (int j = M - 2; j >= 0; j--) {
                tempR[j] = Math.max(dp[i - 1][j], tempR[j + 1]) + map[i][j];
            }
            
            for (int j = 0; j < M; j++) {
                dp[i][j] = Math.max(tempL[j], tempR[j]);
            }
        }
        // (N, M)까지의 최대 탐사 가치 합 출력
        System.out.println(dp[N - 1][M - 1]);
    }
}
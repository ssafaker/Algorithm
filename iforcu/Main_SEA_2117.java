package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [홈 방범 서비스]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5V61LqAf8DFAWu]
 *  난이도 : [모의 SW 역량테스트]
 *  설명:
 *  - 2차원 배열에 방범 서비스 제공
 *  - 방범 서비스 범위 K는 1 ~ (N+1)까지
 *  - M * houseCount - operateCost 이익이 나오면 houseCount의 최대 값을 구하는 문제
 *  풀이:
 *  - 모든 위치에서 K만큼 범위의 모든 값을 탐색
 *  - 이익이 나오면 집 갯수 리턴 아니면 0 리턴
 *  - dist = |a1 - a2| + |b1 - b2|를 이용해 집 탐색
 */

public class Main_SEA_2117 {
    static int N, M, operateCost;
    static int[][] grid;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            grid = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            int ans = 0;
            // K 는 1 ~ N+1
            for (int K = 1; K <= N+1; K++) {
                operateCost = K * K + (K-1) * (K-1);
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        int cost = getCost(i,j, K);
                        ans = Math.max(ans, cost);
                    }
                }
            }
            System.out.println("#" + test_case + " " + ans);
        }
    }
    static int getCost(int i, int j, int K) {
        int houseCount = 0;
        for (int r = i-K; r <= i+K; r++) {
            for (int c = j-K; c <= j+K; c++) {
                if(!isValid(r, c)) continue;
                // 거리 공식 사용 벗어나면 탐색 안함.
                int dist = Math.abs(i-r) + Math.abs(j-c);
                if(dist > (K-1)) continue;
                if(grid[r][c] == 1) houseCount++;
            }
        }
        // 이익이 날 때만 집 갯수 리턴 
        return (houseCount * M) - operateCost >= 0 ? houseCount : 0;
    }

    static boolean isValid(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}

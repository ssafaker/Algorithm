package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [디저트 카페]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5VwAr6APYDFAWu]
 *  난이도 : [모의 SW 역량테스트]
 *  설명:
 *  - 대각선 방향으로 이동하면서 경로를 구하는 문제
 *  - 출발지점과 도착지점이 같아야하며, 직사각형 모양이 되어야함
 *  - 직사격형 모양이 되려면 출발 방향과 도착 방향이 정확해야함.
 *  - 지나간 경로의 값은 중복이 되면 안됨.
 *  풀이:
 *  - DFS를 이용해 구함
 *  - 지나간 경로는 boolean[101]으로 구함
 *  - 다음 dir 방향은 0 or 1로 만들어 앞으로 직진 or 옆으로 꺽기로 탐색함
 *  - startR, startC에 초기 위치를 저장함.
 *  - 방향은 0:↘, 1:↗, 2:↖, 3:↙
 *  - 방향 3을 넘어가면 직사각형 실패, 출발지점 돌아왔을 때 방향 3이 아니면 실패
 */

public class Main_SEA_2105 {
    static int N, ans, startR, startC;
    static int[][] grid;
    static boolean[] visitedDessert;
    static int[] dr = {1, 1, -1, -1};
    static int[] dc = {1, -1, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            N = Integer.parseInt(br.readLine());
            grid = new int[N][N];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            ans = -1;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    startR = i;
                    startC = j;
                    visitedDessert = new boolean[101];
                    dfs(i, j, 0, 1);
                }
            }
            System.out.println("#" + test_case + " " + ans);
        }
    }

    static void dfs(int r, int c, int dir, int count) {
        visitedDessert[grid[r][c]] = true;
        // 다음 방향을 0 or 1로 만들어 이동
        for (int i = 0; i < 2; i++) {
            int nextDir = dir + i;
            // 방향이 4를 넘어가면 실패
            if (nextDir >= 4) continue;
            int nr = r + dr[nextDir];
            int nc = c + dc[nextDir];

            if (isValid(nr, nc)) {
                if (nr == startR && nc == startC) {
                    // 방향이 3이 맞으면 성공
                    if (nextDir == 3) {
                        ans = Math.max(ans, count);
                    }
                    continue;
                }

                //방문을 안했으면 dfs
                if (!visitedDessert[grid[nr][nc]]) {
                    dfs(nr, nc, nextDir, count + 1);
                }
            }
        }
        visitedDessert[grid[r][c]] = false;
    }

    static boolean isValid(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}
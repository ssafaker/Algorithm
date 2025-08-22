package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [감시]
 *  링크 : [https://www.acmicpc.net/problem/15683]
 *  난이도 : [골드 3]
 *  설명:
 *  - 사무실에 설치된 CCTV가 감시할 수 있는 방향을 설정하여,
 *    사각지대(감시되지 않는 영역)의 최소 크기를 구하는 문제.
 *  - CCTV는 종류(1~5)에 따라 감시 가능한 방향이 다름.
 *  - 벽(6)은 감시 불가능하며 CCTV는 통과 가능.
 *  풀이:
 *  - DFS + 백트래킹:
 *    각 CCTV에 대해 가능한 방향 조합을 모두 탐색하며,
 *    감시 영역을 표시했다가 원상 복구하는 방식으로 진행.
 *  - 모든 CCTV 방향 설정이 끝났을 때 사각지대(0의 개수)를 계산해 최솟값 갱신.
 */

public class Main_BOJ_15683 {
    static int N, M, size, ans;
    static int[][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static List<int[]> cctvs;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        cctvs = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] != 0  && map[i][j] != 6) cctvs.add(new int[] {i, j, map[i][j]});
            }
        }
        ans = N * M;
        size = cctvs.size();
        cctvs.sort((a,b) -> Integer.compare(b[2], a[2])); // CCTV 번호 큰 순서대로 정렬
        
        dfs(0);

        System.out.println(ans);
    }

    // DFS + 백트래킹: 모든 CCTV의 방향 경우 탐색
    static void dfs(int depth) {
        if(depth == size) {
            int count = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if(map[i][j] == 0) count++;
                }
            }
            ans = Math.min(ans, count);
            return;
        }

        int curR = cctvs.get(depth)[0];
        int curC = cctvs.get(depth)[1];
        // 1. 일직선
        if(map[curR][curC] == 1) {
            for (int d = 0; d < 4; d++) {
                int[][] temp = new int[N][];
                for (int j = 0; j < N; j++) {
                    temp[j] = Arrays.copyOf(map[j], N);
                }

                visibleCCTV(curR, curC, d);
                dfs(depth + 1);
                for (int j = 0; j < N; j++) {
                    map[j] = Arrays.copyOf(temp[j], N);
                }
            }
        }
        // 2. 양방향
        else if(map[curR][curC] == 2) {
            for (int d = 0; d < 2; d++) {
                int[][] temp = new int[N][];
                for (int j = 0; j < N; j++) {
                    temp[j] = Arrays.copyOf(map[j], N);
                }
                
                visibleCCTV(curR, curC, d);
                visibleCCTV(curR, curC, d+2);
                dfs(depth + 1);

                for (int j = 0; j < N; j++) {
                    map[j] = Arrays.copyOf(temp[j], N);
                }
            }
        }
        // 3. 직각
        else if(map[curR][curC] == 3) {
            for (int d = 0; d < 4; d++) {
                int[][] temp = new int[N][];
                for (int j = 0; j < N; j++) {
                    temp[j] = Arrays.copyOf(map[j], N);
                }

                visibleCCTV(curR, curC, d);
                visibleCCTV(curR, curC, (d + 1) % 4);
                dfs(depth + 1);
                map = temp;

                for (int j = 0; j < N; j++) {
                    map[j] = Arrays.copyOf(temp[j], N);
                }
            }
        }
        // 4. 3방향
        else if(map[curR][curC] == 4) {
            for (int d = 0; d < 4; d++) {
                int[][] temp = new int[N][];
                for (int j = 0; j < N; j++) {
                    temp[j] = Arrays.copyOf(map[j], N);
                }

                visibleCCTV(curR, curC, d);
                visibleCCTV(curR, curC, (d + 1) % 4);
                visibleCCTV(curR, curC, (d + 2) % 4);
                dfs(depth + 1);
                map = temp;

                for (int j = 0; j < N; j++) {
                    map[j] = Arrays.copyOf(temp[j], N);
                }
            }
        }
        // 5. 4방향
        else if(map[curR][curC] == 5) {
            int[][] temp = new int[N][];
            for (int j = 0; j < N; j++) {
                temp[j] = Arrays.copyOf(map[j], N);
            }

            visibleCCTV(curR, curC, 0);
            visibleCCTV(curR, curC, 1);
            visibleCCTV(curR, curC, 2);
            visibleCCTV(curR, curC, 3);
            dfs(depth + 1);

            for (int j = 0; j < N; j++) {
                map[j] = Arrays.copyOf(temp[j], N);
            }
        }
    }
    
    // CCTV 시야 표시
    static void visibleCCTV(int r, int c, int d) {
        while (true) {
            r += dr[d];
            c += dc[d];
            if(!isValid(r, c)) return;
            if(map[r][c] == 6) return;
            if(map[r][c] == 0) map[r][c] = -1;
        }
    }

    static boolean isValid(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < M;
    }
}

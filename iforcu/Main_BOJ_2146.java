package iforcu;

import java.io.*;
import java.util.*;

/**
 * 문제: [다리 만들기]
 * 난이도: [Gold 3]
 * 링크: [https://www.acmicpc.net/problem/2146]
 * 설명:
 *  N×N 지도에서 1은 땅, 0은 바다일 때,
 *  섬과 섬을 연결하는 가장 짧은 다리의 길이를 구하는 문제.
 * 풀이:
 *  - Step 1: BFS로 섬을 구분하여 고유 번호를 부여. (FF : 플로드 필)
 *  - Step 2: 각 섬의 모든 땅 좌표에서 BFS 확장 → 바다를 지나 다른 섬에 도달할 때의 거리 계산.
 *  - Step 3: 그중 최단 다리 길이를 정답으로 선택.
 *  - 핵심 포인트: BFS로 섬 labeling + BFS 다리 탐색을 결합.
 */
public class Main_BOJ_2146 {
    static int N, ans;
    static int[][] map;
    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};
    static boolean[][] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        visited = new boolean[N][N];
        
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // Step 1: BFS로 섬 라벨링
        int num = 2;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 1) {
                    bfs(i, j, num);
                    num++;
                } 
            }
        }
        
        // Step 2: 각 섬에서 다른 섬으로 가는 최단 다리 탐색
        ans = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] != 0) {
                    ans = Math.min(ans, line(i, j));
                } 
            }
        }
        System.out.println(ans);
    }
    
    // 섬 라벨링 BFS(FF)
    static void bfs(int r, int c, int num) {
        if (visited[r][c]) return;
        map[r][c] = num;
        Queue<int[]> queue = new LinkedList<>();
        visited[r][c] = true;
        queue.offer(new int[] {r, c});

        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int curR = temp[0];
            int curC = temp[1];

            for (int d = 0; d < 4; d++) {
                int nr = curR + dr[d];
                int nc = curC + dc[d];
                if (!isValid(nr, nc)) continue;
                if (!visited[nr][nc] && map[nr][nc] == 1) {
                    visited[nr][nc] = true;
                    map[nr][nc] = num;
                    queue.offer(new int[] {nr, nc});
                }
            }
        }
    }

    // 다리 길이 탐색 BFS
    static int line(int r, int c) {
        for (int row = 0; row < N; row++) {
            Arrays.fill(visited[row], false);
        }
        int curI = map[r][c];
        int count = Integer.MAX_VALUE;
        Queue<int[]> queue = new LinkedList<>();
        visited[r][c] = true;
        queue.offer(new int[] {r, c, 0});

        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int curR = temp[0];
            int curC = temp[1];
            int curLine = temp[2];
            if (curLine > count) continue;
            for (int d = 0; d < 4; d++) {
                int nr = curR + dr[d];
                int nc = curC + dc[d];
                if (!isValid(nr, nc) || visited[nr][nc] || map[nr][nc] == curI) continue;
                if (map[nr][nc] == 0) {
                    visited[nr][nc] = true;
                    queue.offer(new int[] {nr, nc, curLine + 1});
                } else {
                    count = Math.min(count, curLine);
                }
            }
        }
        return count;
    }

    static boolean isValid(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}

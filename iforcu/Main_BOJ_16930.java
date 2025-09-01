package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: 달리기 (BOJ 16930)
 *  링크 : https://www.acmicpc.net/problem/16930
 *  난이도 : 플래티넘 3
 *  설명:
 *   - N x M 격자에서 시작 위치에서 목표 위치까지 이동해야 한다.
 *   - 이동 시 한 방향으로 최대 K칸까지 이동 가능하며, 벽(#)을 만나면 이동 불가.
 *   - 최소 시간(이동 횟수)으로 목표에 도달하는 값을 출력해야 한다.
 *  풀이:
 *   - BFS 탐색을 이용.
 *   - 각 칸에 도착하는 최소 시간을 기록하는 `mapTime` 배열 사용.
 *   - 현재 위치에서 4방향으로 최대 K칸까지 확장하며 이동 가능 여부 확인.
 *   - 이미 더 짧은 시간에 방문한 칸이면 탐색 중단 (가지치기).
 *   - BFS가 종료되었을 때 목표 지점에 도달하지 못하면 -1 출력.
 */

public class Main_BOJ_16930 {
    static int N, M, K;
	static char[][] map;
	static int[][] mapTime;
	static int row1, col1, row2, col2;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new char[N][M];
		mapTime = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			Arrays.fill(mapTime[i], -1);
		}
		
		for (int i = 0; i < N; i++) {
			String s = br.readLine();
			char[] c = s.toCharArray();
			for (int j = 0; j < M; j++) {
				map[i][j] = c[j];
			}
		}
		
		st = new StringTokenizer(br.readLine());
		row1 = Integer.parseInt(st.nextToken()) - 1;
		col1 = Integer.parseInt(st.nextToken()) - 1;
		row2 = Integer.parseInt(st.nextToken()) - 1;
		col2 = Integer.parseInt(st.nextToken()) - 1;
		System.out.println(bfs(row1, col1, row2, col2));
	}

	static int bfs(int startR, int startC, int endR, int endC) {
		Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] {startR, startC});
        mapTime[startR][startC] = 0;

        while (!q.isEmpty()) {
            int[] temp = q.poll();
            int r = temp[0];
            int c = temp[1];
            int time = mapTime[r][c];

            if (r == endR && c == endC) return time;

            for (int d = 0; d < 4; d++) {
                for (int k = 1; k <= K; k++) {
                    int nr = r + dr[d] * k;
                    int nc = c + dc[d] * k;

                    // 범위 밖이거나 벽이면 그 방향 탐색 종료
                    if (!isValid(nr, nc) || map[nr][nc] == '#') break;
                    // 이미 더 짧은 시간에 방문한 경우 탐색 중단
                    if (mapTime[nr][nc] != -1 && mapTime[nr][nc] < time + 1) break; 
                    
                    // 방문한 적이 없는 시간만 갱신 및 q에 삽입
                    if (mapTime[nr][nc] == -1) {
                        mapTime[nr][nc] = time + 1;
                        q.offer(new int[] {nr, nc});
					}
				}
			}
		}
		return -1;
	}
	
	static boolean isValid(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < M;
	}
}

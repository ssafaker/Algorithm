package iforcu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main_BOJ_2636 {
	
	static int N, M;
	static int[][] map;
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	static int[] ans;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		map = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			String[] line = br.readLine().split(" ");
			for (int j = 0; j < M; j++) {
			    map[i][j] = Integer.parseInt(line[j]);
			}
		}
		
		int cnt = 2;
		while (checkCheese()) {
			bfs(cnt);
			cnt++;
		}
		
		int sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(map[i][j] == cnt-1) sum++;
			}
		}
		System.out.println(cnt-2);
		System.out.println(sum);
	}

	static boolean checkCheese() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(map[i][j] == 1) return true;
			}
		}
		return false;
	}

	static void bfs(int g) {
		Queue<int[]> que = new LinkedList<>();
		boolean[][] visited = new boolean[N][M];
		que.offer(new int[] {0,0});
		visited[0][0] = true;
		
		while(!que.isEmpty()) {
			int[] cur = que.poll();
			int r = cur[0];
			int c = cur[1];
			
			for (int i = 0; i < 4; i++) {
				int nr = r+dr[i];
				int nc = c+dc[i];
				if (!check(nr, nc) || visited[nr][nc]) continue;
				visited[nr][nc] = true;
				if (map[nr][nc] == 1) {
                    map[nr][nc] = g;
                } else {
                    que.offer(new int[]{nr, nc});
                }
			}
		}
	}

	static boolean check(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < M;
	}
}

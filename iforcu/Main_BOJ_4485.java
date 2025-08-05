package iforcu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main_BOJ_4485 {
	static int N;
	static int T;
	static int count;
	static int[][] map;
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = 0;
		while (true) {
			N = Integer.parseInt(br.readLine());
			if(N == 0 ) break;
			T++;
			map = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				String[] input = br.readLine().split(" ");
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(input[j]);
				}
			}
			bfs();
			System.out.printf("Problem %d: %d\n",T,count);
		}
	}

	static void bfs() {
		int[][] dist = new int[N][N];
		boolean[][] visited = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		}
		
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));
		dist[0][0] = map[0][0];
		pq.add(new int[] {0,0,map[0][0]});
		
		while(!pq.isEmpty()) {
			int[] node = pq.poll();
			int r = node[0];
			int c = node[1];
			
		 	if (visited[r][c]) continue;
	        visited[r][c] = true;
			
			for (int i = 0; i < 4; i++) {
				int nr = r + dr[i];
				int nc = c + dc[i];
				
				if(!valid(nr, nc)) continue;
				int newCost = dist[r][c] + map[nr][nc];
				if(newCost < dist[nr][nc]) {
					dist[nr][nc] = newCost;
					pq.add(new int[] {nr, nc, newCost});
				}
			}
		}
		
		count = dist[N-1][N-1];
	}
	
	static boolean valid(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}
}
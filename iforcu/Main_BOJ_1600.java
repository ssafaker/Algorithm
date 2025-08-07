package iforcu;

import java.io.*;
import java.util.*;

public class Main_BOJ_1600 {
    static int K, W, H;
	static int[][] map;
	static boolean[][][] visited;
	static int dr[] = {1, 0, -1, 0};
	static int dc[] = {0, 1, 0, -1};
	static int horseR[] = {2, 2, 1, -1, -2, -2, 1, -1};
	static int horseC[] = {1, -1, 2, 2, 1, -1, -2, -2};
	
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		K = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
		
		map = new int[H][W];
		visited = new boolean[H][W][K+1];
		
		for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < W; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
		
		 int result = bfs();
		
		 System.out.println(result);
	}

	static int bfs() {
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] {0,0,0,K});
		visited[0][0][K] = true;
		
		while (!queue.isEmpty()) {
			int[] current = queue.poll();
            int r = current[0];
            int c = current[1];
            int moves = current[2];
            int kLeft = current[3];
            
            if (r == H - 1 && c == W - 1) {
                return moves;
            }
            
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (isValid(nr, nc) && map[nr][nc] == 0 && !visited[nr][nc][kLeft]) {
                    visited[nr][nc][kLeft] = true;
                    queue.offer(new int[]{nr, nc, moves + 1, kLeft});
                }
            }
            
            if (kLeft > 0) {
                for (int i = 0; i < 8; i++) {
                    int nr = r + horseR[i];
                    int nc = c + horseC[i];
                    int newK = kLeft - 1;

                    if (isValid(nr, nc) && map[nr][nc] == 0 && !visited[nr][nc][newK]) {
                        visited[nr][nc][newK] = true;
                        queue.offer(new int[]{nr, nc, moves + 1, newK});
                    }
                }
            }
			
		}
		return -1;
	}
	
	static boolean isValid(int r, int c) {
		return r >= 0 && r < H && c >= 0 && c < W;
	}
}

package iforcu;

import java.util.*;
import java.io.*;

public class Main_BOJ_1194 {
	static int N, M;
	static char[][] map;
	static int startR, startC;
	static boolean[][][] visited;
	static int[] dr = {1, 0, -1, 0};
	static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        map = new char[N][M];
        visited = new boolean[N][M][1<<6];
        
        for (int i = 0; i < N; i++) {
			String s = br.readLine();
			char[] c = s.toCharArray();
			for (int j = 0; j < M; j++) {
				map[i][j] = c[j];
				if (map[i][j] == '0') {
					startR = i;
					startC = j;
					map[i][j] = '.';	
				}
			}
		}
        
        System.out.println(bfs());
    }

	static int bfs() {
		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[] {startR, startC, 0, 0});
		visited[startR][startC][0] = true;
		while(!queue.isEmpty()) {
			int[] temp = queue.poll();
			int r = temp[0];
			int c = temp[1];
			int cost = temp[2];
			int key = temp[3];
			
			for (int i = 0; i < 4; i++) {
	            int nr = r + dr[i];
	            int nc = c + dc[i];

	            if(!isValid(nr, nc) || map[nr][nc] == '#') continue;

	            char cell = map[nr][nc];

	            if (Character.isLowerCase(cell)) {
	                int new_key_bitmask = key | (1 << (cell - 'a'));
	                if (!visited[nr][nc][new_key_bitmask]) {
	                    visited[nr][nc][new_key_bitmask] = true;
	                    queue.offer(new int[] {nr, nc, cost + 1, new_key_bitmask});
	                }
	            } else if (Character.isUpperCase(cell)) { 
	                if ((key & (1 << (cell - 'A'))) != 0) {
	                    if (!visited[nr][nc][key]) {
	                        visited[nr][nc][key] = true;
	                        queue.offer(new int[] {nr, nc, cost + 1, key});
	                    }
	                }
	            } else if (cell == '1') { 
	                return cost + 1;
	            } else { 
	                if (!visited[nr][nc][key]) {
	                    visited[nr][nc][key] = true;
	                    queue.offer(new int[] {nr, nc, cost + 1, key});
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
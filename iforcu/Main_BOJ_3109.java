package iforcu;

import java.io.*;
import java.util.*;

public class Main_BOJ_3109 {
    static int R, C;
	static char[][] map;
	static boolean[][] visited;
	static int[] dr = {-1, 0, 1};
	static int[] dc = {1, 1, 1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		map = new char[R][C];
		visited = new boolean[R][C];
		int ans = 0;
		
		for (int i = 0; i < R; i++) {
			String line = br.readLine();
			for (int j = 0; j < C; j++) {
				map[i][j] = line.charAt(j);
			}
		}
		
		for (int i = 0; i < R; i++) {
			if (dfs(i, 0)) {
				ans++;
			}
		}
		
		System.out.println(ans);
		
	}
	
	static boolean dfs(int r, int c) {
		visited[r][c] = true;
		
		if(c == C-1) return true;
		
		for(int i = 0; i < 3; i++) {
			int nr = r + dr[i];
			int nc = c + dc[i];
			if(isValid(nr, nc)) {
				if(!visited[nr][nc] && map[nr][nc] =='.') {
					if(dfs(nr, nc)) return true;
				}
			}
		}
		return false;
	}

	static boolean isValid(int r, int c) {
		return r >= 0 && r < R && c >= 0 && c < C;
	}
}

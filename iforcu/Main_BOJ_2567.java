package iforcu;

import java.io.*;

public class Main_BOJ_2567 {
    static int[][] map;
	static int N;
	static int r,c;
	static int nr,nc;
	static int count;
	static int[] dr = {1, 0, -1, 0};
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[101][101];
		nr = 0; 
		nc = 0;
		count = 0;
		
		for (int k = 0; k < N; k++) {
			String[] s = br.readLine().split(" ");
			r = Integer.parseInt(s[0]);
			c = Integer.parseInt(s[1]);
			for (int i = r; i < r+10; i++) {
				for (int j = c; j < c+10; j++) {
					map[i][j] = 1;
				}
			}
		}
		
		for (int i = 0; i < 101; i++) {
			for (int j = 0; j < 101; j++) {
				if (map[i][j] == 1) {
					for (int k = 0; k < 4; k++) {
						nr = i + dr[k];
						nc = j + dc[k];
						
						if(0 <= nr && nr < 101 && 0 <= nc && nc < 101) {
							if (map[nr][nc] == 0) {
								count++;
							}
								
						} else {
							count++;
						}
					}
				}
			}
		}
		
		System.out.println(count);
	}
}

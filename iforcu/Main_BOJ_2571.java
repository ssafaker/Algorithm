package iforcu;

import java.util.*;
import java.io.*;

public class Main_BOJ_2571 {
    static int N;
	static int[][] paper;
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	static int nr, nc;
	
	public static void main(String[] args ) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		paper = new int[101][101];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			for (int nr = r; nr < r + 10; nr++) {
				for (int nc = c; nc < c + 10; nc++) {
					paper[nr][nc] = 1;
				}
			}
		}
		
		int[][] heights = new int[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (paper[i][j] == 1) {
                    heights[i][j] = (i == 0) ? 1 : heights[i - 1][j] + 1;
                }
            }
        }
        
        int maxArea = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) { 
                int minHeight = heights[i][j];
                if (minHeight == 0) continue;

                for (int k = j; k >= 0; k--) {
                    if (heights[i][k] == 0) break; 

                    minHeight = Math.min(minHeight, heights[i][k]);
                    
                    int width = j - k + 1;
                    maxArea = Math.max(maxArea, width * minHeight);
                }
            }
        }

        System.out.println(maxArea);
	}
}

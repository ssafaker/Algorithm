package ssafy.study.P0801;

import java.io.*;
import java.util.*;

public class Main_BOJ_16927 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int R = Integer.parseInt(st.nextToken());
		int[][] A = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int[][] B = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				int mi = Math.min(Math.min(Math.min(i, j), N - i - 1), M - j - 1);
				int mo = 2 * (N - 2 * mi) + 2 * (M - 2 * mi) - 4;
				int len = R % mo;
				int x = i, y = j;
				int sx = mi, sy = mi, ex = N - mi - 1, ey = M - mi - 1;
				while (len > 0) {
					if (y == sy && x != ex) {
						int temp = x;
						x += Math.min(len, ex - x);
						len -= Math.min(len, ex - temp);
					} else if (x == ex && y != ey) {
						int temp = y;
						y += Math.min(len, ey - y);
						len -= Math.min(len, ey - temp);
					} else if (y == ey && x != sx) {
						int temp = x;
						x -= Math.min(len, x - sx);
						len -= Math.min(len, temp - sx);
					} else if (x == sx && y != sy) {
						int temp = y;
						y -= Math.min(len, y - sy);
						len -= Math.min(len, temp - sy);
					}
				}
				B[x][y] = A[i][j];
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				bw.write(B[i][j] + " ");
			}
			bw.write("\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}
}
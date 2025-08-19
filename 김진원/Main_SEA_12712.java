package ssafy.study.P0816;
import java.io.*;
import java.util.*;

public class Main_SEA_12712 {
	static int[] dr = { -1, 0, 1, 0 };
	static int[] dc = { 0, 1, 0, -1 };
	static int[] diagr = { -1, 1, 1, -1 };
	static int[] diagc = { 1, 1, -1, -1 };
	static int N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int[][] A = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					A[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			int max = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int sum1 = A[i][j];
					int sum2 = A[i][j];
					for (int k = 1; k < M; k++) {
						for (int d = 0; d < 4; d++) {
							int nr = i + dr[d] * k;
							int nc = j + dc[d] * k;
							if (!check(nr, nc))
								continue;
							sum1 += A[nr][nc];
						}
						for (int d = 0; d < 4; d++) {
							int nr = i + diagr[d] * k;
							int nc = j + diagc[d] * k;
							if (!check(nr, nc))
								continue;
							sum2 += A[nr][nc];
						}
					}
					max = Math.max(max, sum1);
					max = Math.max(max, sum2);
				}
			}
			bw.write("#" + t + " " + max + "\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}

	public static boolean check(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}
}
import java.io.*;
import java.util.*;

public class Main_BOJ_17070 {
	static int N, totalCnt;
	static int[][] map;
	static int[] dr = { 0, 1, 1 };
	static int[] dc = { 1, 1, 0 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 파이프 이동 -> 1. 아래, 우측 아래, 우측 3가지의 조합
		totalCnt = 0;
		dfs(0, 1, 0);
		System.out.println(totalCnt);
	}

	private static void dfs(int r, int c, int d) {
		if (r == N - 1 && c == N - 1) {
			totalCnt++;
			return;
		}

		if (d == 0) {
			for (int nd = 0; nd <= 1; nd++) {
				int nr = r + dr[nd];
				int nc = c + dc[nd];
				if (!check(nr, nc))
					continue;
				if (isWall(nr, nc, nd))
					continue;
				dfs(nr, nc, nd);
			}
		} else if (d == 1) {
			for (int nd = 0; nd <= 2; nd++) {
				int nr = r + dr[nd];
				int nc = c + dc[nd];
				if (!check(nr, nc))
					continue;
				if (isWall(nr, nc, nd))
					continue;
				dfs(nr, nc, nd);
			}
		} else {
			for (int nd = 1; nd <= 2; nd++) {
				int nr = r + dr[nd];
				int nc = c + dc[nd];
				if (!check(nr, nc))
					continue;
				if (isWall(nr, nc, nd))
					continue;
				dfs(nr, nc, nd);
			}
		}
	}

	private static boolean isWall(int r, int c, int d) {
		if (d != 1) {
			if (map[r][c] == 1) {
				return true;
			} else {
				return false;
			}
		} else {
			if (map[r - 1][c] == 1 || map[r][c] == 1 || map[r][c - 1] == 1) {
				return true;
			} else {
				return false;
			}
		}

	}

	private static boolean check(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}
}

import java.io.*;
import java.util.*;

public class Main_BOJ_2630 {
	static int N, white, blue;
	static int[][] paper;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		paper = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				paper[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		dfs(0, 0, N);

		System.out.println(blue);
		System.out.println(white);
	}

	private static void dfs(int r, int c, int size) {
		if (isSame(r, c, size)) {
			if (paper[r][c] == 1) {
				white++;
			} else {
				blue++;
			}
			return;
		}

		int half = size / 2;
		dfs(r, c, half);
		dfs(r, c + half, half);
		dfs(r + half, c, half);
		dfs(r + half, c + half, half);
	}

	private static boolean isSame(int r, int c, int size) {
		int color = paper[r][c];
		for (int i = r; i < r + size; i++) {
			for (int j = c; j < c + size; j++) {
				if (paper[i][j] != color)
					return false;
			}
		}
		return true;
	}
}

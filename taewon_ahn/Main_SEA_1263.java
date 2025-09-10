import java.io.*;
import java.util.*;

public class Main_SEA_1263 {
	static int[][] Dist;
	static int INF = Integer.MAX_VALUE / 3;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());

			Dist = new int[N][N];
			for (int i = 0; i < N; i++) {
				Arrays.fill(Dist[i], INF);
				Dist[i][i] = 0;
			}

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int n = Integer.parseInt(st.nextToken());
					if (n == 1) {
						Dist[i][j] = n;
					}

				}
			}

			for (int k = 0; k < N; k++) {
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						Dist[i][j] = Math.min(Dist[i][j], Dist[i][k] + Dist[k][j]);
					}
				}
			}

			int answer = Integer.MAX_VALUE;
			for (int[] row : Dist) {
				int rowSum = 0;
				for (int a : row) {
					rowSum += a;
				}
				answer = Math.min(answer, rowSum);
			}

			System.out.println("#" + tc + " " + answer);
		}
	}
}

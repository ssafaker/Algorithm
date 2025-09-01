import java.io.*;
import java.util.*;

public class Main_BOJ_14890 {
	static int N, L, answer;
	static int[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 모든 행과 열 투포인터 비교
		answer = 0;
		for (int i = 0; i < N; i++) {
			checkRoad(map[i]);
		}

		for (int i = 0; i < N; i++) {
			int[] road = new int[N];
			for (int j = 0; j < N; j++) {
				road[j] = map[j][i];
			}
			checkRoad(road);
		}

		System.out.println(answer);
	}

	private static void checkRoad(int[] road) {
		int s = 0;
		int e = 0;
		int sLen = 0;
		while (s < N && e < N) {
			if (road[s] == road[e]) {
				sLen++;
				e++;
			} else if (road[e] - road[s] == -1) {
				// e 시작 지점부터 L만큼이 동일한지 확인
				for (int i = e; i < e + L; i++) {
					if (i >= N)
						return;
					if (road[i] - road[s] != -1)
						return;
				}
				// 모두 확인하고 다음 끝지점으로 이동
				sLen = -1;
				e = e + L - 1;
				s = e;
			} else if (road[e] - road[s] == 1) {
				if (sLen < L)
					return;
				// 모두 확인 끝
				sLen = 0;
				s = e;
			} else {
				return;
			}
		}
		answer++;
	}
}

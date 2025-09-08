package ssafy.study.P0913;

/**
 * 알고리즘: BFS
 * 시간복잡도: O(N * M * K)
 */
import java.io.*;
import java.util.*;

public class Main_BOJ_16930 {
	static int[][] A; // 격자: -1=벽, 0=미방문, 1+=해당 칸까지의 이동 횟수
	static int[] dr = { -1, 0, 1, 0 }; // 상, 우, 하, 좌 (행 변화)
	static int[] dc = { 0, 1, 0, -1 }; // 상, 우, 하, 좌 (열 변화)
	static int N, M, K; // N: 행, M: 열, K: 한 번에 최대로 갈 수 있는 칸 수

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		A = new int[N][M];

		// 격자 입력: '.'은 빈칸(0), '#'은 벽(-1)
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				char ch = str.charAt(j);
				if (ch == '.')
					A[i][j] = 0; // 미방문 빈칸
				else
					A[i][j] = A[i][j] = -1; // 벽(중복 대입이지만 결과는 -1)
			}
		}

		// 시작/도착 좌표 (1-indexed 입력 → 0-indexed로 변환)
		st = new StringTokenizer(br.readLine());
		int x1 = Integer.parseInt(st.nextToken()) - 1;
		int y1 = Integer.parseInt(st.nextToken()) - 1;
		int x2 = Integer.parseInt(st.nextToken()) - 1;
		int y2 = Integer.parseInt(st.nextToken()) - 1;

		// BFS로 최단 이동 횟수 계산
		bfs(x1, y1);

		// 도착지에 기록된 값이 0이면 미방문 → -1, 아니면 기록된 이동 횟수 출력
		int result = A[x2][y2] == 0 ? -1 : A[x2][y2];
		// 시작==도착인 경우 이동 0
		if (x1 == x2 && y1 == y2)
			result = 0;

		bw.write(result + "\n");
		bw.flush();
		bw.close();
		br.close();
	}

	static void bfs(int x, int y) {
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		A[x][y] = 0; // 시작점의 이동 횟수는 0
		queue.offer(new int[] { x, y, 0 }); // {r, c, 현재까지 이동 횟수}

		while (!queue.isEmpty()) {
			int[] now = queue.poll();
			int r = now[0];
			int c = now[1];
			int dist = now[2]; // 현재 칸까지의 이동 횟수

			// 4방향으로 "미끄러지듯" 최대 K칸 전진
			for (int d = 0; d < 4; d++) {
				for (int step = 1; step <= K; step++) {
					int nr = r + dr[d] * step;
					int nc = c + dc[d] * step;

					// 격자 밖이면 이 방향 탐색 종료
					if (nr < 0 || nr >= N || nc < 0 || nc >= M)
						break;

					// 벽이면 이 방향 탐색 종료
					if (A[nr][nc] == -1)
						break;

					// 이미 더 짧은 이동 횟수(< dist+1)로 방문한 칸이면,
					// 이 방향으로 더 가도 이득 없음 → 방향 종료
					if (A[nr][nc] < dist + 1 && A[nr][nc] != 0)
						break;

					// 같은 이동 횟수(== dist+1)로 이미 방문한 칸은,
					// 그 칸은 스킵하고 "그 방향"은 계속 진행(다음 step 시도)
					if (A[nr][nc] == dist + 1)
						continue;

					// 미방문(0)인 칸 최초 방문 → 이동 횟수(dist+1) 기록 후 큐에 삽입
					A[nr][nc] = dist + 1;
					queue.offer(new int[] { nr, nc, A[nr][nc] });
				}
			}
		}
	}
}

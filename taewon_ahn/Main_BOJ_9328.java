import java.io.*;
import java.util.*;

public class Main_BOJ_9328 {
	static int H, W, answer;
	static char[][] map;
	static boolean[] keys;
	static List<int[]>[] doors;
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());

			// 가장자리를 '.' 로 채워서 시작
			map = new char[H + 2][W + 2];
			for (char[] row : map) {
				Arrays.fill(row, '.');
			}

			for (int i = 1; i <= H; i++) {
				String line = br.readLine();
				for (int j = 1; j <= W; j++) {
					map[i][j] = line.charAt(j - 1);
				}
			}

			// 열쇠 입력 처리
			keys = new boolean[26];
			char[] charArr = br.readLine().toCharArray();
			for (char c : charArr) {
				if (c != '0') {
					keys[c - 'a'] = true;
				}
			}

			// 문방문 초기화
			doors = new List[26];
			for (int i = 0; i < 26; i++) {
				doors[i] = new ArrayList<>();
			}

			answer = 0;
			bfs(0, 0);
			System.out.println(answer);
		}
	}

	private static void bfs(int r, int c) {
		boolean[][] visited = new boolean[H + 2][W + 2];
		Queue<int[]> q = new ArrayDeque<>();

		q.offer(new int[] { r, c });
		visited[r][c] = true;

		while (!q.isEmpty()) {
			int[] cur = q.poll();

			for (int d = 0; d < 4; d++) {
				int nr = cur[0] + dr[d];
				int nc = cur[1] + dc[d];
				if (!check(nr, nc))
					continue;
				if (visited[nr][nc] || map[nr][nc] == '*')
					continue;

				char cell = map[nr][nc];
				visited[nr][nc] = true;
				if (cell == '.') {
					// 빈공간
					q.offer(new int[] { nr, nc });
				} else if (cell >= 'a' && cell <= 'z') {
					// 키를 만났을 경우
					int keyIdx = cell - 'a';
					keys[keyIdx] = true;
					q.offer(new int[] { nr, nc });

					// 이전에 만났던 문중에서 열 수 있는 문으로 이동
					for (int[] door : doors[keyIdx]) {
						visited[door[0]][door[1]] = false;
						q.offer(new int[] { door[0], door[1] });
					}
				} else if (cell >= 'A' && cell <= 'Z') {
					// 문을 만난경우
					int keyIdx = cell - 'A';
					if (keys[keyIdx]) {
						// 키가 이미 있다면 그대로 진행
						q.offer(new int[] { nr, nc });
					} else {
						// 키가 없다면 doors에 등록하고 나중에 열쇠 먹으면 방문
						doors[keyIdx].add(new int [] {nr, nc});
					}
				} else if (cell == '$') {
					answer++;
					q.offer(new int[] { nr, nc });
				}
			}
		}
	}

	private static boolean check(int r, int c) {
		return r >= 0 && r < H + 2 && c >= 0 && c < W + 2;
	}
}

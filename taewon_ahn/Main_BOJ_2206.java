import java.io.*;
import java.util.*;

public class Main_BOJ_2206 {
    static int N, M;
    static int[][] map;
    static int[] dr = { -1, 1, 0, 0 };
    static int[] dc = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j) - '0';
            }
        }

        System.out.println(bfs());
    }

    private static int bfs() {
        // visited[r][c][0]: 벽을 부수지 않고 방문
        // visited[r][c][1]: 벽을 부수고 방문
        boolean[][][] visited = new boolean[N][M][2];
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[] { 0, 0, 1, 0 }); // r, c, 거리, 벽부순여부(0:안부숨, 1:부숨)
        visited[0][0][0] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1], dist = cur[2], broken = cur[3];

            if (r == N - 1 && c == M - 1) {
                return dist;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M)
                    continue;

                if (map[nr][nc] == 0) { // 빈 공간
                    if (!visited[nr][nc][broken]) {
                        visited[nr][nc][broken] = true;
                        queue.offer(new int[] { nr, nc, dist + 1, broken });
                    }
                } else { // 벽
                    if (broken == 0 && !visited[nr][nc][1]) {
                        visited[nr][nc][1] = true;
                        queue.offer(new int[] { nr, nc, dist + 1, 1 });
                    }
                }
            }
        }

        return -1;
    }
}
import java.io.*;
import java.util.*;

public class Main_BOJ_2146 {
    static int N;
    static int[][] map;
    static int[] dr = { -1, 1, 0, 0 };
    static int[] dc = { 0, 0, 1, -1 };
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        visited = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 섬 구분
        int islandNum = 2;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 1) {
                    markIsland(i, j, islandNum++);
                }
            }
        }

        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] >= 2) {
                    answer = Math.min(answer, findBridge(i, j, map[i][j]));
                }
            }
        }

        System.out.println(answer);
    }

    private static void markIsland(int r, int c, int num) {
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[] { r, c });
        map[r][c] = num;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (!check(nr, nc))
                    continue;
                if (map[nr][nc] != 1)
                    continue;

                map[nr][nc] = num;
                q.offer(new int[] { nr, nc });
            }
        }
    }

    private static int findBridge(int sr, int sc, int islandNum) {
        // 초기화
        for (boolean[] row : visited) {
            Arrays.fill(row, false);
        }

        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[] { sr, sc, 0 });
        visited[sr][sc] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1], dist = cur[2];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (!check(nr, nc))
                    continue;
                if (visited[nr][nc])
                    continue;
                if (map[nr][nc] == islandNum)
                    continue;

                if (map[nr][nc] > 1) {
                    return dist;
                }

                visited[nr][nc] = true;
                q.offer(new int[] { nr, nc, dist + 1 });
            }
        }

        return Integer.MAX_VALUE;
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}
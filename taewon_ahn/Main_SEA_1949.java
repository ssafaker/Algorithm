import java.io.*;
import java.util.*;

public class Main_SEA_1949 {
    static int N, K, maxDist;
    static int[][] map;
    static boolean[][] visited;
    static int[] dr = { -1, 1, 0, 0 };
    static int[] dc = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            map = new int[N][N];
            visited = new boolean[N][N];
            int topHeight = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    topHeight = Math.max(topHeight, map[i][j]);
                }
            }

            maxDist = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] == topHeight) {
                        resetVisited();
                        visited[i][j] = true;
                        dfs(i, j, K, 1);
                    }
                }
            }

            System.out.println("#" + tc + " " + maxDist);
        }
    }

    private static void resetVisited() {
        for (boolean[] row : visited) {
            Arrays.fill(row, false);
        }
    }

    private static void dfs(int r, int c, int k, int dist) {
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (!check(nr, nc))
                continue;
            if (visited[nr][nc])
                continue;

            if (map[nr][nc] < map[r][c]) {
                // 높이가 낮은 경우 바로 진행
                visited[nr][nc] = true;
                dfs(nr, nc, k, dist + 1);
                visited[nr][nc] = false;
            } else if (k > 0 && map[nr][nc] - k < map[r][c]) {
                // 높이가 높거나 같고 공사가 가능한 경우
                visited[nr][nc] = true;
                int save = map[nr][nc];
                map[nr][nc] = map[r][c] - 1;
                dfs(nr, nc, 0, dist + 1);
                map[nr][nc] = save;
                visited[nr][nc] = false;
            } else {
                // 공사 불가 -> 최대값 계속 업데이트
                maxDist = Math.max(dist, maxDist);
            }
        }
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}
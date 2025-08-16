import java.io.*;
import java.util.StringTokenizer;

public class Main_SEA_2105 {
    static int N, maxCnt, startR, startC;
    static int[][] map;
    static boolean[] visited;
    static int[] dr = { 1, 1, -1, -1 };
    static int[] dc = { 1, -1, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());

            map = new int[N][N];
            visited = new boolean[101];
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            maxCnt = -1;
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    startR = r;
                    startC = c;
                    visited[map[r][c]] = true;
                    dfs(r, c, 0, 1);
                    visited[map[r][c]] = false;
                }
            }

            System.out.println("#" + tc + " " + maxCnt);
        }
    }

    private static void dfs(int r, int c, int d, int cnt) {
        for (int nd = d; nd < 4; nd++) { // 직선 또는 우측 꺽기
            int nr = r + dr[nd];
            int nc = c + dc[nd];

            if (!check(nr, nc))
                continue;

            if (nr == startR && nc == startC && cnt >= 4) {
                maxCnt = Math.max(maxCnt, cnt);
                return;
            }

            if (!visited[map[nr][nc]]) {
                visited[map[nr][nc]] = true;
                dfs(nr, nc, nd, cnt + 1);
                visited[map[nr][nc]] = false;
            }
        }

    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}
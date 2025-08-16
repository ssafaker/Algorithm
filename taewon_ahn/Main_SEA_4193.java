import java.io.*;
import java.util.*;

public class Main_SEA_4193 {
    static int N, startR, startC, endR, endC;
    static int[][] map;
    static boolean[][] visited;
    static int[] dr = { -1, 1, 0, 0 };
    static int[] dc = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());

            map = new int[N][N];
            visited = new boolean[N][N];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            StringTokenizer st = new StringTokenizer(br.readLine());
            startR = Integer.parseInt(st.nextToken());
            startC = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            endR = Integer.parseInt(st.nextToken());
            endC = Integer.parseInt(st.nextToken());

            System.out.printf("#%d %d\n", tc, bfs(startR, startC));
        }
    }

    private static int bfs(int r, int c) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] { r, c, 0, 0 }); // r, c, block, cnt
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cr = cur[0];
            int cc = cur[1];
            int block = cur[2];
            int cnt = cur[3];

            if (cr == endR && cc == endC) {
                return cnt;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cr + dr[d];
                int nc = cc + dc[d];
                if (check(nr, nc) && !visited[nr][nc]) {
                    if (map[nr][nc] == 0) {
                        visited[nr][nc] = true;
                        queue.offer(new int[] { nr, nc, map[nr][nc], cnt + 1 });
                    } else { // 소용돌이
                        if ((cnt + 1) % 3 == 0) {
                            visited[nr][nc] = true;
                            queue.offer(new int[] { nr, nc, map[nr][nc], cnt + 1 });
                        } else {
                            queue.offer(new int[] { cr, cc, block, cnt + 1 });
                        }

                    }
                }
            }
        }

        return -1;
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N && map[r][c] != 1;
    }

}
import java.io.*;
import java.util.*;

public class Main_BOJ_1600 {
    static int K, W, H;
    static int[][] map;
    static int[] dr = { -1, 1, 0, 0 };
    static int[] dc = { 0, 0, 1, -1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        K = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        map = new int[H][W];
        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < W; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(bfs());
    }

    private static int bfs() {
        boolean[][][] visited = new boolean[H][W][K + 1]; // K사용 횟수 (0~K)
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] { 0, 0, 0, 0 }); // row, col, 이동횟수, K사용횟수

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curR = cur[0];
            int curC = cur[1];
            int curMoveCnt = cur[2];
            int curKCnt = cur[3];

            if (curR == H - 1 && curC == W - 1) {
                return curMoveCnt;
            }

            if (curKCnt < K) {
                // 말이동 경로 탐색
                for (int d = 0; d < 4; d++) {
                    int nr = curR + dr[d] * 2;
                    int nc = curC + dc[d] * 2;
                    if (d < 2) { // 상하
                        for (int i = -1; i < 2; i += 2) {
                            if (check(nr, nc + i) && map[nr][nc + i] == 0 && !visited[nr][nc + i][curKCnt + 1]) {
                                visited[nr][nc + i][curKCnt + 1] = true;
                                queue.offer(new int[] { nr, nc + i, curMoveCnt + 1, curKCnt + 1 });
                            }
                        }
                    } else { // 좌우
                        for (int i = -1; i < 2; i += 2) {
                            if (check(nr + i, nc) && map[nr + i][nc] == 0 && !visited[nr + i][nc][curKCnt + 1]) {
                                visited[nr + i][nc][curKCnt + 1] = true;
                                queue.offer(new int[] { nr + i, nc, curMoveCnt + 1, curKCnt + 1 });
                            }
                        }
                    }
                }
            }

            // 일반 이동
            for (int d = 0; d < 4; d++) {
                int nr = curR + dr[d];
                int nc = curC + dc[d];
                if (check(nr, nc) && map[nr][nc] == 0 && !visited[nr][nc][curKCnt]) {
                    visited[nr][nc][curKCnt] = true;
                    queue.offer(new int[] { nr, nc, curMoveCnt + 1, curKCnt });
                }
            }
        }

        return -1;
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < H && c >= 0 && c < W;
    }
}
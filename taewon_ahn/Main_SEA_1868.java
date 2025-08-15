
import java.io.*;
import java.util.*;

public class Main_SEA_1868 {
    static int N;
    static char[][] board;
    static boolean[][] visited;
    static int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1, };
    static int[] dc = { -1, 0, 1, 1, -1, -1, 0, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            board = new char[N][N];
            visited = new boolean[N][N];

            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < N; j++) {
                    board[i][j] = line.charAt(j);
                }
            }

            int minClickCnt = findMinClickCount();
            System.out.printf("#%d %d\n", tc, minClickCnt);
        }
    }

    private static int findMinClickCount() {
        int clickCnt = 0;

        // 1. 주변에 지뢰 없는 것 먼저 클릭
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == '.' && !isBombNearby(i, j)) {
                    clickCnt++;
                    clickZero(i, j);
                }
            }
        }

        // 2. 나머지 .위치 갯수 카운트
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == '.') {
                    clickCnt++;
                }
            }
        }

        return clickCnt;
    }

    private static void clickZero(int r, int c) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] { r, c });
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cr = cur[0];
            int cc = cur[1];
            board[cr][cc] = '0';

            for (int d = 0; d < 8; d++) {
                int nr = cr + dr[d];
                int nc = cc + dc[d];
                if (check(nr, nc) && !visited[nr][nc] && board[nr][nc] == '.') {
                    visited[nr][nc] = true;
                    if (!isBombNearby(nr, nc)) {
                        queue.offer(new int[] { nr, nc });
                    } else {
                        board[nr][nc] = '!';
                    }
                }

            }
        }
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    private static boolean isBombNearby(int r, int c) {
        for (int d = 0; d < 8; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if (check(nr, nc) && board[nr][nc] == '*') {
                return true;
            }

        }
        return false;
    }

}
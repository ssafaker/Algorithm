import java.io.*;
import java.util.*;

public class Main_SEA_1949 {
    static int N, K, maxCnt;
    static boolean canUseK;
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
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // 가장 높은 봉우리 찾기
            Stack<int[]> stack = new Stack<>();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int curVal = map[i][j];
                    if (stack.size() > 0) {
                        int topVal = stack.peek()[2];
                        if (curVal > topVal) {
                            // 기존 stack 모두 제거
                            while (!stack.isEmpty()) {
                                stack.pop();
                            }
                            stack.add(new int[] { i, j, curVal });
                        } else if (curVal == topVal) {
                            stack.add(new int[] { i, j, curVal });
                        }
                    } else {
                        stack.add(new int[] { i, j, curVal });
                    }
                }
            }

            maxCnt = 0;
            canUseK = true;
            while (!stack.isEmpty()) {
                int[] cur = stack.pop();
                // System.out.println("start: " + cur[0] + "," + cur[1]);
                dfs(cur[0], cur[1], 1);
                // System.out.println("---------------------------");
            }

            System.out.println("#" + tc + " " + maxCnt);
        }
    }

    private static void dfs(int r, int c, int cnt) {
        visited[r][c] = true;
        int curVal = map[r][c];
        int cantGoCnt = 0;

        // System.out.println("moveto: " + r + "," + c + " cnt: " + cnt + " curVal: " +
        // curVal);

        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];

            if (check(nr, nc) && !visited[nr][nc]) {
                int nextVal = map[nr][nc];

                if (nextVal < curVal) { // 다음 칸을 공사 안하는 경우
                    dfs(nr, nc, cnt + 1);
                } else if (canUseK && nextVal - K < curVal) { // 다음칸을 공사하는 경우
                    int save = map[nr][nc];
                    map[nr][nc] = curVal - 1;
                    canUseK = false;
                    dfs(nr, nc, cnt + 1);
                    canUseK = true;
                    map[nr][nc] = save;
                } else {
                    cantGoCnt++;
                }
            } else {
                cantGoCnt++;
            }
        }

        if (cantGoCnt >= 4 && cnt > maxCnt) {
            maxCnt = cnt;
        }

        visited[r][c] = false;
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}
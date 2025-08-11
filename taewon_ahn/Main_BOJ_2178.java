package taewon_ahn;

import java.io.*;
import java.util.*;

public class Main_BOJ_2178 {
    static int N, M;
    static int[][] A;
    static boolean[][] visited;
    static int[] dr = { -1, 0, 1, 0 };
    static int[] dc = { 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        A = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                A[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }

        bfs(0, 0);

        System.out.println(A[N - 1][M - 1]);
    }

    private static void bfs(int r, int c) {
        Queue<int[]> que = new ArrayDeque<>();
        que.offer(new int[] { r, c });
        visited[r][c] = true;

        while (!que.isEmpty()) {
            int[] cur = que.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (check(nr, nc)) {
                    visited[nr][nc] = true;
                    A[nr][nc] = A[cur[0]][cur[1]] + 1;
                    que.offer(new int[] { nr, nc });
                }
            }
        }
    }

    private static boolean check(int nr, int nc) {
        return nr >= 0 && nr < N && nc >= 0 && nc < M && A[nr][nc] == 1 && !visited[nr][nc];
    }

}

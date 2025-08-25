import java.io.*;
import java.util.*;

public class Main_BOJ_1194 {
    static int N, M;
    static char[][] map;
    static int[] dr = { -1, 1, 0, 0 };
    static int[] dc = { 0, 0, -1, 1 };

    static class State {
        int r, c, keys, dist;

        public State(int r, int c, int keys, int dist) {
            this.r = r;
            this.c = c;
            this.keys = keys;
            this.dist = dist;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];

        int startR = 0;
        int startC = 0;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == '0') {
                    startR = i;
                    startC = j;
                    map[i][j] = '.';
                }
            }
        }

        System.out.println(bfs(startR, startC));
    }

    private static int bfs(int sr, int sc) {
        boolean[][][] visited = new boolean[N][M][64]; // 63 -> 열쇠 6개 모두 보유한 상태 (비트마스크)
        Queue<State> q = new ArrayDeque<>();

        q.offer(new State(sr, sc, 0, 0));
        visited[sr][sc][0] = true;

        while (!q.isEmpty()) {
            State cur = q.poll();

            if (map[cur.r][cur.c] == '1') {
                return cur.dist;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M)
                    continue;
                if (map[nr][nc] == '#')
                    continue;
                if (visited[nr][nc][cur.keys])
                    continue;

                char cell = map[nr][nc];

                // 문인 경우
                if (cell >= 'A' && cell <= 'F') {
                    if ((cur.keys & 1 << (cell - 'A')) == 0)
                        continue;
                }

                // 열쇠인 경우
                int newKeys = cur.keys;

                if (cell >= 'a' && cell <= 'f') {
                    newKeys |= (1 << (cell - 'a'));
                }

                visited[nr][nc][newKeys] = true;
                q.offer(new State(nr, nc, newKeys, cur.dist + 1));
            }
        }

        return -1;
    }

}
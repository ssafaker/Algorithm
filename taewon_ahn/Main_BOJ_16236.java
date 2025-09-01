import java.io.*;
import java.util.*;

public class Main_BOJ_16236 {

    static int N, sharkSize = 2, eaten = 0, time = 0;
    static int[][] map;
    static int sharkR, sharkC;
    static int[] dr = {-1, 0, 0, 1};
    static int[] dc = {0, -1, 1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 9) {
                    sharkR = i;
                    sharkC = j;
                    map[i][j] = 0;
                }
            }
        }

        while (true) {
            Fish target = findFish();
            if (target == null) {
                break;
            }

            time += target.dist;
            sharkR = target.r;
            sharkC = target.c;
            map[sharkR][sharkC] = 0;

            if (++eaten == sharkSize) {
                sharkSize++;
                eaten = 0;
            }
        }

        System.out.println(time);
    }

    static Fish findFish() {
        PriorityQueue<Fish> pq = new PriorityQueue<>();
        boolean[][] visited = new boolean[N][N];
        Queue<int[]> q = new LinkedList<>();

        q.offer(new int[]{sharkR, sharkC, 0});
        visited[sharkR][sharkC] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1], dist = cur[2];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (check(nr, nc) && !visited[nr][nc] && map[nr][nc] <= sharkSize) {
                    visited[nr][nc] = true;
                    if (map[nr][nc] > 0 && map[nr][nc] < sharkSize) {
                        pq.offer(new Fish(nr, nc, dist + 1));
                    }
                    q.offer(new int[]{nr, nc, dist + 1});
                }
            }
        }

        return pq.isEmpty() ? null : pq.poll();
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    static class Fish implements Comparable<Fish> {

        int r, c, dist;

        public Fish(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }

        @Override
        public int compareTo(Fish f) {
            if (dist != f.dist) {
                return dist - f.dist;
            }
            if (r != f.r) {
                return r - f.r;
            }
            return c - f.c;
        }
    }
}

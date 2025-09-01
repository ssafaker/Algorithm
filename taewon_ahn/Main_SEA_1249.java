import java.io.*;
import java.util.*;

public class Main_SEA_1249 {
    static int N;
    static int[][] map;
    static int[][] Dist;
    static int[] dr = { -1, 1, 0, 0 };
    static int[] dc = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());

            map = new int[N][N];
            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < N; j++) {
                    map[i][j] = Character.getNumericValue(line.charAt(j));
                }
            }

            // 다익스트라
            Dist = new int[N][N];
            for (int i = 0; i < N; i++) {
                Arrays.fill(Dist[i], Integer.MAX_VALUE);
            }
            Dist[0][0] = map[0][0];

            PriorityQueue<Node> pq = new PriorityQueue<>((e1, e2) -> e1.w - e2.w);
            pq.offer(new Node(0, 0, Dist[0][0]));
            while (!pq.isEmpty()) {
                Node cur = pq.poll();

                if (cur.w > Dist[cur.r][cur.c])
                    continue;

                for (int d = 0; d < 4; d++) {
                    int nr = cur.r + dr[d];
                    int nc = cur.c + dc[d];
                    if (!check(nr, nc))
                        continue;
                    int dist = Dist[cur.r][cur.c] + map[nr][nc];
                    if (dist < Dist[nr][nc]) {
                        Dist[nr][nc] = dist;
                        pq.offer(new Node(nr, nc, dist));
                    }
                }
            }

            System.out.println("#" + tc + " " + Dist[N - 1][N - 1]);
        }
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    static class Node {
        int r, c, w;

        public Node(int r, int c, int w) {
            this.r = r;
            this.c = c;
            this.w = w;
        }
    }
}
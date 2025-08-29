import java.io.*;
import java.util.*;

public class Main_BOJ_4485 {
    static int[][] map;
    static int[] dr = { -1, 1, 0, 0 };
    static int[] dc = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int tc = 1;

        while (true) {
            int n = Integer.parseInt(br.readLine());
            if (n == 0)
                break;

            int[][] map = new int[n][n];
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            sb.append("Problem ").append(tc++).append(": ").append(dijkstra(n, map)).append("\n");
        }

        System.out.println(sb);
    }

    private static int dijkstra(int n, int[][] map) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[][] dist = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][0] = map[0][0];
        pq.offer(new Node(0, 0, map[0][0]));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (cur.cost > dist[cur.x][cur.y])
                continue;

            for (int d = 0; d < 4; d++) {
                int nr = cur.x + dr[d];
                int nc = cur.y + dc[d];
                if (nr < 0 || nr > n - 1 || nc < 0 || nc > n - 1)
                    continue;
                int newCost = cur.cost + map[nr][nc];
                if (newCost < dist[nr][nc]) {
                    dist[nr][nc] = newCost;
                    pq.offer(new Node(nr, nc, newCost));
                }
            }
        }

        return dist[n - 1][n - 1];
    }

    static class Node implements Comparable<Node> {
        int x, y, cost;

        Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }
}
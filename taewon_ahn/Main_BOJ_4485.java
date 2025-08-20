
import java.io.*;
import java.util.*;

public class Main_BOJ_4485 {

    static class Node implements Comparable<Node> {

        int v;
        int w;

        public Node(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Node o) {
            return this.w - o.w;
        }

    }

    static int N;
    static int[] dr = {-1, 1, 0, 0,};
    static int[] dc = {0, 0, -1, 1};
    static List<Node>[] A;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());
        int pCnt = 1;

        while (N != 0) {
            int[] Dist = new int[N * N];
            Arrays.fill(Dist, Integer.MAX_VALUE);
            A = new ArrayList[N * N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    A[i * N + j] = new ArrayList<>();
                }
            }

            int[][] map = new int[N][N];
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            Dist[0] = map[0][0];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int d = 0; d < 4; d++) {
                        int nr = i + dr[d];
                        int nc = j + dc[d];
                        if (check(nr, nc)) {
                            A[i * N + j].add(new Node(nr * N + nc, map[nr][nc]));
                        }
                    }
                }
            }

            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.offer(new Node(0, map[0][0]));

            while (!pq.isEmpty()) {
                Node cur = pq.poll();

                if (cur.w > Dist[cur.v]) {
                    continue;
                }

                for (Node next : A[cur.v]) {
                    int nextDist = next.w + Dist[cur.v];
                    if (nextDist < Dist[next.v]) {
                        Dist[next.v] = nextDist;
                        pq.offer(new Node(next.v, nextDist));
                    }
                }
            }

            System.out.println("Problem " + pCnt + ": " + Dist[N * N - 1]);
            pCnt++;
            N = Integer.parseInt(br.readLine());
        }
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}

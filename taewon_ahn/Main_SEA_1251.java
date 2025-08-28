import java.io.*;
import java.util.*;

public class Main_SEA_1251 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());

            long[] xArr = new long[N];
            long[] yArr = new long[N];

            // X 좌표 읽기
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                xArr[i] = Long.parseLong(st.nextToken());
            }

            // Y 좌표 읽기
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                yArr[i] = Long.parseLong(st.nextToken());
            }

            double E = Double.parseDouble(br.readLine());

            // Prim's algorithm
            boolean[] visited = new boolean[N];
            PriorityQueue<Node> pq = new PriorityQueue<>((n1, n2) -> Long.compare(n1.w, n2.w));

            pq.offer(new Node(0, 0));
            long mstWeight = 0;
            int edgesAdded = 0;

            while (!pq.isEmpty() && edgesAdded < N) {
                Node cur = pq.poll();

                if (visited[cur.dest])
                    continue;

                visited[cur.dest] = true;
                mstWeight += cur.w; // 순수 거리제곱 누적
                edgesAdded++;

                // 현재 정점에서 방문하지 않은 모든 정점으로의 간선 추가
                for (int i = 0; i < N; i++) {
                    if (!visited[i]) {
                        long dx = xArr[cur.dest] - xArr[i];
                        long dy = yArr[cur.dest] - yArr[i];
                        long weight = dx * dx + dy * dy;
                        pq.offer(new Node(i, weight));
                    }
                }
            }

            // 최종 결과에만 E를 적용하고 반올림
            long result = Math.round(E * mstWeight);
            System.out.println("#" + tc + " " + result);
        }
    }

    static class Node {
        int dest;
        long w;

        public Node(int dest, long weight) {
            this.dest = dest;
            this.w = weight;
        }
    }
}
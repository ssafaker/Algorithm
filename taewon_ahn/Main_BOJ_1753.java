import java.io.*;
import java.util.*;

public class Main_BOJ_1753 {

    static class Node implements Comparable<Node> {

        int v, w;

        Node(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Node o) {
            return this.w - o.w;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        int Start = Integer.parseInt(br.readLine());

        List<Node>[] A = new ArrayList[V + 1];
        for (int i = 1; i <= V; i++) {
            A[i] = new ArrayList<>();
        }
        int[] Dist = new int[V + 1];
        Arrays.fill(Dist, Integer.MAX_VALUE);
        Dist[Start] = 0;

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            A[u].add(new Node(v, w));
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(Start, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (cur.w > Dist[cur.v]) {
                continue;
            }

            for (Node next : A[cur.v]) {
                int curDist = Dist[cur.v] + next.w;
                if (curDist < Dist[next.v]) {
                    Dist[next.v] = curDist;
                    // 최단경로순 우선순위를 위해 누적 거리를 w로 설정해 주는게 중요!
                    pq.offer(new Node(next.v, curDist));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < Dist.length; i++) {
            sb.append(Dist[i] == Integer.MAX_VALUE ? "INF" : Dist[i]);
            sb.append("\n");
        }
        System.out.println(sb);
    }
}


import java.io.*;
import java.util.*;

public class Main_BOJ_1197 {

    static int[] parent;

    static class Edge {

        int from, to, weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            edges.add(new Edge(u, v, w));
        }

        Collections.sort(edges, (e1, e2) -> e1.weight - e2.weight);

        parent = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            parent[i] = i;
        }

        int mstWeights = 0;
        int edgeCnt = 0;

        for (Edge e : edges) {
            if (find(e.from) != find(e.to)) {
                union(e.from, e.to);
                mstWeights += e.weight;
                edgeCnt++;

                if (edgeCnt == V - 1) {
                    break;
                };
            }
        }

        System.out.println(mstWeights);
    }

    static int find(int i) {
        if (parent[i] == i) {
            return i;
        } else {
            return parent[i] = find(parent[i]);
        }
    }

    static void union(int a, int b) {
        parent[find(b)] = find(a);
    }
}


import java.io.*;
import java.util.*;

public class Main_BOJ_1167 {
    static class Node {
        int to, weight;

        public Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static List<Node>[] tree;
    static boolean[] visited;
    static int maxDist = 0;
    static int maxNode = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        tree = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
        }

        // 트리 등록
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            while (true) {
                int to = Integer.parseInt(st.nextToken());
                if (to == -1)
                    break;
                int weight = Integer.parseInt(st.nextToken());
                tree[from].add(new Node(to, weight));
            }
        }

        // 1번 노드에서 가장 먼거리
        visited = new boolean[n + 1];
        dfs(1, 0);

        // 찾은 노드에서 가장 먼거리
        visited = new boolean[n + 1];
        dfs(maxNode, 0);

        System.out.println(maxDist);
    }

    private static void dfs(int node, int dist) {
        visited[node] = true;

        if (dist > maxDist) {
            maxDist = dist;
            maxNode = node;
        }

        for (Node next : tree[node]) {
            if (!visited[next.to]) {
                dfs(next.to, dist + next.weight);
            }
        }
    }
}

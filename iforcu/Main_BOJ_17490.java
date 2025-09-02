package iforcu;

import java.io.*;
import java.util.*;

/**
 * 문제: [일감호에 다리 놓기]
 * 난이도: [Gold 3]
 * 링크: [https://www.acmicpc.net/problem/17490]
 * 설명:
 *  - N개의 섬이 원형으로 다리로 연결되어 있다.
 *  - 각 섬에는 돌이 놓여 있고, 이 돌을 사용해 섬을 연결할 수 있다.
 *  - 일부 다리(M개)는 제거될 수 있다.
 *  - 모든 섬을 연결하는 최소 비용이 K 이하인지 판별하는 문제.
 *
 * 풀이:
 *  - 그래프를 구성:
 *    · 정점 0은 가상의 노드로, 각 섬(i)과 (돌 비용) 간선을 연결.
 *    · 섬들은 원형 구조이므로 i ↔ i+1 (마지막은 1과 연결)로 0비용 간선을 추가.
 *  - 다리 M개가 제거되면 해당 간선을 그래프에서 제거.
 *  - 이후 MST(최소 스패닝 트리)를 Prim 알고리즘으로 계산.
 *    → MST의 총 비용이 모든 섬을 연결하는 최소 비용.
 *  - 결과 판정:
 *    · M ≤ 1이면 항상 "YES" (원형 구조 유지 or 직선 구조 → 연결 가능).
 *    · M ≥ 2이면 원형이 끊겨 여러 구간이 생길 수 있으므로 Prim 결과로 판별.
 *    · MST 비용이 K 이하이면 "YES", 아니면 "NO".
 */

public class Main_BOJ_17490 {
    static int N, M;
    static Long K;
    static List<Edge>[] graph;

    static class Edge implements Comparable<Edge> {
        int w;
        long cost;
        public Edge(int w, long cost) {
            this.w = w;
            this.cost = cost;
        }
        
        public int compareTo(Edge o) {
            return Long.compare(this.cost, o.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Long.parseLong(st.nextToken());

        graph = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        // 섬의 돌 비용 입력 및 그래프 연결
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            int stone = Integer.parseInt(st.nextToken());
            graph[i].add(new Edge(0, stone));
            graph[0].add(new Edge(i, stone));

            int next = (i == N) ? 1 : i + 1;
            graph[i].add(new Edge(next, 0));
            graph[next].add(new Edge(i, 0));
        }

        // 제거된 다리 반영
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int u = Integer.parseInt(st.nextToken());
            graph[v].removeIf(edge -> edge.w == u);
            graph[u].removeIf(edge -> edge.w == v);
        }

        long least = prim(0);

        if (M <= 1) System.out.println("YES");
        else System.out.println(least <= K ? "YES" : "NO");
    }

    // Prim 알고리즘으로 MST 비용 계산
    static long prim(int start) {
        boolean[] visited = new boolean[N + 1];

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(start, 0));

        long total = 0;
        int count = 0;
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int v = edge.w;
            long cost = edge.cost;

            if (visited[v]) continue;
            visited[v] = true;
            total += cost;
            count++;

            for (Edge e : graph[v]) {
                if (!visited[e.w]) {
                    pq.offer(e);
                }
            }
        }
        if (count < N + 1) return Long.MAX_VALUE;

        return total;
    }
}

package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : [정점들의 거리]
 *  링크   : [https://www.acmicpc.net/problem/1761]
 *  난이도 : [플래티넘 5]
 *  설명   :
 *    - N개의 정점으로 이루어진 트리에서 M개의 두 노드 쌍 사이의 거리를 구하는 문제.
 *    - N은 최대 40,000, M은 최대 10,000, 간선의 가중치는 10,000 이하.
 *  풀이   :
 *    - LCA(Lowest Common Ancestor)를 Binary Lifting으로 구현하여 빠르게 계산.
 *    - 루트에서 각 노드까지의 거리를 미리 계산하고, 두 노드 간 거리는 dist[u] + dist[v] - 2*dist[lca]로 구함.
 */

public class Main_BOJ_1761 {
    static class Node {
        int to;
        int weight;

        Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static int N, M;
    static List<Node>[] tree;
    static int[] depth;
    static long[] dist;
    static int[][] parent;
    static int MAX_K;

    public static void main(String[] args) throws IOException {
        // 입력 처리 및 변수 초기화
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        // Binary Lifting을 위한 최대 K값 계산
        MAX_K = (int) Math.ceil(Math.log(N) / Math.log(2));

        // 트리 및 배열 초기화
        tree = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }
        depth = new int[N + 1];
        dist = new long[N + 1];
        parent = new int[N + 1][MAX_K];

        // 트리 간선 정보 입력
        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            tree[u].add(new Node(v, w));
            tree[v].add(new Node(u, w));
        }

        // DFS로 깊이와 거리 계산
        dfs(1, 0, 0, 0);
        // Binary Lifting을 위한 부모 테이블 채우기
        fillParent();

        StringBuilder sb = new StringBuilder();
        M = Integer.parseInt(br.readLine());

        // M개의 쿼리 처리
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            // LCA를 구하고 두 노드 간 거리 계산
            int lcaNode = lca(u, v);
            long result = dist[u] + dist[v] - 2 * dist[lcaNode];
            sb.append(result).append("\n");
        }

        System.out.print(sb);
    }

    // DFS로 각 노드의 깊이, 루트에서의 거리, 부모 노드 설정
    static void dfs(int current, int p, int d, long d_sum) {
        depth[current] = d;
        dist[current] = d_sum;
        parent[current][0] = p;

        for (Node next : tree[current]) {
            if (next.to != p) {
                dfs(next.to, current, d + 1, d_sum + next.weight);
            }
        }
    }

    // Binary Lifting을 위해 2^k 번째 부모 정보 채우기
    static void fillParent() {
        for (int k = 1; k < MAX_K; k++) {
            for (int i = 1; i <= N; i++) {
                parent[i][k] = parent[parent[i][k - 1]][k - 1];
            }
        }
    }

    // LCA(Lowest Common Ancestor)를 Binary Lifting으로 구하는 함수
    static int lca(int u, int v) {
        // u가 더 깊은 노드가 되도록 swap
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        // u를 v와 같은 깊이까지 올리기
        for (int k = MAX_K - 1; k >= 0; k--) {
            if (depth[u] - depth[v] >= (1 << k)) {
                u = parent[u][k];
            }
        }

        // 같은 노드면 바로 반환
        if (u == v) {
            return u;
        }

        // 공통 조상을 찾기 위해 동시에 올라가기
        for (int k = MAX_K - 1; k >= 0; k--) {
            if (parent[u][k] != parent[v][k]) {
                u = parent[u][k];
                v = parent[v][k];
            }
        }

        return parent[u][0];
    }
}

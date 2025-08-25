package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [하나로]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV15StKqAQkCFAYD]
 *  난이도 : [D4]
 *  설명:
 *  - 여러 섬을 해저터널로 연결할 때, 환경 부담금을 최소화하는 문제.
 *  - 두 섬 사이의 연결 비용은 거리^2 * 환경세율(E).
 *  - 값이 int를 벗어나기 때문에 weight 및 정답은 long으로 선언
 *  풀이:
 *  - Kruskal 알고리즘으로 MST(최소 신장 트리)를 구성.
 *  - Edge 리스트를 구성해 거리^2 기준으로 정렬 후, Union-Find로 사이클 방지하며 선택.
 */

public class Main_SEA_1251 {
    static int N;
    static double E;
    static long[] xCoords, yCoords;

    // 간선 클래스 (두 섬과 거리^2 가중치)
    static class Edge implements Comparable<Edge> {
        Integer u, v;
        long weight;

        public Edge(Integer u, Integer v, long weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        public int compareTo(Edge other) {
            return Long.compare(this.weight, other.weight);
        }
    }

    // Union-Find (Disjoint Set)
    static class UnionFind {
        int[] parent, rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i; 
        }

        public int find(int x) {
            if (parent[x] == x) return x;
            return parent[x] = find(parent[x]); // 경로 압축
        }

        public boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB) return false;

            if (rank[rootA] < rank[rootB]) {
                parent[rootA] = rootB;
            } else if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine()); // 테스트 케이스 개수

        for (int test_case = 1; test_case <= T; test_case++) {
            N = Integer.parseInt(br.readLine()); // 섬 개수

            xCoords = new long[N];
            yCoords = new long[N];

            // 섬들의 좌표 입력
            StringTokenizer stX = new StringTokenizer(br.readLine());
            StringTokenizer stY = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                xCoords[i] = Integer.parseInt(stX.nextToken());
                yCoords[i] = Integer.parseInt(stY.nextToken());
            }
            E = Double.parseDouble(br.readLine().trim()); // 환경 부담 세율

            // 모든 간선(섬 간 거리^2) 생성
            List<Edge> edges = new ArrayList<>(N * (N - 1) / 2);
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    long dx = xCoords[i] - xCoords[j];
                    long dy = yCoords[i] - yCoords[j];
                    long weight = dx * dx + dy * dy;
                    edges.add(new Edge(i, j, weight));
                }
            }

            // Kruskal을 위해 간선 정렬
            Collections.sort(edges);

            UnionFind uf = new UnionFind(N);
            long totalCost = 0;
            int picked = 0;

            // MST 구성
            for (Edge edge : edges) {
                if (uf.union(edge.u, edge.v)) {
                    totalCost += edge.weight;
                    if (++picked == N - 1) break;
                }
            }

            // 환경 부담금 계산 (반올림)
            long ans = Math.round(E * (double) totalCost);
            System.out.println("#" + test_case + " " + ans);
        }
    }
}

package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [행성 터널]
 *  링크 : [https://www.acmicpc.net/problem/2887]
 *  난이도 : [플래티넘 5]
 *  설명: 
 *   - N개의 행성을 모두 연결하는 터널의 최소 비용을 구하는 문제.
 *   - 터널 비용은 두 행성 간 x, y, z 좌표 차이의 최솟값.
 *   - 모든 행성 간 직접 연결할 필요 없이 간접적으로 연결되면 됨.
 *  풀이:
 *   - 모든 간선을 고려하면 O(N²)로 메모리 초과 발생.
 *   - 각 좌표축별로 정렬하여 인접한 행성들만 간선으로 추가.
 *   - 크루스칼 알고리즘으로 최소 스패닝 트리 구성.
 */

public class Main_BOJ_2887 {
    static int N;
    static List<int[]> planets;
    static int[] parent, rank;
    static int[] posX, posY, posZ;

    // 간선 정보를 담는 클래스
    static class Edge implements Comparable<Edge> {
        int v, u, w;
        public Edge(int v, int u, int w) {
            this.v = v;
            this.u = u;
            this.w = w;
        }

        public int compareTo(Edge o) {
            return this.w - o.w;
        }

        public String toString() {
            return this.v +" "+this.u+" "+this.w;
        }
    }

    // Union-Find의 Find 연산
    static int find(int x) {
        if(parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    // Union-Find의 Union 연산 (rank 기반 최적화)
    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if(rootA == rootB) return;
        if(rank[rootA] < rank[rootB]) parent[rootA] = rootB; 
        else if(rank[rootA] > rank[rootB]) parent[rootB] = rootA;
        else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }
    }
    
    static boolean isConnected(int a, int b) {
        return find(a) == find(b);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        planets = new ArrayList<>();      parent = new int[N+1];
        rank = new int[N+1];
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < N; i++) parent[i] = i;
        for (int i = 1; i <= N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            planets.add(new int[]{a, b, c, i}); // x, y, z, 행성번호
        } 

        // x좌표 기준 정렬 후 인접한 행성들만 간선 추가
        planets.sort((p1, p2) -> Integer.compare(p1[0], p2[0]));
        for (int i = 0; i < N-1; i++) {
            int[] p1 = planets.get(i);
            int[] p2 = planets.get(i+1);
            int cost = Math.abs(p1[0] - p2[0]);
            edges.add(new Edge(p1[3], p2[3], cost));
        }

        // y좌표 기준 정렬 후 인접한 행성들만 간선 추가
        planets.sort((p1, p2) -> Integer.compare(p1[1], p2[1]));
        for (int i = 0; i < N-1; i++) {
            int[] p1 = planets.get(i);
            int[] p2 = planets.get(i+1);
            int cost = Math.abs(p1[1] - p2[1]);
            edges.add(new Edge(p1[3], p2[3], cost));
        }

        // z좌표 기준 정렬 후 인접한 행성들만 간선 추가
        planets.sort((p1, p2) -> Integer.compare(p1[2], p2[2]));
        for (int i = 0; i < N-1; i++) {
            int[] p1 = planets.get(i);
            int[] p2 = planets.get(i+1);
            int cost = Math.abs(p1[2] - p2[2]);
            edges.add(new Edge(p1[3], p2[3], cost));
        }


        Collections.sort(edges);

        // 크루스칼 알고리즘으로 MST 구성
        int ans = 0;
        for (Edge edge : edges) {
            if(!isConnected(edge.v, edge.u)) {
                union(edge.v, edge.u);
                ans += edge.w;
            }
        }
        System.out.println(ans);
    }
}
